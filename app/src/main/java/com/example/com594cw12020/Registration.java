package com.example.com594cw12020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class Registration extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    public String Gender = "Your Radio Check is broken";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final RadioGroup rg = (RadioGroup) findViewById(R.id.RadioSex);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
          public void onCheckedChanged(RadioGroup group, int checkedId) {
              switch (checkedId) {
                  case R.id.Male_radioButton:
                      Gender = "Male";
                      break;
                  case R.id.Female_radioButton:
                      Gender = "Female";
                      break;
                  case R.id.Other_radioButton:
                      Gender = "Other";
                      break;
              }
          }
      });

        //create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
    }

    public void Register(View view){
        String username = ((EditText)findViewById(R.id.Username_reg)).getText().toString();
        String password = ((EditText)findViewById(R.id.Password_reg)).getText().toString();
        String confirmPassword = ((EditText)findViewById(R.id.confirm_Password_reg)).getText().toString();
        String email = ((EditText)findViewById(R.id.Email_reg)).getText().toString();
        String gender = Gender;

        if (!password.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Passwords do not match",
                    Toast.LENGTH_LONG).show();
        } else {
            //Save the Data in Database
            loginDataBaseAdapter.insertEntry(username, password, email, gender);
            Toast.makeText(getApplicationContext(),
                    "Your Account is successfully Created. You can Sign In now",
                    Toast.LENGTH_LONG).show();
            Log.d("checking", "Account Created");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    /** Called when the user taps the Login here button */
    public void displayLogin(View view){
        // Do something in response to button
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}