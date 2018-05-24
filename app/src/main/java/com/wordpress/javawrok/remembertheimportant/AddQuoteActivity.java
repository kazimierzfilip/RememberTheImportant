package com.wordpress.javawrok.remembertheimportant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddQuoteActivity extends AppCompatActivity {

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_quote);
        mEditText = findViewById(R.id.add_quote_edit);
    }



    public void add(View view){
        String quote = mEditText.getText().toString();
        Intent intentData = new Intent();
        intentData.putExtra("quote",quote);
        setResult(RESULT_OK,intentData);
        finish();
    }
}
