plugins {
    kotlin("jvm") version "1.9.21"
}

group = "fit.d6.candy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        url = uri("https://libraries.minecraft.net")
    }
}

dependencies {
    api(project(":library-interfaces"))
    api("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    api("com.mojang:brigadier:1.0.18")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}