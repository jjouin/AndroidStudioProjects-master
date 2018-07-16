package fr.julien.eldapptest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private ImageButton shoplist;
    private ImageButton coffee;
    private ImageButton appointment;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.shoplist= findViewById(R.id.shoplist);
        this.coffee=findViewById(R.id.coffee);
        this.appointment=findViewById(R.id.appointment);

        shoplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),ShopList.class);
                startActivity(otherAct);
            }
        });
        coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),Directory.class);
                startActivity(otherAct);

            }
        });

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),Shop.class);
                startActivity(otherAct);
            }
        });
    }
}

