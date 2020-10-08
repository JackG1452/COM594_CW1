package com.example.com594cw12020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    }

    public void listDetails(View view){

        ArrayList<String> fetchedDetails = loginDataBaseAdapter.fetchAllDetails();
        List<Map<String, String>> listArray = new ArrayList<>();

        //String[] userNameArray = new String[]{};
        //String[] passwordArray = new String[]{};

        for(int i=0; i< fetchedDetails.size(); i++)
        {
            Log.e("Test", fetchedDetails.get(i));
            Map<String, String> listItem = new HashMap<>(2);
            listItem.put("userNameKey", "User Name: " + fetchedDetails.get(i));
            i++;
            listItem.put("passwordKey", "Password: " +fetchedDetails.get(i));
            listArray.add(listItem);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listArray, android.R.layout.simple_list_item_2,
                new String[] {"userNameKey", "passwordKey" },
                new int[] {android.R.id.text1, android.R.id.text2 });

        ListView listView = findViewById(R.id.listViewAnimals);
        listView.setAdapter(simpleAdapter);

    }

    public void logOut(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}