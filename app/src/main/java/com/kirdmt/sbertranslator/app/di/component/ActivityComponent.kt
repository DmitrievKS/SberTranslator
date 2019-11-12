package com.kirdmt.sbertranslator.app.di.component

import com.kirdmt.sbertranslator.app.di.module.ActivityModule
import com.kirdmt.sbertranslator.app.ui.main.MainActivity

import dagger.Component

@Component(modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

}