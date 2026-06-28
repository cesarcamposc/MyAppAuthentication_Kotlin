package com.cesarcampos.myappauthentication.ui.navigation

/**
 * Clase sellada para definir las rutas de navegación.
 *
 * Objetivo para el estudiante: Aprender a usar Sealed Classes para gestionar la navegación
 * de forma segura (Type-safe), evitando errores de escritura manual en los strings de las rutas.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")
}
