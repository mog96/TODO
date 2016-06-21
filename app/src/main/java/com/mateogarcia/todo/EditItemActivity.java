package com.mateogarcia.todo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String text = getIntent().getStringExtra("text");
        etEditText = (EditText) findViewById(R.id.etEditText);
        etEditText.setText(text);
        etEditText.setSelection(etEditText.getText().length());
    }

    private final int REQUEST_CODE = 20;

    public void onUpdate(View view) {
        Intent intent = new Intent(EditItemActivity.this, MainActivity.class);
        intent.putExtra("updatedtext", etEditText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancel(View view) {
        this.finish();
    }
}
