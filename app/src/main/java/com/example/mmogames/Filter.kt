package com.example.mmogames

enum class FilterType {CATEGORY, PLATFORM}

class Filter(var id: Int, val text: String, val type: FilterType, var status: Boolean = false) {
}

