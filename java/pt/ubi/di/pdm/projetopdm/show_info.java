package pt.ubi.di.pdm.projetopdm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class show_info extends AppCompatActivity {

    UserLocalDB db;
    Button btBack, btUpdate, btDelete;
    TextView tvSite, tvUser, tvPass;
    String username, website, login, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        db = new UserLocalDB(this);
        btBack = (Button) findViewById(R.id.btback);
        btUpdate = (Button) findViewById(R.id.update);
        btDelete = (Button) findViewById(R.id.Apagar);
        tvSite = (TextView) findViewById(R.id.tvwebinfo);
        tvUser = (TextView) findViewById(R.id.tvuserinfo);
        tvPass =  (TextView) findViewById(R.id.tvpassinfo);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            username = extras.getString("username");
            website = extras.getString("website");
        }
        SQLiteDatabase bd = db.getReadableDatabase();
        Cursor c = bd.query("info", null, "username=? and website=?", new String[]{username,website},null,null,null);
        while(c.moveToNext()){
            login = c.getString(2);
            password = c.getString(3);
        }
        tvSite.setText(website);
        tvUser.setText(login);
        tvPass.setText(password);



        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(show_info.this, mainpage.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Confirmalert();
            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(show_info.this, updateInfo.class);
                j.putExtra("username", username);
                j.putExtra("website", website);
                startActivity(j);
                finish();
            }
        });





    }

    public void Confirmalert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("You're about to delete the login information for this website. Do you want to proceed?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteInfo(username,website);
                Toast.makeText(getApplicationContext(),"You've deleted the information from this website", Toast.LENGTH_SHORT).show();
                Intent j = new Intent(show_info.this, mainpage.class);
                j.putExtra("username", username);
                startActivity(j);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
