package fr.julien.eldapptest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsDAO extends DAOBase {

    public ItemsDAO (Context pContext) {
        super(pContext);
    }

    public void insertItems(Items i)
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_name, i.getName());
        values.put(DataBaseManager.Constants.KEY_COL_USE, 1);
        values.put(DataBaseManager.Constants.KEY_COL_IN, 1);
        mDb.insert(DataBaseManager.Constants.myTableItems,null,values);
    }

    public void deleteContact( Long id )
    {
        mDb.delete(DataBaseManager.Constants.myTableItems, DataBaseManager.Constants.KEY_COL_ID +"=?", new String[]{String.valueOf(id)});
    }

    public void upDateItems (Items i)
    {
        ContentValues values = new ContentValues();
        if (i.getIn()==0)
            values.put(DataBaseManager.Constants.KEY_COL_USE, i.getUse()+1);
        values.put(DataBaseManager.Constants.KEY_COL_IN, (i.getIn()+1)%2);
        values.put(DataBaseManager.Constants.KEY_COL_STRIKE, 0);
        mDb.update(DataBaseManager.Constants.myTableItems, values, DataBaseManager.Constants.KEY_COL_ID+"=?", new String[]{String.valueOf(i.getId())});
    }

    public void upDateItems2 (Items i)
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_STRIKE, (i.getStrike()+1)%2);
        mDb.update(DataBaseManager.Constants.myTableItems, values, DataBaseManager.Constants.KEY_COL_ID+"=?", new String[]{String.valueOf(i.getId())});
    }


    public Items selectByID (long id)
    {
        String[] column = {DataBaseManager.Constants.KEY_COL_ID,DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_USE,
                DataBaseManager.Constants.KEY_COL_IN, DataBaseManager.Constants.KEY_COL_STRIKE};
        String selection = DataBaseManager.Constants.KEY_COL_ID + "=?";
        String[] selectionArg = new String[] { Long.toString(id) };
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,DataBaseManager.Constants.KEY_COL_USE,"60" );
        c.moveToNext();
        Items item= new Items(c.getLong(0),c.getString(1),c.getLong(2), c.getInt(3), c.getInt(4));
        return item;
    }


    public ArrayList<String>  selectedList ()
    {

        ArrayList<String> _list  = new ArrayList<String>();
        String[] column = {DataBaseManager.Constants.KEY_COL_name};
        String selection = DataBaseManager.Constants.KEY_COL_IN+ "=?";
        String[] selectionArg = new String[] {"1"};
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,DataBaseManager.Constants.KEY_COL_USE,"60" );
        while (c.moveToNext())
        {
            String name =c.getString(0);
            _list.add(name);
        }
        return _list;
    }


    public Items selectByName (String str)
    {
        String[] column = {DataBaseManager.Constants.KEY_COL_ID,DataBaseManager.Constants.KEY_COL_name, DataBaseManager.Constants.KEY_COL_USE, DataBaseManager.Constants.KEY_COL_IN,DataBaseManager.Constants.KEY_COL_STRIKE};
        String selection = DataBaseManager.Constants.KEY_COL_name + "=?";
        String[] selectionArg = new String[] { str};
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,DataBaseManager.Constants.KEY_COL_USE,"60" );
        c.moveToNext();
        Items item= new Items(c.getLong(0),c.getString(1),c.getLong(2), c.getInt(3),c.getInt(4));
        return item;
    }

    public ArrayList<String>  selectByName2 (String str)
    {
        ArrayList<String> _list = new ArrayList<String> () ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name};
        String selection = DataBaseManager.Constants.KEY_COL_name+ "=?";
        String[] selectionArg = new String[] {str};
        //Cursor c = bdd.query(TABLE_LIVRES, new String[] {COL_ID, COL_ISBN, COL_TITRE}, COL_TITRE + " LIKE \"" + titre +"\"", null, null, null, null);

        Cursor c = mDb.query( DataBaseManager.Constants.myTableItems, column,  DataBaseManager.Constants.KEY_COL_name +" LIKE '%"+str+"%'", null,null,null,null);
       // Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,selection,selectionArg,null,null,null,"10" );


            while (c.moveToNext()) {
                String name = c.getString(0);
                _list.add(name);
            }
        return _list;
    }

    public void DeleteAllItems ()
    {
        ContentValues values = new ContentValues();
        values.put(DataBaseManager.Constants.KEY_COL_IN, 0);
        mDb.update(DataBaseManager.Constants.myTableItems, values, DataBaseManager.Constants.KEY_COL_IN+"=?", new String[]{String.valueOf(1)});
    }

    public List<String> select100 ()
    {
        List<String> _list = new ArrayList<String>() ;
        String[] column = {DataBaseManager.Constants.KEY_COL_name};
        Cursor c = mDb.query(DataBaseManager.Constants.myTableItems,column,"",null,
                null,null,DataBaseManager.Constants.KEY_COL_USE+" DESC","100" );
        while (c.moveToNext())
        {
            String name =c.getString(0);

            _list.add(name);
        }

        return _list;
    }
}
