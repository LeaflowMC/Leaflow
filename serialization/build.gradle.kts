plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "io.github.leaflowmc"
version = "0.1.0"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))

    implementation(libs.kotlinx.serialization.core)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}