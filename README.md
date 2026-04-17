# 👁️ OpenVision - Acessibilidade Digital

O **OpenVision** é um aplicativo Android desenvolvido em Kotlin com foco em **acessibilidade digital**, oferecendo recursos que auxiliam pessoas com deficiência visual ou dificuldades de percepção de cores.

O projeto integra funcionalidades como leitura de tela, filtros para daltonismo, tradução com feedback em áudio e elementos flutuantes, criando uma experiência mais inclusiva e acessível.

---

## 🚀 Funcionalidades

### 🔊 Leitor de Tela (Text-to-Speech)

* Leitura automática de elementos da interface
* Detecção de foco e mudanças de texto
* Suporte ao idioma português (pt-BR)

### 🎨 Filtros para Daltonismo

* Protanopia (vermelho)
* Deuteranopia (verde)
* Tritanopia (azul)
* Monocromático
* Persistência de preferências com DataStore

### 🌍 Tradução com Áudio

* Entrada de texto para tradução
* Saída com leitura em voz (TTS)
* Interface simples e acessível

### 🪟 Tradução Flutuante

* Overlay sobre outros apps
* Janela arrastável
* Tradução rápida em qualquer tela

### 📱 Widget de Acessibilidade

* Atalhos rápidos:

  * Filtro de cores
  * TalkBack
  * Tradução

### ⚙️ Configurações de Acessibilidade

* Ajuste de contraste (base)
* Controle de tamanho de fonte (base)
* Integração com configurações do sistema (TalkBack)

---

## 🧠 Conceitos aplicados

* Accessibility Service (Android)
* Text-to-Speech (TTS)
* Jetpack Compose
* MVVM (Model-View-ViewModel)
* DataStore (persistência)
* Services e Overlays (WindowManager)
* Widgets Android
* StateFlow e Coroutines

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura organizada:

* **UI (Jetpack Compose)** → Telas e componentes
* **ViewModel** → Gerenciamento de estado (StateFlow)
* **Data** → Persistência com DataStore
* **Service** → Serviços em background (overlay e acessibilidade)
* **Utils** → Lógicas auxiliares (daltonismo, acessibilidade)

---

## ▶️ Como executar

1. Clone o repositório:

```bash id="clone01"
git clone git@github.com:DiogoLael/OpenVision.git
```

2. Abra no Android Studio

3. Execute em um dispositivo físico ou emulador

---

## ⚠️ Permissões necessárias

O app requer permissões especiais para funcionar corretamente:

* Acesso ao **Serviço de Acessibilidade**
* Permissão de **sobreposição (draw over apps)**
* Acesso a configurações do sistema

---

## 📌 Observações

* Algumas funcionalidades (como tradução real) podem ser expandidas com APIs externas
* O projeto foi estruturado para facilitar futuras melhorias, como aplicação real de filtros visuais

---

## 👨‍💻 Autor

Desenvolvido por **Diogo Lael**

---

## 💡 Objetivo

Este projeto tem como objetivo explorar recursos de acessibilidade no Android e demonstrar, na prática, como criar aplicações mais inclusivas utilizando Kotlin e Jetpack Compose.
