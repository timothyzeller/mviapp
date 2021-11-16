package com.example.mviapp.utils

import androidx.lifecycle.LiveData

/**
 * Implementation of [androidx.lifecycle.LiveData] which does not accepts nullable values
 * and should be initialized with an value upon creation
 */
class NonNullableMutableLiveData<T>(value: T) : LiveData<T>(value) {

    override fun getValue(): T {
        return super.getValue() as T
    }

    public override fun setValue(value: T) {
        super.setValue(value)
    }

    public override fun postValue(value: T) {
        super.postValue(value)
    }
}