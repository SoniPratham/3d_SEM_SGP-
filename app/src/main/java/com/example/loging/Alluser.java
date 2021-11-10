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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class Alluser extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView ls;
    private ArrayList<Setuserdata> arr;
    private Myadpt adpt;
    private RequestQueue myreq;
    private StringRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alluser);


        ls=(ListView)findViewById(R.id.lst);
        arr=new ArrayList<Setuserdata>();
        adpt=new Myadpt(this,arr);

        req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/Getuser.php", new Response.Listener<String>() {
            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {

                try
                {
                    jarr=new JSONArray(response);

                    for(int i=0;i<jarr.length();i++)
                    {
                        Setuserdata obj = new Setuserdata();
                        jobj=jarr.getJSONObject(i);

                        obj.setU_name(jobj.getString("u_name"));
                        obj.setU_tech(jobj.getString("u_tech"));
                        obj.setU_exp(jobj.getString("u_exp"));
                        obj.setU_address(jobj.getString("u_address"));
                        obj.setU_city(jobj.getString("u_city"));
                        obj.setU_contact(jobj.getString("u_contact"));
                        obj.setU_email(jobj.getString("u_email"));

                        arr.add(obj);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                ls.setAdapter(adpt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        myreq= Volley.newRequestQueue(Alluser.this);
        myreq.add(req);

        ls.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Setuserdata obj = arr.get(position);

        Intent myin = new Intent(Alluser.this, Single_user.class);

        myin.putExtra("name",obj.getU_name());
        myin.putExtra("email",obj.getU_email());
        myin.putExtra("contact",obj.getU_contact());
        myin.putExtra("address",obj.getU_address());
        myin.putExtra("city",obj.getU_city());
        myin.putExtra("technology",obj.getU_tech());
        myin.putExtra("experience",obj.getU_exp());

        startActivity(myin);
    }

    class Myadpt extends BaseAdapter
    {
        private final Context c;
        private final ArrayList<Setuserdata> arr;
        private final LayoutInflater inf;
        private Holder hol;

        Myadpt(Context c, ArrayList<Setuserdata> arr)
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

            if(cv==null)
            {
                cv=inf.inflate(R.layout.layout_user,null);
                hol=new Holder();
                hol.nm=(TextView) cv.findViewById(R.id.unm);
                hol.tech=(TextView) cv.findViewById(R.id.tech);
                hol.exp=(TextView) cv.findViewById(R.id.exp);
                cv.setTag(hol);
            }
            else
            {
                hol=(Holder) cv.getTag();
            }

            Setuserdata val = arr.get(position);
            hol.nm.setText(val.getU_name());
            hol.tech.setText(val.getU_tech());
            hol.exp.setText(val.getU_exp());
            return cv;
        }

        class Holder
        {
            TextView nm;
            TextView tech;
            TextView exp;
        }
    }
}
