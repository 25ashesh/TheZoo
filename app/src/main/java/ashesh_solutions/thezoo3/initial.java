package ashesh_solutions.thezoo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class initial extends AppCompatActivity {
    private Handler mHandler = new Handler();
    boolean quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);
        quit = false;

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ( !quit)
                    doStuff();
            }
        },2000);
    }
    private void doStuff(){
        Intent intent = new Intent(initial.this,Zoo.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed(){
        quit=true;
        super.onBackPressed();
    }
}
