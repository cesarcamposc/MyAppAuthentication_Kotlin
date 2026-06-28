package com.cesarcampos.myappauthentication.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cesarcampos.myappauthentication.ui.components.AuthTextField
import com.cesarcampos.myappauthentication.ui.components.PrimaryButton
import com.cesarcampos.myappauthentication.ui.theme.BackgroundGray
import com.cesarcampos.myappauthentication.ui.theme.GreenPrimary

/**
 * Pantalla de Recuperación de Contraseña.
 *
 * Objetivo para el estudiante: Implementar una pantalla con un solo campo de entrada
 * y entender cómo manejar el flujo de "Volver" (Navigation Backstack).
 */
@Composable
fun ForgotPasswordScreen(
    viewModel: AuthViewModel,
    onNavigateBack: () -> Unit,
    onSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundGray)
            .padding(24.dp)
    ) {
        // Botón Volver
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .background(Color.White, CircleShape)
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Forgot Password",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Enter your email address to receive a reset link and regain access to your account.",
                fontSize = 14.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

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
                    AuthTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = "Email address",
                        leadingIcon = Icons.Default.Email
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    if (isLoading) {
                        CircularProgressIndicator(color = GreenPrimary)
                    } else {
                        PrimaryButton(
                            text = "Continue",
                            onClick = { viewModel.forgotPassword(email, onSuccess) }
                        )
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = it, color = Color.Red, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
