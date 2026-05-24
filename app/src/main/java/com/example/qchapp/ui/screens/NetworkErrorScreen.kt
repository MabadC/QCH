package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.theme.*

@Composable
fun NetworkErrorScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(60.dp)
        )

        // Logo gris

        Image(
            painter = painterResource(
                id = R.drawable.qch_logo_bnw
            ),
            contentDescription = "QCH Logo",

            modifier = Modifier.size(120.dp)
        )

        Spacer(
            modifier = Modifier.height(40.dp)
        )

        // Título

        Text(
            text = "Error de red",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        // Icono sin conexión

        Image(
            painter = painterResource(
                id = R.drawable.network_error
            ),
            contentDescription = "Sin conexión",

            modifier = Modifier.size(180.dp)
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        // Texto explicación

        Text(
            text =
                "No se puede acceder a la información.\n" +
                        "Revisa tu conexión e inténtalo de nuevo.",

            textAlign = TextAlign.Center,

            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        // Botón

        QCHButton(
            text = "Reintentar",
            color = QCHGreen,
            onClick = {},

            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(70.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NetworkErrorScreenPreview() {
    NetworkErrorScreen()
}