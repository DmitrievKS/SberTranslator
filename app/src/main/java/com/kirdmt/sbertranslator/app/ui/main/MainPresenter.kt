package com.kirdmt.sbertranslator.app.ui.main


import android.util.Log
import com.kirdmt.sbertranslator.app.api.ApiServiceInterface
import com.kirdmt.sbertranslator.app.models.SavedWord
import com.kirdmt.sbertranslator.app.room.SavedWordDao
import com.kirdmt.sbertranslator.app.room.SavedWordRepository
import com.kirdmt.sbertranslator.app.room.SavedWordRoomDatabase
import com.kirdmt.sbertranslator.app.util.Constants
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class MainPresenter() : MainContract.Presenter {

    private val subscriptions = CompositeDisposable()
    private val api: ApiServiceInterface = ApiServiceInterface.create()
    private lateinit var mainView: MainContract.View

    lateinit var db: SavedWordRoomDatabase
    lateinit var dao: SavedWordDao
    lateinit var repository: SavedWordRepository

    override fun translate(word: String, language: String) {

        api.translate(Constants.API_KEY, word, language)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                if (!word.equals(result.text[0])) {
                    mainView?.setTranslation(result.text[0])
                    insert(word, result.text[0])
                }
            }, { error ->
                //Log.d("ResultTAG", "We have error ")
                error.printStackTrace()
            })

    }

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View) {

        mainView = view
        initRoom()
        getRoomData()
        //deleteAll()
        //getCount()

    }

    fun deleteAll() {
        GlobalScope.launch(Dispatchers.Main) {
            repository.deleteAll()
        }
    }

    fun initRoom() {

        db = SavedWordRoomDatabase.getDatabase(mainView.getContext())
        dao = db.savedWordDao()
        repository = SavedWordRepository(dao)

    }

    fun getCount() {
        GlobalScope.launch(Dispatchers.Main) {
            Log.d("RoomTag", "Row count: " + repository.getCount())
        }
    }

    fun insert(nativeWord: String, translatedWord: String) {


        GlobalScope.launch(Dispatchers.Main) {
            val count: Int = repository.getCountMatches(nativeWord)

            if (count == 0)
                GlobalScope.launch(Dispatchers.Main) {
                    repository.insert(SavedWord(nativeWord, translatedWord))
                }
        }

    }

    fun getRoomData() {

        repository!!.getSavedWords()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ words ->
                mainView?.setAdapterData(words as ArrayList<SavedWord>)
                //Log.d("RoomTag", "First row: " + words.get(0).toString())
            }, { error ->
                error.printStackTrace()
            })

    }

    override fun findSpecialData(search: String) {

        if (search.length > 0) {
            GlobalScope.launch(Dispatchers.Main) {

                var listSpesialWords: List<SavedWord>? =
                    repository.searchSavedWords(search)

                if (!listSpesialWords!!.isEmpty()) {
                    mainView?.setAdapterData(listSpesialWords as ArrayList<SavedWord>)
                }

            }
        } else {
            getRoomData()
        }
    }


}