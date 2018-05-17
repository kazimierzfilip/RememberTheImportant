package com.wordpress.javawrok.remembertheimportant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TEXT_FILE = "texts.txt";
    private TextView mTextView;
    private ArrayList<String> textList;
    private int pos;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");

        pos = 0;
        textList = new ArrayList<>();
        mTextView = findViewById(R.id.main_textView);
        mTextView.setText(R.string.welcome);

//        try {
//            ObjectOutputStream out = new
//                   ObjectOutputStream(openFileOutput(TEXT_FILE, MODE_PRIVATE));
//            out.writeObject(textList);
//            out.close();
//            Toast. makeText (this,"Text Saved !",Toast. LENGTH_LONG ).show();
//        } catch (java.io.IOException e) {
//            Toast. makeText (this,"Sorry Text could't be added",Toast. LENGTH_LONG ).show();
//        }

        try {
            ObjectInputStream in = new ObjectInputStream(openFileInput(TEXT_FILE));

            try {
                textList = (ArrayList) in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InvalidClassException e2) {
                e2.printStackTrace();
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addQuote(MenuItem item){
        Intent intent = new Intent(this,AddQuoteActivity.class);
        startActivityForResult(intent,101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==101 && resultCode==102 && data!=null){
            String quote = data.getStringExtra("quote");
            if(quote!=null && quote.compareTo("")!=0)
                textList.add(quote);
        }
    }

    public void readFromFile(String file){
        try {
            InputStreamReader in = new InputStreamReader(openFileInput(file));
            BufferedReader bufIn = new BufferedReader(in);
            String line;
            while((line=bufIn.readLine())!=null){
                textList.add(line);
            }
            bufIn.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next(MenuItem item){
        if(pos>=textList.size()) {
            pos = 0;
        }
        mTextView.setText(textList.get(pos));
        pos+=1;
    }
}
