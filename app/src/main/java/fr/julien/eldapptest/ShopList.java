package fr.julien.eldapptest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShopList extends AppCompatActivity {

    private ImageButton AddButton;
    private ImageButton CallButton;
    private List<HashMap<String, String>> _list;
    private ListView _view;
    private String LIST="list.txt";
    private FileOutputStream out=null;
    private FileInputStream in=null;
    private ImageButton DeleteButton;
    private EditText _addText;
    private EditText _addCom ;
    private final static int ID_DIALOG_DELETE=0;
    private final static int ID_DIALOG_CALL=1;
    private final static int ID_DIALOG_DELETE_item=2;
    private int _position;
    private boolean all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
        _view = (ListView) findViewById(R.id.ShopList);

        this.DeleteButton=(ImageButton) findViewById(R.id.DeleteButton);

        _addText = (EditText)findViewById(R.id.AddText);
        _addCom = (EditText)findViewById(R.id.AddQuantity);

        this.AddButton = (ImageButton) findViewById(R.id.AddButton);
        CallButton = (ImageButton)findViewById(R.id.CallButton);
        _list = new ArrayList<HashMap<String, String>>();

        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent otherAct = new Intent(getApplicationContext(),Call.class);
                startActivity(otherAct);
            }
        });

        final ListAdapter adapter = new SimpleAdapter(this,
                _list,
                android.R.layout.simple_list_item_2,
                new String[]{"Text1", "Text2"},
                new int[]{android.R.id.text1, android.R.id.text2});



        _view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                all=false;
                _position=position;
                showDialog(0);


            }
        });
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all=true;
                showDialog(0);
                _view.setAdapter(adapter);
                /*_list.clear();
                 */

            }
        });

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _addStr=_addText.getText().toString();
                String _comStr = _addCom.getText().toString();
                if (_comStr.equals(""))_comStr="  ";

                _addText.getText().clear();
                _addCom.getText().clear();


                HashMap<String, String> element = new HashMap<>();
                element.put("Text1",_addStr);
                element.put("Text2",_comStr);
                _list.add(element);

                _view.setAdapter(adapter);

            }
            //maListView.setOnItemClickListener()

        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            out= openFileOutput(LIST, MODE_PRIVATE);
            //out.write("".getBytes());
            out.write(_list.toString().getBytes());
            if (out != null)
                out.close();
        }catch (FileNotFoundException e ) {e.printStackTrace();} catch (IOException e ){e.printStackTrace();}
        _list.clear();

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onStart() {
        super.onStart();


        final ListAdapter adapter = new SimpleAdapter(this,
                _list,
                android.R.layout.simple_list_item_2,
                new String[]{"Text1", "Text2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        _view.setAdapter(adapter);
        try {
            FileInputStream input = openFileInput(LIST);
            int value;
            // On utilise un StringBuffer pour construire la chaîne au fur et à mesure
            StringBuilder lu = new StringBuilder();
            // On lit les caractères les uns après les autres
            while ((value = input.read()) != -1) {
                // On écrit dans le fichier le caractère lu
                lu.append((char) value);
            }
            String[] strRead = lu.toString().split(",");
            for (int i = 0; i < strRead.length / 2; i++) {

                HashMap<String, String> element = new HashMap<>();
                String[] T2 = strRead[i * 2].split("=");
                String[] T1 = strRead[i * 2 + 1].split("=");


                element.put("Text1", T1[1].split("\\}")[0]);
                element.put("Text2", T2[1]);

                _list.add(element);
            }
            if (in != null)
                in.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Dialog onCreateDialog(int id){
        final ListAdapter adapter = new SimpleAdapter(this,
                _list,
                android.R.layout.simple_list_item_2,
                new String[]{"Text1", "Text2"},
                new int[]{android.R.id.text1, android.R.id.text2});
        switch (id){
            case ID_DIALOG_DELETE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you want to delete all your shoplist ?");
                builder.setCancelable(true);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (all) _list.clear();
                        else _list.remove(_position);
                        _view.setAdapter(adapter);

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




    @Override
    public void onPrepareDialog (int id, Dialog box){

    }
}
