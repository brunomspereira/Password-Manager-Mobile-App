package pt.ubi.di.pdm.projetopdm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import org.w3c.dom.Text;

import java.net.Inet4Address;

public class Register extends Activity implements View.OnClickListener {

    UserLocalDB db;
    TextView bBack;
    Button bRegister;
    EditText reUsername, rePassword, reType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new UserLocalDB(this);

        reUsername = (EditText) findViewById(R.id.UsernameRegister);
        rePassword = (EditText) findViewById(R.id.PasswordRegister);
        reType = (EditText) findViewById(R.id.PasswordRetype);
        bRegister  = (Button) findViewById(R.id.RegisterButton);
        bBack = (TextView) findViewById(R.id.already);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = reUsername.getText().toString();
                String Password = rePassword.getText().toString();
                String retype = reType.getText().toString();

                if (username.equals("") || Password.equals("") || retype.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Password.equals(retype)) {
                        Boolean chkusername = db.chkusername(username);
                        if (chkusername == true) {
                            Boolean insert = db.insert(username, Password);
                            if (insert == true) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
                                Intent j = new Intent(Register.this, mainpage.class);
                                j.putExtra("username", username);
                                startActivity(j);
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Register.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
    @Override
    public void onClick(View v) {

    }

}
