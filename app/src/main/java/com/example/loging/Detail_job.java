package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Detail_job extends AppCompatActivity {

    private Bundle bnl;
    private TextView nm;
    private TextView eml;
    private TextView cno;
    private TextView add;
    private TextView pst;
    private TextView nps;
    private TextView req;
    private TextView edu;
    private TextView exp;
    private TextView slr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job);


        bnl=getIntent().getExtras();

        nm=(TextView)findViewById(R.id.nm);
        eml=(TextView)findViewById(R.id.eml);
        cno=(TextView)findViewById(R.id.cno);
        add=(TextView)findViewById(R.id.add);
        pst=(TextView)findViewById(R.id.pst);
        nps=(TextView)findViewById(R.id.nps);
        req=(TextView)findViewById(R.id.req);
        edu=(TextView)findViewById(R.id.edu);
        exp=(TextView)findViewById(R.id.exp);
        slr=(TextView)findViewById(R.id.slr);

        nm.setText(bnl.getString("cnm"));
        eml.setText(bnl.getString("ceml"));
        cno.setText(bnl.getString("cnum"));
        add.setText(bnl.getString("cad"));
        pst.setText(bnl.getString("pst"));
        nps.setText(bnl.getString("np"));
        req.setText(bnl.getString("req"));
        edu.setText(bnl.getString("edu"));
        exp.setText(bnl.getString("exp"));
        slr.setText(bnl.getString("sal"));





    }
}