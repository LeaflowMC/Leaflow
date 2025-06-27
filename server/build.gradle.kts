plugins {
    kotlin("jvm") version "2.1.20"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    api(project(":protocol"))
    implementation(project(":serialization"))
    implementation(project(":text"))

    implementation(libs.netty.all)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.bundles.kotlinx.serialization)

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
