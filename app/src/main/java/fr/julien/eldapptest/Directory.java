package fr.julien.eldapptest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Directory extends AppCompatActivity {

    private ListView ContactView;
    private ImageButton CallButton;
    private DataBaseManager dbManager;
    private SQLiteDatabase db;
    private List<HashMap<String, String>> _list;
    private Button AddButton;
    private int _position;
    final private int ID_DIALOG_DELETE =0;
    public final static String Nom= "com.example.carla.app1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        _list = new ArrayList<HashMap<String, String>>();
        ContactView=(ListView)findViewById(R.id.ContactView);
        CallButton = (ImageButton)findViewById(R.id.CallButton);

        ContactsDA0 ContDAO =new ContactsDA0(this);
        ContDAO.open();

        AddButton=(Button)findViewById(R.id.AddButton);

        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),Call.class);
                startActivity(otherAct);
            }
        });


        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),AddContact.class);
                startActivity(otherAct);
            }
        });

        ContactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // _position=position;

                Intent otherAct = new Intent(getApplicationContext(),ContactView.class);
                otherAct.putExtra(Nom,getContactId(position));
                startActivity(otherAct);
            }
        });





    }
    @Override
    protected void onPause() {
        super.onPause();
        _list.clear();

    }



    public Long getContactId(int position)
    {
        HashMap<String, String> element =_list.get(position);
        String _id = element.get("id");
        return (Long.parseLong(_id));
    }

    @Override
    protected void onStart() {
        super.onStart();
        _list.clear();
        ContactsDA0 ContDAO =new ContactsDA0(this);
        ContDAO.open();
        _list = ContDAO.selectALL();
        // Contacts contacts=ContDAO.selectByID(3);



        final ListAdapter adapter = new SimpleAdapter(this,
                _list,
                android.R.layout.simple_list_item_2,
                new String[]{"Text1", "Text2"},
                new int[]{android.R.id.text1, android.R.id.text2});


        ContactView.setAdapter(adapter);





    }


}
