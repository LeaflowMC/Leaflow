plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    api(project(":common"))
    api(project(":protocol"))
    implementation(project(":serialization"))
    api(project(":text"))

    implementation(libs.netty.all)
    api(libs.kotlinx.coroutines)
    implementation(libs.bundles.kotlinx.serialization)
    implementation(libs.fastutil)

    implementation(libs.log4j.api)

    testImplementation(kotlin("test"))
}

tasks.test {
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
