package com.example.domain.auth.usecase.model

data class SignUpInfo(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val phone: String? = null
)
