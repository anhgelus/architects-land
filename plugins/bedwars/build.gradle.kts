plugins {
    kotlin("jvm") version "1.9.23"
}

group = "world.anhgelus.architectsland.bedwars"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

tasks.withType<ProcessResources> {
    val props = mapOf("version" to version)
    filesMatching("plugin.yml") {
        expand(props)
    }
}