package com.example.qchapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.qchapp.R
import com.example.qchapp.ui.theme.Dimens

@Composable
fun TopBarLogo(
    onBackClick: () -> Unit = {}
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Spacer(
            modifier = Modifier.height(
                Dimens.TopBarTopPadding
            )
        )

        Image(
            painter = painterResource(
                id = R.drawable.flecha
            ),
            contentDescription = "Volver",

            modifier = Modifier
                .size(Dimens.BackArrowSize)
                .clickable {
                    onBackClick()
                }
        )

        Spacer(
            modifier = Modifier.height(
                Dimens.MediumSpacing
            )
        )

        Image(
            painter = painterResource(
                id = R.drawable.qch_logo
            ),
            contentDescription = "QCH Logo",

            modifier = Modifier
                .size(Dimens.LogoSize)
                .align(
                    Alignment.CenterHorizontally
                )
        )
    }
}