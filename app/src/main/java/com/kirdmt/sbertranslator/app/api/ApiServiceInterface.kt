package com.kirdmt.sbertranslator.app.api

import com.kirdmt.sbertranslator.app.models.TranslatedWord
import com.kirdmt.sbertranslator.app.util.Constants
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiServiceInterface {

    //trnsl.1.1.20191103T124950Z.3cb102d7f318572a.aef818e1bd05ac29c2475c641f6da67b63de88c2
    //en de ru
    //https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191103T124950Z.3cb102d7f318572a.aef818e1bd05ac29c2475c641f6da67b63de88c2&text=box&lang=en-ru&format=plain


    /*? [key=<API-ключ>]
    & [text=<переводимый текст>]
    & [lang=<направление перевода>]
    & [format=<формат текста>]
    & [options=<опции перевода>]
    & [callback=<имя callback-функции>]
*/
    //https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20191103T124950Z.3cb102d7f318572a.aef818e1bd05ac29c2475c641f6da67b63de88c2&text=box&lang=en-ru


    @POST(Constants.ADDITIONAL_URL)
    @FormUrlEncoded
    fun translate(
        @Field(Constants.KEY_FIELD) key: String,
        @Field(Constants.TEXT_FIELD) text: String,
        @Field(Constants.LANG_FIELD) lang: String
    ): Single<TranslatedWord>


    companion object Factory {
        fun create(): ApiServiceInterface {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()

            return retrofit.create(ApiServiceInterface::class.java);
        }
    }
}