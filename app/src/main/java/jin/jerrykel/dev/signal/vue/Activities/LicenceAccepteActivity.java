package jin.jerrykel.dev.signal.vue.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.controler.Controler;
import jin.jerrykel.dev.signal.model.InfomationAppUser;
import jin.jerrykel.dev.signal.utils.Utils;


public class LicenceAccepteActivity extends AppCompatActivity {
    private Controler controler;
    private  InfomationAppUser infomationAppUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controler = Controler.getInstance(this);
        setContentView(R.layout.activity_licence_acepte);
        TextView textView1Licence = findViewById(R.id.textView1Licence);
        TextView textView2Licence = findViewById(R.id.textView2Licence);
        String htmlStr = getString(R.string.licence_bla_part1_1) + " <b>"+getString(R.string.licence_bla_part1_2)+"</b>. "+getString(R.string.licence_bla_part1_3);
        String htmlStr1 = getString(R.string.licence_bla_part2_1)+" <b>"+getString(R.string.licence_bla_part2_2)+"</b>. ";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            textView1Licence.setText(Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_LEGACY));
            textView2Licence.setText(Html.fromHtml(htmlStr1, Html.FROM_HTML_MODE_LEGACY));
        }else {
            textView1Licence.setText(Html.fromHtml(htmlStr));
            textView2Licence.setText(Html.fromHtml(htmlStr1));
        }
        textView2Licence.setOnClickListener(v -> {
          Utils.openTermsOfServicePage(LicenceAccepteActivity.this);
        });
        textView1Licence.setOnClickListener(v -> {
            Utils.openPrivacyPage(LicenceAccepteActivity.this);
        });
        infomationAppUser = controler.getManager().getInformation();
        if(infomationAppUser== null){
            infomationAppUser = new InfomationAppUser(true,new Date());
            controler.getManager().insertInformation(infomationAppUser);
        }

        Log.d("infomationAppUser1",infomationAppUser.toString());
    }

    public void BOnclick(View view) {


        infomationAppUser.setFirstLaunch(false);
        controler.getManager().updateInformation(infomationAppUser);
        startActivity(new Intent(this, LoginActivity.class));
        Log.d("infomationAppUser2",infomationAppUser.toString());
        finish();
    }

}