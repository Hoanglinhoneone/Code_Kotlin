plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jsoup:jsoup:1.16.1")
    implementation("org.seleniumhq.selenium:selenium-java:4.25.0")
//    implementation ("org.seleniumhq.selenium:selenium-devtools-v86:4.20.0")
    // https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-devtools-v96
//    implementation("org.seleniumhq.selenium:selenium-devtools-v86:4.20.0")
    implementation("org.seleniumhq.selenium:selenium-devtools-v86:4.0.0-beta-2")
    implementation("org.json:json:20240303")
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.11.0")



}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}