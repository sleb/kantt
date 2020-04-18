import Versions.clikt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.70"
    kotlin("plugin.serialization") version "1.3.70"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt:clikt:$clikt")

    testImplementation(kotlin("kotlin-test"))
    testImplementation(kotlin("test-junit"))
}

application {
    mainClassName = "io.kantt.AppKt"
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "11"
    }
}
