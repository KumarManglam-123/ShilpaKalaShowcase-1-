# Shilpa-Kala Showcase 🏛️

A premium, production-grade Android application for traditional Indian stone and wood artisans (Shilpis) to showcase their craft, manage portfolios, and connect with global buyers.

> *"Preserving heritage through digital craftsmanship"*

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🎨 **Artisan Onboarding** | Multi-step profile wizard with image upload and real-time validation |
| 🖼️ **Artwork Portfolio** | Masonry grid gallery supporting up to 30 high-res images per artwork |
| 🔍 **Pinch-to-Zoom Viewer** | Full-screen image viewer with pan, zoom, and double-tap gestures |
| 📐 **WIP Timeline** | Visual work-in-progress tracker across 5 artisan stages |
| 📜 **Heritage Stories** | Bilingual (English + Kannada) cultural narratives with GenAI readiness |
| 💬 **WhatsApp Inquiry** | One-tap buyer inquiry with SMS, email, and share-sheet fallbacks |
| 🔎 **Full-Text Search** | FTS4-powered instant search with material and style filters |
| 🌙 **Dark Mode** | Full Material 3 dark/light theming with gold accent palette |
| 📴 **Offline-First** | Complete offline browsing with Room + DataStore persistence |
| 🔐 **Encrypted Storage** | SQLCipher-encrypted database for artisan data protection |
| ⚙️ **Settings** | Language toggle, font scaling, theme switch, and cache management |

---

## 🏗️ Architecture

```
MVVM + Clean Architecture + Repository Pattern
```

```
┌─────────────────────────────────────────────────┐
│                   UI Layer                      │
│  Jetpack Compose + Material 3 + Navigation      │
├─────────────────────────────────────────────────┤
│                Domain Layer                     │
│  Use Cases + Models + Repository Interfaces     │
├─────────────────────────────────────────────────┤
│                 Data Layer                      │
│  Room + DataStore + Retrofit + Workers          │
├─────────────────────────────────────────────────┤
│                  DI Layer                       │
│  Hilt Modules (App, DB, Network, Repo, Worker)  │
└─────────────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
app/src/main/java/com/shilpakala/showcase/
├── core/                   # Theme, Constants, Utils, Extensions, UI Components
│   ├── constants/          # AppConstants
│   ├── extensions/         # Context, String, Modifier extensions
│   ├── network/            # ConnectivityObserver
│   ├── resource/           # Resource sealed class
│   ├── theme/              # Color, Type, Shape, Theme (M3)
│   ├── ui/                 # GoldButton, Shimmer, StateComponents, OfflineBanner
│   └── utils/              # IdGenerator, ImageCompressor, DateTimeUtils
├── data/
│   ├── local/
│   │   ├── db/             # Room Database, DAOs, Entities, Converters
│   │   └── datastore/      # PreferencesManager
│   ├── mapper/             # Entity <-> Domain mappers
│   ├── remote/             # Gemini API service + DTOs
│   ├── repository/         # Repository implementations
│   └── worker/             # SyncWorker, CacheCleanupWorker
├── di/                     # Hilt modules
├── domain/
│   ├── model/              # Shilpi, Artwork, Heritage, WipStage, etc.
│   ├── repository/         # Repository interfaces
│   └── usecase/            # Business logic use cases
├── feature/
│   ├── splash/             # Splash screen
│   ├── onboarding/         # 3-page onboarding carousel
│   ├── profile/            # 4-step profile wizard
│   ├── home/               # Dashboard with featured artisan
│   ├── artwork/            # List, Detail, Add, Image Viewer
│   ├── wip/                # Work-in-progress timeline
│   ├── heritage/           # Heritage list + detail
│   ├── search/             # FTS-powered search
│   ├── inquiry/            # WhatsApp inquiry helper
│   └── settings/           # App preferences
├── navigation/             # NavGraph + Screen routes
├── MainActivity.kt
└── ShilpaKalaApp.kt        # Hilt Application class
```

---

## 🛠️ Tech Stack

| Category | Technology |
|----------|-----------|
| **Language** | Kotlin 2.1 |
| **UI** | Jetpack Compose + Material 3 |
| **Architecture** | MVVM + Clean Architecture |
| **DI** | Hilt (Dagger) |
| **Database** | Room + SQLCipher (encrypted) |
| **Search** | Room FTS4 |
| **Preferences** | DataStore |
| **Networking** | Retrofit + OkHttp |
| **Image Loading** | Coil |
| **Background** | WorkManager |
| **Navigation** | Navigation Compose (animated) |
| **Build** | Gradle 9.0 + AGP 8.9.1 + KSP |
| **Testing** | JUnit + MockK + Turbine + Truth |
| **Min SDK** | 26 (Android 8.0) |
| **Target SDK** | 35 (Android 15) |

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Meerkat (2024.3+) or newer
- **JDK 17** (configured automatically via `gradle.properties`)
- **Android SDK 35**

### Build & Run

```bash
# Clone the repository
git clone https://github.com/KumarManglam-123/ShilpaKalaShowcase-1-.git
cd ShilpaKalaShowcase-1-

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

### Configuration (Optional)

Add to `local.properties` for Gemini AI heritage narratives:
```properties
GEMINI_API_KEY=your_api_key_here
```

---

## 🎨 Design System

- **Primary Palette:** Charcoal `#1A1A1A` + Heritage Gold `#D4A853`
- **Typography:** System default with configurable font scaling
- **Dark Mode:** Full M3 dark theme with surface tonal elevation
- **Animations:** Slide + fade transitions (300ms), shimmer loading states
- **ID Format:** Artisans `SK-KA-0001` → Products `SKP-KA-0001-001`

---

## 🧪 Testing

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests
./gradlew connectedDebugAndroidTest
```

Test coverage includes:
- `IdGeneratorTest` — ID format and atomic increment validation
- `StringExtensionsTest` — Sanitization, name validation, ellipsize
- `CreateShilpiUseCaseTest` — Artisan creation with validation
- `AddArtworkUseCaseTest` — Artwork validation (title, images, limits)
- `HomeViewModelTest` — ViewModel state observation with Turbine

---

## 📱 Screens

| Screen | Description |
|--------|-------------|
| **Splash** | Animated brand reveal → auto-navigate |
| **Onboarding** | 3-page carousel + language selection |
| **Profile Wizard** | Name → Location → Specialisation → Photo |
| **Home** | Featured artisan, quick actions, recent artworks |
| **Artwork List** | Masonry grid with status badges |
| **Artwork Detail** | Gallery, dimensions, WhatsApp inquiry, WIP link |
| **Image Viewer** | Pinch-to-zoom, double-tap, pan gestures |
| **WIP Timeline** | 5-stage visual progress tracker |
| **Heritage** | Bilingual cultural narratives |
| **Search** | Debounced FTS search + material filters |
| **Settings** | Language, theme, font scale, cache clear |

---

## 📄 License

This project is for educational and portfolio demonstration purposes.

---

## 👤 Author

**Kumar Manglam**

---

*Built with ❤️ for the artisans of India*
