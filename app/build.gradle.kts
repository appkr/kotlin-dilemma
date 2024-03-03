plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("de.m3y.kformat:kformat:0.10")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("dev.appkr.kotlindilemma.AppKt")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "dev.appkr.kotlindilemma.AppKt")
    }
}

tasks.shadowJar {
    archiveBaseName.set("app")
    manifest {
        attributes("Main-Class" to "dev.appkr.kotlindilemma.AppKt")
    }
    mergeServiceFiles {
        include("META-INF/*.kotlin_module")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
