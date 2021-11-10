package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

public class Add_Port extends AppCompatActivity implements View.OnClickListener {

    private EditText tl;
    private EditText inf;
    private EditText lk;
    private EditText tc;
    private Button btn;
    private StringRequest req;
    private RequestQueue myreq;
    private SharedPreferences pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__port);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Add Work Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pr= PreferenceManager.getDefaultSharedPreferences(this);

        tl = (EditText) findViewById(R.id.pr_tl);
        inf = (EditText) findViewById(R.id.pr_inf);
        lk = (EditText) findViewById(R.id.pr_lk);
        tc = (EditText) findViewById(R.id.pr_tc);

        btn = (Button) findViewById(R.id.pr_btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(tl.getText().toString().isEmpty())
        {
            tl.setError("Title");
            return;
        }
        else if(inf.getText().toString().isEmpty())
        {
            inf.setError("Information");
            return;
        }
        else if(lk.getText().toString().isEmpty())
        {
            lk.setError("Link");
            return;
        }
        else if(tc.getText().toString().isEmpty())
        {
            tc.setError("Technology");
            return;
        }
        else
        {
            req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/addport.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    String res = response.trim();
                    if(res.equals("User Work Profile Added"))
                    {
                        Toasty.success(Add_Port.this,""+res,Toasty.LENGTH_LONG).show();
                        //startActivity(new Intent(Add_Port.this, Login.class));
                    }
                    else
                    {
                        Toasty.warning(Add_Port.this,""+res,Toasty.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {


                    HashMap<String, String> val = new HashMap<String, String>();
                    val.put("uid",pr.getString("userid",""));
                    val.put("title",tl.getText().toString());
                    val.put("info",inf.getText().toString());
                    val.put("link",lk.getText().toString());
                    val.put("tech",tc.getText().toString());

                    return val;
                }
            };


            myreq = Volley.newRequestQueue(Add_Port.this);

            myreq.add(req);

        }

    }
}