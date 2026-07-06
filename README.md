# lib-tdlib-android

A Layer-0 mirror of the [TGX-Android/tdlib](https://github.com/TGX-Android/tdlib)
prebuilt - the [TDLib](https://github.com/tdlib/td) JNI native library
(`libtdjni.so` + its OpenSSL `libsslx.so`/`libcryptox.so`) and the generated
`org.drinkless.tdlib` Java API - packaged as a single Android AAR and published
via JitPack.

It exists so consumers (e.g. `capullo-source-telegram`) resolve TDLib as **one
immutable coordinate** instead of running `setup_tdlib.sh` + `git-lfs` on every
build/CI/JitPack run. The `.so` are committed directly (each ABI < 50 MB), so
there is no LFS dependency and builds are reproducible.

This repo **builds nothing from source** - it is a verbatim redistribution of
the prebuilt bytes. See [`NOTICE`](NOTICE) for provenance (pinned upstream
commit) and licenses (TDLib = Boost 1.0, OpenSSL = Apache 2.0).

## Usage

```kotlin
repositories { maven("https://jitpack.io") }

dependencies {
    // pin to a commit or tag; delivers the org.drinkless.tdlib API + libtdjni.so transitively
    implementation("com.github.capullo-tech:lib-tdlib-android:<tag>")
}
```

- `namespace` / package: `org.drinkless.tdlib`
- `minSdk` 24 (the TGX-Android prebuilt floor), `compileSdk` 36
- ABIs: `arm64-v8a`, `armeabi-v7a`, `x86`, `x86_64`

## Bumping the TDLib version

1. Re-copy `TdApi.java`/`Client.java` and `jniLibs/<abi>/*.so` from a newer
   `TGX-Android/tdlib` commit.
2. Update the pinned commit in [`NOTICE`](NOTICE).
3. Verify a consumer build packages `libtdjni.so`, then tag a new release.
