package com.example.com594cw12020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.database.Cursor;

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

        /*TextView txtgender = (TextView) findViewById(R.id.textView2);
        String loginPassword = intent.getStringExtra("Password");
        txtgender.setText("Password: " + loginPassword);*/
    }

    public void logOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}