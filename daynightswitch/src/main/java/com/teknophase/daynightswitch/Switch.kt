package com.teknophase.daynightswitch

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class SwitchWidth {
    Small,
    Medium,
    Large
}

enum class SwitchHeight(val size: Dp) {
    Small(32.dp),
    Medium(48.dp),
    Large(64.dp)
}

@Composable
fun DayNightSwitch(
    isDay: Boolean,
    onClick: () -> Unit,
    animationDuration: Int = 500,
    switchWidth: SwitchWidth = SwitchWidth.Small,
    height: SwitchHeight = SwitchHeight.Small
) {
    val width: Dp = when (switchWidth) {
        SwitchWidth.Small -> height.size.times(2)
        SwitchWidth.Medium -> height.size.times(2.5f)
        SwitchWidth.Large -> height.size.times(3)
    }
    val starPadding: Dp = when (switchWidth) {
        SwitchWidth.Small -> 8.dp
        SwitchWidth.Medium -> 16.dp
        SwitchWidth.Large -> 24.dp
    }
    val circlePositionOffset: Dp by animateDpAsState(
        targetValue = if (isDay) 0.dp else width - height.size,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOut
        ),
        label = "circlePositionAnimation"
    )
    val elementsPositionOffset: Dp by animateDpAsState(
        targetValue = if (isDay) 0.dp else height.size,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOut
        ),
        label = "elementsPositionAnimation"
    )
    val backgroundColor by animateColorAsState(
        targetValue = if (isDay) Color(0xFF85C2F1) else Color(0xFF1F313E), // Change colors based on the state
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = EaseOut
        ),
        label = "backgroundColorAnimation"
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(height.size))
            .size(width, height.size)
            .background(backgroundColor)
            .clickable { onClick() },
    ) {
        Box(
            modifier = Modifier
                .alpha(0.25f)
                .offset(x = circlePositionOffset - height.size.times(0.5f))
        ) {
            Image(
                painter = painterResource(id = R.drawable.glow),
                contentDescription = "",
                modifier = Modifier
                    .blur(2.dp)
                    .height(height.size.times(2))
                    .align(Alignment.CenterStart),
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = elementsPositionOffset)
        ) {
            Image(
                painter = painterResource(id = R.drawable.clouds),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .offset(y = elementsPositionOffset - height.size)
        ) {
            Image(
                painter = painterResource(id = R.drawable.stars),
                contentDescription = "",
                modifier = Modifier.padding(start = starPadding),
                contentScale = ContentScale.FillHeight
            )
        }


        Crossfade(
            targetState = isDay,
            label = "button",
            modifier = Modifier.offset(x = circlePositionOffset)
        ) {
            if (it) {
                Image(
                    painter = painterResource(id = R.drawable.button_sun),
                    contentDescription = "",
                    alignment = Alignment.BottomStart,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.button_moon),
                    contentDescription = "",
                    alignment = Alignment.BottomEnd,
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp)
                )
            }


        }
    }
}

@Composable
@Preview
fun DayNightSwitchPreview() {
    var isDay by remember {
        mutableStateOf(true)
    }
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Small,
            height = SwitchHeight.Small
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Small,
            height = SwitchHeight.Medium
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Small,
            height = SwitchHeight.Large
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Medium,
            height = SwitchHeight.Small
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Medium,
            height = SwitchHeight.Medium
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Medium,
            height = SwitchHeight.Large
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Large,
            height = SwitchHeight.Small
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Large,
            height = SwitchHeight.Medium
        )
        DayNightSwitch(
            isDay = isDay,
            onClick = { isDay = !isDay },
            switchWidth = SwitchWidth.Large,
            height = SwitchHeight.Large
        )
    }
}