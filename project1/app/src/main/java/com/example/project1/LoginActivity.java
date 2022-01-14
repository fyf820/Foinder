package com.example.project1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;
//this is the login implementation
public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "login";
    private Button sig;
    private Button log;
    EditText UserE;
    EditText Password;
    String name;
    String pass;
    private String emailName;

    View focusView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserE=(EditText)findViewById(R.id.logUseremail);
        Password=(EditText)findViewById(R.id.regPassword);

        sig=(Button)findViewById(R.id.historyButton);
        sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openreg();
            }
        });

        log=(Button)findViewById(R.id.findbutton);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlog();
            }
        });

    }

    public void openreg(){
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void openlog(){
        name=UserE.getText().toString();
        pass=Password.getText().toString();

        if (TextUtils.isEmpty(name)){
            UserE.setError(getString(R.string.error_field_required));
            focusView = UserE;
        }

        if (TextUtils.isEmpty(pass)) {
            Password.setError(getString(R.string.error_field_required));
            focusView = Password;
        }
        emailName= name;
        Log.i(TAG,"email "+emailName);
        String valid = valiadate(name, pass);

        if (valid.equals("Login Failed")) {
            Password.setError(valid);
            log.setError(valid);
        }
        else{
            String [] arrValid = valid.split("-");
            String name=arrValid[0];
            String userID=arrValid[1];
            Log.i(TAG,"valid "+valid);
            Log.i(TAG,"name "+name);
            Log.i(TAG,"userID "+userID);
            Integer userIDInt = Integer.valueOf(userID);
            Intent newActivity1 = new Intent(this, MainOption.class);
            newActivity1.putExtra("email",emailName);
            newActivity1.putExtra("name",name);
            newActivity1.putExtra("userID",userIDInt);
            startActivity(newActivity1);
        }


    }

    //match data
    private String valiadate(String user,String pass){
        String type = "login";
        BackgroundWorker backgroundWorker = new BackgroundWorker((this));
        try {
            return backgroundWorker.execute(type,user,pass).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Login Failed";
    }

}
