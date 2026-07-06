package com.cesarcampos.myappauthentication.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth")
    suspend fun authenticate(
        @Body request: AuthRequest
    ): Response<AuthResponse>
}

data class AuthRequest(
    val action: String,
    val email: String,
    val password: String,
    val name: String? = null
)

data class AuthResponse(
    val message: String,
    val name: String?
)