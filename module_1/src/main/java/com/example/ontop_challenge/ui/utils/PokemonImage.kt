package com.example.ontop_challenge.ui.utils

import android.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter

@Composable
fun PokemonImage(
    url: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val isInPreview = LocalInspectionMode.current

    if (isInPreview) {
        Box(modifier = modifier.background(Color.LightGray))
    } else {
        AsyncImage(
            model = url,
            contentDescription = contentDescription,
            placeholder = ColorPainter(Color.LightGray),
            error = painterResource(id = R.drawable.ic_menu_report_image),
            contentScale = ContentScale.Crop,
            modifier = modifier
        )
    }
}
