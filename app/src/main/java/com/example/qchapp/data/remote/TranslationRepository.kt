package com.example.qchapp.data.remote

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
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

    private val spanishTranslator by lazy {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.SPANISH)
            .build()

        Translation.getClient(options)
    }

    suspend fun prepareTranslationModels() {
        val conditions = DownloadConditions.Builder()
            .build()

        val modelManager = RemoteModelManager.getInstance()

        val spanishModel = TranslateRemoteModel.Builder(TranslateLanguage.SPANISH)
            .build()

        englishTranslator
            .downloadModelIfNeeded(conditions)
            .await()

        spanishTranslator
            .downloadModelIfNeeded(conditions)
            .await()

        modelManager
            .download(spanishModel, conditions)
            .await()
    }

    suspend fun translateToEnglish(text: String): String {
        return englishTranslator
            .translate(text)
            .await()
    }

    suspend fun translateToSpanish(text: String): String {
        return spanishTranslator
            .translate(text)
            .await()
    }
}