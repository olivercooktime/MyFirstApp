package com.example.myfirstapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.documentfile.provider.DocumentFile
import com.example.myfirstapp.databinding.ActivityFileTransferBinding
import kotlin.concurrent.thread

class FileTransferActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFileTransferBinding
    private var sourceUri: Uri? = null
    private var targetUri: Uri? = null

    private val sourcePickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        if (uri != null) {
            sourceUri = uri
            contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            val name = getFolderName(uri)
            binding.tvSourcePath.text = "源文件夹: $name"
            binding.tvSourcePath.setTextColor(0xFF333333.toInt())
            checkTransferReady()
        }
    }

    private val targetPickerLauncher = registerForActivityResult(
        ActivityResultContracts.OpenDocumentTree()
    ) { uri ->
        if (uri != null) {
            targetUri = uri
            contentResolver.takePersistableUriPermission(
                uri, Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
            )
            val name = getFolderName(uri)
            binding.tvTargetPath.text = "目标文件夹: $name"
            binding.tvTargetPath.setTextColor(0xFF333333.toInt())
            checkTransferReady()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileTransferBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSelectSource.setOnClickListener {
            sourcePickerLauncher.launch(null)
        }

        binding.btnSelectTarget.setOnClickListener {
            targetPickerLauncher.launch(null)
        }

        binding.btnTransfer.setOnClickListener {
            startTransfer()
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun checkTransferReady() {
        binding.btnTransfer.isEnabled = sourceUri != null && targetUri != null
    }

    private fun getFolderName(uri: Uri): String {
        val docId = DocumentsContract.getTreeDocumentId(uri)
        return docId.substringAfterLast(":")
    }

    private fun startTransfer() {
        val src = sourceUri ?: return
        val dst = targetUri ?: return

        binding.btnTransfer.isEnabled = false
        binding.progressBar.visibility = android.view.View.VISIBLE
        binding.tvStatus.text = "正在转移..."

        thread {
            try {
                val sourceDoc = DocumentFile.fromTreeUri(this, src) ?: return@thread
                val sourceName = getFolderName(src)

                val targetDoc = DocumentFile.fromTreeUri(this, dst) ?: return@thread
                val newFolder = targetDoc.createDirectory(sourceName) ?: return@thread

                val files = sourceDoc.listFiles()
                var moved = 0
                val total = files.size

                for (file in files) {
                    if (file.isFile) {
                        val inputStream = contentResolver.openInputStream(file.uri) ?: continue
                        val newFile = newFolder.createFile(
                            file.type ?: "application/octet-stream",
                            file.name ?: "unknown"
                        ) ?: continue

                        contentResolver.openOutputStream(newFile.uri)?.use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                        inputStream.close()

                        file.delete()
                        moved++
                        runOnUiThread {
                            binding.tvStatus.text = "已转移: $moved / $total"
                        }
                    }
                }

                runOnUiThread {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.tvStatus.text = "转移完成! 共移动 $moved 个文件"
                    Toast.makeText(this, "转移完成", Toast.LENGTH_SHORT).show()
                    binding.btnTransfer.isEnabled = true
                }
            } catch (e: Exception) {
                runOnUiThread {
                    binding.progressBar.visibility = android.view.View.GONE
                    binding.tvStatus.text = "转移失败: ${e.message}"
                    binding.btnTransfer.isEnabled = true
                }
            }
        }
    }
}
