
plugins {
    kotlin("jvm") version "1.3.21"
}

group = "tomasvolker"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.1.1")
    
    testImplementation("junit", "junit", "4.12")
}
