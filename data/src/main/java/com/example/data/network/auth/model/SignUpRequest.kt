package com.example.data.network.auth.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    @SerialName("FullName")
    val fullName: String,

    @SerialName("Email")
    val email: String,

    @SerialName("Password")
    val password: String,

    @SerialName("ConfirmPassword")
    val confirmPassword: String,

    @SerialName("PhoneNumber")
    val phoneNumber: String? = null
)
