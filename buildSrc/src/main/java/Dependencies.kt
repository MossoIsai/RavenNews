import Dependencies.appCompat
import Dependencies.coroutines
import Dependencies.espressoCore
import Dependencies.extJunit
import Dependencies.gson
import Dependencies.hilt
import Dependencies.hiltCompiler
import Dependencies.junit
import Dependencies.kotlinCore
import Dependencies.lifeCycle
import Dependencies.lifeCycleViewModel
import Dependencies.logginInterceptor
import Dependencies.material
import Dependencies.navigationFragment
import Dependencies.navigationUI
import Dependencies.okHttp
import Dependencies.retrofit
import Dependencies.retrofitConverter
import Dependencies.glide
import Dependencies.roomKtx
import Dependencies.roomCompiler
import Dependencies.roomRuntime
import Dependencies.shimmer
import Dependencies.lottie
import ext.androidTestImplementation
import ext.implementation
import ext.kapt
import ext.testImplementation
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {

    /** General **/

    const val kotlinCore = "androidx.core:core-ktx:${Versions.kotlinCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val hiltPlugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hilt}"
    const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

    /** Testing **/

    const val junit = "junit:junit:${Versions.junit}"
    const val extJunit = "androidx.ext.test.ext:junit:${Versions.extJunit}"
    const val espressoCore = "androidx.ext.test.espresso:espresso-core:${Versions.espresso}"

    /** Network **/

    val gson by lazy { "com.google.code.gson:gson:${Versions.gsonVersion}" }
    val okHttp by lazy { "com.squareup.okhttp3:okhttp:${Versions.okhttpVersion}" }
    val retrofit by lazy { "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}" }
    val retrofitConverter by lazy { "com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}" }
    val logginInterceptor by lazy { "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}" }

    /** DI **/

    val hilt by lazy { "com.google.dagger:hilt-android:${Versions.hilt}" }
    val hiltCompiler by lazy { "com.google.dagger:hilt-android-compiler:${Versions.hilt}" }

    /** Navigation **/

    val navigationUI by lazy { "androidx.navigation:navigation-ui-ktx:${Versions.navVersion}" }
    val navigationFragment by lazy { "androidx.navigation:navigation-fragment-ktx:${Versions.navVersion}" }
    val lifeCycleViewModel by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}" }
    val glide by lazy { "com.github.bumptech.glide:glide:${Versions.glide}" }

    /** room */
    val roomKtx by lazy { "androidx.room:room-ktx:${Versions.roomVersion}" }
    val roomCompiler by lazy { "androidx.room:room-compiler:${Versions.roomVersion}" }
    val roomRuntime by lazy { "androidx.room:room-runtime:${Versions.roomVersion}" }

    /** shimmer **/
    val shimmer by lazy { "com.facebook.shimmer:shimmer:${Versions.shimmerVersion}" }

    /** lottie **/
    val lottie by lazy { "com.airbnb.android:lottie:${Versions.lottieVersion}" }
}

/** Dependencies **/

fun DependencyHandler.general() {
    implementation(material)
    implementation(appCompat)
    implementation(lifeCycle)
    implementation(coroutines)
    implementation(kotlinCore)
}

fun DependencyHandler.testing() {
    testImplementation(junit)
    androidTestImplementation(extJunit)
    androidTestImplementation(espressoCore)
}

fun DependencyHandler.network() {
    implementation(gson)
    implementation(okHttp)
    implementation(retrofit)
    implementation(logginInterceptor)
    implementation(retrofitConverter)
}

fun DependencyHandler.di() {
    kapt(hiltCompiler)
    implementation(hilt)
}

fun DependencyHandler.navigation() {
    implementation(navigationUI)
    implementation(navigationFragment)
    implementation(lifeCycleViewModel)
    implementation(glide)
}

fun DependencyHandler.room() {
    implementation(roomKtx)
    kapt(roomCompiler)
    implementation(roomRuntime)
}

fun DependencyHandler.shimmer() {
    implementation(shimmer)
}

fun DependencyHandler.lottie() {
  implementation(lottie)
}
