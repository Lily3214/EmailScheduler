plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.sun.mail:javax.mail:1.6.2")
    implementation("org.apache.commons:commons-csv:1.10.0")
    implementation ("com.opencsv:opencsv:5.5")
}

tasks.test {
    useJUnitPlatform()
}
