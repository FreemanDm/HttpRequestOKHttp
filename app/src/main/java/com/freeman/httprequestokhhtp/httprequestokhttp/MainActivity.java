package com.freeman.httprequestokhhtp.httprequestokhttp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputPassword, inputEmail;
    Button loginBtn;
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_btn){
            new LoginTask().execute();
        }
    }

    class  LoginTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... params) {
            User user = new User();
            user.email = "mnb@poit.com";
            user.password = "12345";
            Gson gson = new Gson();
            String rBody = gson.toJson(user);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(JSON, rBody);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://telranstudentsproject.appspot.com/_ah/api/contactsApi/v1/login")
                    .post(requestBody)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()){
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response ==null){
                Toast.makeText(MainActivity.this, "Request error ", Toast.LENGTH_SHORT).show();
            }
            else {

                Gson gson = new Gson();
                Token t = gson.fromJson(response, Token.class);
                token = t.token;
                Log.d("MyTag", token);
            }
        }
    }
}
