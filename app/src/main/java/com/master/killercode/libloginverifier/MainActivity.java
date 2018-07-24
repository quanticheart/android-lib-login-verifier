package com.master.killercode.libloginverifier;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.master.killercode.loginverifier.ListActivity.ListActivity;
import com.master.killercode.loginverifier.ListActivity.UserModel;
import com.master.killercode.loginverifier.LoginVerifier;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.btn);
        final EditText et = findViewById(R.id.et);
        final EditText etn = findViewById(R.id.etn);
        final EditText etw = findViewById(R.id.etw);

        final LoginVerifier log = new LoginVerifier(MainActivity.this);
        UserModel user = UserModel.getData(MainActivity.this);

        String userName = user.getUser();
        String pass = user.getPass();
        String name = user.getUserName();

        if (userName.equals("")||pass.equals("")){

            et.setText(user.getUser());
            etn.setText(user.getUserName());
            etw.setText(user.getPass());

        }else{

            log.inLogin(name,userName, pass);

            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(intent);
            finish();

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, "Digite seu Login", Toast.LENGTH_SHORT).show();
                } else {

                    if (etn.getText().toString().trim().equals("")){
                        log.inLogin(et.getText().toString().trim() , etw.getText().toString().trim());
                    } else {
                        log.inLogin(etn.getText().toString().trim(), et.getText().toString().trim(), etw.getText().toString().trim());
                    }

                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent);
                    finish();
//                    log.inLogin();
//                    log.inLogin(et.getText().toString().trim());

                }
            }
        });


    }


}
