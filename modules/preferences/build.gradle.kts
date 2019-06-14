/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

plugins {
    id("com.android.library")
    kotlin("multiplatform")
    `maven-publish`
    id("com.jfrog.bintray")
}

android {
    setDefaults()
    buildTypes.getByName("release").consumerProguardFiles("proguard-rules.pro")
}

kotlin {
    metadataPublication(project)
    androidWithPublication(project)
    iosWithPublication(project)
    sourceSets {
        getByName("commonMain").dependencies {
            api(splitties("experimental"))
            compileOnly(Libs.kotlinX.coroutines.coreCommon)
        }
        getByName("androidMain").dependencies {
            api(splitties("appctx"))
            api(splitties("mainthread"))

            api(Libs.kotlin.stdlibJdk7)
            compileOnly(Libs.kotlinX.coroutines.android)
        }
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.Experimental")
            }
        }
    }
}

afterEvaluate {
    publishing {
        setupAllPublications(project)
    }

    bintray {
        setupPublicationsUpload(project, publishing, skipMetadataPublication = true)
    }
}
