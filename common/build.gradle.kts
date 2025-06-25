plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.netty.buffer)
    implementation(libs.kyori.adventure.nbt)

}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
