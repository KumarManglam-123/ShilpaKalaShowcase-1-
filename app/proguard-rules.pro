# Shilpa-Kala Showcase ProGuard Rules

# Keep Room entities
-keep class com.shilpakala.showcase.data.local.entity.** { *; }

# Keep Retrofit API interfaces
-keep interface com.shilpakala.showcase.data.remote.api.** { *; }

# Keep DTOs
-keep class com.shilpakala.showcase.data.remote.dto.** { *; }

# Keep domain models
-keep class com.shilpakala.showcase.domain.model.** { *; }

# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class * extends com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# SQLCipher
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }

# Coil
-keep class coil.** { *; }

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**
