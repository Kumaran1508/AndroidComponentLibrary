package com.teknophase.pageindicator

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
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
//@Preview
fun SelectedIndicationPrevious() {
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

@Composable
@NonRestartableComposable
fun SelectedIndicationNext() {
    val context = LocalContext.current
    var animating = remember {
        mutableStateOf(false)
    }
    var rotation = animateFloatAsState(
        targetValue = if (animating.value) -90f else 0f,
        label = "",
        animationSpec = tween(
            if (animating.value) 500 else 1,
            delayMillis = 500,
            easing = EaseOut
        )
    )

    LaunchedEffect(Unit) {
        animating.value = true
    }



    val color = animateColorAsState(
        targetValue = if (animating.value) Color(0xFF3578EA)
        else Color(0xFFD9D9D9),
        label = "color",
        animationSpec = tween(
            if (animating.value) 500 else 1,
            delayMillis = 500,
            easing = EaseOut
        )
    )
    val translateX = animateFloatAsState(
        targetValue = if (animating.value) (15.dp).toPx(context) else 0f,
        label = "rotation",
        animationSpec = tween(
            if (animating.value) 500 else 1,
            delayMillis = 500,
            easing = EaseOut
        )
    )


    Box(
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .width(24.dp)
                .height(6.dp)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0f, 1f)
                    rotationZ = rotation.value
                    translationX = translateX.value
                }
                .background(color.value)
        ) {}
    }

}


@Composable
@Preview
fun UnselectedIndicationPrevious() {
    val context = LocalContext.current
    var animating by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        animating = true
    }

    val rotation = animateFloatAsState(
        targetValue = if (animating) -90f else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 700,
            easing = EaseIn
        )
    )

    val color = animateColorAsState(
        targetValue = if (animating) Color(0xFFD9D9D9)
        else Color(0xFF3578EA),
        label = "color",
        animationSpec = tween(
            durationMillis = 700,
            easing = EaseIn
        )
    )
    val translateX = animateFloatAsState(
        targetValue = if (animating) (15.dp).toPx(context) else 0f,
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
                .width(6.dp)
                .height(24.dp)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(0f, 1f)
                    rotationZ = rotation.value
                    translationX = translateX.value
                }
                .background(color.value)
        )
    }
}

@Composable
@Preview
fun UnselectedIndicationNext() {
    val context = LocalContext.current
    var animating by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        animating = true
    }

    val rotation = animateFloatAsState(
        targetValue = if (animating) 90f else 0f,
        label = "",
        animationSpec = tween(
            durationMillis = 700,
            easing = EaseIn
        )
    )

    val color = animateColorAsState(
        targetValue = if (animating) Color(0xFFD9D9D9)
        else Color(0xFF3578EA),
        label = "color",
        animationSpec = tween(
            durationMillis = 700,
            easing = EaseIn
        )
    )
    val translateX = animateFloatAsState(
        targetValue = if (animating) -(15.dp).toPx(context) else 0f,
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
                .width(6.dp)
                .height(24.dp)
                .graphicsLayer {
                    transformOrigin = TransformOrigin(1f, 1f)
                    rotationZ = rotation.value
                    translationX = translateX.value
                }
                .background(color.value)
        )
    }
}

@Composable
@Preview
fun SelectedPrevious() {
    Row {
        SelectedIndicationPrevious()
        UnselectedIndicationPrevious()
    }
}

@Composable
@Preview
fun SelectedNext() {
    Row {
        UnselectedIndicationNext()
        SelectedIndicationNext()
    }
}

@Preview
@Composable
fun UnAnimatedIndication(isSelected: Boolean = false, rotation: Float = 0f, origin: TransformOrigin = TransformOrigin.Center, translateX: Float = 0f) {
    Column(
        modifier = Modifier
            .width(32.dp)
            .height(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Box(
            modifier = Modifier
                .width(if (isSelected) 6.dp else 24.dp)
                .height(if (isSelected) 24.dp else 6.dp)
                .background(if (isSelected) Color(0xFF3578EA) else Color(0xFFD9D9D9))
                .graphicsLayer {
                    transformOrigin = origin
                    rotationZ = rotation
                    translationX = translateX
                }
        )
    }
}