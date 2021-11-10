package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

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

public class Add_feedback extends AppCompatActivity implements View.OnClickListener {


    private EditText msg;
    private Button btn;
    private StringRequest req;
    private RequestQueue myreq;
    private SharedPreferences pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feedback);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Add Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pr = PreferenceManager.getDefaultSharedPreferences(this);

        msg = (EditText) findViewById(R.id.fb_msg);

        btn = (Button) findViewById(R.id.fb_btn);

        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (msg.getText().toString().isEmpty()) {
            msg.setError("feedback");
            return;
        } else {
            req = new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/addfeedback.php", new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    String res = response.trim();
                    if (res.equals("Feedback Added"))
                    {
                        Toasty.success(Add_feedback.this, "" + res, Toasty.LENGTH_LONG).show();
                        //startActivity(new Intent(Add_feedback.this, Login.class));
                    } else {
                        Toasty.warning(Add_feedback.this, "" + res, Toasty.LENGTH_LONG).show();
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
                    val.put("title",msg.getText().toString());

                    return val;
                }
            };

            myreq = Volley.newRequestQueue(Add_feedback.this);

            myreq.add(req);




        }

    }
}