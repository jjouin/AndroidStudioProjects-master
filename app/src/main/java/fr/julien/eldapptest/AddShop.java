package fr.julien.eldapptest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class AddShop extends AppCompatActivity {

    private ListView ItemsView;
    private EditText AddText;
    private ImageButton AddButton;
    private ImageButton CallButton;
    private ImageButton Back;
    private List<String> _list;
    ItemsDAO itemsDAO= new ItemsDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        _list = new ArrayList<>();
        ItemsView=findViewById(R.id.listView);
        AddText =findViewById(R.id.AddText) ;
        AddButton= findViewById(R.id.AddButton);
        Back = findViewById(R.id.imageButton);
        CallButton = findViewById(R.id.CallButton);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _list.clear();

    }


    @Override
    protected void onStart() {
        super.onStart();

        itemsDAO.open();
        setAdaptaterView();

        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),Call.class);
                startActivity(otherAct);
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AddText.getText().length()>0)
                {
                    Log.i("Coucou","non vide");
                    Items i = new Items(1,AddText.getText().toString(), 1,1,0);
                    itemsDAO.insertItems(i);
                    AddText.setText("");
                    setAdaptaterView();
                }
            }
        });

        AddText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                _list = itemsDAO.selectByName2(AddText.getText().toString());
                ListAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_checked, _list);
                ItemsView.setAdapter(adapter);

            }
        });

        ItemsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Items i = getItem(position);
                    itemsDAO.upDateItems(i);
            }
        });
    }

    private void setAdaptaterView ()
    {
        _list = itemsDAO.select100();
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_checked, _list);

        ItemsView.setAdapter(adapter);

        for (int i = 0; i < ItemsView.getCount(); i++) {
            // For every other item in the list, set as checked.
            Items items= getItem(i);
            if (items.getIn()==1)
                ItemsView.setItemChecked(i,true);
        }
    }

    public Items getItem(int position)
    {
        String str =_list.get(position);
        return (itemsDAO.selectByName(str));
    }
}

