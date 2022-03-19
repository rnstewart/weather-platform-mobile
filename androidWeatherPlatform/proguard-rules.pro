-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.SerializationKt
-keep,includedescriptorclasses class com.zmosoft.weatherplatform.**$$serializer { *; } # <-- change package name to your app's
-keepclassmembers class com.zmosoft.weatherplatform.** { # <-- change package name to your app's
    *** Companion;
}
-keepclasseswithmembers class com.zmosoft.weatherplatform.** { # <-- change package name to your app's
    kotlinx.serialization.KSerializer serializer(...);
}