package com.app.localeexample

import android.content.res.Resources
import android.os.Bundle
import androidx.core.os.ConfigurationCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {
    private lateinit var currentSystemLocaleCode: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentSystemLocaleCode =
            ConfigurationCompat.getLocales(Resources.getSystem().configuration).get(0).language
        if (storage.getPreferredLocale() == LocaleUtil.OPTION_PHONE_LANGUAGE) {
            if (currentSystemLocaleCode in LocaleUtil.supportedLocales) {
                tvAppLocale.text = getString(
                    R.string.phone_language,
                    Locale(currentSystemLocaleCode).displayLanguage
                )
            } else {
                //current system language is neither English nor my second language (for me Bangla)
                tvAppLocale.text = "English"
            }
        } else {
            if (currentSystemLocaleCode == storage.getPreferredLocale()) {
                tvAppLocale.text = getString(
                    R.string.phone_language,
                    Locale(currentSystemLocaleCode).displayLanguage
                )
            } else {
                tvAppLocale.text = Locale(storage.getPreferredLocale()).displayLanguage
            }
        }
        initRadioButtonUI()
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.op1 -> {
                    updateAppLocale("en")
                    recreate()
                }
                R.id.op2 -> {
                    updateAppLocale("bn")
                    recreate()
                }
                R.id.op3 -> {
                    updateAppLocale("hi")
                    recreate()
                }
                R.id.op4 -> {
                    updateAppLocale("ar")
                    recreate()
                }
            }
        }
    }

    private fun initRadioButtonUI() {
        op1.text = getLanguageNameByCode("en")
        op2.text = getLanguageNameByCode("bn")
        op3.text = getLanguageNameByCode("hi")
        op4.text = getLanguageNameByCode("ar")
        when (storage.getPreferredLocale()) {
            "en" -> op1.isChecked = true
            "bn" -> op2.isChecked = true
            "hi" -> op3.isChecked = true
            "ar" -> op4.isChecked = true
        }
    }

    private fun getLanguageNameByCode(code: String): String {
        val tempLocale = Locale(code)
        return tempLocale.getDisplayLanguage(tempLocale)
    }

    private fun updateAppLocale(locale: String) {
        storage.setPreferredLocale(locale)
        LocaleUtil.applyLocalizedContext(applicationContext, locale)
    }
}