package com.example.xzxz.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xzxz.Dish;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Dish>> dishList;

    public HomeViewModel() {
        dishList = new MutableLiveData<>();
    }

    public LiveData<List<Dish>> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishes) {
        dishList.setValue(dishes);
    }
}
