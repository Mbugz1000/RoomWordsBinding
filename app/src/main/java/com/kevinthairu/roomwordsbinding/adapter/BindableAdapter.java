package com.kevinthairu.roomwordsbinding.adapter;

public interface BindableAdapter<T> {
       //Thisallowsus to use the Binding Adapter with the specific Recycler Adapters we want
       void setWords(T word);
}
