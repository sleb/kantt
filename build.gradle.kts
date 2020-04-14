import Versions.zircon

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
    implementation("org.hexworks.zircon:zircon.core:$zircon")
    implementation("org.hexworks.zircon:zircon.jvm.swing:$zircon")

    testImplementation(kotlin("kotlin-test"))
    testImplementation(kotlin("test-junit"))
}

application {
    mainClassName = "io.kantt.AppKt"
}
