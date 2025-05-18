package com.example.smartbottle.core.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.smartbottle.R


val SFProText = FontFamily(
    Font(R.font.sf_pro_text_regular, FontWeight.Normal),
    Font(R.font.sf_pro_text_medium, FontWeight.Medium),
    Font(R.font.sf_pro_text_semibold, FontWeight.SemiBold)
)

val SFProDisplay = FontFamily(
    Font(R.font.sf_pro_display_bold, FontWeight.Bold)
)

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 36.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = SFProText,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = SFProText,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    titleLarge = TextStyle(
        fontFamily = SFProDisplay,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),

    titleMedium = TextStyle(
        fontFamily = SFProText,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,),

    labelSmall = TextStyle(
        fontFamily = SFProText,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

