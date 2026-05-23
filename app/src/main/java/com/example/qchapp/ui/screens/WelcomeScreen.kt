package com.example.qchapp.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGray
import com.example.qchapp.ui.theme.QCHGreen
import com.example.qchapp.ui.theme.QCHOrange

@Composable
fun WelcomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Logo

        Image(
            painter = painterResource(id = R.drawable.qch_logo),
            contentDescription = "QCH Logo",
            modifier = Modifier.size(Dimens.LogoSize)
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
            modifier = Modifier.height(Dimens.ExtraLargeSpacing)
        )

        // Texto presentación

        Text(
            text = "¿Qué cocino hoy?",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(Dimens.SmallSpacing)
        )

        Text(
            text = "¿Qué hay en tu despensa?",
            style = MaterialTheme.typography.bodyMedium,
            color = QCHGray
        )

        Spacer(
            modifier = Modifier.height(Dimens.ExtraLargeSpacing)
        )

        // Botón iniciar sesión

        QCHButton(
            text = "INICIAR SESIÓN",
            color = QCHGreen,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(Dimens.MediumSpacing)
        )

        // Botón registrarse

        QCHButton(
            text = "REGISTRARSE",
            color = QCHOrange,
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(Dimens.MediumSpacing)
        )

        // Botón usuario anónimo

        OutlinedButton(
            onClick = {},

            shape = RoundedCornerShape(10.dp),

            border = BorderStroke(
                1.dp,
                QCHGray
            ),

            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = QCHGray
            ),

            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.AnonymousButtonHeight)
        ) {

            Text(
                text = "CONTINUAR COMO\nUSUARIO ANÓNIMO",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}