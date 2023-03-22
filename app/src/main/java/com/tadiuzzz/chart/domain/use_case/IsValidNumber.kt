package com.tadiuzzz.chart.domain.use_case

class IsValidNumber {

    operator fun invoke(number: String): Boolean {
        val allSymbolsAcceptable = number.all {
            numberSymbols.contains(it)
        }
        if (number.isBlank()) return true
        if (!allSymbolsAcceptable) return false
        return number.toDoubleOrNull() != null
    }

    companion object {
        private val numberSymbols = "0123456789"
    }

}