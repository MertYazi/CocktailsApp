buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.1")
        classpath("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:5.0.0.4638")
    }
}
plugins {
    id("com.android.application") version "8.3.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.23" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("androidx.navigation.safeargs") version "2.5.0" apply false
}