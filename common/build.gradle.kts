plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `java-library`
    `maven-publish`
}

repositories {
    mavenLocal()
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "leaflow-common"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
