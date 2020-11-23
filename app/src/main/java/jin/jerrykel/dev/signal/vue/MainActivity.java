package jin.jerrykel.dev.signal.vue;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.AppsActivity;
import jin.jerrykel.dev.signal.vue.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        int times = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, times);
    }
}