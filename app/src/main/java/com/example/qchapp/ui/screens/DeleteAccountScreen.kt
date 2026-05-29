package com.example.qchapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.components.TopBar
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHOrange
import com.example.qchapp.ui.theme.QCHGreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun DeleteAccountScreen(
    onBackClick: () -> Unit = {},
    onAccountDeleted: () -> Unit = {}
) {
    val context = LocalContext.current
    val user = FirebaseAuth.getInstance().currentUser
    val userEmail = user?.email ?: ""

    var emailConfirmation by remember {
        mutableStateOf("")
    }

    var showDialog by remember {
        mutableStateOf(false)
    }

    val emailMatches = emailConfirmation.trim() == userEmail

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(36.dp))

        TopBar(
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Eliminar usuario",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Esta acción eliminará tu cuenta de forma permanente. Para confirmar, escribe tu correo electrónico.",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = userEmail,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        QCHTextField(
            value = emailConfirmation,
            onValueChange = {
                emailConfirmation = it
            },
            placeholder = "Escribe tu e-mail",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        QCHButton(
            text = "Eliminar usuario",
            color = if (emailMatches) QCHOrange else QCHGreen,
            onClick = {
                if (!emailMatches) {
                    Toast.makeText(
                        context,
                        "El e-mail no coincide",
                        Toast.LENGTH_LONG
                    ).show()
                    return@QCHButton
                }

                showDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text("Confirmar eliminación")
            },
            text = {
                Text(
                    text = "¿Seguro que quieres eliminar tu usuario? Esta acción no se puede deshacer, perderás todas las recetas guardadas."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false

                        user?.delete()
                            ?.addOnSuccessListener {
                                Toast.makeText(
                                    context,
                                    "Usuario eliminado correctamente",
                                    Toast.LENGTH_LONG
                                ).show()

                                FirebaseAuth.getInstance().signOut()
                                onAccountDeleted()
                            }
                            ?.addOnFailureListener {
                                Toast.makeText(
                                    context,
                                    "No se pudo eliminar el usuario. Vuelve a iniciar sesión e inténtalo de nuevo.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}