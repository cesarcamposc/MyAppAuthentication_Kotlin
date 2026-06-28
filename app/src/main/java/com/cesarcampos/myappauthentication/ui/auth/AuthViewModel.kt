package com.cesarcampos.myappauthentication.ui.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * ViewModel para gestionar el estado de la autenticación.
 *
 * Objetivo para el estudiante: Entender la separación entre la lógica de negocio (ViewModel)
 * y la interfaz de usuario (Compose). El ViewModel sobrevive a cambios de configuración.
 */
class AuthViewModel : ViewModel() {

    // Estado para controlar si la app está cargando algo
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Estado para mostrar mensajes de error a los usuarios
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    /**
     * Simulación de inicio de sesión.
     */
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        if (validateEmail(email) && validatePassword(password)) {
            executeAuthAction(onSuccess)
        }
    }

    /**
     * Simulación de registro de usuario.
     */
    fun register(name: String, email: String, password: String, confirmPass: String, onSuccess: () -> Unit) {
        if (name.isBlank()) {
            _errorMessage.value = "El nombre no puede estar vacío"
            return
        }
        if (password != confirmPass) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }
        if (validateEmail(email) && validatePassword(password)) {
            executeAuthAction(onSuccess)
        }
    }

    /**
     * Simulación de recuperación de contraseña.
     */
    fun forgotPassword(email: String, onSuccess: () -> Unit) {
        if (validateEmail(email)) {
            executeAuthAction(onSuccess)
        }
    }

    // --- Lógica de soporte (Privada) ---

    private fun validateEmail(email: String): Boolean {
        return if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            true
        } else {
            _errorMessage.value = "Introduce un correo electrónico válido"
            false
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.length >= 6) {
            true
        } else {
            _errorMessage.value = "La contraseña debe tener al menos 6 caracteres"
            false
        }
    }

    private fun executeAuthAction(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            
            // Simulamos una demora de red (API call)
            delay(2000)
            
            _isLoading.value = false
            onSuccess()
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
