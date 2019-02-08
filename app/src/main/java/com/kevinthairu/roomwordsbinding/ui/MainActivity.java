package com.kevinthairu.roomwordsbinding.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kevinthairu.roomwordsbinding.adapter.WordListAdapter;
import com.kevinthairu.roomwordsbinding.dataStorage.Word;
import com.kevinthairu.roomwordsbinding.R;
import com.kevinthairu.roomwordsbinding.viewModel.WordViewModel;
import com.kevinthairu.roomwordsbinding.databinding.ActivityMainBinding;
import com.kevinthairu.roomwordsbinding.databinding.ContentMainBinding;

public class MainActivity extends AppCompatActivity {

    private WordViewModel mWordViewModel;
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            mWordViewModel.insert(word);
        }else{
            Toast.makeText(getApplicationContext(),R.string.empty_not_saved,Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        //executePendingBindings() function is for immediate binding.
        binding.executePendingBindings();
        binding.setLifecycleOwner(this); //not sure if this is necessary4

        setSupportActionBar(binding.toolbar);

        final WordListAdapter adapter = new WordListAdapter(this);
        binding.content.recyclerview.setAdapter(adapter);
        binding.content.recyclerview.setLayoutManager(new LinearLayoutManager(this));

        // Update the cached copy of the words in the adapter, Using the OnChange declared method
        mWordViewModel.getAllWords().observe(this, adapter::setWords);

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,NewWordActivity.class);
            startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = adapter.getWordPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myWord.getWord(), Toast.LENGTH_LONG).show();

                        mWordViewModel.deleteWord(myWord);
                    }
                }
        );

        helper.attachToRecyclerView(binding.content.recyclerview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            Toast.makeText(this, "Clearing the data...", Toast.LENGTH_SHORT).show();
            mWordViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
