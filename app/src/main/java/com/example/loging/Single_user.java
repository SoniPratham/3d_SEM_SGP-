package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Single_user extends AppCompatActivity {

    private Bundle bnl;
    private TextView unm;
    private TextView eml;
    private TextView cno;
    private TextView adr;
    private TextView ct;
    private TextView tch;
    private TextView exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_user);

        bnl=getIntent().getExtras();

        unm=(TextView)findViewById(R.id.nm);
        eml=(TextView)findViewById(R.id.eml);
        cno=(TextView)findViewById(R.id.cno);
        adr=(TextView)findViewById(R.id.add);
        ct=(TextView)findViewById(R.id.ct);
        tch=(TextView)findViewById(R.id.tch);
        exp=(TextView)findViewById(R.id.exp);

        unm.setText(bnl.getString("name"));
        eml.setText(bnl.getString("email"));
        cno.setText(bnl.getString("contact"));
        adr.setText(bnl.getString("address"));
        ct.setText(bnl.getString("city"));
        tch.setText(bnl.getString("technology"));
        exp.setText(bnl.getString("experience"));
    }
}