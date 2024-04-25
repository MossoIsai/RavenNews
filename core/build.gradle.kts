import java.util.Properties

plugins {
    id(ModulePlugin.MODULE_NAME)
}

android {
    namespace = "com.raven.core"
    defaultConfig {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField(
            "String",
            "MY_API_KEY",
            "\"" + properties.getProperty("API_KEY_NYK_TIMES") + "\""
        )
    }
}

dependencies {
    di()
    general()
    testing()
}
