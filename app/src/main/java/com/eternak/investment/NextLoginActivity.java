package com.eternak.investment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NextLoginActivity extends AppCompatActivity {
    TextView signUp;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_login);
        back = (ImageView)findViewById(R.id.back);

        signUp = (TextView)findViewById(R.id.signUp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(NextLoginActivity.this,LoginActivity.class);
                startActivity(it);


            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(NextLoginActivity.this,RegisterActivity.class);
                startActivity(it);
            }
        });
    }
}
