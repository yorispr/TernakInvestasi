package com.eternak.investment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import custom_font.MyEditText;

public class LoginActivity extends AppCompatActivity {
    TextView signUp;
    ImageView next;
    MyEditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signUp =(TextView)findViewById(R.id.signUp);
        next = (ImageView)findViewById(R.id.next);

        email = (MyEditText)findViewById(R.id.inputEmail);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this, NextLoginActivity.class);
                if(!email.getText().toString().matches("")){
                    it.putExtra("email",email.getText().toString());
                    startActivity(it);
                }else{
                    Toast.makeText(getApplicationContext(),"Isikan email terlebih dahulu!",Toast.LENGTH_LONG).show();
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(it);
            }
        });

    }
}
