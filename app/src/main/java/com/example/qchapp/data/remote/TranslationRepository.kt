package com.example.qchapp.data.remote

import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.tasks.await

object TranslationRepository {

    private val englishTranslator by lazy {

        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.SPANISH)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()

        Translation.getClient(options)
    }

    suspend fun translateToEnglish(
        text: String
    ): String {

        englishTranslator
            .downloadModelIfNeeded()
            .await()

        return englishTranslator
            .translate(text)
            .await()
    }
}