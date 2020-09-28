package pt.ubi.di.pdm.projetopdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class addInfo extends AppCompatActivity {

    UserLocalDB db;
    EditText siteINFO, userINFO, passINFO;
    Button addINFO, backINFO, generatePass;
    String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);
        db = new UserLocalDB(this);

        siteINFO = (EditText) findViewById(R.id.siteINFO);
        userINFO = (EditText) findViewById(R.id.usernameINFO);
        passINFO = (EditText) findViewById(R.id.passINFO);
        addINFO = (Button) findViewById(R.id.btAddInfo);
        backINFO = (Button) findViewById(R.id.backADD);
        generatePass = (Button) findViewById(R.id.generatePass);

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            username = extras.getString("username");
        }

        generatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = 12;
                passINFO.setText(Getpassword(length), TextView.BufferType.EDITABLE);
            }
        });


        addINFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String website = siteINFO.getText().toString();
                String userinfo = userINFO.getText().toString();
                String password = passINFO.getText().toString();

                if (website.equals("") || userinfo.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else   {
                    boolean checkInfo = db.chkInfo(website,username);
                    if(checkInfo == true){
                        boolean ins = db.insertInfo(username,website,userinfo,password);
                        if (ins == true) {
                            Toast.makeText(getApplicationContext(), "Information added successfully", Toast.LENGTH_SHORT).show();
                            Intent j = new Intent(addInfo.this, mainpage.class);
                            j.putExtra("username", username);
                            startActivity(j);
                            finish();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "You already have login information for this website", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        backINFO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(addInfo.this, mainpage.class);
                i.putExtra("username", username);
                startActivity(i);
                finish();
            }
        });
    }

    public String Getpassword(int length){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder stringBuilder = new StringBuilder();

        Random rand = new Random();

        for (int i = 0; i < length; i++){
            char c = chars[rand.nextInt(chars.length)];
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
