package com.teknophase.pageindicator

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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

@Composable
fun Indication(
    isSelected: Boolean,
    angle: Float,
    transformOrigin: TransformOrigin
) {
    val rotation = animateFloatAsState(
        targetValue = angle,
        label = "",
        animationSpec = tween(1000)
    )

    val color = animateColorAsState(
        targetValue = if (isSelected) Color(0xFF3578EA)
        else Color(0xFFD9D9D9),
        label = "color",
        animationSpec = tween(1000)
    )
//    val translateX = animateFloatAsState(
//        targetValue =
//        if (isSelected && moveDirection == MoveDirection.NEXT) 12f
//        else if (isSelected && moveDirection == MoveDirection.PREVIOUS) -12f
//        else -0f,
//        label = "rotation",
//        animationSpec = tween(1000)
//    )



    Box(
        modifier = Modifier
            .width(24.dp)
            .height(6.dp)
            .graphicsLayer {
//                transformOrigin = if(isSelected && moveDirection==MoveDirection.NEXT) TransformOrigin(0f,1f)
//                else if (isSelected && moveDirection==MoveDirection.PREVIOUS) TransformOrigin(1f,1f)
//                else if (!isSelected && moveDirection==MoveDirection.PREVIOUS) TransformOrigin(0f,0f)
//                else if (!isSelected && moveDirection==MoveDirection.NEXT) TransformOrigin(0f,1f)
//                else TransformOrigin.Center
//                transformOrigin = TransformOrigin(0.5f, 1f)
                rotationZ = rotation.value
//                translationX = translateX.value
            }
//            .rotate(rotation.value)
            .background(color.value)
    )
}

@Composable
//@Preview
fun Indicator(index: Int, direction: MoveDirection) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.height(36.dp)
        )
        {
            for (i in 1..4) {
                val isSelected = i == index
                if (isSelected) {
                    when (direction) {
                        MoveDirection.NEXT -> {
                            SelectedIndicationNext()
                        }

                        MoveDirection.PREVIOUS -> {
                            val context = LocalContext.current

                            Column(
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {

                                var animating by remember {
                                    mutableStateOf(false)
                                }

                                val transition = updateTransition(targetState = animating, label = "selectedIndicationTransition")

                                val rotation by transition.animateFloat(label = "rotation") { animatingState ->
                                    if (animatingState) 90f else 0f
                                }

                                val color by transition.animateColor(label = "color") { animatingState ->
                                    if (animatingState) Color(0xFF3578EA) else Color(0xFFD9D9D9)
                                }

                                val translateX by transition.animateFloat(label = "translateX") { animatingState ->
                                    if (animatingState) -(15.dp).toPx(context) else 0f
                                }

                                DisposableEffect(Unit) {
                                    animating = true
                                    onDispose {
                                        animating = false
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(6.dp)
                                        .graphicsLayer {
                                            transformOrigin = TransformOrigin(1f, 1f)
                                            rotationZ = rotation
                                            translationX = translateX
                                        }
                                        .background(color)
                                )
                            }
                        }

                        else -> UnAnimatedIndication(true)
                    }
                } else {
                    if (index - 1 == i && direction == MoveDirection.NEXT) UnselectedIndicationNext()
                    else if (index + 1 == i && direction == MoveDirection.PREVIOUS) UnselectedIndicationPrevious()
                    else UnAnimatedIndication()
                }


//                if(i==index-1 && direction==MoveDirection.NEXT) SelectedNext()
//                else if (i==index && direction==MoveDirection.NEXT) Box {}
//                else if(i==index && direction==MoveDirection.PREVIOUS) SelectedPrevious()
//                else if(i==index+1 && direction==MoveDirection.PREVIOUS) Box {}
//                else UnAnimatedIndication()

            }
        }
    }
}

@Composable
@Preview
fun PageIndicator() {
    var index by remember {
        mutableIntStateOf(2)
    }
    var direction by remember {
        mutableStateOf(MoveDirection.NONE)
    }
    Column {
        Indicator(index = index, direction = direction)
        Button(
            onClick = {
                index--
                direction = MoveDirection.PREVIOUS
            },
            enabled = index > 1
        ) {
            Text(text = "<")
        }
        Button(
            onClick = {
                index++
                direction = MoveDirection.NEXT
            },
            enabled = index < 4
        ) {
            Text(text = ">")
        }
    }
}

enum class MoveDirection {
    PREVIOUS,
    NEXT,
    NONE
}

enum class IndicatorType {
    SELECTED_PREVIOUS,
    SELECTED_NEXT,
    UNSELECTED_PREVIOUS,
    UNSELECTED_NEXT
}

@Composable
fun animateAngleChange(currentAngle: Float, newAngle: Float, onAnimationEnd: () -> Unit) {
    val animatedAngle by animateFloatAsState(
        targetValue = newAngle,
        animationSpec = tween(durationMillis = 500),
        label = ""
    )

    LaunchedEffect(currentAngle) {
        if (animatedAngle == newAngle) {
            onAnimationEnd()
        }
    }
}