package com.example.myviewlist;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    ListView listviewData;
    ArrayAdapter<String> adapter;
    ArrayList arrayContent = new ArrayList<String>(Arrays.asList("Android","React","Native","Java"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewData = findViewById(R.id.listviewData);
        adapter = new CustomAdapter(this, arrayContent);
        listviewData.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_done) {
            String itemSelected = "Selected Items: ";
            for(int i=0;i<listviewData.getCount();i++) {
                if(listviewData.isItemChecked(i)) {
                    itemSelected += "\n" + listviewData.getItemAtPosition(i);
                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}