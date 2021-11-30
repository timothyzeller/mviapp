package com.example.mviapp.plants

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mviapp.data.Plant
import com.example.mviapp.data.PlantRepository
import com.example.mviapp.utils.NonNullableMutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.UnicastSubject

class PlantsViewModel(
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _state = NonNullableMutableLiveData(PlantsViewState())
    val state: LiveData<PlantsViewState> = _state

    private val uiSubject: UnicastSubject<PlantsUIAction> = UnicastSubject.create()

    init {
        uiSubject
            .observeOn(Schedulers.io())
            .doOnNext { Log.d("TAG1", Thread.currentThread().name) }
            .concatMap(::onAction)
            .doOnNext { Log.d("TAG2", Thread.currentThread().name) }
            .doOnNext { Log.d("TAG3", Thread.currentThread().name) }
            .subscribe(
                { _state.postValue(it) },
                { Log.e("", "Error") }
            )
            .addTo(compositeDisposable)
    }

    private fun onAction(action: PlantsUIAction): Observable<PlantsViewState> {
        return Observable.fromCallable {
            when (action) {
                PlantsUIAction.RefreshData -> loadPlants()
            }
        }.onErrorResumeNext {
            Observable.fromCallable {
                Log.e("TAG", "Error: $it")
                _state.value
            }
        }
    }

    private fun loadPlants(): PlantsViewState {
        val plants = plantRepository.getPlants()
        return _state.value.copy(plants = plants)
    }

    fun refreshData() {
        uiSubject.onNext(PlantsUIAction.RefreshData)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}

data class PlantsViewState(
    val plants: List<Plant> = emptyList()
)

sealed class PlantsUIAction {
    object RefreshData : PlantsUIAction()
}