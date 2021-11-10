package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;

public class MyMAp extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment obj;
    private LatLng la;
    private MarkerOptions mk;
    private BitmapDescriptor icon;
    private CameraPosition cm;
    private CameraUpdate cmp;
    private TextView nm;
    private TextView eml;
    private TextView cnt;
    private TextView adr;
    private TextView tc;
    private Bundle bnl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_m_ap);

        nm=(TextView)findViewById(R.id.cm);
        eml=(TextView)findViewById(R.id.eml);
        cnt=(TextView)findViewById(R.id.cnt);
        adr=(TextView)findViewById(R.id.adr);
        tc=(TextView)findViewById(R.id.tc);

        obj = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mp);

        obj.getMapAsync(this);

        bnl=getIntent().getExtras();

        nm.setText(bnl.getString("cname"));
        eml.setText(bnl.getString("email"));
        adr.setText(bnl.getString("address"));
        tc.setText(bnl.getString("tech"));
        cnt.setText(bnl.getString("contact"));
    }

    @Override
    public void onMapReady(GoogleMap map) {

        Float lt = Float.valueOf(bnl.getString("lt"));
        Float lg = Float.valueOf(bnl.getString("lg"));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        la=new LatLng(lt, lg);

        mk=new MarkerOptions();

        icon= BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);

        mk.icon(icon);

        mk.title(bnl.getString("cname"));

        mk.position(la);

        map.addMarker(mk);

        cm= CameraPosition.builder().target(la).zoom(16).build();


        cmp= CameraUpdateFactory.newCameraPosition(cm);

        map.animateCamera(cmp);
    }
}