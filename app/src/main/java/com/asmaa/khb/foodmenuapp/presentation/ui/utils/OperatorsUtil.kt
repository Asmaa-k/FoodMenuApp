package com.asmaa.khb.foodmenuapp.presentation.ui.utils

/* format data returned from api */
fun Int.formatSingleDigitNumber(): String = String.format("%02d", this)
fun Float.formatFloatNumber(): String = "%.2f".format(this)
fun Int.intGreaterThanChecker(): Boolean = this > 0

