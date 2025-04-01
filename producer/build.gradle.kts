plugins {
    java
    application
    idea

    alias(libs.plugins.testLogging)
}

dependencies {
    implementation(project(":protocol"))

    runtimeOnly(libs.grpc.networkCommunication)
    implementation(libs.bundles.grpc)
    if (JavaVersion.current().isJava9Compatible) {
        compileOnly(libs.annotationsApi)
    }

    implementation(libs.bundles.logging)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.bundles.test)
}

application {
    mainClass = "dk.rohdef.danske_bank.Main"
}

tasks.getByName<Zip>("distZip") {
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}
tasks.getByName<Tar>("distTar") {
    archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}
tasks.test {
    useJUnitPlatform()
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}