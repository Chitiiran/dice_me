import java.io.FileInputStream // Add this import
import java.util.Properties // Add this import

plugins {
    id("com.android.application")
    id("kotlin-android")
    // The Flutter Gradle Plugin must be applied after the Android and Kotlin Gradle plugins.
    id("dev.flutter.flutter-gradle-plugin")
}

// --- START: Keystore properties loading (Kotlin DSL) ---
val keystoreProperties = Properties() // Removed java.util. because of import
val keystorePropertiesFile = rootProject.file("key.properties")
if (keystorePropertiesFile.exists()) {
    keystorePropertiesFile.inputStream().use { keystoreProperties.load(it) }
}
// --- END: Keystore properties loading (Kotlin DSL) ---


android {
    namespace = "com.example.dice_me" // Make sure this matches your applicationId
    compileSdk = flutter.compileSdkVersion
    ndkVersion = flutter.ndkVersion

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    // --- START: Signing Configs (Kotlin DSL) ---
    signingConfigs {
        create("release") {
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }
    // --- END: Signing Configs (Kotlin DSL) ---

    defaultConfig {
        // TODO: Specify your own unique Application ID (https://developer.android.com/studio/build/application-id.html).
        applicationId = "dev.ckm.dice_me" // This is where you set your unique package name
        // You can update the following values to match your application needs.
        // For more information, see: https://flutter.dev/to/review-gradle-config.
        minSdk = flutter.minSdkVersion
        targetSdk = flutter.targetSdkVersion
        versionCode = flutter.versionCode
        versionName = flutter.versionName
    }

    buildTypes {
        release {
            // Apply your new release signing config
            signingConfig = signingConfigs.getByName("release")

            // Recommended for release builds:
            isMinifyEnabled = true // Corrected syntax
            isShrinkResources = true // Corrected syntax
        }
    }
}

flutter {
    source = "../.."
}