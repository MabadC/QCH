package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.theme.*
import androidx.compose.foundation.clickable

@Composable
fun ChangePasswordScreen(
    onBackClick: () -> Unit = {}
) {

    var currentPassword by remember {
        mutableStateOf("")
    }

    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var currentVisible by remember {
        mutableStateOf(false)
    }

    var newVisible by remember {
        mutableStateOf(false)
    }

    var confirmVisible by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(36.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            Image(
                painter = painterResource(
                    id = R.drawable.flecha
                ),
                contentDescription = "Volver",

                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        onBackClick()
                    }
            )
        }

        Image(
            painter = painterResource(
                id = R.drawable.qch_logo
            ),

            contentDescription = "Logo",

            modifier = Modifier.size(90.dp)
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Text(
            text = "Cambiar contraseña",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        QCHTextField(
            value = currentPassword,
            onValueChange = {
                currentPassword = it
            },

            placeholder = "Contraseña actual",
            icon = Icons.Default.Lock,

            isPassword = true,
            passwordVisible = currentVisible,

            onPasswordVisibilityChange = {
                currentVisible = !currentVisible
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        QCHTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
            },

            placeholder = "Nueva contraseña",
            icon = Icons.Default.Lock,

            isPassword = true,
            passwordVisible = newVisible,

            onPasswordVisibilityChange = {
                newVisible = !newVisible
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        QCHTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },

            placeholder = "Confirmar contraseña",
            icon = Icons.Default.Lock,

            isPassword = true,
            passwordVisible = confirmVisible,

            onPasswordVisibilityChange = {
                confirmVisible = !confirmVisible
            },

            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        QCHButton(
            text = "Guardar cambios",
            color = QCHGreen,
            onClick = {},

            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordPreview() {
    ChangePasswordScreen()
}