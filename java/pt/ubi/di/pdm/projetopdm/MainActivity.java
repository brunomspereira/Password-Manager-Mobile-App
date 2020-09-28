package pt.ubi.di.pdm.projetopdm;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity implements View.OnClickListener {

    UserLocalDB db;
    EditText username;
    EditText password;
    Button entrar;
    TextView tvRegister;
    UserLocalDB userlocalStore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new UserLocalDB(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        entrar = (Button) findViewById(R.id.Entrar);
        tvRegister = (TextView) findViewById(R.id.register);

        userlocalStore = new UserLocalDB(this);

        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                Boolean chkuserpass = db.userpass(user, pass);
                if(user.equals("") || pass.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(chkuserpass== true){
                        Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent j = new Intent(MainActivity.this, mainpage.class);
                        j.putExtra("username", user);
                        startActivity(j);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
                finish();
            }
        });

    }


    @Override
    public void onClick(View v) {

    }
}