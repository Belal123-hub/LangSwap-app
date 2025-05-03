package com.example.langswap.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ProfileEditScreen(){
    Box (modifier = Modifier.background(color = Color.Red)){
        Text(
            text = "This is prfile screen",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
            )
    }
}