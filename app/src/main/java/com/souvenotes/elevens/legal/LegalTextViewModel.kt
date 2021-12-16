package com.souvenotes.elevens.legal

import android.content.res.AssetManager
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_LEGACY
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

class LegalTextViewModel(
    private val assetManager: AssetManager
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading.distinctUntilChanged()

    private val _legalText = MutableLiveData<Spanned>()
    val legalText: LiveData<Spanned> = _legalText.distinctUntilChanged()

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean> = _showError.distinctUntilChanged()

    fun loadFile(filename: String) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val fileString = getText(filename)
                _legalText.postValue(getHtml(fileString))
            } catch (e: Exception) {
                _showError.postValue(true)
            } finally {
                _loading.postValue(false)
            }
        }
    }

    private fun getText(filename: String): String {
        val stringBuilder = StringBuilder()
        val inputStream = assetManager.open(filename)
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var nextLine = bufferedReader.readLine()
        while (nextLine != null) {
            stringBuilder.append(nextLine)
            nextLine = bufferedReader.readLine()
        }
        bufferedReader.close()
        inputStreamReader.close()
        inputStream.close()
        return stringBuilder.toString()
    }

    private fun getHtml(html: String): Spanned {
        return HtmlCompat.fromHtml(html, FROM_HTML_MODE_LEGACY)
    }
}