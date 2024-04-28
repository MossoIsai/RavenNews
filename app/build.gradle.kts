import config.ProjectConfig

plugins {
    id(AppPlugin.PLUGIN_APP)
}

android {
    namespace = ProjectConfig.appId
}

dependencies {
    di()
    general()
    testing()
    navigation()
    room()

    implementation(project(":core"))
    implementation(project(":feature:home"))
    implementation(project(":common:network"))
}
