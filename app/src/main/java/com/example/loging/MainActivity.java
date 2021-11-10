package com.example.loging;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;
import com.novoda.merlin.Connectable;
import com.novoda.merlin.Disconnectable;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;

import es.dmoral.toasty.Toasty;



public class MainActivity extends AppCompatActivity {



    public Merlin merlin;
    private MerlinsBeard merlinsBeard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();



        merlinsBeard = MerlinsBeard.from(MainActivity.this);

        if (!merlinsBeard.isConnected())
        {
            Toasty.error(MainActivity.this,"Inernet Not Connected",Toasty.LENGTH_LONG).show();

        }

        merlin = new Merlin.Builder().withConnectableCallbacks().build(MainActivity.this);

        merlin.registerConnectable(new Connectable() {
            @Override
            public void onConnect() {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        startActivity(new Intent(MainActivity.this, Login.class));
                    }
                },5000);
            }
        });



    }

    @Override
    protected void onResume() {
        merlin.bind();
        super.onResume();
    }

    @Override
    protected void onPause() {
        merlin.unbind();
        super.onPause();
    }

}
