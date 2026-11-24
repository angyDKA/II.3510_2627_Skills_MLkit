package com.isep.shared.domain

object BarcodeText {
    fun appendToDescription(description: String, value: String): String {
        val code = value.trim()
        if (code.isEmpty()) return description
        val line = "Barcode: $code"
        if (description.lineSequence().any { it == line }) return description
        return if (description.isBlank()) line else description.trimEnd() + "\n" + line
    }
}
