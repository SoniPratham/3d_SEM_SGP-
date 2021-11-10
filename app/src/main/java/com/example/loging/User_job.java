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

public class User_job extends AppCompatActivity implements AdapterView.OnItemClickListener {


    private ListView jb;
    private ArrayList<Setjobdata> arr;
    private Myadpt adpt;
    private StringRequest req;
    private RequestQueue myreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_job);


        jb = (ListView) findViewById(R.id.uj);
        arr=new ArrayList<Setjobdata>();
        adpt=new Myadpt(this,arr);

        req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/Getjob.php", new Response.Listener<String>() {
            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {
                try {
                    jarr=new JSONArray(response);

                    for (int i=0;i<jarr.length();i++)
                    {
                        Setjobdata obj = new Setjobdata();
                        jobj=jarr.getJSONObject(i);

                        obj.setCo_name(jobj.getString("cmp_name"));
                        obj.setPost(jobj.getString("post"));
                        obj.setNum_post(jobj.getString("num_post"));
                        obj.setCmp_address(jobj.getString("cmp_address"));
                        obj.setCmp_email(jobj.getString("cmp_email"));
                        obj.setCmp_no(jobj.getString("cmp_no"));
                        obj.setEdu(jobj.getString("edu"));
                        obj.setReq(jobj.getString("req"));
                        obj.setExp(jobj.getString("exp"));
                        obj.setSalary(jobj.getString("salary"));

                        arr.add(obj);
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
               jb.setAdapter(adpt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        myreq= Volley.newRequestQueue(User_job.this);

        myreq.add(req);

        jb.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Setjobdata val = arr.get(position);

        Intent i = new Intent(User_job.this, Detail_job.class);

        i.putExtra("cnm",val.getCo_name());
        i.putExtra("cad",val.getCmp_address());
        i.putExtra("cnum",val.getCmp_no());
        i.putExtra("ceml",val.getCmp_email());
        i.putExtra("pst",val.getPost());
        i.putExtra("edu",val.getEdu());
        i.putExtra("exp",val.getExp());
        i.putExtra("req",val.getReq());
        i.putExtra("sal",val.getSalary());
        i.putExtra("np",val.getNum_post());

        startActivity(i);

    }

    class Myadpt extends BaseAdapter
    {
        private final Context c;
        private final ArrayList<Setjobdata> arr;
        private final LayoutInflater inf;
        private Holder hol;

        Myadpt(Context c, ArrayList<Setjobdata> arr)
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

                cv=inf.inflate(R.layout.layout_job,null);

                hol=new Holder();
                hol.cnm=(TextView) cv.findViewById(R.id.cnm);
                hol.pst=(TextView)cv.findViewById(R.id.pst);
                hol.psn=(TextView)cv.findViewById(R.id.psn);
                cv.setTag(hol);


            }
            else
            {

                hol=(Holder) cv.getTag();
            }


            Setjobdata val = arr.get(position);
            hol.cnm.setText(val.getCo_name());
            hol.pst.setText(val.getPost());
            hol.psn.setText(val.getNum_post());

            return cv;
        }

        class Holder
        {
            TextView cnm;
            TextView pst;
            TextView psn;

        }



    }
}


