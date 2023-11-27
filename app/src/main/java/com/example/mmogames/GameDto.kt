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
    minimum_system_requirements_var: MinimumSystemRequirements?,
    var screenshotArray: ArrayList<String>
) {
    var minimum_system_requirements: MinimumSystemRequirements?
        get() {
            if (field != null) {
                if (field!!.os == null && field!!.graphics == null
                    && field!!.processor == null && field!!.memory == null
                    && field!!.storage == null
                ) {
                    return null
                }
            }
            return field
        }

    init {
        minimum_system_requirements = minimum_system_requirements_var
    }
}

class MinimumSystemRequirements(
    val os: String?,
    val graphics: String?,
    val processor: String?,
    val memory: String?,
    val storage: String?
) {
}