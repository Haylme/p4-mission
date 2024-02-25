plugins {
  id("com.android.application")
  id("org.jetbrains.kotlin.android")


  kotlin("plugin.serialization") version "1.6.10"

}

android {
  namespace = "com.aura"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.aura"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {




  implementation ("ch.qos.logback:logback-classic:1.2.3")

  implementation ("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")


  implementation ("androidx.fragment:fragment-ktx:1.6.2")



implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")




  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")


  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")


  implementation ("com.squareup.retrofit2:retrofit:2.7.2")
  implementation ("com.squareup.retrofit2:converter-gson:2.5.0")


    implementation ("com.google.code.gson:gson:2.10.1")


  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.11.0")
  implementation("androidx.annotation:annotation:1.7.1")
  implementation("androidx.constraintlayout:constraintlayout:2.1.4")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}