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
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("org.slf4j:slf4j-api:2.0.0") // Thư viện SLF4J
    implementation("ch.qos.logback:logback-classic:1.2.3") // Thư viện Logback

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