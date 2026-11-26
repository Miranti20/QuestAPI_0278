package com.example.tugas9.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tugas9.modeldata.DataSiswa
import com.example.tugas9.repositori.RepositoryDataSiswa
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

sealed interface StatusUiSiswa {
    data class Success(
        val siswa: List<DataSiswa> = listOf()
    ) : StatusUiSiswa

    object Error : StatusUiSiswa
    object Loading : StatusUiSiswa
}

class HomeViewModel(
    private val repositoryDataSiswa: RepositoryDataSiswa
) : ViewModel() {

    var listSiswa: StatusUiSiswa by mutableStateOf(
        StatusUiSiswa.Loading
    )
        private set

    init {
        loadSiswa()
    }

    fun loadSiswa() {
        viewModelScope.launch {
            listSiswa = StatusUiSiswa.Loading

            listSiswa = try {
                StatusUiSiswa.Success(
                    siswa = repositoryDataSiswa.getDataSiswa()
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Log.e(
                    "API_SISWA",
                    "Gagal mengambil data siswa",
                    e
                )
                StatusUiSiswa.Error
            }
        }
    }
}