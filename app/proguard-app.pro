#data目录下的类保持
-keep class app.lonzh.lisper.data.** { *; }
-dontwarn app.lonzh.lisper.data.**

#LiveDataBus
-dontwarn com.jeremyliao.liveeventbus.**
-keep class com.jeremyliao.liveeventbus.** { *; }
-keep class androidx.lifecycle.** { *; }
-keep class androidx.arch.core.** { *; }

#agentWeb
-keep class com.just.agentweb.** {
    *;
}
-dontwarn com.just.agentweb.**

# 微信不混淆
-dontwarn com.tencent.**
-keep class com.tencent.** { *; }