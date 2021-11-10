package com.example.loging;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class user_list extends AppCompatActivity {

    private SharedPreferences pr;
    private ListView ls;
    private ArrayList<Setuserlist> arr;
    private StringRequest req;
    private Myadpt adpt;
    private RequestQueue myreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setTitle("Project List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pr = PreferenceManager.getDefaultSharedPreferences(this);
        ls=(ListView)findViewById(R.id.lu);
        arr=new ArrayList<Setuserlist>();
        adpt= new Myadpt(this,arr);




        req= new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/Getuserlist.php?uid="+pr.getString("userid",""), new Response.Listener<String>() {


            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {

                //Toast.makeText(user_list.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    jarr = new JSONArray(response);

                    for (int i = 0; i < jarr.length(); i++) {
                        Setuserlist obj = new Setuserlist();

                        jobj = jarr.getJSONObject(i);
                        obj.setPrid(jobj.getString("not_id"));
                        obj.setTechnology(jobj.getString("technology"));
                        obj.setInformation(jobj.getString("information"));
                        obj.setRupees(jobj.getString("rupees"));
                        obj.setTime(jobj.getString("Time"));
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

        myreq= Volley.newRequestQueue(user_list.this);
        myreq.add(req);

    }
    class Myadpt extends BaseAdapter
    {

        private final Context c;
        private ArrayList<Setuserlist> arr;
        private final LayoutInflater lf;
        private Holder hol;


        Myadpt(Context c,ArrayList<Setuserlist> arr)
        {

            this.c=c;
            this.arr=arr;

            lf=(LayoutInflater)c.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() { return  arr.size();}

        @Override
        public Object getItem(int position) { return arr.get(position);}

        @Override
        public long getItemId(int position) { return position;}

        @Override
        public View getView(int position, View cv, ViewGroup parent) {

            if(cv==null)
            {
                cv=lf.inflate(R.layout.layout_userlist,null);


                hol=new Holder();
                hol.btn=(Button) cv.findViewById(R.id.apl);
                hol.teh=(TextView) cv.findViewById(R.id.ul_teh);
                hol.inf=(TextView) cv.findViewById(R.id.ul_inf);
                hol.rs=(TextView) cv.findViewById(R.id.ul_rs);
                hol.tm=(TextView) cv.findViewById(R.id.ul_tm);
                cv.setTag(hol);


           }
            else
            {
                hol=(Holder) cv.getTag();
            }

            final Setuserlist obj = arr.get(position);
            hol.teh.setText(obj.getTechnology());
            hol.inf.setText(obj.getInformation());
            hol.rs.setText(obj.getRupees());
            hol.tm.setText(obj.getTime());

            hol.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alt = new AlertDialog.Builder(user_list.this);

                    alt.setMessage("Are you Sure to Apply This Project ?");

                    alt.setCancelable(false);

                    alt.setNeutralButton("Apply Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            applynow(obj.getPrid());
                        }
                    });

                    alt.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Toasty.warning(user_list.this,"Cancle TO Apply",Toasty.LENGTH_LONG).show();
                        }
                    });

                    alt.show();
                }
            });
            return cv;
        }

        class Holder
        {
            TextView teh,inf,rs,tm;
            Button btn;
        }
    }

    public void applynow(String prid)
    {
        StringRequest req1 = new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/apply.php?uid="+pr.getString("userid","")+"&&pid="+prid, new Response.Listener<String>() {


            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {

                Toast.makeText(user_list.this, ""+response, Toast.LENGTH_SHORT).show();

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue myreq1 = Volley.newRequestQueue(user_list.this);
        myreq1.add(req1);
    }
}