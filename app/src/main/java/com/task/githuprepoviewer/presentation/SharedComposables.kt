package com.task.githuprepoviewer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
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
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(CircleShape), contentAlignment = Alignment.Center
    ) {
        GlideImage(
            model = avatarUrl,
            contentDescription = "Owner Avatar Image",
            loading = placeholder(R.drawable.jetpack_compose_icon),
            failure = placeholder(ColorPainter(Color.Red)),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
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

@Composable
fun RoundedCornerText(
    text: String,
    textStyle: TextStyle,
    backgroundColor: Color,
    textColor: Color = Color.Unspecified
) {
    Box(
        Modifier
            .padding(6.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
        )
    }
}

@Composable
fun LoadingState() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorState(message: String) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lottie_error))

    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column {
            LottieAnimation(composition = composition, Modifier.size(400.dp))
            Text(
                text = message,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}

fun LazyListState.isReachedBottom(): Boolean {
    val layoutInfo = layoutInfo
    val visibleItemsInfo = layoutInfo.visibleItemsInfo
    return if (layoutInfo.totalItemsCount == 0) {
        false
    } else {
        val lastVisibleItem = visibleItemsInfo.last()
        val viewportHeight = layoutInfo.viewportEndOffset + layoutInfo.viewportStartOffset
        (lastVisibleItem.index + 1 == layoutInfo.totalItemsCount &&
                lastVisibleItem.offset + lastVisibleItem.size <= viewportHeight)
    }
}