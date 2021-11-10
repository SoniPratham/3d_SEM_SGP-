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
import com.hanks.htextview.HTextView;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button login_btn;
    private EditText eml;
    private EditText ps;
    private Button btn;
    private StringRequest req;
    private Button btn1;

    private SharedPreferences pr;
    private SharedPreferences.Editor val;
    private HTextView hTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loging);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        hTextView = (HTextView) findViewById(R.id.wl);
        hTextView.setTypeface(null);
        hTextView.animateText("Welcome to Login");

        ps=(EditText)findViewById(R.id.password);
        eml=(EditText)findViewById(R.id.email);
        btn=(Button)findViewById(R.id.login_btn);
        btn1=(Button)findViewById(R.id.reg_btn);

        btn.setOnClickListener(this);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });

        pr= PreferenceManager.getDefaultSharedPreferences(this);
        val=pr.edit();
    }

    @Override
    public void onClick(View v) {

        if(eml.getText().toString().isEmpty())
        {
            Toasty.error(Login.this, "ENTER USERNAME.", Toast.LENGTH_SHORT, true).show();

            return;
        }
        else if(ps.getText().toString().isEmpty())
        {
            Toasty.error(Login.this, "ENTER PASSWORD.", Toast.LENGTH_SHORT, true).show();
            return;
        }
        else
        {

            final KProgressHUD dlg = KProgressHUD.create(Login.this);
            dlg.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
            dlg.setLabel("Please wait");
            dlg.setDimAmount(0.5f);
            dlg.setCancellable(false);
            dlg.show();


        req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/userlogin.php", new Response.Listener<String>() {
            private String str;
            private JSONObject jobj;

            @Override
            public void onResponse(String response) {

                dlg.dismiss();

                try
                {

                    jobj= new JSONObject(response);
                    str = jobj.get("msg").toString();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                if(str.equals("User Login"))
                {
                    try
                    {
                        val.putString("userid",jobj.get("uid").toString());
                        val.putString("usernm",jobj.get("name").toString());
                        val.putString("userem",jobj.get("email").toString());
                        val.putString("usercn",jobj.get("contact").toString());
                        val.putString("useradd",jobj.get("address").toString());
                        val.putString("userct",jobj.get("city").toString());
                        val.putString("usertch",jobj.get("technology").toString());
                        val.putString("userexp",jobj.get("experience").toString());
                        val.commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    finish();
                    startActivity(new Intent(Login.this, Allmenu.class));
                }
                else
                {
                    Toasty.error(Login.this, ""+str, Toast.LENGTH_LONG, true).show();
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

                val.put("email",eml.getText().toString());
                val.put("pass",ps.getText().toString());
                return val;
            }
        };


            RequestQueue myreq = Volley.newRequestQueue(Login.this);
        myreq.add(req);

        }
    }
}


