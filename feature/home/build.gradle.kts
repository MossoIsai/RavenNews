import ext.implementation

plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.home"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    di()
    general()
    testing()
    network()
    navigation()

    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation(project(":core"))

}
