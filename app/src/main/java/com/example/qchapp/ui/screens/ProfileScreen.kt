package com.example.qchapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.components.BottomBar
import com.example.qchapp.ui.components.ProfileOption
import com.example.qchapp.ui.components.QCHButton
import com.example.qchapp.ui.components.TopBar
import com.example.qchapp.ui.theme.*
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onFavoritesClick: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onChangePasswordClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onEditUsernameClick: () -> Unit = {},
    onDeleteAccountClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {}
) {
    val user = FirebaseAuth.getInstance().currentUser

    val isAnonymous = user == null || user.isAnonymous

    val userName = user?.displayName ?: "Usuario QCH"
    val userEmail = user?.email ?: "Sin e-mail"

    Scaffold(
        bottomBar = {
            BottomBar(
                selectedItem = "profile",
                onSearchClick = onSearchClick,
                onFavoritesClick = onFavoritesClick,
                onProfileClick = onProfileClick
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = Dimens.ScreenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(36.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                TopBar(
                    onBackClick = onBackClick
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Perfil",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (isAnonymous) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario anónimo",
                    tint = QCHGreen,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Estás usando QCH como invitado",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Para acceder a tu perfil, guardar recetas y ver tus recetas guardadas, inicia sesión o crea una cuenta.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                QCHButton(
                    text = "Iniciar sesión",
                    color = QCHGreen,
                    onClick = onLoginClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(Dimens.ButtonHeight)
                )

                Spacer(modifier = Modifier.height(14.dp))

                QCHButton(
                    text = "Crear cuenta",
                    color = QCHOrange,
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(Dimens.ButtonHeight)
                )

            } else {

                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Usuario",
                    tint = QCHGreen,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = userEmail,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(28.dp))

                ProfileOption(
                    icon = Icons.Default.Lock,
                    text = "Cambiar contraseña",
                    onClick = onChangePasswordClick
                )

                ProfileOption(
                    icon = Icons.Default.Edit,
                    text = "Cambiar nombre de usuario",
                    onClick = onEditUsernameClick
                )

                ProfileOption(
                    icon = Icons.Default.Delete,
                    text = "Eliminar usuario",
                    onClick = onDeleteAccountClick
                )

                Spacer(modifier = Modifier.weight(1f))

                QCHButton(
                    text = "Cerrar sesión",
                    color = QCHOrange,
                    onClick = onLogoutClick,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(Dimens.ButtonHeight)
                )

                Spacer(modifier = Modifier.height(28.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}