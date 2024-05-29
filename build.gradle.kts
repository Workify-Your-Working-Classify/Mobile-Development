@file:Suppress("DEPRECATION")

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
