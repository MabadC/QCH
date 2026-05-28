package com.example.qchapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.components.TopBarLogo
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGray
import com.example.qchapp.ui.theme.QCHGreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PasswordRecoveryScreen(
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {

    var email by remember {
        mutableStateOf("")
    }

    var loading by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        TopBarLogo(
            onBackClick = onBackClick
        )

        Spacer(
            modifier = Modifier.height(Dimens.SmallSpacing)
        )

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

        QCHButton(
            text =
                if (loading)
                    "ENVIANDO..."
                else
                    "RECUPERAR CONTRASEÑA",

            color = QCHGreen,

            onClick = {

                if (email.isBlank()) {

                    Toast.makeText(
                        context,
                        "Introduce tu e-mail",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@QCHButton
                }

                loading = true

                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->

                        loading = false

                        if (task.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Correo de recuperación enviado",
                                Toast.LENGTH_LONG
                            ).show()

                            onLoginClick()

                        } else {

                            Toast.makeText(
                                context,
                                task.exception?.message
                                    ?: "No se pudo enviar el correo",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
        )

        Spacer(
            modifier = Modifier.height(
                Dimens.ExtraLargeSpacing
            )
        )

        Text(
            text = "Volver a iniciar sesión",
            color = QCHGray,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,

            modifier = Modifier.clickable {
                onLoginClick()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordRecoveryScreenPreview() {
    PasswordRecoveryScreen()
}