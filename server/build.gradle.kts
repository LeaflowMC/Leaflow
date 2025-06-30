plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    api(project(":protocol"))
    implementation(project(":serialization"))
    api(project(":text"))

    implementation(libs.netty.all)
    implementation(libs.kotlinx.coroutines)
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
