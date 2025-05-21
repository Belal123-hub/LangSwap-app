package com.example.langswap.ui.common

import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.langswap.R

@Composable
 fun PasswordVisibilityToggle(
    visible: Boolean,
    onToggle: (Boolean) -> Unit
) {
    IconToggleButton(checked = visible, onCheckedChange = onToggle) {
        Icon(
            painter = painterResource(
                if (visible) R.drawable.ic_visibility_off
                else R.drawable.ic_visibility_on
            ),
            contentDescription = stringResource(R.string.toggle_password_visibility)
        )
    }
}