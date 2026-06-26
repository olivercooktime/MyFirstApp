package com.example.myfirstapp

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout

class GradientBackground @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var offset = 0f

    private val colors = intArrayOf(
        0xFF6200EE.toInt(),
        0xFF03DAC5.toInt(),
        0xFFFF6200.toInt(),
        0xFFE91E63.toInt(),
        0xFF6200EE.toInt()
    )

    private val animator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = 5000
        repeatCount = ValueAnimator.INFINITE
        interpolator = LinearInterpolator()
        addUpdateListener {
            offset = it.animatedFraction
            invalidate()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        animator.start()
    }

    override fun onDetachedFromWindow() {
        animator.cancel()
        super.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        val w = width.toFloat()
        val h = height.toFloat()
        val shift = offset * w * 2

        paint.shader = LinearGradient(
            -shift, -shift / 2, w + shift, h + shift / 2,
            colors, null, Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, 0f, w, h, paint)
        super.dispatchDraw(canvas)
    }
}
