package com.example.domain.auth.usecase.model

data class AuthInfo(
    val fullName: String? = null,
    val email: String,
    val password: String,
    val confirmPassword: String? = null,
    val phone: String? =null
)
