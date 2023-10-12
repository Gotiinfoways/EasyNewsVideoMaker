plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.easynewsvideomaker.easynewsvideomaker"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.easynewsvideomaker.easynewsvideomaker"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    //view Binding
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.firebase:firebase-firestore-ktx:24.8.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //ssp
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    //sdp
    implementation ("com.intuit.sdp:sdp-android:1.1.0")

    //circle Imageview
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    //color picker
    implementation ("com.github.yukuku:ambilwarna:2.0.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.12.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))

    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics-ktx")
    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth-ktx")
    //firebase database
    implementation("com.google.firebase:firebase-database-ktx")
    //firebase in store
    implementation ("com.google.firebase:firebase-storage:20.2.1")
    //firebase notification
//
//    //add these libraries
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation ("com.google.firebase:firebase-messaging:23.2.1")
    //merge FFmpeg
    implementation ("com.arthenica:mobile-ffmpeg-full:4.4")
//
//    implementation ("com.github.uguraltinsoy:FCMSend:1.0.5")
//

}