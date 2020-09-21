package ru.mikov.sbdelivery.extensions

import android.text.TextUtils
import android.util.Patterns

fun String.hasDigits(): Boolean {
    return this.matches(".*\\d+.*".toRegex())
}

fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
