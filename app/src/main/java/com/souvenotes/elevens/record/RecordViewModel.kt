package com.souvenotes.elevens.record

import androidx.lifecycle.*
import com.souvenotes.elevens.utils.ElevensSharedPreferences
import kotlinx.coroutines.launch

class RecordViewModel(
    private val elevensSharedPrefs: ElevensSharedPreferences
) : ViewModel() {

    private val _totalWins = MutableLiveData<Int>()
    val totalWins: LiveData<Int> = _totalWins.distinctUntilChanged()

    private val _totalLosses = MutableLiveData<Int>()
    val totalLosses: LiveData<Int> = _totalLosses.distinctUntilChanged()

    private val _totalGames = MutableLiveData<Int>()
    val totalGames: LiveData<Int> = _totalGames.distinctUntilChanged()

    private val _totalResets = MutableLiveData<Int>()
    val totalResets: LiveData<Int> = _totalResets.distinctUntilChanged()

    init {
        _totalWins.postValue(elevensSharedPrefs.totalWins)
        _totalLosses.postValue(elevensSharedPrefs.totalLosses)
        _totalGames.postValue(elevensSharedPrefs.totalGames)
        _totalResets.postValue(elevensSharedPrefs.totalResets)
    }

    fun resetRecord() {
        viewModelScope.launch {
            with(elevensSharedPrefs) {
                totalWins = 0
                totalLosses = 0
                totalGames = 0
                totalResets = 0
            }
            _totalWins.postValue(elevensSharedPrefs.totalWins)
            _totalLosses.postValue(elevensSharedPrefs.totalLosses)
            _totalGames.postValue(elevensSharedPrefs.totalGames)
            _totalResets.postValue(elevensSharedPrefs.totalResets)
        }
    }
}