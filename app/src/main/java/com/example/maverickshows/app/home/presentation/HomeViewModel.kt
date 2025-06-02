package com.example.maverickshows.app.home.presentation

import com.example.maverickshows.app.home.data.HomeDataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeDataRepositoryImpl: HomeDataRepositoryImpl
): ViewModel() {

}