plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "codes.sinister"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://repo.papermc.io/repository/maven-public")
    maven("https://oss.sonatype.org/content/groups/public")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.1.5")
    implementation("com.github.Revxrsal.Lamp:common:3.1.5")
    implementation("net.kyori:adventure-api:4.18.0")
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("fr.mrmicky:fastboard:2.1.3")
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    shadowJar {
        archiveFileName.set("Luxeria-${version}.jar")
    }

    javadoc {
        options.encoding = Charsets.UTF_8.name()
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
        filesMatching("plugin.yml") {
            expand(project.properties)
        }
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}
