# AGENTS.md

## Scope and intent
- This repository is a **single-module Android app** (`:app`) using **Jetpack Compose + Material 3**.
- Treat this as a starter-style codebase: keep changes minimal, direct, and easy to run in Android Studio.
- There are currently no additional service/data modules; avoid introducing architecture layers unless requested.

## Big-picture architecture
- Entry point is `app/src/main/java/com/example/unseenandstrong/MainActivity.kt`.
- Runtime UI flow is: `MainActivity.onCreate()` -> `setContent { UnseenAndStrongTheme { Scaffold { Greeting(...) }}}`.
- Theme composition is centralized in `app/src/main/java/com/example/unseenandstrong/ui/theme/Theme.kt`.
- Color tokens live in `app/src/main/java/com/example/unseenandstrong/ui/theme/Color.kt` and typography in `app/src/main/java/com/example/unseenandstrong/ui/theme/Type.kt`.
- XML theme bridge is `app/src/main/res/values/themes.xml` (`Theme.UnseenAndStrong`) and is referenced by `AndroidManifest.xml`.

## Build and test workflows
- Use the Gradle wrapper from repo root (`./gradlew`), not a global Gradle install.
- Core local build: `./gradlew :app:assembleDebug`.
- Local JVM unit tests: `./gradlew :app:testDebugUnitTest`.
- Instrumentation tests (device/emulator required): `./gradlew :app:connectedDebugAndroidTest`.
- Lint check: `./gradlew :app:lintDebug`.
- `:app:testDebugUnitTest` was run successfully during analysis (BUILD SUCCESSFUL).

## Project-specific conventions
- Dependencies and plugin versions are managed in `gradle/libs.versions.toml`; prefer updating catalog entries instead of hardcoding versions.
- Gradle scripts use Kotlin DSL (`build.gradle.kts`), with project repos locked via `RepositoriesMode.FAIL_ON_PROJECT_REPOS` in `settings.gradle.kts`.
- Android config specifics in `app/build.gradle.kts`:
  - `namespace` and `applicationId` are both `com.example.unseenandstrong`.
  - `compileSdk` uses `release(36) { minorApiLevel = 1 }`.
  - `minSdk = 29`, `targetSdk = 36`, Java compatibility is 11.
- UI code follows Compose patterns with preview functions (`@Preview`) kept near composables (`GreetingPreview`).

## Integration points and boundaries
- App entry/activity declaration and app-level metadata are in `app/src/main/AndroidManifest.xml`.
- Backup/data-transfer hooks are enabled in manifest and point to:
  - `app/src/main/res/xml/backup_rules.xml`
  - `app/src/main/res/xml/data_extraction_rules.xml`
  These files are mostly template defaults; preserve references unless intentionally changing backup behavior.
- Release minification is currently disabled (`isMinifyEnabled = false`); `app/proguard-rules.pro` is mostly placeholder comments.

## Existing AI guidance discovery
- Searched for `.github/copilot-instructions.md`, `AGENT*.md`, `CLAUDE.md`, cursor/windsurf/cline rule files, and `README.md`.
- No matching files were found, so this file is the current canonical AI-agent guidance.

## Unseen & Strong: AI Assistant Guidelines

1. Project Context: It is a gentle, low-pressure digital companion designed for people navigating chronic, invisible illnesses.
2. Strictly Offline-First MVP: For Phase 1, we are using local device storage ONLY (e.g., Room/SQLite). Do NOT suggest or write code for backend servers, Firebase, AWS, or cloud syncing. Medical data privacy is paramount; everything stays on the phone.
3. Zero-Guilt Architecture: Do not write any code for "streaks," gamification, or punitive alerts for missed days. The app celebrates effort, not completion.
4. Protect Against Feature Bloat: Our Phase 1 MVP is strictly: A daily check-in, a digital comfort box, an offline journal, and a Flare Day UI toggle. If I ask for a complex feature (like a community forum or e-commerce shop), gently remind me that it belongs in Phase 2 or Phase 4.
5. Strict UI / Accessibility Rules (Low Sensory Overload): Users of this app experience migraines and brain fog. You must strictly avoid high-contrast stark whites or deep blacks. Use the provided color palette for all UI elements:
   * Primary UI: Soft Blush Pink (#F6C1D1) and Lavender Purple (#B9A6FF).
   * Backgrounds/Cards: Soft Cloud Grey (#E6E6EA).
   * Text/Icons: Deep Fog Grey (#6E6E78).
6. Flare Day Mode Logic: When writing UI code, always account for how the screen might simplify or dim (using Night Lavender #4B3F72) when this toggle is active.
