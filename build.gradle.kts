import Versions.clikt
import Versions.koin
import Versions.moshi
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.70"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt:clikt:$clikt")
    implementation("com.squareup.moshi:moshi:$moshi")
    implementation("com.squareup.moshi:moshi-kotlin:$moshi")
    implementation("org.koin:koin-core:$koin")

    testImplementation(kotlin("kotlin-test"))
    testImplementation(kotlin("test-junit"))
}

application {
    mainClassName = "io.kantt.AppKt"
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = listOf(
            "-Xopt-in=com.github.ajalt.clikt.sources.ExperimentalValueSourceApi"
        )
    }
}
