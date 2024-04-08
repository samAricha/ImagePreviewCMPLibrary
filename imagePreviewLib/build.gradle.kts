import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)

    alias(libs.plugins.nmcp)
    alias(libs.plugins.mavenPublishVanniktech)
    alias(libs.plugins.dokka)

}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "11"
            }
        }
    }
    
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "org.teka.the_library"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.teka.image_preview_cmp_library"
            packageVersion = "1.0.0"
        }
    }
}

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()

    pom {
        name.set("ImagePreviewCMPLib")
        description.set("Compose Multiplatform Image Preview Library")
        url.set("https://github.com/samAricha/ImagePreviewCMPLibrary")

        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }

        issueManagement {
            system.set("GitHub Issues")
            url.set("https://github.com/samAricha/ImagePreviewCMPLibrary/issues")
        }

        developers {
            developer {
                id.set("samAricha")
                name.set("Aricha Samson")
                email.set("samaricha001@gmail.com")
            }
        }

        scm {
            connection.set("scm:git:git://github.com:samAricha/ImagePreviewCMPLibrary.git")
            developerConnection.set("scm:git:ssh://github.com:samAricha/ImagePreviewCMPLibrary.git")
            url.set("https://github.com/samAricha/ImagePreviewCMPLibrary")
        }
    }
}

nmcp {
    publishAllPublications {
        // get from ~/.gradle/gradle.properties (HOME/.gradle/gradle.properties)
        username = System.getenv("MAVEN_CENTRAL_USERNAME")
        password = System.getenv("MAVEN_CENTRAL_PASSWORD")
        publicationType = "AUTOMATIC"
    }
}
