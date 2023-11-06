package com.example.mmogames

class GameDto(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val genre: String,
    val short_description: String,
    val game_url: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    val release_date: String,
    val profile_url: String,
    var minimum_system_requirements: MinimumSystemRequirements?,
    var screenshotArray: ArrayList<String>
) {
}

class MinimumSystemRequirements(
    val os: String,
    val processor: String,
    val memory: String,
    val storage: String
) {
}