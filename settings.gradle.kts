/*
 * Copyright (c) 2023 teemEight
 * SPDX-License-Identifier: Apache-2.0
 */

rootProject.name = "trees-8"
include("lib")
include("app")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
    }

    versionCatalogs {
        create("libs") {
            version("kotlin", "1.8.20")
            plugin("kotlin-jvm", "org.jetbrains.kotlin.jvm").versionRef("kotlin")
            plugin("kotlin-serialization", "org.jetbrains.kotlin.plugin.serialization").versionRef("kotlin")
            plugin("kotlin-noarg", "org.jetbrains.kotlin.plugin.noarg").versionRef("kotlin")

            plugin("compose", "org.jetbrains.compose").version("1.4.0")

            library("kotlinx-serialization-json", "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
            library("gson", "com.google.code.gson:gson:2.10.1")

            version("exposed", "0.41.1")
            library("exposed-core", "org.jetbrains.exposed", "exposed-core").versionRef("exposed")
            library("exposed-dao", "org.jetbrains.exposed", "exposed-dao").versionRef("exposed")
            library("exposed-jdbc", "org.jetbrains.exposed", "exposed-jdbc").versionRef("exposed")

            version("neo4j-ogm", "4.0.5")
            library("neo4j-ogm-core", "org.neo4j", "neo4j-ogm-core").versionRef("neo4j-ogm")
            library("neo4j-ogm-bolt", "org.neo4j", "neo4j-ogm-bolt-driver").versionRef("neo4j-ogm")

        }
    }
}
