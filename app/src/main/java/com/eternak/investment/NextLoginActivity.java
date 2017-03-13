package com.eternak.investment;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import custom_font.MyEditText;

public class NextLoginActivity extends AppCompatActivity {
    TextView signUp;
    ImageView btnlogin;
    ImageView back;
    String email;
    MyEditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_login);
        back = (ImageView)findViewById(R.id.back);

        signUp = (TextView)findViewById(R.id.signUp);
        btnlogin = (ImageView)findViewById(R.id.btnLogin);

        password = (MyEditText)findViewById(R.id.inputPassword);

        email = getIntent().getStringExtra("email");
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

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONRequest("https://eternak.id/eternak/index.php/C_API/cekLogin",email,password.getText().toString());
            }
        });

    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void JSONRequest(String url, final String email, final String password){
        Log.d("URL",url);
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                                JSONArray customerarray = response.getJSONArray("customers");
                                //for(int i=0;i<customerarray.length();i++){
                                Log.d("nama", customerarray.getJSONObject(0).getString("firstname"));

                                if(!customerarray.getJSONObject(0).getString("firstname").matches("")){
                                    SessionManager s = new SessionManager(getApplicationContext());
                                    s.createLoginSession(customerarray.getJSONObject(0).getString("firstname"), email, customerarray.getJSONObject(0).getString("id"));
                                    Intent i = new Intent(NextLoginActivity.this,MainActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(i);
                                }
                                //}

                                Log.d("JSONRequest", response.toString());
                        }catch(JSONException je){je.printStackTrace();}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("NextLoginActivity","Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", md5("hfebj248asdbue20314baduebannm2uopcj3eshxxakufagtaiurgtyt39tijwqw9beulhgyofgqck4ml0"+password));
                Log.d("password",md5(password));
                return params;
            }
        };

        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectReq,"NextLoginActivity");
    }
}
