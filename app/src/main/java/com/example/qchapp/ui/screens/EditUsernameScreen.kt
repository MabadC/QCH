package com.example.qchapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.QCHTextField
import com.example.qchapp.ui.components.TopBar
import com.example.qchapp.ui.theme.Dimens
import com.example.qchapp.ui.theme.QCHGreen
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EditUsernameScreen(
    onBackClick: () -> Unit = {}
) {

    val context = LocalContext.current

    val user = FirebaseAuth.getInstance().currentUser

    var username by remember {
        mutableStateOf(
            user?.displayName ?: ""
        )
    }

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
            text = "Cambiar nombre de usuario",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))

        QCHTextField(
            value = username,
            onValueChange = {
                username = it
            },
            placeholder = "Nombre de usuario",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        QCHButton(
            text = "Guardar cambios",
            color = QCHGreen,
            onClick = {

                if (username.isBlank()) {

                    Toast.makeText(
                        context,
                        "Introduce un nombre válido",
                        Toast.LENGTH_LONG
                    ).show()

                    return@QCHButton
                }

                val profileUpdates =
                    UserProfileChangeRequest.Builder()
                        .setDisplayName(username)
                        .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnSuccessListener {

                        Toast.makeText(
                            context,
                            "Nombre actualizado correctamente",
                            Toast.LENGTH_LONG
                        ).show()

                        onBackClick()
                    }
                    ?.addOnFailureListener {

                        Toast.makeText(
                            context,
                            "Error al actualizar el nombre",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.ButtonHeight)
        )
    }
}