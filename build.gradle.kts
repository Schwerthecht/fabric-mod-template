plugins {
    `java-library`
    `maven-publish`
    signing
    id("fabric-loom").version("1.0-SNAPSHOT")
    id("maven-publish")
}

group = property("maven_group")!!
version = "${property("mod_version")}+mc${property("minecraft_version")}"

repositories {
    mavenCentral()
    maven("https://maven.quiltmc.org/repository/release")
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api_version")}")
    compileOnly("org.quiltmc:quilt-loader:${property("quilt_loader_version")}")
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }
}

java {
    withSourcesJar()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

publishing {
    repositories {
        maven {
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
