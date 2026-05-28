package com.example.qchapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.qchapp.R
import com.example.qchapp.ui.theme.QCHGreen

@Composable
fun RecipePreview(
    title: String,
    time: String,
    difficulty: String,
    imageUrl: String? = null,
    image: Int = R.drawable.recipe_placeholder,
    isSaved: Boolean = false,
    showDelete: Boolean = false,
    onSaveClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onClick: () -> Unit = {}
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = title,
                placeholder = painterResource(id = image),
                error = painterResource(id = image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(84.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "$time · $difficulty",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick =
                    if (showDelete)
                        onDeleteClick
                    else
                        onSaveClick
            ) {
                Icon(
                    imageVector =
                        if (showDelete)
                            Icons.Default.Delete
                        else if (isSaved)
                            Icons.Default.Bookmark
                        else
                            Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = QCHGreen,
                    modifier = Modifier.size(34.dp)
                )
            }
        }
    }
}