plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.android.gradlePlugin)
    implementation("org.gradle.android.cache-fix:org.gradle.android.cache-fix.gradle.plugin:3.0.1")
}
