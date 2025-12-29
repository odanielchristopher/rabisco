# Rabisco - App

### Arquitetura do projeto

```
  com.rabisco/
  │
  ├── data/                    # Camada de dados (persistência e APIs)
  │   ├── local/               # Room ou DataStore
  │   └── repositories/        # Implementação das interfaces do domínio
  │
  ├── domain/                  # Regras e modelos de negócio
  │   ├── models/              # Entidades (Texto, Usuario, Prompt, etc.)
  │   ├── contracts/           # Contratos de todo o projeto
  │   └── usecases/            # Casos de uso (SalvarTexto, BuscarPrompts)
  │
  ├── ui/                      # Interface do usuário (Jetpack Compose)
  │   ├── screens/             # Telas do projeto
  │   │   ├── auth/            # Login, cadastro
  │   │   ├── home/            # Tela inicial e lista de textos
  │   │   ├── write/           # Tela de escrita (prompt ou livre)
  │   │   ├── stats/           # Tela de progresso e XP
  │   │   └── settings/        # Tema escuro, lembretes
  │   │
  │   ├── theme/               # Cores, tipografia, MaterialTheme
  │   └── components/          # Composables reutilizáveis
  │
  ├── navigation/              # Controle de rotas (Navigation Compose)
  │
  ├── core/                    # Infraestrutura e utilitários
  │   ├── di/                  # Configuração de Hilt
  │   ├── notifications/       # Lembretes diários
  │   └── utils/               # Funções auxiliares
  │
  ├── MainActivity.kt          # Arquivo de inicialização do app
  └── RabiscoApp.kt            # Classe principal com o @HiltAndroidApp
```
