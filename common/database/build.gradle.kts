plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.database"
}
dependencies {
    di()
    general()
}