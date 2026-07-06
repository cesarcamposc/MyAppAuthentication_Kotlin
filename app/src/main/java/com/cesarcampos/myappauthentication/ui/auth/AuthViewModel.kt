package com.cesarcampos.myappauthentication.ui.auth

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesarcampos.myappauthentication.data.local.AppDatabase
import com.cesarcampos.myappauthentication.data.local.UserEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

/**
 * ViewModel para gestionar el estado de la autenticación.
 *
 * Objetivo para el estudiante: Entender la separación entre la lógica de negocio (ViewModel)
 * y la interfaz de usuario (Compose). El ViewModel sobrevive a cambios de configuración.
 */
class AuthViewModel(application: Application)  : AndroidViewModel(application) {

    private val userDao = AppDatabase.getDatabase(application).userDao()

    // Estado para controlar si la app está cargando algo
    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    // Estado para mostrar mensajes de error a los usuarios
    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    /**
     * Login Real con Sqlite.
     */
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        if (!validateEmail(email) || !validatePassword(password)) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val user = userDao.login(email, password)

            if (user != null) {
                onSuccess()
            } else {
                _isLoading.value = false
                _errorMessage.value = "Credentials don't match"
            }
        }
    }

    /**
     * Register Real con Sqlite.
     */
    fun register(
        name: String,
        email: String,
        password: String,
        confirmPass: String,
        onSuccess: () -> Unit) {
        if (name.isBlank()) {
            _errorMessage.value = "El nombre no puede estar vacío"
            return
        }
        if (password != confirmPass) {
            _errorMessage.value = "Las contraseñas no coinciden"
            return
        }

        if (!validateEmail(email) || !validatePassword(password)) return
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val existingUser = userDao.getUserByEmail(email)

            if (existingUser != null) {
                _isLoading.value = false
                _errorMessage.value = "User already exists"
                return@launch
            }

            userDao.insert(UserEntity(email = email, password = password))

            _isLoading.value = false
            onSuccess()
        }
    }


    /**
     * Simulación de recuperación de contraseña.
     */
    fun forgotPassword(email: String, onSuccess: () -> Unit) {
        if (!validateEmail(email)) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            delay(2000.milliseconds)

            _isLoading.value = false
            onSuccess()
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


    fun clearError() {
        _errorMessage.value = null
    }
}
