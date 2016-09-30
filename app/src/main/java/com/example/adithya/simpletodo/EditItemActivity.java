package com.example.adithya.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.data;
import static com.example.adithya.simpletodo.R.id.item_name;

public class EditItemActivity extends AppCompatActivity {

    public int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        Intent firsttosecond = getIntent();
        EditText item_name = (EditText) findViewById(R.id.editText);
        String itemfrommain = firsttosecond.getStringExtra("selected_item");
        pos = firsttosecond.getExtras().getInt("position");
        item_name.setText(itemfrommain);

    }

    public void click_save (View v)
    {
        EditText item_name = (EditText) findViewById(R.id.editText);
        Intent secondtofirst = new Intent();
        secondtofirst.putExtra("item_name", item_name.getText().toString());
        secondtofirst.putExtra("pos",pos);
        setResult(RESULT_OK, secondtofirst);
        finish();
    }

}

