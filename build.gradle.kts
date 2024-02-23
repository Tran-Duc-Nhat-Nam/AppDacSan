buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            when (requested.name) {
                "javapoet" -> useVersion("1.13.0")
            }
        }
    }
    dependencies {
        classpath(libs.google.services)
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
