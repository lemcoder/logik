import com.strumenta.antlrkotlin.gradle.AntlrKotlinTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.antlr.kotlin)
}

val antlrDir = "antlr"
val antlrGeneratedDir = "generatedAntlr"

group = "me.user"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    listOf(
        mingwX64(),
        linuxX64()
    ).forEach {
        it.apply {
            binaries {
                executable {
                    entryPoint = "pl.lemanski.logik.main"
                }
            }
        }
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        nativeMain {
            kotlin {
                srcDir(layout.buildDirectory.dir(antlrGeneratedDir))
            }

            dependencies {
                implementation(libs.antlr.kotlin)
            }
        }
    }
}

val generateKotlinGrammarSource = tasks.register<AntlrKotlinTask>("generateKotlinGrammarSource") {
    dependsOn("cleanGenerateKotlinGrammarSource")

    // We want to process all .g4 files in the antlr directory
    source = fileTree(layout.projectDirectory.dir(antlrDir)) {
        include("**/*.g4")
    }

    // We want the generated source files to have this package name
    val pkgName = "pl.lemanski.logik.antlr"
    packageName = pkgName

    arguments = listOf("-visitor")

    val outDir = "$antlrGeneratedDir/${pkgName.replace(".", "/")}"
    outputDirectory = layout.buildDirectory.dir(outDir).get().asFile
}

tasks.withType<KotlinCompilationTask<*>> {
    dependsOn(generateKotlinGrammarSource)
}


