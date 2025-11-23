// Local do arquivo: build.gradle.kts (na pasta raiz do seu projeto)

// Este bloco define os plugins e suas versões para todo o projeto.
// O 'apply false' significa que o plugin é configurado aqui, mas aplicado no módulo 'app'.
plugins {
    // Plugin para aplicativos Android
    id("com.android.application") version "8.13.1" apply false

    // Plugin para a linguagem Kotlin no Android
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false

    // Plugin de serviços do Google (essencial para o Firebase)
    id("com.google.gms.google-services") version "4.4.2" apply false
}
