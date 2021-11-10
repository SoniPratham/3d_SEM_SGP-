package com.example.loging;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;

import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Allmenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences pr;
    private TextView nam;
    private TextView eml;
    private TextView cn;
    private TextView add;
    private TextView ct;
    private TextView tch;
    private TextView exp;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allmenu);

        pr= PreferenceManager.getDefaultSharedPreferences(this);


        nam=(TextView)findViewById(R.id.nm);
        eml=(TextView)findViewById(R.id.eml);
        cn=(TextView)findViewById(R.id.cn);
        add=(TextView)findViewById(R.id.add);
        ct=(TextView)findViewById(R.id.ct);
        tch=(TextView)findViewById(R.id.tch);
        exp=(TextView)findViewById(R.id.exp);

        btn=(Button)findViewById(R.id.btn);


        btn.setOnClickListener(new View.OnClickListener() {
            private EditText utch;
            private EditText uexp;
            private EditText uct;
            private EditText ads;
            private EditText ucn;
            private EditText ueml;
            private EditText ups;
            private EditText unm;
            private Button upd_btn;
            private Dialog dlg;

            @Override
            public void onClick(View v) {

                dlg=new Dialog(Allmenu.this);

                dlg.setContentView(R.layout.layout_update);

                dlg.setCancelable(false);


                dlg.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                unm = (EditText) dlg.findViewById(R.id.user_et);
                ups=(EditText)dlg.findViewById(R.id.password_et);
                ueml=(EditText)dlg.findViewById(R.id.email_et);
                ucn=(EditText)dlg.findViewById(R.id.contact_et);
                ads=(EditText)dlg.findViewById(R.id.address_et);
                uct=(EditText)dlg.findViewById(R.id.city_et);
                uexp=(EditText)dlg.findViewById(R.id.experience_et);
                utch=(EditText)dlg.findViewById(R.id.technology_et);

                unm.setText(pr.getString("usernm",""));
                ueml.setText(pr.getString("userem",""));
                ucn.setText(pr.getString("usercn",""));
                ads.setText(pr.getString("useradd",""));
                uct.setText(pr.getString("userct",""));
                utch.setText(pr.getString("usertch",""));
                uexp.setText(pr.getString("userexp",""));

                upd_btn=(Button)dlg.findViewById(R.id.update_btn);

                upd_btn.setOnClickListener(new View.OnClickListener() {
                    private RequestQueue myreq;
                    private StringRequest req;

                    @Override
                    public void onClick(View v) {

                        req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/profile.php?uid="+pr.getString("userid",""), new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(response.equals("Profile Updated"))
                                {
                                    finish();
                                    startActivity(new Intent(Allmenu.this,Login.class));

                                }
                                else
                                {
                                    Toasty.info(Allmenu.this,""+response,Toasty.LENGTH_LONG).show();
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
                                val.put("unm",unm.getText().toString());
                                //val.put("pass",ps.getText().toString());
                                val.put("email",ueml.getText().toString());
                                val.put("con",ucn.getText().toString());
                                val.put("address",ads.getText().toString());
                                val.put("city",uct.getText().toString());
                                val.put("exp",uexp.getText().toString());
                                val.put("thc",utch.getText().toString());

                                return val;
                            }
                        };


                        myreq = Volley.newRequestQueue(Allmenu.this);

                        myreq.add(req);
                        dlg.dismiss();
                    }
                });

                dlg.show();
            }
        });

        nam.setText(pr.getString("usernm",""));
        eml.setText(pr.getString("userem",""));
        cn.setText(pr.getString("usercn",""));
        add.setText(pr.getString("useradd",""));
        ct.setText(pr.getString("userct",""));
        tch.setText(pr.getString("usertch",""));
        exp.setText(pr.getString("userexp",""));



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Developer House");


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.prf)
        {
            Toasty.success(this,"Profile",Toasty.LENGTH_LONG).show();
        }

        if(id == R.id.wp)
        {
            startActivity(new Intent(Allmenu.this,Work_profile.class));
        }

        if(id == R.id.awp)
        {
            startActivity(new Intent(Allmenu.this,Add_Port.class));
        }

        if(id == R.id.ap)
        {
            startActivity(new Intent(Allmenu.this,Add_project.class));
        }

        if(id == R.id.com)
        {
            startActivity(new Intent(Allmenu.this,Company_ex.class));
        }

        if(id == R.id.lu)
        {
            Toasty.success(this,"List of user",Toasty.LENGTH_LONG).show();
        }

        if(id == R.id.j)
        {
            startActivity(new Intent(Allmenu.this,User_job.class));
        }



        if(id == R.id.lp)
        {
            startActivity(new Intent(Allmenu.this,user_list.class));
        }

        if(id == R.id.nu)
        {
            startActivity(new Intent(Allmenu.this,Alluser.class));
        }

        if(id == R.id.fb)
        {
            startActivity(new Intent(Allmenu.this,Feedback.class));
        }
        if(id == R.id.add)
        {
            startActivity(new Intent(Allmenu.this,Add_feedback.class));
        }




        if(id == R.id.lo)
        {
            SharedPreferences.Editor obj = pr.edit();

            obj.clear();

            obj.commit();
            finish();
            startActivity(new Intent(Allmenu.this,Login.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.allmenu, menu);
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}