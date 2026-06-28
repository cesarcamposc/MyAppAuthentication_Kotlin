package com.cesarcampos.myappauthentication.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cesarcampos.myappauthentication.ui.auth.AuthViewModel
import com.cesarcampos.myappauthentication.ui.auth.ForgotPasswordScreen
import com.cesarcampos.myappauthentication.ui.auth.LoginScreen
import com.cesarcampos.myappauthentication.ui.auth.RegisterScreen

/**
 * Grafo de Navegación Principal.
 *
 * Objetivo para el estudiante: Aprender a conectar todas las pantallas en un único flujo.
 * Aquí es donde se define qué pantalla se muestra al iniciar y cómo se pasa el ViewModel
 * compartido entre ellas.
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    // Compartimos el mismo ViewModel para que el estado (errores, carga) 
    // sea coherente en todo el flujo de auth.
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        // Pantalla de Login
        composable(Screen.Login.route) {
            // Limpiamos errores al entrar a una pantalla nueva
            authViewModel.clearError()
            
            LoginScreen(
                viewModel = authViewModel,
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onNavigateToForgot = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                onLoginSuccess = {
                    // Aquí iría la navegación al Home de la app
                    println("Login exitoso")
                }
            )
        }

        // Pantalla de Registro
        composable(Screen.Register.route) {
            authViewModel.clearError()
            
            RegisterScreen(
                viewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    // Después de registrar, volvemos al login o vamos al home
                    navController.popBackStack()
                }
            )
        }

        // Pantalla de Olvidé mi contraseña
        composable(Screen.ForgotPassword.route) {
            authViewModel.clearError()
            
            ForgotPasswordScreen(
                viewModel = authViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSuccess = {
                    // Al tener éxito, volvemos para que el usuario inicie sesión
                    navController.popBackStack()
                }
            )
        }
    }
}
