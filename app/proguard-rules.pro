# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-obfuscationdictionary  xxl-rules-class.txt
-classobfuscationdictionary xxl-rules-class.txt
-packageobfuscationdictionary xxl-rules-class.txt
-keep public class com.eptonic.photocollage.model.ItemInfo
#-keep public class * extends com.eptonic.photocollage.model.ItemInfo
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties { *; }

# If you DO use SQLCipher:
-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }

# If you do NOT use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do NOT use RxJava:
-dontwarn rx.**

#-keep public class com.yd.photoeditor.model.XXXXXXXXXXXXXX
#-keep public class * extends com.yd.photoeditor.model.XXXXXXXXXXXXXX
#-keepclassmembers class com.yd.photoeditor.model.XXXXXXXXXXXXXX
-keep public class com.eptonic.photocollage.model.PhotoStickerSaveData
-keepclassmembers class com.eptonic.photocollage.model.PhotoStickerSaveData