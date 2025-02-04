import support.Constants

import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("org.gradle.android.cache-fix")
}

val libs = the<LibrariesForLibs>()

kotlin {
    compilerOptions {
        jvmTarget = Constants.JVM_TARGET
        languageVersion = Constants.LANGUAGE_VERSION
    }
}

android {
    namespace = "com.example.oom.${project.name.replace("-", ".")}"
    compileSdk = Constants.COMPILE_SDK

    defaultConfig {
        minSdk = Constants.MIN_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    compileOptions {
        sourceCompatibility = Constants.JAVA_VERSION
        targetCompatibility = Constants.JAVA_VERSION
    }
    testOptions.unitTests.all { test ->
        test.useJUnitPlatform()
    }
}

androidComponents.beforeVariants { variant ->
    // Disable the debug build type for libraries because we only publish release.
    if (variant.buildType == "debug") {
        variant.enable = false
    }
}

dependencies {
    api(libs.dagger)
    ksp(libs.dagger.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
