package com.example.mmogames

import android.util.Log
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class GameInfoConerterFactory : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit
    ): Converter<ResponseBody, GameDto?>? {
        Log.i("responseBodyConverter", "zashel")
        return try {
            // Use TypeToken of Gson to get type literal for the parameterized type represented Data<T>
            val dataType: Type = TypeToken.getParameterized(GameDto::class.java, type).type
//            val converter: Converter<ResponseBody, GameDto?> =
//                retrofit.nextResponseBodyConverter(this, dataType, annotations)
//
            GameInfoConverter()
        } catch (e: Exception) {
            Log.i("responseBodyConverter", "catch null")
            null
        }
    }
}
