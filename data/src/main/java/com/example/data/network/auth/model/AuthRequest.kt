package com.example.data.network.auth.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val address: String? = null,
    val birthDate: String? = null,
    val phoneNumber: String,
    val gender: String? = null
)
