package io.github.maikotrindade.nomadrewards.ui.base

import android.app.Application
import io.github.maikotrindade.nomadrewards.network.ApiService
import io.github.maikotrindade.nomadrewards.network.ApiServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class ApplicationController : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()

            androidContext(this@ApplicationController)
            modules(
                module { single<ApiService> { ApiServiceImpl() } }
            )
        }
    }
}