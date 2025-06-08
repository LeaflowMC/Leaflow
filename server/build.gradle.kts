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

    implementation(libs.ktor.network)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.log4j.api)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}