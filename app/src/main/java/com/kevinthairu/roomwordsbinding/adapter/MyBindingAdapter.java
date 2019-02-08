package com.kevinthairu.roomwordsbinding.adapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.kevinthairu.roomwordsbinding.dataStorage.Word;

import java.util.List;

public class MyBindingAdapter {

    //Binding Adapter can be put in any class.
    @BindingAdapter("android:words")
    public static void setWords(RecyclerView recyclerView, List<Word> liveData){
        if (recyclerView.getAdapter() instanceof BindableAdapter){
            ((BindableAdapter) recyclerView.getAdapter()).setWords(liveData);
        }
    }
}
