package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Add_project extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences pr;
    private EditText tl;
    private EditText inf;
    private EditText rs;
    private EditText tm;
    private Button btn;
    private StringRequest req;
    private RequestQueue myreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);


        getSupportActionBar().setTitle("Add Project");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pr = PreferenceManager.getDefaultSharedPreferences(this);


        tl = (EditText) findViewById(R.id.teh);
        inf = (EditText) findViewById(R.id.inf);
        rs = (EditText) findViewById(R.id.ru);
        tm = (EditText) findViewById(R.id.tm);

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (tl.getText().toString().isEmpty()) {
            tl.setError("Technology");
            return;

        } else if (inf.getText().toString().isEmpty()) {
            inf.setError("Information");
            return;
        } else if (rs.getText().toString().isEmpty()) {
            rs.setError("Rs");
            return;
        } else if (tm.getText().toString().isEmpty()) {
            tm.setError("Time");
            return;
        } else {

            req = new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/addproject.php", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    String res = response.trim();
                    if(res.equals("Project Added"))
                    {
                        Toasty.success(Add_project.this,""+res,Toasty.LENGTH_LONG).show();
                        //startActivity(new Intent(Add_project.this, Login.class));
                    }
                    else
                    {
                        Toasty.warning(Add_project.this,""+res,Toasty.LENGTH_LONG).show();
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
                    val.put("Tech",tl.getText().toString());
                    val.put("Info",inf.getText().toString());
                    val.put("Rs",rs.getText().toString());
                    val.put("Time",tm.getText().toString());
                    return val;





                }
            };



            myreq = Volley.newRequestQueue(Add_project.this);

            myreq.add(req);

        }
    }
}