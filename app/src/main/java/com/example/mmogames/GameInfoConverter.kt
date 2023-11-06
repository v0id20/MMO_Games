package com.example.mmogames

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Converter
import java.io.IOException


class GameInfoConverter() :
    Converter<ResponseBody, GameDto?> {
//    private val mConverter: Converter<ResponseBody, GameDto?>
//    init {
//        mConverter = converter
//    }

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): GameDto? {
        lateinit var kakaha: String
        kakaha = String(value.bytes())
        try {
            var parser: JsonParser = JsonParser()
            var json: JsonElement = parser.parse(kakaha)

            try {
                var screneshotJson: JsonArray = (json as JsonObject).getAsJsonArray("screenshots")
                var screenshots: ArrayList<String> = ArrayList()
                for (i in 0..screneshotJson.size() - 1) {
                    var idjs: JsonObject = screneshotJson.get(i) as JsonObject
                    screenshots += idjs.get("image").asString
                    Log.i("parse converter machine", "web: " + idjs.get("image").asString)
                }
                val gson: Gson = Gson()
                val dataModel: GameDto? = gson.fromJson<GameDto?>(json, GameDto::class.java)
                dataModel?.screenshotArray = screenshots
                return dataModel
            } catch (e: JSONException) {
                Log.d("convert", "no screenshots in JSON")
                return null
            }
        } catch (e: JSONException) {
            Log.i("convert", "problem with exctraction of value")
            return null
        }
    }
}