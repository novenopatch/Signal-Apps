package jin.jerrykel.dev.signal.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.vue.BaseActivity;

public class AppsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
    }
}