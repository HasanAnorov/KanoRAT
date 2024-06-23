package com.ierusalem.androrat.core.app

import android.app.Application
import android.app.LocaleManager
import android.app.UiModeManager
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.ierusalem.androrat.core.data.preferences.DataStorePreferenceRepository
import com.ierusalem.androrat.core.emulator_detection.EmulatorDetector
import com.ierusalem.androrat.core.utils.log
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class AndroRatApp: Application() {

    companion object {
        var applicationScope = MainScope()
    }

    @Inject
    lateinit var dataStorePreferenceRepository: DataStorePreferenceRepository

    override fun onCreate() {
        super.onCreate()

        val isRunningOnEmulatorDetector = EmulatorDetector.isRunningOnEmulator()
        if (isRunningOnEmulatorDetector != null && isRunningOnEmulatorDetector) {
            log("Emulator detected!")
            throw IllegalStateException("Mobile Device Required!")
        }

        applicationScope.launch {
            dataStorePreferenceRepository.getLanguage.collect { languageCode ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    applicationContext.getSystemService(LocaleManager::class.java).applicationLocales =
                        LocaleList.forLanguageTags(languageCode)
                } else {
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.forLanguageTags(languageCode)
                    )
                }
            }
        }

        applicationScope.launch {
            dataStorePreferenceRepository.getTheme.collect { isSystemInDarkMode ->
                if (isSystemInDarkMode) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        applicationContext.getSystemService(UiModeManager::class.java)
                            .setApplicationNightMode(UiModeManager.MODE_NIGHT_YES)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        applicationContext.getSystemService(UiModeManager::class.java)
                            .setApplicationNightMode(UiModeManager.MODE_NIGHT_NO)
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }

    }

    override fun onLowMemory() {
        super.onLowMemory()
        applicationScope.cancel("onLowMemory() called by system")
        applicationScope = MainScope()
    }

}