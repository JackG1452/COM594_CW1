package com.example.com594cw12020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    public String Gender = "Male";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final RadioGroup rg = findViewById(R.id.RadioSex);

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
    public boolean verifyUsername(String username){
        if(username.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter Username",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if(loginDataBaseAdapter.userNameChecker(username)){

            Pattern p = Pattern.compile("[^A-Za-z0-9]");
            Matcher m = p.matcher(username);
            boolean b = m.find();
            boolean isUpperCase = Character.isUpperCase(username.charAt(0));

            if(username.length() < 4 || username.length() >8){
                Toast.makeText(getApplicationContext(), "Username should be between 4-8 characters",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            else if(b){
                Toast.makeText(getApplicationContext(), "Username should not contain any special characters or spaces",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            else if(!isUpperCase){
                Toast.makeText(getApplicationContext(), "Username should start with capital letter",
                        Toast.LENGTH_LONG).show();
                return false;
            }
            else{
                return true;
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Username already exists",
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public boolean verifyPassword(String password, String confirmPassword){

        boolean hasRequirements = false;
        Pattern letter = Pattern.compile("[a-zA-z]");
        Pattern capitalLetter = Pattern.compile("[A-Z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasLetter = letter.matcher(password);
        Matcher hasCapitalLetter = capitalLetter.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        if(hasLetter.find()){
            if(hasCapitalLetter.find()){
                if(hasDigit.find()){
                    if(hasSpecial.find()) {
                        hasRequirements = true;
                    }
                }
            }
        }
        else{
            return false;
        }


        if (password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter Password & Confirm Password",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!password.equals(confirmPassword)){
            Toast.makeText(getApplicationContext(), "Passwords do not match",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(password.length() < 5 || password.length() >9) {
            Toast.makeText(getApplicationContext(), "Password should be between 5-9 characters",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!hasRequirements){
            Toast.makeText(getApplicationContext(), "Password should contain 1 capital letter, 1 number, and 1 special character",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    public boolean verifyEmail(String email){

        boolean hasRequirements = false;
        Pattern emailPattern = Pattern.compile( "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher isEmail = emailPattern.matcher(email);

        if(isEmail.find()){
            hasRequirements = true;
        }
        if (email.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please enter Email",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!hasRequirements){
            Toast.makeText(getApplicationContext(), "Email is not in valid format",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    public void Register(View view) {
        String username = ((EditText) findViewById(R.id.Username_reg)).getText().toString();
        String password = ((EditText) findViewById(R.id.Password_reg)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirm_Password_reg)).getText().toString();
        String email = ((EditText) findViewById(R.id.Email_reg)).getText().toString();
        String gender = Gender;

        if (verifyUsername(username)) {
            if (verifyPassword(password,  confirmPassword)) {
                if (verifyEmail(email)) {

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