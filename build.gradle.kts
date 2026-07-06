plugins {
    id("com.android.library")
    `maven-publish`
}

// Layer-0 mirror of the TGX-Android/tdlib prebuilt (pinned commit 63524bd14b - see NOTICE). This is
// a verbatim redistribution of the prebuilt libtdjni.so (+ its libsslx.so/libcryptox.so OpenSSL deps)
// and the generated org.drinkless.tdlib Java API; it is NOT built from TDLib source here. Promoted out
// of capullo-source-telegram's script-populated :tdlib module so every consumer resolves it as one
// jitpack coordinate (com.github.capullo-tech:lib-tdlib-android:<tag>) instead of running
// setup_tdlib.sh + git-lfs on each build.
android {
    namespace = "org.drinkless.tdlib"
    compileSdk = 36
    defaultConfig {
        // 24 = TDLib prebuilt (TGX-Android) floor; the source lib + apps match it.
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }
    packaging {
        jniLibs { useLegacyPackaging = true }
    }
    // Publish the release AAR (with jniLibs + org.drinkless.tdlib classes) as a single variant.
    publishing {
        singleVariant("release")
    }
}

dependencies {
    // org.drinkless.tdlib.TdApi references androidx.annotation (@Nullable etc.).
    implementation("androidx.annotation:annotation:1.9.1")
}

// jitpack rewrites the group to com.github.capullo-tech and the version to the tag/commit; a plain
// group here mirrors how lib-media3-ffmpeg-android publishes. artifactId defaults to the project name.
group = "tech.capullo.tdlib"

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
            }
        }
    }
}
