package com.wordpress.javawrok.remembertheimportant;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        openQuotes();
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
        } else if (requestCode==1000){
            readFromFile(data.getData());
        }
    }

    public void saveQuotes(){
        try {
            ObjectOutputStream out = new
                   ObjectOutputStream(openFileOutput(TEXT_FILE, MODE_PRIVATE));
            out.writeObject(textList);
            out.close();
            Toast. makeText (this,"Text Saved !",Toast. LENGTH_LONG ).show();
        } catch (java.io.IOException e) {
            Toast. makeText (this,"Sorry, problems during saving",Toast. LENGTH_LONG ).show();
        }
    }

    public void openQuotes(){
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

    public void readFromFile(Uri uri){
        try {
            InputStream input = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(input));
            String line;
            StringBuilder buf = new StringBuilder();
            while((line=reader.readLine())!=null){
                Log.v("SEEEEEEEEEEEEE",line);
                if(line.compareTo("")==0) {
                    textList.add(buf.toString());
                    buf=new StringBuilder();
                } else {
                    buf.append(line);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFromFile(MenuItem menuItem){
        Intent getFileIntent = new Intent();
        getFileIntent.setAction(Intent.ACTION_PICK);
        startActivityForResult(getFileIntent,1000);
//        readFromFile(file);
    }

    public void next(View view){
        if(pos>=textList.size()) {
            pos = 0;
        }
        mTextView.setText(textList.get(pos));
        pos+=1;
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveQuotes();
    }
}
