# MyFirstApp

一个 Android Kotlin 示例应用，展示动态渐变色背景效果。

## 技术栈

| 项目 | 版本 |
|------|------|
| 语言 | Kotlin |
| Gradle | 8.11.1 |
| AGP | 8.7.3 |
| Kotlin Plugin | 2.0.21 |
| 最低 SDK | API 24 (Android 7.0) |
| 目标 SDK | API 35 (Android 15) |

## 目录结构

```
MyFirstApp/
├── AGENTS.md                          # AI 开发规范（注释和修改记录要求）
├── build.gradle.kts                   # 根项目构建配置（插件版本声明）
├── settings.gradle.kts                # 项目设置（仓库源、模块包含）
├── gradle.properties                  # Gradle 属性（JVM 参数、JDK 路径）
├── gradlew.bat                        # Windows 下的 Gradle Wrapper 脚本
├── README.md                          # 项目说明文档
│
├── gradle/wrapper/
│   ├── gradle-wrapper.jar             # Gradle Wrapper 引导程序
│   └── gradle-wrapper.properties      # Gradle 版本配置
│
└── app/                               # 应用模块
    ├── build.gradle.kts               # 应用构建配置（SDK 版本、依赖）
    ├── proguard-rules.pro             # ProGuard 混淆规则
    │
    └── src/main/
        ├── AndroidManifest.xml        # 应用清单（权限、Activity 声明）
        │
        ├── java/com/example/myfirstapp/
        │   ├── MainActivity.kt        # 主界面 Activity
        │   ├── FileTransferActivity.kt # 文件转移页面 Activity
        │   └── GradientBackground.kt  # 自定义动态渐变背景 View
        │
        └── res/
            ├── layout/
            │   ├── activity_main.xml           # 主界面布局
            │   └── activity_file_transfer.xml  # 文件转移页面布局
            │
            ├── drawable/
            │   ├── gradient_bg.xml                # 渐变色背景 drawable
            │   ├── ic_launcher_background.xml     # 启动图标背景（紫色）
            │   └── ic_launcher_foreground.xml     # 启动图标前景（白色十字）
            │
            ├── mipmap-anydpi-v26/
            │   ├── ic_launcher.xml        # 自适应启动图标
            │   └── ic_launcher_round.xml  # 圆形启动图标
            │
            └── values/
                ├── strings.xml            # 字符串资源
                └── themes.xml             # 主题样式
```

## 文件说明

### 构建配置

| 文件 | 作用 |
|------|------|
| `build.gradle.kts` | 定义全局插件版本（AGP、Kotlin） |
| `settings.gradle.kts` | 配置仓库源（Google、Maven Central），声明包含的模块 |
| `gradle.properties` | JVM 内存配置、AndroidX 启用、JDK 路径指向 Android Studio JBR |
| `gradle-wrapper.properties` | 指定 Gradle 版本（8.11.1），实现免安装 Gradle |

### 源代码

| 文件 | 作用 |
|------|------|
| `MainActivity.kt` | 主界面 Activity，包含文件选择和文件转移入口 |
| `FileTransferActivity.kt` | 文件转移页面，选择源/目标文件夹并执行文件移动 |
| `GradientBackground.kt` | 自定义 FrameLayout，使用 ValueAnimator + LinearGradient 实现动态渐变色流动效果 |

### 资源文件

| 文件 | 作用 |
|------|------|
| `activity_main.xml` | 主界面布局，包含标题、文件选择按钮和文件转移按钮 |
| `activity_file_transfer.xml` | 文件转移页面布局，包含源/目标文件夹选择、转移按钮、进度显示和返回按钮 |
| `gradient_bg.xml` | 静态渐变色 drawable（备用） |
| `ic_launcher_*.xml` | 自适应图标（Android 8.0+），紫色背景 + 白色前景 |
| `strings.xml` | 字符串资源定义 |
| `themes.xml` | Material 主题，紫色配色方案 |

## 运行方式

```bash
# 构建并安装到模拟器
gradlew.bat installDebug

# 启动应用
adb shell am start -n com.example.myfirstapp/.MainActivity
```

## 应用效果

- 屏幕显示 **"你好世界"** 白色粗体文字（32sp，居中）
- 背景为**动态渐变色过渡动画**：紫色 → 青色 → 橙色 → 粉色 → 循环
- 动画周期 5 秒，无限循环

## 修改记录

### 2026-06-26 添加开发规范文档

- 新增 AGENTS.md 文件，定义 AI 辅助开发的代码注释规范和 README 修改记录要求
- 更新 README.md 目录结构，补充 FileTransferActivity、activity_file_transfer.xml、AGENTS.md
- 更新 README.md 文件说明，完善源代码和资源文件的描述

### 2026-06-26 修复文件转移页面布局问题

- 为 activity_file_transfer.xml 添加 fitsSystemWindows 属性，解决内容被 ActionBar 遮挡的问题
- 调整底部返回按钮的 marginBottom，防止被系统导航栏遮挡

### 2026-06-26 新增文件转移功能

- 新增 FileTransferActivity：选择源/目标文件夹，将源文件夹内的文件移动到目标文件夹下的同名新文件夹
- 主页新增"文件转移"按钮，点击跳转到文件转移页面
- AndroidManifest.xml 注册新 Activity，声明存储读写权限
- strings.xml 添加文件转移相关字符串资源

### 2026-06-26 修复应用闪退问题

- GradientBackground 从继承 View 改为继承 FrameLayout，解决 ClassCastException
- 使用 dispatchDraw 替代 onDraw 绘制渐变背景

### 2026-06-26 优化界面样式

- 将布局根元素改为 FrameLayout，GradientBackground 作为背景层
- 新增"打开文件管理器"按钮，使用系统文件选择器
- 文字改为"你好世界"，白色粗体 32sp
- 背景使用自定义 GradientBackground 实现动态渐变色过渡动画

### 2026-06-26 项目初始化

- 创建 Android Kotlin 项目，配置 Gradle 8.11.1、AGP 8.7.3、Kotlin 2.0.21
- 配置 Gradle 使用 Android Studio 自带 JBR（JDK 21）
- 创建 MainActivity 和基础布局
- 配置动态渐变色背景动画
