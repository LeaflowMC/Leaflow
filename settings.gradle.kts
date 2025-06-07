plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
    kotlin("jvm") version "2.1.20" apply false
}

rootProject.name = "leaflow"
include("common")
include("protocol")
include("server")
include("serialization")
