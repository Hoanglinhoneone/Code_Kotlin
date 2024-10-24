plugins {
    kotlin("jvm") version "1.9.0"
    id("io.ktor.plugin") version "3.0.0" // Phiên bản Ktor plugin mới nhất
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
    implementation("org.apache.logging.log4j:log4j-api:2.24.1")
    implementation("org.apache.logging.log4j:log4j-core:2.24.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")

    implementation("io.ktor:ktor-client-core:3.0.0") // Thay thế bằng phiên bản mới nhất
    implementation("io.ktor:ktor-client-cio:3.0.0")  // Hoặc bạn có thể chọn các engine khác như ktor-client-okhttp, ktor-client-java, v.v.
    implementation("io.ktor:ktor-client-json:3.0.0")
    implementation("io.ktor:ktor-client-logging:3.0.0") // (Tùy chọn) Để ghi nhật ký các yêu cầu HTTP
    implementation("io.ktor:ktor-client-jackson:3.0.0")
    implementation("io.ktor:ktor-server-netty:3.0.0")  // Sử dụng Netty engine cho server
    implementation("io.ktor:ktor-server-core:3.0.0")   // Các chức năng core, bao gồm Route và routing
    implementation("io.ktor:ktor-server-content-negotiation:3.0.0") // Cho content negotiation
    implementation("io.ktor:ktor-serialization-gson:3.0.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.0.0")


    testImplementation("io.ktor:ktor-server-tests:1.3.2-1.4-M2")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-test-junit
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:2.0.21")

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