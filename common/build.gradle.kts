plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.bundles.kotlinx.serialization)
    implementation(libs.netty.buffer)
    implementation(libs.kyori.adventure.nbt)

    testImplementation(kotlin("test"))
    testImplementation(project(":serialization"))
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}