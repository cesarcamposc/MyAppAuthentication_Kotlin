package com.cesarcampos.myappauthentication.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cesarcampos.myappauthentication.ui.components.AuthTextField
import com.cesarcampos.myappauthentication.ui.components.PrimaryButton
import com.cesarcampos.myappauthentication.ui.theme.BackgroundGray
import com.cesarcampos.myappauthentication.ui.theme.GreenPrimary

/**
 * Pantalla de Inicio de Sesión.
 *
 * Objetivo para el estudiante: Aprender a construir layouts complejos usando Column,
 * manejar el estado local de los inputs y conectar la UI con el ViewModel.
 */
@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgot: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Estado local para los campos (UI State)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Obtenemos el estado desde el ViewModel
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título y Subtítulo
            Text(
                text = "Log in",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your email and password to securely access your account and manage your services.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Card blanca central (Efecto de diseño moderno)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Input de Email
                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email address",
                        leadingIcon = Icons.Default.Email
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Input de Password
                    AuthTextField(
                        value = password,
                        onValueChange = { password = it },
                        placeholder = "Password",
                        leadingIcon = Icons.Default.Lock,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val icon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = icon, contentDescription = null, tint = Color.Gray)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Enlaces de soporte (Remember me y Forgot Password)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = false, onCheckedChange = {})
                            Text("Remember me", fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(
                            text = "Forgot Password",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.clickable { onNavigateToForgot() }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Botón de Login
                    if (isLoading) {
                        CircularProgressIndicator(color = GreenPrimary)
                    } else {
                        PrimaryButton(
                            text = "Login",
                            onClick = { viewModel.login(email, password, onLoginSuccess) }
                        )
                    }

                    // Mensaje de Error
                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = it, color = Color.Red, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Link a Registro
            Row {
                Text(text = "Don't have an account? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    text = "Sign Up here",
                    color = GreenPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Footer (Opcional - Social Login)
            Text(text = "Or Continue With Account", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialIcon(text = "f") // Simulación
                SocialIcon(text = "G")
                SocialIcon(text = "")
            }
        }
    }
}

@Composable
fun SocialIcon(text: String) {
    Surface(
        modifier = Modifier.size(45.dp),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        shadowElevation = 1.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(text = text, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
