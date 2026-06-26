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
├── build.gradle.kts                  # 根项目构建配置（插件版本声明）
├── settings.gradle.kts               # 项目设置（仓库源、模块包含）
├── gradle.properties                 # Gradle 属性（JVM 参数、JDK 路径）
├── gradlew.bat                       # Windows 下的 Gradle Wrapper 脚本
│
├── gradle/wrapper/
│   ├── gradle-wrapper.jar            # Gradle Wrapper 引导程序
│   └── gradle-wrapper.properties     # Gradle 版本配置
│
└── app/                              # 应用模块
    ├── build.gradle.kts              # 应用构建配置（SDK 版本、依赖）
    ├── proguard-rules.pro            # ProGuard 混淆规则
    │
    └── src/main/
        ├── AndroidManifest.xml       # 应用清单（权限、Activity 声明）
        │
        ├── java/com/example/myfirstapp/
        │   ├── MainActivity.kt       # 主界面 Activity
        │   └── GradientBackground.kt # 自定义动态渐变背景 View
        │
        └── res/
            ├── layout/
            │   └── activity_main.xml # 主界面布局（GradientBackground + TextView）
            │
            ├── drawable/
            │   ├── gradient_bg.xml                # 渐变色背景 drawable
            │   ├── ic_launcher_background.xml     # 启动图标背景（紫色）
            │   └── ic_launcher_foreground.xml     # 启动图标前景（白色十字）
            │
            ├── mipmap-anydpi-v26/
            │   ├── ic_launcher.xml       # 自适应启动图标
            │   └── ic_launcher_round.xml # 圆形启动图标
            │
            └── values/
                ├── strings.xml      # 字符串资源
                └── themes.xml       # 主题样式
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
| `MainActivity.kt` | 应用入口 Activity，使用 ViewBinding 绑定布局 |
| `GradientBackground.kt` | 自定义 FrameLayout，使用 ValueAnimator + LinearGradient 实现动态渐变色流动效果 |

### 资源文件

| 文件 | 作用 |
|------|------|
| `activity_main.xml` | 主界面布局，根元素为 GradientBackground，包含居中的 TextView |
| `gradient_bg.xml` | 静态渐变色 drawable（备用） |
| `ic_launcher_*.xml` | 自适应图标（Android 8.0+），紫色背景 + 白色前景 |
| `strings.xml` | 字符串定义：应用名 "MyFirstApp"，显示文本 "你好世界" |
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
