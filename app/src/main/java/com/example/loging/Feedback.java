package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class Feedback extends AppCompatActivity {


    private ListView fb;
    private ArrayList<Setfeedbackdata> arr;
    private Myadpt adpt;
    private RequestQueue myreq;
    private StringRequest req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        fb= (ListView)findViewById(R.id.fdb);
        arr=new ArrayList<Setfeedbackdata>();
        adpt=new Myadpt(this,arr);

        req=new StringRequest(Request.Method.POST, "https://alldevelopers.000webhostapp.com/Webservice/GetFeedback.php", new Response.Listener<String>() {
            private JSONObject jobj;
            private JSONArray jarr;

            @Override
            public void onResponse(String response) {

               // Toasty.success(Feedback.this,""+response,Toasty.LENGTH_LONG).show();
                try {
                    jarr=new JSONArray(response);

                    for (int i=0;i<jarr.length();i++)
                    {

                        Setfeedbackdata obj = new Setfeedbackdata();
                        jobj=jarr.getJSONObject(i);
                        obj.setName(jobj.getString("u_name"));
                        obj.setMsg(jobj.getString("Message"));

                        arr.add(obj);


                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                fb.setAdapter(adpt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        myreq= Volley.newRequestQueue(Feedback.this);
        myreq.add(req);

    }

    class  Myadpt extends BaseAdapter
    {
        private final Context c;
        private final ArrayList<Setfeedbackdata> arr;
        private final LayoutInflater inf;
        private Holder hol;

        Myadpt(Context c, ArrayList<Setfeedbackdata> arr)
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

                cv = inf.inflate(R.layout.layout_fbuser, null);


                hol = new Holder();
                hol.name=cv.findViewById(R.id.unm);
                hol.msg=cv.findViewById(R.id.fb_msg);
                cv.setTag(hol);

            }
            else
            {
                hol=(Holder) cv.getTag();

            }

            Setfeedbackdata val = arr.get(position);
            hol.name.setText(val.getName());
            hol.msg.setText(val.getMsg());
            return cv;
        }

            class Holder
            {
                TextView name;
                TextView msg;

            }

    }

}
