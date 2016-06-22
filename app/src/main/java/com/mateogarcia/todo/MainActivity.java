package com.mateogarcia.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    // ArrayAdapter<String> aTodoAdapter;
    ItemsAdapter itemsAdapter;
    ListView lvItems;
    EditText etEditText;

    private final int REQUEST_CODE = 20;

    private int editingItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();

        lvItems = (ListView) findViewById(R.id.lvlItems);
        lvItems.setAdapter(itemsAdapter);
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Must notify adapter that list view needs to be refreshed.
                todoItems.remove(position);
                itemsAdapter.notifyDataSetChanged();

                writeItems();

                // Return true to activate.
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                editingItem = i;
                intent.putExtra("text", todoItems.get(i));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        etEditText = (EditText) findViewById(R.id.etEditText);
    }

    public void populateArrayItems() {
        todoItems = new ArrayList<String>();
        readItems();
        itemsAdapter = new ItemsAdapter(this, todoItems);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {

        }
    }

    public void onAddItem(View view) {
        itemsAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String editedText = data.getExtras().getString("updatedtext");
            todoItems.set(editingItem, editedText);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }
}
