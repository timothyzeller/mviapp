package com.example.mviapp.utils

import androidx.fragment.app.Fragment
import com.example.mviapp.App
import com.example.mviapp.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = App().providePlantRepository(requireContext())
    return ViewModelFactory(repository, this)
}
