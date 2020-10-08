package com.example.com594cw12020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;

public class Welcome extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter.open();

        TextView txtname = findViewById(R.id.textView);
        Intent intent = getIntent();
        String loginName = intent.getStringExtra("Name");
        txtname.setText("Welcome, " + loginName);

        String cursor = loginDataBaseAdapter.fetch(loginName);
        final TextView details = findViewById(R.id.textView2);
        details.setText(cursor);

        ArrayList<String> wigwag = loginDataBaseAdapter.fetchAllDetails();
        Log.e("Test", String.valueOf(wigwag));
        //final TextView foo = findViewById(R.id.textView2);
        //foo.setText(Arrays.toString(new ArrayList[]{wigwag}));

        //ArrayList<String> test = loginDataBaseAdapter.fetchAllDetails();


        //final TextView test = findViewById(R.id.textView3);
        //test.setText(cursor);


        /*TextView txtgender = (TextView) findViewById(R.id.textView2);
        String loginPassword = intent.getStringExtra("Password");
        txtgender.setText("Password: " + loginPassword);*/
    }

    /*public void listDetails(View view) {
        try {
            String[] textArray = {"One", "Two", "Three", "Four"};
            for( int i = 0; i < textArray.length; i++ )
            {
                TextView textView = new TextView(this);
                textView.setText(textArray[i]);

                setContentView(R.layout.activity_welcome);
            }
            /*int textViewCount = 10;

            TextView[] textViewArray = new TextView[textViewCount];

            for(int i = 0; i < textViewCount; i++) {
                textViewArray[i] = new TextView(this);
            }
        }catch (Exception ex){
            Log.e("Error", "error login");
        }
    }*/

    public void logOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}