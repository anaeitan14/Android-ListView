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

public class MainActivity extends AppCompatActivity {


    ListView listviewData;
    ArrayAdapter<String> adapter;
    String[] arrayContent = {"Android", "Apple","React","Lenovo","Nvidia","Java","Selenium"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listviewData = findViewById(R.id.listviewData);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayContent);
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