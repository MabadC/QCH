package com.example.qchapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.theme.*
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.qchapp.ui.components.TopBarLogo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

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
    var showTermsDialog by remember { mutableStateOf(false) }
    var termsOpened by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    var loading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopBarLogo(
            onBackClick = onBackClick
        )

        Text(
            text = "QCH",
            color = QCHGreen,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.ExtraBold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

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

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = checked,
                onCheckedChange = { isChecked ->

                    if (!termsOpened && isChecked) {
                        showTermsDialog = true
                    } else {
                        checked = isChecked
                    }
                },
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
                },
                modifier = Modifier.clickable {
                    showTermsDialog = true
                }
            )
        }

        Spacer(modifier = Modifier.height(28.dp))

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

    // Términos y condiciones
    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = {
                showTermsDialog = false
            },
            title = {
                Text("Términos y condiciones")
            },
            text = {
                Column(
                    modifier = Modifier
                        .height(300.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = """
                        Al crear una cuenta en QCH aceptas utilizar la aplicación de forma responsable.

                        QCH permite buscar recetas, consultar información culinaria y guardar recetas favoritas asociadas a tu cuenta.

                        Los datos de usuario se utilizan únicamente para permitir el inicio de sesión, la gestión del perfil y la sincronización de recetas favoritas.

                        Las recetas mostradas pueden proceder de servicios externos, por lo que la información puede variar o contener errores.

                        La traducción automática puede no ser exacta. Se recomienda revisar la receta original cuando sea necesario.

                        QCH no sustituye asesoramiento profesional nutricional, médico o dietético.

                        Puedes cerrar sesión o eliminar tu cuenta desde el perfil de usuario.
                    """.trimIndent(),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        termsOpened = true
                        checked = true
                        showTermsDialog = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showTermsDialog = false
                    }
                ) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegisterScreen()
}