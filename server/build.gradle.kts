plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    `maven-publish`
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

    implementation(libs.bundles.netty)
    api(libs.netty.transport)
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = "leaflow-server"
            version = project.version.toString()

            from(components["java"])
        }
    }
}
