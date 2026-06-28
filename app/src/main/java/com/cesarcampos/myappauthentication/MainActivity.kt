package com.cesarcampos.myappauthentication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.cesarcampos.myappauthentication.ui.navigation.AppNavigation
import com.cesarcampos.myappauthentication.ui.theme.MyAppAuthenticationTheme

/**
 * Punto de entrada principal de la aplicación.
 *
 * Objetivo para el estudiante: Entender que la Actividad es solo el contenedor
 * y que AppNavigation() es quien toma el control de lo que se muestra.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Habilita el diseño de borde a borde (status bar transparente, etc)
        enableEdgeToEdge()
        
        setContent {
            MyAppAuthenticationTheme {
                // Surface proporciona el color de fondo base y elevación
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Iniciamos nuestra lógica de navegación
                    AppNavigation()
                }
            }
        }
    }
}
