package com.teknophase.pageindicator

import androidx.annotation.IntRange
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

enum class BoxState {
    Small, // Initial state
    Big,   // Final state
}

@Composable
fun AnimatedBox(@IntRange(2L) size: Int, selected: Int = 1) {
    val context = LocalContext.current
    var selected by remember {
        mutableIntStateOf(if (selected <= size) selected else 1)
    }

    val color = remember {
        mutableStateOf((1..size).map { Color(0xFFD9D9D9) }.toMutableList())
    }

    val rotation = remember {
        mutableStateOf((1..size).map { if(it==selected) -90f else 0f }.toMutableList())
    }

    val translateX = remember {
        mutableStateOf((1..size).map { 15f }.toMutableList())
    }

    val transformOrigins = remember {
        mutableStateOf((1..size).map {
            TransformOrigin(0f, 1f)
        }.toMutableList())
    }


    Column {
        Row {
            for (i in 1..size) {

                Box {

                    val isSelected = i == selected

                    val rotationIndex = animateFloatAsState(
                        targetValue = rotation.value[i - 1],
                        label = "",
                        animationSpec = tween(
                            durationMillis = 700,
                            easing = EaseIn
                        )
                    )

                    val colorIndex = animateColorAsState(
                        targetValue = if (!isSelected) Color(0xFFD9D9D9)
                        else Color(0xFF3578EA),
                        label = "color",
                        animationSpec = tween(
                            durationMillis = 700,
                            easing = EaseIn
                        )
                    )
                    val translateXIndex = animateFloatAsState(
                        targetValue = translateX.value[i-1],
                        label = "rotation",
                        animationSpec = tween(
                            durationMillis = 700,
                            easing = EaseIn
                        )
                    )

                    Column(
                        modifier = Modifier
                            .width(32.dp)
                            .height(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height(6.dp)
                                .graphicsLayer {
                                    transformOrigin = transformOrigins.value[i-1]
                                    rotationZ = rotationIndex.value
                                    translationX = translateXIndex.value
                                }
                                .background(colorIndex.value)
                        )
                    }
                }
            }
        }

        Button(
            onClick = {
                rotation.value[selected - 1] = -180f
                rotation.value[selected - 2] = 90f
                transformOrigins.value[selected - 1] = TransformOrigin(0f,0f)
                transformOrigins.value[selected - 2] = TransformOrigin(1f,1f)
//                for(i in 1..size) {
//                    if(i==selected-1 || i==selected - 2) translateX.value[i] = -(15.dp).toPx(context)
//                    else translateX.value[i] = 0f
//                }
                selected--
            },
            enabled = selected > 1
        ) {
            Text(text = "<")
        }
        Button(
            onClick = {
                rotation.value[selected - 1] = 0f
                rotation.value[selected] = -90f
//                for(i in 1..size) {
//                    if(i==selected-1 || i==selected) translateX.value[i] = (15.dp).toPx(context)
//                    else translateX.value[i] = 0f
//                }
                selected++
            },
            enabled = selected < 4
        ) {
            Text(text = ">")
        }
    }

}

@Preview
@Composable
fun PreviewAnimatedBox() {
    Surface {
        AnimatedBox(4)
    }
}
