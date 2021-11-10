package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Company_ex extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView cnm;
    private ArrayList<SetCoData> arr;
    private StringRequest req;
    private RequestQueue myreq;
    private Myadpt adpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_ex);

        getSupportActionBar().setTitle("Company");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
            cnm=(ListView) findViewById(R.id.lst);
            arr= new ArrayList<SetCoData>();
            adpt=new Myadpt(this,arr);
        req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/showcompany.php", new Response.Listener<String>() {
            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {

                // Toasty.success(Company_ex.this,""+response, Toasty.LENGTH_LONG).show();

                try {
                    jarr=new JSONArray(response);

                    for (int i=0;i<jarr.length();i++)
                    {

                        SetCoData obj = new SetCoData();
                        jobj=jarr.getJSONObject(i);
                        obj.setCmp_name(jobj.getString("cmp_name"));
                        obj.setCmp_address(jobj.getString("cmp_address"));
                        obj.setCmp_email(jobj.getString("cmp_email"));
                        obj.setCmp_lat(jobj.getString("cmp_lat"));
                        obj.setCmp_long(jobj.getString("cmp_long"));
                        obj.setCmp_no(jobj.getString("cmp_no"));
                        obj.setTech(jobj.getString("tech"));


                        arr.add(obj);


                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                cnm.setAdapter(adpt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        myreq= Volley.newRequestQueue(Company_ex.this);
        myreq.add(req);

        cnm.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        SetCoData obj = arr.get(position);


        Intent myval = new Intent(Company_ex.this, MyMAp.class);

        myval.putExtra("cname",obj.getCmp_name());
        myval.putExtra("address",obj.getCmp_address());
        myval.putExtra("email",obj.getCmp_email());
        myval.putExtra("lt",obj.getCmp_lat());
        myval.putExtra("lg",obj.getCmp_long());
        myval.putExtra("contact",obj.getCmp_no());
        myval.putExtra("tech",obj.getTech());
        startActivity(myval);
    }

    class  Myadpt extends BaseAdapter
    {
        private final Context c;
        private final ArrayList<SetCoData> arr;
        private final LayoutInflater inf;
        private Holder hol;

        Myadpt(Context c, ArrayList<SetCoData> arr)
        {
            this.c=c;
            this.arr=arr;
            inf=(LayoutInflater)c.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View cv, ViewGroup parent) {

            if (cv == null) {

                cv = inf.inflate(R.layout.layout_co_info, null);


                hol = new Holder();
                hol.name=cv.findViewById(R.id.cnm);
                cv.setTag(hol);

            }
            else
            {
                hol=(Holder) cv.getTag();

            }

            SetCoData val = arr.get(position);
            hol.name.setText(val.getCmp_name());

            return cv;
        }

        class Holder
        {
            TextView name;


        }

    }
}