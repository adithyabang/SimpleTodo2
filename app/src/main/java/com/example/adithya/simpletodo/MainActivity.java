package com.example.adithya.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.attr.data;
import static android.R.attr.y;
import static com.example.adithya.simpletodo.R.id.item_name;
import static com.example.adithya.simpletodo.R.id.lvItems;
import static com.example.adithya.simpletodo.R.id.search_badge;
import static java.lang.reflect.Array.getInt;


public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView) findViewById(R.id.lvItems);
        //items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,R.layout.list_view_row,R.id.listText, items);
        lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        //items.add("Second Item");
        //items.add("Third Item");
        setupListViewListener();
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View item,int pos, long id) {
                        TextView listText = (TextView) item.findViewById(R.id.listText);
                        String text = listText.getText().toString();
                        //String indexid = String.valueOf(pos);
                        Intent firstosecond = new Intent(MainActivity.this, EditItemActivity.class);
                        firstosecond.putExtra("key id", id);
                        firstosecond.putExtra("position",itemsAdapter.getPosition(text));
                        firstosecond.putExtra("selected_item", text);
                        startActivityForResult(firstosecond,1);
                    }

                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent secondtofirst) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (secondtofirst.hasExtra("item_name")) {
                String item_name = secondtofirst.getExtras().getString("item_name");
                //int position =0;
               int position = secondtofirst.getExtras().getInt("pos");
                Toast.makeText(this, item_name, Toast.LENGTH_SHORT).show();
                //TextView listText = (TextView) findViewById(R.id.listText);
                //listText.setText(item_name);
                //writeItems();
                items.set(position,item_name);
                //itemsAdapter.add(item_name);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
    }

    private void readItems () {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "tdo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }

    }

    private void writeItems () {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "tdo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
