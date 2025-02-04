package support

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

object Constants {

    const val COMPILE_SDK = 35
    const val TARGET_SDK = 35
    const val MIN_SDK = 24

    val JVM_TARGET = JvmTarget.JVM_17
    val JAVA_VERSION = JavaVersion.VERSION_17

    val LANGUAGE_VERSION = KotlinVersion.DEFAULT
}