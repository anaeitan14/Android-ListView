package com.example.myviewlist;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView listviewData;
    ArrayAdapter<String> adapter;
    ArrayList arrayContent = new ArrayList<String>();
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase = FirebaseDatabase.getInstance();

        listviewData = findViewById(R.id.listviewData);
        System.out.println(arrayContent);
        readItems();
        System.out.println(arrayContent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_done) {
            String itemSelected = "Selected Items: \n";
            for(int i=0;i<listviewData.getCount();i++) {
                try {

                    CheckBox cb = (CheckBox)listviewData.getChildAt(i).findViewById(R.id.checkBox);
                    if(cb.isChecked()) {
                        itemSelected += listviewData.getItemAtPosition(i) +" ";
                    }
                } catch (Exception e) {

                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    public void addItem(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");

        EditText et_item = findViewById(R.id.etItem);
        String item = et_item.getText().toString().trim();
        int item_id = (int)(Math.random()*100);

        myRef.child(item_id+"").setValue(item).addOnCompleteListener(
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            et_item.setText("");
                        }
                    }
                });
    }

    public void readItems() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");
        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayContent.clear();
                for(DataSnapshot itemSnapShot : snapshot.getChildren()) {
                    arrayContent.add((itemSnapShot.getValue().toString()));
                }
                adapter = new CustomAdapter(getBaseContext(), arrayContent);
                listviewData.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("cancelled fetch");
            }
        });

    }
}