object Dependencies {
    /**
     * kotlin
     */
    const val KOTLIN_CORE = "androidx.core:core-ktx:${Versions.KOTLIN}"

    /**
     * koin
     */
    const val KOIN_CORE = "io.insert-koin:koin-core:${Versions.KOIN}"
    const val KOIN_ANDROID = "io.insert-koin:koin-android:${Versions.KOIN}"

    /**
     * Compose ui
     */
    const val COMPOSE_BOM = "androidx.compose:compose-bom:${Versions.COMPOSE_BOM}"
    const val COMPOSE_LIFECYCLE_RUNTIME = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.COMPOSE_LIFECYCLE_RUNTIME}"
    const val COMPOSE_NAV_ANIMATION = "com.google.accompanist:accompanist-navigation-animation:${Versions.NAV_ANIMATION}"
    const val COMPOSE_VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.COMPOSE_VIEWMODEL}"
    const val COMPOSE_KOIN = "io.insert-koin:koin-androidx-compose:${Versions.KOIN}"
    const val COMPOSE_COIL = "io.coil-kt:coil-compose:${Versions.COMPOSE_COIL}"

    /**
     * Material 3
     */
    const val COMPOSE_MATERIAL3 = "androidx.compose.material3:material3:${Versions.COMPOSE_MATERIAL3}"
    const val COMPOSE_MATERIAL3_WINDOW = "androidx.compose.material3:material3-window-size-class:${Versions.COMPOSE_MATERIAL3}"

}