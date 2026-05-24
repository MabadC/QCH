package com.example.qchapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.qchapp.ui.theme.QCHGreen

@Composable
fun IngredientField(
    value: String,
    onValueChange: (String) -> Unit,
    isAddButton: Boolean = false,
    onAddClick: () -> Unit = {},
    onRemoveClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        QCHTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = "ingrediente",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        FilledIconButton(
            onClick =
                if (isAddButton)
                    onAddClick
                else
                    onRemoveClick,

            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = QCHGreen
            )
        ) {
            Icon(
                imageVector =
                    if (isAddButton)
                        Icons.Default.Add
                    else
                        Icons.Default.Remove,

                contentDescription =
                    if (isAddButton)
                        "Añadir ingrediente"
                    else
                        "Eliminar ingrediente",

                tint = Color.White
            )
        }
    }
}