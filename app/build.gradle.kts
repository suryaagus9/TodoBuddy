plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.todobuddy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.todobuddy"
        minSdk = 24
        targetSdk = 34
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
}

dependencies {

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0") // Instal retrofit2
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Instal retrofit2-converter //
    // OkHttp
    implementation ("com.squareup.okhttp3:okhttp:4.9.1") // Instal okhttp  (caching, logging, dll)

    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.airbnb.android:lottie:6.4.1")


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}