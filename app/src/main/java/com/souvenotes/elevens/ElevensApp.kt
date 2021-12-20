package com.souvenotes.elevens

import android.app.Application
import android.content.res.AssetManager
import com.google.android.gms.ads.MobileAds
import com.souvenotes.elevens.gameboard.GameBoardViewModel
import com.souvenotes.elevens.legal.LegalTextViewModel
import com.souvenotes.elevens.record.RecordViewModel
import com.souvenotes.elevens.utils.ElevensSharedPreferences
import com.souvenotes.elevens.utils.ElevensSharedPreferencesImpl
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class ElevensApp : Application() {

    private val appModule = module {
        single<ElevensSharedPreferences> { ElevensSharedPreferencesImpl(get()) }
        factory<AssetManager> { get<Application>().assets }
        viewModel { GameBoardViewModel(get()) }
        viewModel { RecordViewModel(get()) }
        viewModel { LegalTextViewModel(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@ElevensApp)
            modules(appModule)
        }

        MobileAds.initialize(this)
    }
}