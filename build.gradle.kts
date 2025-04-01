allprojects {
    version = "0.0.1-SNAPSHOT"
    group = "dk.rohdef.danske_bank"

    repositories {
        mavenLocal()
        mavenCentral()
    }

    // Locks the gradle versions and stores which configurations the dependencies are for
    // This is useful for code scanners such as Trivy
    dependencyLocking {
        lockMode.set(LockMode.STRICT)
        lockAllConfigurations()
    }
}

subprojects {
}