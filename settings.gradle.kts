import org.gradle.kotlin.dsl.support.listFilesOrdered

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("com.gradle.develocity") version "3.17.6"
}

develocity {
    buildScan {
        termsOfUseUrl.set("https://gradle.com/help/legal-terms-of-use")
        termsOfUseAgree.set("yes")

        // see https://docs.gradle.com/develocity/gradle-plugin/current/#controlling_when_build_scans_are_published
        publishing.onlyIf { true }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "oom"

includeBuild("convention-plugins")

include(":app")
include(":scripts")

file("modules").listFilesOrdered { it.isDirectory }.forEach { dir ->
    include(":modules:${dir.name}")
}
