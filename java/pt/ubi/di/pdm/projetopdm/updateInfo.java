package pt.ubi.di.pdm.projetopdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

public class updateInfo extends AppCompatActivity {

    UserLocalDB db;
    Button btBack, btUpdate, generatePASS;
    TextView websiteSHOW;
    EditText userINFO, passINFO;
    String username, website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        btBack = (Button) findViewById(R.id.btBackINFO);
        btUpdate = (Button) findViewById(R.id.updateINFO);
        websiteSHOW = (TextView) findViewById(R.id.tvwebinfo);
        userINFO = (EditText) findViewById(R.id.etUserInfo);
        passINFO = (EditText) findViewById(R.id.etPassInfo);
        generatePASS = (Button) findViewById(R.id.generatepasswordUPDATE);
        db = new UserLocalDB(this);

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            username = extras.getString("username");
            website = extras.getString("website");
        }

        websiteSHOW.setText(website);


        generatePASS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int length = 12;
                passINFO.setText(Getpassword(length), TextView.BufferType.EDITABLE);
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(updateInfo.this, show_info.class);
                intent.putExtra("username", username);
                intent.putExtra("website", website);
                startActivity(intent);
                finish();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = userINFO.getText().toString();
                String pass = passINFO.getText().toString();


                SQLiteDatabase bd = db.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("login", login);
                contentValues.put("password", pass);
                bd.update("info",contentValues,"website=? and username=?", new String[]{website,username});

                Toast.makeText(getApplicationContext(), "Information successfully changed", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(updateInfo.this, show_info.class);
                i.putExtra("username", username);
                i.putExtra("website", website);
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
