package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private EditText nm;
    private EditText ps;
    private EditText eml;
    private EditText cn;
    private EditText ads;
    private EditText ct;
    private EditText exp;
    private EditText tch;
    private Button btn;
    private StringRequest req;
    private RequestQueue myreq;
    private Context yourContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        nm=(EditText)findViewById(R.id.user_et);
        ps=(EditText)findViewById(R.id.password_et);
        eml=(EditText)findViewById(R.id.email_et);
        cn=(EditText)findViewById(R.id.contact_et);
        ads=(EditText)findViewById(R.id.address_et);
        ct=(EditText)findViewById(R.id.city_et);
        exp=(EditText)findViewById(R.id.experience_et);
        tch=(EditText)findViewById(R.id.technology_et);

        btn=(Button)findViewById(R.id.register_btn);

        btn.setOnClickListener(this);
    }

    @Override

    public void onClick(View v) {

        if (nm.getText().toString().isEmpty()) {
            nm.setError("Enter Username");
            return;
        } else if (ps.getText().toString().isEmpty()) {
            ps.setError("Enter Password");
            return;
        } else if (eml.getText().toString().isEmpty()) {
            eml.setError("Enter Email");
            return;
        } else if (cn.getText().toString().isEmpty()) {
            cn.setError("Enter Contact");
            return;
        } else if (ads.getText().toString().isEmpty()) {
            ads.setError("Enter Address");
            return;
        } else if (ct.getText().toString().isEmpty()) {
            ct.setError("Enter City");
            return;
        } else if (exp.getText().toString().isEmpty()) {
            exp.setError("Enter Experience");
            return;
        } else if (tch.getText().toString().isEmpty()) {
            tch.setError("Enter Technology");
            return;
        } else {


            req = new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/adduser.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String res = response.trim();
                    if (res.equals("User Added")) {

                        startActivity(new Intent(Registration.this, Login.class));
                    } else {
                        Toast.makeText(Registration.this, "" + response, Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    HashMap<String, String> val = new HashMap<String, String>();
                    val.put("unm", nm.getText().toString());
                    val.put("pass", ps.getText().toString());
                    val.put("email", eml.getText().toString());
                    val.put("con", cn.getText().toString());
                    val.put("address", ads.getText().toString());
                    val.put("city", ct.getText().toString());
                    val.put("exp", exp.getText().toString());
                    val.put("thc", tch.getText().toString());

                    return val;
                }
            };


            myreq = Volley.newRequestQueue(Registration.this);

            myreq.add(req);
        }
    }


}