package com.example.qchapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.qchapp.R
import com.example.qchapp.ui.theme.Dimens

@Composable
fun TopBar(
    onBackClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.TopBarTopPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.flecha),
            contentDescription = "Volver",
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onBackClick()
                }
        )

        Image(
            painter = painterResource(id = R.drawable.qch_logo),
            contentDescription = "QCH Logo",
            modifier = Modifier.size(Dimens.TopBarLogoSize)
        )
    }
}