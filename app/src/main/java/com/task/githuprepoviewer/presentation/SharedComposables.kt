package com.task.githuprepoviewer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.task.githuprepoviewer.R

val fontFamily = FontFamily(
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_semi_bold_italic, FontWeight.SemiBold),
    Font(R.font.mulish_medium, FontWeight.Medium),
    Font(R.font.mulish_light, FontWeight.Light)
)

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CircularAvatarImage(
    avatarUrl: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        GlideImage(
            model = avatarUrl,
            contentDescription = "Owner Avatar Image",
            loading = placeholder(R.drawable.jetpack_compose_icon),
            failure = placeholder(ColorPainter(Color.Red)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
        )
    }
}

@Composable
fun LabeledIcon(
    painter: Painter,
    label: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    colorFilter: ColorFilter,
    textStyle: TextStyle
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier,
            colorFilter = colorFilter
        )
        Text(
            text = label,
            style = textStyle
        )
    }
}