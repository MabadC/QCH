package com.example.qchapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@Composable
fun QCHTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    icon: ImageVector? = null,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordVisibilityChange: (() -> Unit)? = null
) {

    OutlinedTextField(
        value = value,

        onValueChange = onValueChange,

        placeholder = {
            Text(placeholder)
        },

        leadingIcon = icon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
            }
        },

        trailingIcon = {

            if (isPassword) {

                Icon(
                    imageVector =
                        if (passwordVisible)
                            Icons.Default.Visibility
                        else
                            Icons.Default.VisibilityOff,

                    contentDescription = null,

                    modifier = Modifier.clickable {
                        onPasswordVisibilityChange?.invoke()
                    }
                )
            }
        },

        visualTransformation =
            if (isPassword && !passwordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,

        shape = RoundedCornerShape(8.dp),

        modifier = modifier
    )
}