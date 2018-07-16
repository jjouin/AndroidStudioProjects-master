package fr.julien.eldapptest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Shop extends AppCompatActivity {

    private ImageButton CallButton;
    private ImageButton AddButton;
    private ArrayList<String> _list;
    private ListView _view;
    private ImageButton DeleteButton;
    private final static int ID_DIALOG_DELETE=0;
    private int _position;
    private ItemsDAO itemsDAO = new ItemsDAO(this);
    MyListAdaptater adaptater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        _view = (ListView) findViewById(R.id.listView);
        this.AddButton = (ImageButton) findViewById(R.id.AddButton);

        this.DeleteButton=(ImageButton) findViewById(R.id.DeleteButton);
        CallButton=(ImageButton)findViewById(R.id.CallButton);




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
                Intent otherAct = new Intent(getApplicationContext(),AddShop.class);
                startActivity(otherAct);
            }

        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        itemsDAO.open();
        _list = itemsDAO.selectedList();
        _view.setAdapter(new MyListAdaptater(this,R.layout.list, _list));




        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(ID_DIALOG_DELETE);
            }
        });

    }


    @Override
    public Dialog onCreateDialog(int id){

        switch (id){
            case ID_DIALOG_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete all your shoplist?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                            itemsDAO.DeleteAllItems();
                             _list.clear();
                             _view.setAdapter(new MyListAdaptater(getApplicationContext(),R.layout.list, _list));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;


        }
        return super.onCreateDialog(id);
    }


    private  class MyListAdaptater extends ArrayAdapter<String> {
        private int layout;
        List<String> l;
        Context c;

        private MyListAdaptater(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
            layout = resource;
            this.c=context;
            this.l=objects;
        }

        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View v = inflater.inflate(layout, parent, false);
            final String str = l.get(position);
            Items i = itemsDAO.selectByName(str);
            ImageButton delete = (ImageButton) v.findViewById(R.id.buttonDelete);
            TextView item = (TextView) v.findViewById(R.id.text);
            item.setText(str);
            if (i.getStrike()!=0)
            item.getPaint().setStrikeThruText(true);


            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // l.remove(position);
                    Items i = itemsDAO.selectByName(str);
                   itemsDAO.upDateItems(i);
                   _list=itemsDAO.selectedList();
                    _view.setAdapter(new MyListAdaptater(getApplicationContext(),R.layout.list, _list));
                }
            });
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Items i = itemsDAO.selectByName(str);
                    itemsDAO.upDateItems2(i);
                    _view.setAdapter(new MyListAdaptater(getApplicationContext(),R.layout.list, _list));

                }
            });
            return v;
        }
    }




}
