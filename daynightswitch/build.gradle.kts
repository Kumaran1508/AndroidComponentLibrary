plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.teknophase.testapp"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        version = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    buildFeatures {
        compose = true
    }
}

publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.Kumaran1508" // Replace with your package name
            artifactId = "day-night-switch"       // Replace with your library name
            version = "1.0.3" // Library version

            pom {
                name.set("DayNightSwitch Library")
                description.set("An Android library for day-night switches.")
                url.set("https://github.com/Kumaran1508/AndroidComponentLibrary") // Replace with your repo URL

                licenses {
                    license {
                        name.set("Apache License 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0")
                    }
                }

                developers {
                    developer {
                        id.set("teknophase")
                        name.set("Kumaran")
                        email.set("kumarans1508@gamil.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Kumaran1508/AndroidComponentLibrary.git")
                    developerConnection.set("scm:git:ssh://github.com/Kumaran1508/AndroidComponentLibrary.git")
                    url.set("https://github.com/Kumaran1508/AndroidComponentLibrary")
                }
            }
        }
    }
}

dependencies {
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.compose.material3:material3:1.2.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}