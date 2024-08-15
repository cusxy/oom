package com.example.oom.scripts

import java.io.File
import java.util.UUID
import kotlin.random.Random
import kotlin.random.nextUInt

const val MULTIPLIER = 1
const val MODULES_COUNT = 4
const val MODULES_COMPLEXITY = 2
const val MODELS_COUNT = 10 * MULTIPLIER
const val DAGGER_MODULES_COUNT = 5  * MULTIPLIER
const val DAGGER_COMPONENTS_COUNT = 2 * MULTIPLIER
const val UNIT_TESTS_COUNT = 5 * MULTIPLIER
const val ANDROID_TESTS_COUNT = 5 * MULTIPLIER
const val ANDROID_RESOURCES_STRINGS_COUNT = 10 * MULTIPLIER
const val ANDROID_RESOURCES_COLORS_COUNT = 10 * MULTIPLIER

fun main(args: Array<String>) {
    val modulesFile = File("modules")

    if (modulesFile.exists() && modulesFile.isDirectory) {
        modulesFile.deleteRecursively()
    }
    modulesFile.mkdir()

    for (i in 0 until MODULES_COUNT) {
        createModule(modulesFile, i)
    }
}

fun createModule(baseDir: File, number: Int) {
    val moduleFile = File(baseDir, "m${number}")
    moduleFile.mkdir()

    val buildFile = File(moduleFile, "build.gradle.kts")
    buildFile.createNewFile()
    buildFile.writeText(
        """
            |plugins {
            |    id("module-setup")
            |}
            |
            |android {
            |    namespace = "com.example.oom.m${number}"
            |    compileSdk = 34
            |
            |    defaultConfig {
            |        minSdk = 24
            |
            |        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            |        vectorDrawables {
            |            useSupportLibrary = true
            |        }
            |    }
            |    compileOptions {
            |        sourceCompatibility = JavaVersion.VERSION_17
            |        targetCompatibility = JavaVersion.VERSION_17
            |    }
            |    kotlinOptions {
            |        jvmTarget = "17"
            |    }
            |    testOptions.unitTests.all { test ->
            |        test.useJUnitPlatform()
            |    }
            |}
            |
            |dependencies {
            |    api(libs.dagger)
            |    ksp(libs.dagger.compiler)
            |
            |    testImplementation(libs.junit)
            |    testImplementation(libs.kotest.runner.junit5)
            |    testImplementation(libs.kotest.assertions.core)
            |    testImplementation(libs.kotest.property)
            |
            |    androidTestImplementation(libs.androidx.junit)
            |    androidTestImplementation(libs.androidx.espresso.core)
            |    androidTestImplementation(platform(libs.androidx.compose.bom))
            |    androidTestImplementation(libs.androidx.ui.test.junit4)
            |
            ${
                List(number / MODULES_COMPLEXITY) { i ->
                    """
                        |    implementation(project(":modules:m${MODULES_COMPLEXITY * i + number % MODULES_COMPLEXITY}"))
                    """.trimIndent()
                }.joinToString(separator = "\n")
            }
            |}
        """.trimMargin()
    )

    val packageName = "com.example.oom.m${number}"
    val packageFile = File(moduleFile, "src/main/kotlin/com/example/oom/m${number}")
    val packageUnitTestFile = File(moduleFile, "src/test/kotlin/com/example/oom/m${number}")
    val packageAndroidTestFile = File(moduleFile, "src/androidTest/kotlin/com/example/oom/m${number}")
    val androidResourcesFile = File(moduleFile, "src/main/res/values")
    val androidResourcesStringFile = File(androidResourcesFile, "strings.xml")
    val androidResourcesColorsFile = File(androidResourcesFile, "colors.xml")
    packageFile.mkdirs()
    packageUnitTestFile.mkdirs()
    packageAndroidTestFile.mkdirs()
    androidResourcesFile.mkdirs()

    createModels(packageFile, packageName)
    createDI(packageFile, packageName)
    createUnitTests(packageUnitTestFile, packageName)
    createAndroidTests(packageAndroidTestFile, packageName)
    createAndroidResourcesStrings(androidResourcesStringFile, number)
    createAndroidResourcesColors(androidResourcesColorsFile, number)
}

fun createModels(baseDir: File, packageName: String) {
    val modelsFile = File(baseDir, "models")
    modelsFile.mkdirs()

    for (i in 0 until MODELS_COUNT) {
        createModel(modelsFile, packageName, i)
    }
}

fun createModel(baseDir: File, packageName: String, number: Int) {
    val modelFile = File(baseDir, "Model${number}.kt")
    modelFile.createNewFile()
    modelFile.writeText(
        """
            |package ${packageName}.models
            |
            |data class Model${number}(
            |    val value0: String,
            |    val value1: String,
            |    val value2: String,
            |    val value3: String,
            |    val value4: String,
            |    val value5: String,
            |    val value6: String,
            |    val value7: String,
            |    val value8: String,
            |    val value9: String,
            |)
        """.trimMargin()
    )
}

fun createDI(baseDir: File, packageName: String) {
    val diFile = File(baseDir, "di")
    diFile.mkdirs()

    val daggerModulesFile = File(diFile, "modules")
    daggerModulesFile.mkdir()
    for (i in 0 until DAGGER_MODULES_COUNT) {
        createDaggerModule(daggerModulesFile, packageName, i)
    }

    val daggerComponentsFile = File(diFile, "components")
    daggerComponentsFile.mkdir()
    for (i in 0 until DAGGER_COMPONENTS_COUNT) {
        createDaggerComponent(daggerComponentsFile, packageName, i)
    }
}

fun createDaggerModule(baseDir: File, packageName: String, number: Int) {
    val moduleFile = File(baseDir, "Module${number}.kt")
    moduleFile.createNewFile()
    moduleFile.writeText(
        """
            |package ${packageName}.di.modules
            |
            ${
                List(MODELS_COUNT) { i ->
                    """
                        |import ${packageName}.models.Model${i}
                    """.trimIndent()
                }.joinToString(separator = "\n")
            }
            |import dagger.Module
            |import dagger.Provides
            
            |@Module
            |object Module${number} {
            |
            ${
                List(MODELS_COUNT) { i ->
                    """
                        |    @Provides
                        |    fun provideModel${i}(): Model${i} {
                        |        return Model${i}(
                        |            value0 = "${UUID.randomUUID()}",
                        |            value1 = "${UUID.randomUUID()}",
                        |            value2 = "${UUID.randomUUID()}",
                        |            value3 = "${UUID.randomUUID()}",
                        |            value4 = "${UUID.randomUUID()}",
                        |            value5 = "${UUID.randomUUID()}",
                        |            value6 = "${UUID.randomUUID()}",
                        |            value7 = "${UUID.randomUUID()}",
                        |            value8 = "${UUID.randomUUID()}",
                        |            value9 = "${UUID.randomUUID()}",
                        |        )
                        |    }
                    """.trimIndent()
                }.joinToString(separator = "\n\n")
            }
            |}
        """.trimMargin()
    )
}

fun createDaggerComponent(baseDir: File, packageName: String, number: Int) {
    val componentFile = File(baseDir, "Component${number}.kt")
    componentFile.createNewFile()
    componentFile.writeText(
        """
            |package ${packageName}.di.components
            |
            ${
                List(DAGGER_MODULES_COUNT) { i ->
                    """
                        |import ${packageName}.di.modules.Module${i}
                    """.trimIndent()
                }.joinToString(separator = "\n")
            }
            |import dagger.Component
            |
            |@Component(
            |    modules = [
            ${
                List(DAGGER_MODULES_COUNT) { i ->
                    """
                        |        Module${i}::class,
                    """.trimIndent()
                }.joinToString(separator = "\n")
            }
            |    ]
            |)
            |interface Component${number}
        """.trimMargin()
    )
}

fun createUnitTests(baseDir: File, packageName: String) {
    for (i in 0 until UNIT_TESTS_COUNT) {
        createUnitTest(baseDir, packageName, i)
    }
}

fun createUnitTest(baseDir: File, packageName: String, number: Int) {
    val modelFile = File(baseDir, "MyTest${number}.kt")
    modelFile.createNewFile()
    modelFile.writeText(
        """
            |package $packageName
            |
            |import io.kotest.core.spec.style.StringSpec
            |import io.kotest.matchers.*
            |import io.kotest.matchers.string.*
            |
            |class MyTest${number} : StringSpec({
            |    "length should return size of string" {
            |        "hello".length shouldBe 5
            |    }
            |    "startsWith should test for a prefix" {
            |        "world" should startWith("wor")
            |    }
            |})
        """.trimMargin()
    )
}

fun createAndroidTests(baseDir: File, packageName: String) {
    for (i in 0 until ANDROID_TESTS_COUNT) {
        createAndroidTest(baseDir, packageName, i)
    }
}

fun createAndroidTest(baseDir: File, packageName: String, number: Int) {
    val modelFile = File(baseDir, "MyAndroidTest${number}.kt")
    modelFile.createNewFile()
    modelFile.writeText(
        """
            |package $packageName
            |
            |import androidx.test.platform.app.InstrumentationRegistry
            |import androidx.test.ext.junit.runners.AndroidJUnit4
            |
            |import org.junit.Test
            |import org.junit.runner.RunWith
            |
            |import org.junit.Assert.*
            |
            |@RunWith(AndroidJUnit4::class)
            |class MyAndroidTest${number} {
            |    @Test
            |    fun useAppContext() {
            |        // Context of the app under test.
            |        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
            |        assertEquals("${packageName}.test", appContext.packageName)
            |    }
            |}
        """.trimMargin()
    )
}

fun createAndroidResourcesStrings(file: File, number: Int) {
    file.createNewFile()
    file.writeText(
        """
            |<?xml version="1.0" encoding="utf-8"?>
            |<resources>
            ${
                List(ANDROID_RESOURCES_STRINGS_COUNT) { i ->
                    """
                        |    <string name="string_m${number}_${i}">${UUID.randomUUID()}</string>
                    """.trimIndent()
                }.joinToString(separator = "\n")
            }
            |</resources>
        """.trimMargin()
    )
}

@OptIn(ExperimentalStdlibApi::class)
fun createAndroidResourcesColors(file: File, number: Int) {
    file.createNewFile()
    file.writeText(
        """
            |<?xml version="1.0" encoding="utf-8"?>
            |<resources>
            ${
                List(ANDROID_RESOURCES_COLORS_COUNT) { i ->
                    """
                        |    <color name="color_m${number}_${i}">#${Random.nextUInt().toHexString()}</color>
                    """.trimIndent()
                }.joinToString(separator = "\n")
            }
            |</resources>
        """.trimMargin()
    )
}
