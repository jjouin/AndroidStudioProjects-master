package fr.julien.eldapptest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class ContactView extends AppCompatActivity {

    private Long _id;
    private Directory D;
    private Contacts C;
    private ContactsDA0 ContDAO ;
    private TextView textName;
    private TextView textNumber;
    private ImageButton buttonDel;
    private ImageButton buttonCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);
        ContDAO =new ContactsDA0(this);
        ContDAO.open();
        Intent i = getIntent();
        _id=i.getLongExtra(Directory.Nom,1);
        textName=(TextView)findViewById(R.id.Name);
        textNumber=(TextView)findViewById(R.id.Number);
        buttonCall=(ImageButton)findViewById(R.id.buttonCall);
        buttonDel=(ImageButton)findViewById(R.id.buttonDel);



        C=ContDAO.selectByID(_id);
        textName.setText(C.getName());
        textNumber.setText(C.getNumberPhone());

        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Uri telephone = Uri.parse("tel:"+C.getNumberPhone());
                Intent secondActivity = new Intent(Intent.ACTION_DIAL, telephone);
                startActivity(secondActivity);
            }
        });
    }
    @Override
    public Dialog onCreateDialog(final int id){


        final ContactsDA0 ContDAO =new ContactsDA0(this);
        ContDAO.open();




        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you really want to delete  this contact?");
        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ContDAO.deleteContact((long) _id);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


        return super.onCreateDialog(id);
    }




    @Override
    public void onPrepareDialog (int id, Dialog box){

    }
}

