package fr.julien.eldapptest;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Call extends AppCompatActivity {
    private ListView ContactView;
    private List<HashMap<String, String>> _list;
    private ImageButton AddContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        _list = new ArrayList<HashMap<String, String>>();


        ContactView=(ListView)findViewById(R.id.ContactView);
        AddContact = (ImageButton)findViewById(R.id.AddContact);


        ContactsDA0 ContDAO =new ContactsDA0(this);
        ContDAO.open();

        ContactView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> element =_list.get(position);
                String number = element.get("Text2");
                Uri telephone = Uri.parse("tel:"+number);
                Intent secondActivity = new Intent(Intent.ACTION_DIAL, telephone);
                startActivity(secondActivity);
            }
        });

        AddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),AddContact.class);
                startActivity(otherAct);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        _list.clear();

    }



    @Override
    protected void onStart() {
        super.onStart();
        _list.clear();
        ContactsDA0 ContDAO =new ContactsDA0(this);
        ContDAO.open();
        _list = ContDAO.select3();
        // Contacts contacts=ContDAO.selectByID(3);



        final ListAdapter adapter = new SimpleAdapter(this,
                _list,
                android.R.layout.simple_list_item_2,
                new String[]{"Text1", "Text2"},
                new int[]{android.R.id.text1, android.R.id.text2});


        ContactView.setAdapter(adapter);





    }

}
