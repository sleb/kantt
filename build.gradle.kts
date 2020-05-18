import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt:clikt:${Versions.clikt}")
    implementation("com.squareup.moshi:moshi:${Versions.moshi}")
    //implementation("com.squareup.moshi:moshi-kotlin:${Versions.moshi}")
    implementation("org.koin:koin-core:${Versions.koin}")

    kapt("com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}")

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
