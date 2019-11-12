package com.kirdmt.sbertranslator.app.ui.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirdmt.sbertranslator.R
import com.kirdmt.sbertranslator.app.di.component.DaggerActivityComponent
import com.kirdmt.sbertranslator.app.di.module.ActivityModule
import com.kirdmt.sbertranslator.app.models.SavedWord
import com.kirdmt.sbertranslator.app.ui.adapters.SavedWordAdapter
import com.kirdmt.sbertranslator.app.util.Constants
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), MainContract.View {


    private var reversBtn: Button? = null
    private var translateBtn: Button? = null
    private var searchBtn: Button? = null
    private var text: TextView? = null
    private var editTranslate: EditText? = null
    private var editSearch: EditText? = null
    private var spinner_native: Spinner? = null
    private var spinner_translation: Spinner? = null

    private var words: ArrayList<SavedWord> = ArrayList()
    private var wordsAdapter = SavedWordAdapter(
        words,
        { word -> wordItemClicked(word) })

    @Inject
    lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        uiConstruct()
        injectDependency()

        presenter.attach(this)
        presenter.subscribe()

    }

    override fun showProgress(show: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showErrorMessage(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setTranslation(translation: String) {
        text?.setText(translation)
    }

    override fun getContext(): Context {
        return applicationContext
    }

    override fun setAdapterData(words: ArrayList<SavedWord>) {

        this.words = words

        var wordsAdapter = SavedWordAdapter(
            this.words,
            { word -> wordItemClicked(word) })

        words_rv.adapter = wordsAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unsubscribe()
    }


    private fun uiConstruct() {
        reversBtn = findViewById(R.id.reverse_btn) as Button
        translateBtn = findViewById(R.id.translate_btn) as Button
        searchBtn = findViewById(R.id.search_btn) as Button
        text = findViewById(R.id.text) as TextView
        editTranslate = findViewById(R.id.edit_translate) as EditText
        editSearch = findViewById(R.id.edit_search) as EditText
        spinner_native = findViewById(R.id.spinner_native)
        spinner_native?.setSelection(Constants.LANG_RUSSIAN_POSITION)
        spinner_translation = findViewById(R.id.spinner_translate)
        spinner_translation?.setSelection(Constants.LANG_ENGLISH_POSITION)


        words_rv.layoutManager = LinearLayoutManager(this)
        words_rv.adapter = wordsAdapter
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()

        activityComponent.inject(this)
    }

    fun translateButtonOnClick(view: View) {

        val word: String = editTranslate?.text.toString()
        var language: String? = null

        if (spinner_native?.selectedItemPosition == Constants.LANG_RUSSIAN_POSITION && spinner_translation?.selectedItemPosition == Constants.LANG_ENGLISH_POSITION) {
            language = Constants.LANG_RU_EN
        } else if (spinner_native?.selectedItemPosition == Constants.LANG_ENGLISH_POSITION && spinner_translation?.selectedItemPosition == Constants.LANG_RUSSIAN_POSITION) {
            language = Constants.LANG_EN_RU
        } else if (spinner_native?.selectedItemPosition == Constants.LANG_RUSSIAN_POSITION && spinner_translation?.selectedItemPosition == Constants.LANG_DEUTSCH_POSITION) {
            language = Constants.LANG_RU_DE
        } else if (spinner_native?.selectedItemPosition == Constants.LANG_DEUTSCH_POSITION && spinner_translation?.selectedItemPosition == Constants.LANG_RUSSIAN_POSITION) {
            language = Constants.LANG_DE_RU
        } else if (spinner_native?.selectedItemPosition == Constants.LANG_ENGLISH_POSITION && spinner_translation?.selectedItemPosition == Constants.LANG_DEUTSCH_POSITION) {
            language = Constants.LANG_EN_DE
        } else if (spinner_native?.selectedItemPosition == Constants.LANG_DEUTSCH_POSITION && spinner_translation?.selectedItemPosition == Constants.LANG_ENGLISH_POSITION) {
            language = Constants.LANG_DE_EN
        }

        presenter.translate(word, language!!)
    }

    fun reverseButtonOnClick(view: View) {

        var current_position_native: Int? = spinner_native?.selectedItemPosition
        var current_position_translation: Int? = spinner_translation?.selectedItemPosition

        spinner_native?.setSelection(current_position_translation!!)
        spinner_translation?.setSelection(current_position_native!!)
    }

    private fun wordItemClicked(word: SavedWord) {
        //Do something
    }

    fun searchButtonOnClick(view: View) {
        val search: String = editSearch?.text.toString()
        presenter.findSpecialData(search)
    }

}
