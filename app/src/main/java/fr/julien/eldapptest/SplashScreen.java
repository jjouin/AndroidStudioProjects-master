package fr.julien.eldapptest;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread myThread = new Thread (){
            @Override
            public void run(){
                try {
                    android.os.SystemClock.sleep(3000);
                    android.content.Intent intent = new android.content.Intent(getApplicationContext(), MainActivity.class);
                    startActivity (intent);
                    finish();
                } catch ( Exception e){
                    e.printStackTrace();
                }
            }

        };
        myThread.start();
    }
}
