// Local do arquivo: settings.gradle.kts (na pasta raiz do seu projeto)

pluginManagement {
    repositories {
        // Define ONDE o Gradle deve procurar os PLUGINS (ferramentas de build)
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // Define ONDE o Gradle deve procurar as DEPENDÊNCIAS (as bibliotecas do seu app)
        google()
        mavenCentral()
    }
}

// Define o nome do seu projeto e o módulo principal "app"
rootProject.name = "OpenVision" // Verifique se este é o nome do seu projeto
include(":app")
