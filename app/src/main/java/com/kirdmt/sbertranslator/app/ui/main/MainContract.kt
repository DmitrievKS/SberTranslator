package com.kirdmt.sbertranslator.app.ui.main

import android.content.Context
import com.kirdmt.sbertranslator.app.models.SavedWord
import com.kirdmt.sbertranslator.app.ui.base.BaseContract

class MainContract {

    interface View : BaseContract.View {

        fun showProgress(show: Boolean)
        fun showErrorMessage(error: String)
        fun setTranslation(str: String)
        fun getContext() : Context
        fun setAdapterData(words: ArrayList<SavedWord>)

    }

    interface Presenter : BaseContract.Presenter<MainContract.View> {
        fun translate(str: String, language: String)
        fun findSpecialData(search: String)
    }
}