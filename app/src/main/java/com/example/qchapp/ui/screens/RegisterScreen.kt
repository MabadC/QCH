package com.example.qchapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.theme.*
//Firebase
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var loading by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        // Flecha atrás

        Image(
            painter = painterResource(
                id = R.drawable.flecha
            ),
            contentDescription = "Volver",

            modifier = Modifier
                .size(40.dp)
                .clickable {
                    onBackClick()
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            // Logo

            Image(
                painter = painterResource(id = R.drawable.qch_logo),
                contentDescription = "QCH Logo",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre App - QCH

            Text(
                text = "QCH",
                color = QCHGreen,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título de pantalla

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campos formulario

            QCHTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Nombre",
                icon = Icons.Default.Person,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            QCHTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "E-Mail",
                icon = Icons.Default.Email,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

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

            Spacer(modifier = Modifier.height(12.dp))

            // Checkbox términos y condiciones

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = QCHGreen
                    )
                )

                Text(
                    text = buildAnnotatedString {
                        append("Acepto los ")

                        withStyle(
                            style = SpanStyle(
                                color = QCHGreen,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("términos y condiciones")
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botón crear cuenta

            QCHButton(
                text =
                    if (loading)
                        "CREANDO..."
                    else
                        "CREAR CUENTA",

                color = QCHGreen,

                onClick = {

                    if (
                        name.isBlank() ||
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

                    if (!checked) {

                        Toast.makeText(
                            context,
                            "Acepta los términos",
                            Toast.LENGTH_SHORT
                        ).show()

                        return@QCHButton
                    }

                    loading = true

                    auth.createUserWithEmailAndPassword(
                        email,
                        password
                    )
                        .addOnCompleteListener { task ->

                            loading = false

                            if (task.isSuccessful) {

                                // Guardar valores en perfil
                                val user = auth.currentUser

                                val profileUpdates = userProfileChangeRequest {
                                    displayName = name
                                }

                                user?.updateProfile(profileUpdates)
                                    ?.addOnCompleteListener {

                                        Toast.makeText(
                                            context,
                                            "Cuenta creada correctamente",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        onRegisterSuccess()
                                    }

                            } else {

                                Toast.makeText(
                                    context,
                                    task.exception?.message
                                        ?: "Error al registrar usuario",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.ButtonHeight)
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Pie de pantalla

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = QCHGray,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("¿Ya tienes cuenta? ")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = QCHOrange,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append("Inicia sesión")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}