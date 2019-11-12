package com.kirdmt.sbertranslator.app.di.module

import android.app.Activity
import com.kirdmt.sbertranslator.app.api.ApiServiceInterface
import com.kirdmt.sbertranslator.app.ui.main.MainContract
import com.kirdmt.sbertranslator.app.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private var activity: Activity) {

    @Provides
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    fun providePresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    @Provides
    fun provideApiService(): ApiServiceInterface {
        return ApiServiceInterface.create()
    }

}