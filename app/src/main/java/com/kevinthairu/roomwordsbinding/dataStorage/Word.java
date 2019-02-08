package com.kevinthairu.roomwordsbinding.dataStorage;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
//Entity for a single row in the Word_Table. There are no classes for Tables, only for the entities
@Entity(tableName = "word_table")
public class Word {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String mWord) {
        this.mWord = mWord;
    }

    public String getWord() {
        return mWord;
    }
}
