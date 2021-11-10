package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Work_profile extends AppCompatActivity {

    private SharedPreferences pr;

    private Button btn;
    private StringRequest req;
    private RequestQueue myreq;
    private EditText tl;
    private EditText inf;
    private EditText tch;
    private EditText lk;
    private ListView ls;
    private ArrayList<Setwork> arr;
    private Myadpt adpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_profile);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Work Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pr = PreferenceManager.getDefaultSharedPreferences(this);

        ls=(ListView)findViewById(R.id.wp);
        arr=new ArrayList<Setwork>();
        adpt=new Myadpt(this,arr);

        req= new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/GetPort.php?uid="+pr.getString("userid",""), new Response.Listener<String>() {
            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {


                try
                {
                    jarr=new JSONArray(response);

                    for (int i=0;i<jarr.length();i++)
                    {
                        Setwork obj = new Setwork();

                        jobj=jarr.getJSONObject(i);
                        obj.setPr_title(jobj.getString("pr_title"));
                        obj.setPr_info(jobj.getString("pr_info"));
                        obj.setPr_link(jobj.getString("pr_link"));
                        obj.setPr_tech(jobj.getString("pr_tech"));
                        arr.add(obj);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                ls.setAdapter(adpt);
            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        
        myreq= Volley.newRequestQueue(Work_profile.this);
        myreq.add(req);



    }

    class Myadpt extends BaseAdapter
    {

        private final Context c;
        private final ArrayList<Setwork> arr;
        private final LayoutInflater lf;
        private Holder hol;

        Myadpt(Context c, ArrayList<Setwork> arr)
        {

            this.c=c;
            this.arr=arr;

            lf=(LayoutInflater)c.getSystemService(LAYOUT_INFLATER_SERVICE);
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
                   cv=lf.inflate(R.layout.layout_workprofile,null);

                   hol=new Holder();

                hol.tl=(TextView) cv.findViewById(R.id.wp_tl);
                hol.tech=(TextView) cv.findViewById(R.id.wp_tch);
                hol.info=(TextView) cv.findViewById(R.id.wp_inf);
                hol.ln=(TextView) cv.findViewById(R.id.wp_lk);
                cv.setTag(hol);
            }
            else
            {
                hol=(Holder) cv.getTag();
            }

            Setwork obj = arr.get(position);
            hol.tl.setText(obj.getPr_title());
            hol.tech.setText(obj.getPr_tech());
            hol.info.setText(obj.getPr_info());
            hol.ln.setText(obj.getPr_link());

            return cv;
        }

        class Holder
        {
            TextView tl,tech,info,ln;
        }
    }

}