package com.example.com594cw12020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create a instance of SQLite Database
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter= loginDataBaseAdapter.open();
    }

    //Method to handle Click Event of Sign In Button
    public void signIn(View view){
        try{
            String username = ((EditText)findViewById(R.id.Username)).getText().toString();
            String password = ((EditText)findViewById(R.id.Password)).getText().toString();

            //Now fetch the password from the database for the respective username
            String storedPassword = loginDataBaseAdapter.getSingleEntry(username);

            if(storedPassword == "NOT EXIST"){
                Log.e("Error", "User does not exist");
            }

            if(password.equals(storedPassword)){
                Toast.makeText(MainActivity.this, "Successfully Login", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, Welcome.class);
                intent.putExtra("Name", username);
                startActivity(intent);
            }
            else if(storedPassword == "NOT EXIST"){
                Toast.makeText(MainActivity.this,
                        "This given user does not exist, please sign up",
                        Toast.LENGTH_LONG).show();
            }
            else if(!(storedPassword == "NOT EXIST") && !password.equals(storedPassword)){
                Toast.makeText(MainActivity.this,
                        "Password does not match password listed for User, please try again",
                        Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this,
                        "This given records are not available in the database, please sign up",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Log.e("Error", "error login");
        }
    }

    /** Called when the user taps the Register here button */
    public void displayRegistration(View view){
        // Do something in response to button
        Intent intent = new Intent(this , Registration.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Close the database
        loginDataBaseAdapter.close();
    }
}