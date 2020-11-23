package jin.jerrykel.dev.signal.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import jin.jerrykel.dev.signal.R;
import jin.jerrykel.dev.signal.controler.Controler;

public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void login(View v){
        if(v.getId() == R.id.buttonLogin){
            final Button  buttonLogin = findViewById(R.id.buttonLogin);
            final EditText editextEmail = findViewById(R.id.editextEmail);
            final EditText editextPassword = findViewById(R.id.editextPassword);
            if(!editextEmail.getText().toString().isEmpty() && !editextPassword.getText().toString().isEmpty()){
                Controler controler = Controler.getInstance();
                if(controler.connnect(editextEmail.getText().toString(),editextPassword.getText().toString())){

                    buttonLogin.setText("SUCCESS");
                    buttonLogin.setBackgroundColor(Color.GREEN);
                    Intent intent = new Intent(getApplicationContext(), AppsActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(this,"error TO DO",Toast.LENGTH_SHORT).show();
                    buttonLogin.setText("ERROR");
                    buttonLogin.setBackgroundColor(Color.RED);
                    editextEmail.setTextColor(Color.RED);
                    editextPassword.setTextColor(Color.RED);
                    int times = 1000;
                    new Handler().postDelayed(new Runnable() {
                        @SuppressLint("ResourceAsColor")
                        @Override
                        public void run() {
                            buttonLogin.setBackgroundColor(R.color.loginConnectBackgroundColor);
                            buttonLogin.setText(R.string.login);
                            editextEmail.setTextColor(Color.WHITE);
                            editextPassword.setTextColor(Color.WHITE);
                        }
                    }, times);
                }
            }
        }else if(v.getId() == R.id.textViewSinscrire){
            Toast.makeText(this,"TO DO",Toast.LENGTH_LONG).show();
        }

    }
}