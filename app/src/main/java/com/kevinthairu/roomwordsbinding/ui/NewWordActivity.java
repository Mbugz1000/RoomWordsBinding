package com.kevinthairu.roomwordsbinding.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.kevinthairu.roomwordsbinding.R;
import com.kevinthairu.roomwordsbinding.databinding.ActivityNewWordBinding;

public class   NewWordActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "com.example.android.roomwordssample.REPLY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityNewWordBinding newWordBinding = DataBindingUtil.setContentView(this,R.layout.activity_new_word);

        newWordBinding.buttonSave.setOnClickListener(v -> {
            Intent replyIntent = new Intent();
            if (TextUtils.isEmpty(newWordBinding.editWord.getText())) {
                setResult(RESULT_CANCELED, replyIntent);
            } else {
                String word = newWordBinding.editWord.getText().toString();
                replyIntent.putExtra(EXTRA_REPLY, word);
                setResult(RESULT_OK, replyIntent);
            }
            finish();
        });
    }
}
