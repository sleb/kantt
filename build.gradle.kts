import org.gradle.api.tasks.testing.logging.TestLogEvent.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    application
}

repositories {
    jcenter()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.ajalt:clikt:${Versions.clikt}")
    implementation("com.squareup.moshi:moshi:${Versions.moshi}")
    implementation("org.koin:koin-core:${Versions.koin}")

    kapt("com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:${Versions.kotest}")
    testImplementation("io.kotest:kotest-assertions-core-jvm:${Versions.kotest}")
    testImplementation("com.google.jimfs:jimfs:${Versions.jimfs}")

    testRuntimeOnly("io.kotest:kotest-runner-console-jvm:${Versions.kotest}")
}

application {
    mainClassName = "io.kantt.AppKt"
}

tasks {
    test {
        testLogging {
            events(FAILED, PASSED, SKIPPED)
        }
    }
}
