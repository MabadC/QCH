package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGray
import com.example.qchapp.ui.theme.QCHGreen

@Composable
fun PasswordRecoveryScreen() {

    var email by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(
            modifier = Modifier.height(Dimens.MediumSpacing)
        )

        // Flecha atrás

        Image(
            painter = painterResource(id = R.drawable.flecha),
            contentDescription = "Volver",

            modifier = Modifier
                .size(40.dp)
                .align(Alignment.Start)
        )

        Spacer(
            modifier = Modifier.height(Dimens.MediumSpacing)
        )

        // Logo

        Image(
            painter = painterResource(id = R.drawable.qch_logo),
            contentDescription = "QCH Logo",

            modifier = Modifier.size(
                Dimens.LogoSize
            )
        )

        Spacer(
            modifier = Modifier.height(Dimens.SmallSpacing)
        )

        // Nombre aplicación - QCH

        Text(
            text = "QCH",
            color = QCHGreen,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(
            modifier = Modifier.height(
                Dimens.ExtraLargeSpacing
            )
        )

        // Título pantalla

        Text(
            text = "Recuperar contraseña",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(
                Dimens.MediumSpacing
            )
        )

        // Texto descriptivo

        Text(
            text = "Si tu e-mail está registrado en nuestro sistema te mandaremos un correo para restablecer tu contraseña.",

            style = MaterialTheme.typography.bodyMedium,
            color = QCHGray
        )

        Spacer(
            modifier = Modifier.height(
                Dimens.LargeSpacing
            )
        )

        // Campo email

        QCHTextField(
            value = email,
            onValueChange = {
                email = it
            },
            placeholder = "E-mail",
            icon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = Modifier.height(
                Dimens.ExtraLargeSpacing
            )
        )

        // Botón recuperar contraseña

        QCHButton(
            text = "RECUPERAR CONTRASEÑA",
            color = QCHGreen,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(
                Dimens.ExtraLargeSpacing
            )
        )

        // Volver iniciar sesión

        Text(
            text = "Volver a iniciar sesión",
            color = QCHGray,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.clickable {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordRecoveryScreenPreview() {
    PasswordRecoveryScreen()
}