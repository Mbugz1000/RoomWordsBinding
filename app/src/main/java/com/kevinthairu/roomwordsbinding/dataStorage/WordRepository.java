package com.kevinthairu.roomwordsbinding.dataStorage;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
// Class that creates an object that links the data from different sources to the application
public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    //This constrictor gets a handle to the database and initializes the member variables
    public WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
    }

    //This is a wrapper method that returns the cached words as LiveData
    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    public void insert(Word word){
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll(){
        new deleteAllWordsAsyncTask(mWordDao).execute();
    }

    public void deleteWord(Word word){
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public static class deleteAllWordsAsyncTask extends AsyncTask<Void,Void,Void>{
        private WordDao mAsyncTaskDao;

        deleteAllWordsAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    public static class deleteWordAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            //Selecting the first parameter
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}
