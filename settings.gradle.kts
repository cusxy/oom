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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "oom"
include(":app")
include(":scripts")

file("modules").listFilesOrdered { it.isDirectory }.forEach { dir ->
    include(":modules:${dir.name}")
}
