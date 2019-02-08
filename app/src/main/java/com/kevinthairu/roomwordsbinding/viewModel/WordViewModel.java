package com.kevinthairu.roomwordsbinding.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.kevinthairu.roomwordsbinding.dataStorage.Word;
import com.kevinthairu.roomwordsbinding.dataStorage.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {
    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    //This now completely hides the implementation from the UI
    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    //This wrapper for the insert method completely hides the implementation of insert from the UI
    public void insert(Word word){
        mRepository.insert(word);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteWord(Word word){
        mRepository.deleteWord(word);
    }
}
