package com.example.loftmoney.fragments;

import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import com.example.loftmoney.LoftApp;
import com.example.loftmoney.api.LoftAPI;
import com.example.loftmoney.items.Item;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BudgetViewModel extends ViewModel {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<List<Item>> itemsLiveData = new MutableLiveData();
    public MutableLiveData<String> messageString = new MutableLiveData();
    public MutableLiveData<String> messageInt = new MutableLiveData();
    public MutableLiveData<Boolean> swipeRefresh = new MutableLiveData<>(false);

    public void loadData(LoftAPI loftAPI, int currentPosition, SharedPreferences sharedPreferences) {
        String authToken = sharedPreferences.getString(LoftApp.AUTH_KEY, "");
        String type;
        if (currentPosition == 0) {
            type = "expense";
        } else {
            type = "income";
        }
        Disposable disposable = loftAPI.getItems(type, authToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(remoteItems -> {
                    swipeRefresh.postValue(false);
                    if (remoteItems.getStatus().equals("success")) {
                        itemsLiveData.postValue(remoteItems.getMoneyItemsList());
                    } else {
                        messageInt.postValue("There was an error getting the list");
                    }
                }, throwable -> {
                    swipeRefresh.postValue(false);
                    messageString.postValue(throwable.getLocalizedMessage());
                });

        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
