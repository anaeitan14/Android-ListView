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
import android.widget.TextView;
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
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ListView listviewData;
    ArrayAdapter<Product> adapter;
    ArrayList arrayContent = new ArrayList<Product>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new CustomAdapter(getBaseContext(), arrayContent);

        listviewData = findViewById(R.id.listviewData);
        readItems();
        updateProduct();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");


        if (item.getItemId() == R.id.item_done) {
            String itemSelected = "Selected Items: \n";
            for (int i = 0; i < listviewData.getCount(); i++) {
                try {
                    CheckBox cb = listviewData.getChildAt(i).findViewById(R.id.checkBox);
                    if (cb.isChecked()) {
                        itemSelected += listviewData.getItemAtPosition(i) + " ";
                    }
                } catch (Exception e) {

                }
            }
            Toast.makeText(this, itemSelected, Toast.LENGTH_SHORT).show();
        }


        if (item.getItemId() == R.id.remove_item) {
            for (int i = 0; i < listviewData.getCount(); i++) {
                try {
                    CheckBox cb = listviewData.getChildAt(i).findViewById(R.id.checkBox);
                    if (cb.isChecked()) {
//                        TextView tvProductName = (TextView) listviewData.getChildAt(i).findViewById(R.id.productName);
//                        String productToDelete = tvProductName.getText().toString();
//                        myRef.child(productToDelete).removeValue();

                        myRef.child(listviewData.getItemAtPosition(i) + "").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getBaseContext(), "Item removed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                } catch (Exception e) {

                }
            }
        }
        return true;
    }

    public void addItem(View view) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");

        EditText et_item = findViewById(R.id.etItem);
        String item = et_item.getText().toString().trim();

        Product newProduct = new Product(item, 4.3, "Israeli snack", 250);


        myRef.child(newProduct.getName()).setValue(newProduct).addOnCompleteListener(
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
                for (DataSnapshot itemSnapShot : snapshot.getChildren()) {
                    Product temp = new Product(
                            itemSnapShot.getValue(Product.class).getName(),
                            itemSnapShot.getValue(Product.class).getPrice(),
                            itemSnapShot.getValue(Product.class).getInfo(),
                            itemSnapShot.getValue(Product.class).getQty()
                    );
                    arrayContent.add(temp);
                }

                listviewData.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateProduct() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("items");
        HashMap update = new HashMap();
        update.put("price", 53.4);
        myRef.child("test").updateChildren(update).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                Toast.makeText(MainActivity.this, "Item updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}