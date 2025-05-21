package com.example.smartbottle.profile.presentation.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
/*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
*/


@Composable
fun TextFieldView(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "placeholder...",
    textStyle: TextStyle
) {
    BasicTextField(
        singleLine = true,
        value = value,
        onValueChange = onValueChange,
        textStyle = textStyle,
        modifier = modifier,
        decorationBox = { innerTextField ->
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    color = Color.Gray,
                    fontSize = textStyle.fontSize
                )
            }
            innerTextField()
        }
    )
}

fun Modifier.dashedBorder(
    color: Color,
    shape: Shape,
    width: Dp = 1.dp,
    dashWidth: Dp = 4.dp,
    gapWidth: Dp = 4.dp,
    cap: StrokeCap = StrokeCap.Round
): Modifier = this.drawWithContent {
    val outline = shape.createOutline(size, layoutDirection, this)
    val path = Path().apply { addOutline(outline) }
    val stroke = Stroke(
        cap = cap,
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(
            floatArrayOf(dashWidth.toPx() + width.toPx(), gapWidth.toPx() + width.toPx()),
            0f
        )
    )
    drawContent()
    drawPath(path = path, style = stroke, color = color)
}
/*
@Composable
fun PreviewDashedTextField() {
    var input by remember { mutableStateOf("") }
    TextFieldView(
        value = input,
        onValueChange = { input = it },
        placeholder = "Enter something...",
        textStyle = TextStyle(fontSize = 16.sp),
        modifier = Modifier
            .fillMaxWidth()
            .dashedBorder(color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
    )
}
*/
