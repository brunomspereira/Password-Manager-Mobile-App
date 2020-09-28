package pt.ubi.di.pdm.projetopdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.Inet4Address;
import java.util.ArrayList;


public class mainpage extends AppCompatActivity {

    Button btLogout, btAdd;
    UserLocalDB dbhelper;
    String username;
    ListView list;
    ArrayList<String> listItem;
    ArrayList<String> listItemID;
    String[] arguments = {"0"};
    long count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        dbhelper = new UserLocalDB(this);

        btLogout = (Button) findViewById(R.id.Logout);
        btAdd = (Button) findViewById(R.id.Adicionar);
        list = (ListView) findViewById(R.id.lst);
        listItem = new ArrayList<>();
        listItemID = new ArrayList<>();
        Log.i(" INFO", "Oncreate");

        Bundle extras = getIntent().getExtras();

        if(extras!=null){
            username = extras.getString("username");
        }

        arguments[0] = username;

    }

    @Override
    public void onResume(){
        super.onResume();

        Log.i(" INFO", "onResume");
        dbhelper = new UserLocalDB(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Log.i(" INFO", "Before Cursor");
        Cursor c = db.query("info", null,"username = ?", new String[]{username},null,null,null);
        Log.i(" INFO", "vhggvghgv");
        listItem.clear();
        listItemID.clear();

        count = 0;
        Log.i(" INFO", "Dentro do while");
        if(c.getCount()>0){
            while(c.moveToNext()){
                count++;
                String sites = c.getString(1);
                listItem.add(sites);

                Log.i(" INFO", "Dentro do while");
                ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listItem);
                list.setAdapter(adapter);
                list.setVisibility(View.VISIBLE);
            }
        }
        else {
            list.setVisibility(View.GONE);
        }
        c.close();
        db.close();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String web = listItem.get(position);
                Intent y = new Intent(mainpage.this, show_info.class);
                y.putExtra("username", username);
                y.putExtra("website", web);
                startActivity(y);



            }
        });

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mainpage.this, addInfo.class);
                i.putExtra("username", username);
                startActivity(i);
                finish();
            }
        });

        btLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(mainpage.this, MainActivity.class);
                startActivity(j);
                finish();
            }
        });
    }
}
