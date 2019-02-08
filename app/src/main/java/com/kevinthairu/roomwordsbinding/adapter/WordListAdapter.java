package com.kevinthairu.roomwordsbinding.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kevinthairu.roomwordsbinding.dataStorage.Word;
import com.kevinthairu.roomwordsbinding.databinding.RecyclerItemBinding;

import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> implements BindableAdapter <List<Word>> {

    private final LayoutInflater mInflater;
    private List<Word> mWords; // Cached copy of words

    public WordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Binding necessary here and in the WordViewHolder class to prevent the error java.lang.IllegalStateException: ViewHolder views must not be attached when created
        RecyclerItemBinding recyclerItemBinding = RecyclerItemBinding.inflate(mInflater, parent,false);
        return new WordViewHolder(recyclerItemBinding);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mWords != null) {
            Word current = mWords.get(position);
            holder.itemBinding.textView.setText(current.getWord());
        } else {
            // Covers the case of data not being ready yet.
            holder.itemBinding.textView.setText("No Word");
        }
    }

    public void setWords(List<Word> words){
        mWords = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mWords != null)
            return mWords.size();
        else return 0;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerItemBinding itemBinding;

        //ViewHolder takes in a binding instead of a view; Very important
        private WordViewHolder(@NonNull RecyclerItemBinding binding) {
            super(binding.getRoot());
            binding.executePendingBindings();
            itemBinding = binding;
        }
    }

    //a method to get the word at a given position.
    public Word getWordPosition (int position){
        return mWords.get(position);
    }
}
