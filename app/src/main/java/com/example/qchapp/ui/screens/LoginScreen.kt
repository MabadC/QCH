package com.example.qchapp.ui.screens

//Firebase
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.components.TopBarLogo
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGray
import com.example.qchapp.ui.theme.QCHGreen
import com.example.qchapp.ui.theme.QCHOrange
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var passwordVisible by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var loading by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        TopBarLogo(
            onBackClick = onBackClick
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(Dimens.SmallSpacing)
        )

        // Nombre App - QCH

        Text(
            text = "QCH",
            color = QCHGreen,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // Título de pantalla

        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        // Campos formulario

        QCHTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = "E-mail",
            icon = Icons.Default.Email,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        QCHTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = "Contraseña",
            icon = Icons.Default.Lock,
            isPassword = true,
            passwordVisible = passwordVisible,
            onPasswordVisibilityChange = {
                passwordVisible = !passwordVisible
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        // Recuperar contraseña

        Text(
            text = "¿Has olvidado tu contraseña?",
            color = QCHGreen,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.clickable {
                onForgotPasswordClick()
            }
        )

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        // Botón principal

        QCHButton(

            text =
                if (loading)
                    "INICIANDO..."
                else
                    "INICIAR SESIÓN",

            color = QCHGreen,

            onClick = {

                if (
                    email.isBlank() ||
                    password.isBlank()
                ) {

                    Toast.makeText(
                        context,
                        "Completa todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@QCHButton
                }

                loading = true

                auth.signInWithEmailAndPassword(
                    email,
                    password
                )
                    .addOnCompleteListener { task ->

                        loading = false

                        if (task.isSuccessful) {

                            Toast.makeText(
                                context,
                                "Sesión iniciada",
                                Toast.LENGTH_SHORT
                            ).show()

                            onLoginSuccess()

                        } else {

                            Toast.makeText(
                                context,
                                task.exception?.message
                                    ?: "Error al iniciar sesión",
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
            modifier = Modifier.height(28.dp)
        )

// Pie de pantalla

        Text(
            text = buildAnnotatedString {

                withStyle(
                    style = SpanStyle(
                        color = QCHGray,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("¿Aún no formas parte de QCH?\n")
                }

                withStyle(
                    style = SpanStyle(
                        color = QCHOrange,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append("Crear cuenta")
                }
            },

            modifier = Modifier.fillMaxWidth(),

            textAlign = TextAlign.Center
        )
    }
}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}