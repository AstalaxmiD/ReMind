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
        gradlePluginPortal() // Needed for plugins and toolchain downloads
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ReMind"
include(":app")

// Optional: Configure default toolchain repository for automatic download
gradle.settingsEvaluated {
    // This lets Gradle download missing Java toolchains automatically
    pluginManagement.repositories {
        gradlePluginPortal()
    }
}
