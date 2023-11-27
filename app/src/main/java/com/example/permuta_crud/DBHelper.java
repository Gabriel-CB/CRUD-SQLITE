package com.example.permuta_crud;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.permuta_crud.main.FormProdutos;
import com.google.android.gms.maps.model.LatLng;

import java.sql.Array;
import java.util.ArrayList;

public class DBHelper  extends SQLiteOpenHelper {

    private static String dbName = "crud.db";

    public DBHelper(Context context) {
        super(context, dbName, null, 1);
        onCreate(this.getWritableDatabase());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try{

            db.execSQL("CREATE TABLE IF NOT EXISTS fornecedores(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nome VARCHAR" +
                    ")");
            db.execSQL("CREATE TABLE IF NOT EXISTS produtos(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                    "nome VARCHAR," +
                    "fornecedor_id INT," +
                    "FOREIGN KEY ( fornecedor_id ) REFERENCES fornecedores ( id ) "+
                    ")");

        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE pessoas");
        db.execSQL("DROP TABLE fornecedores");
        onCreate(db);
    }

    public ArrayList<Suplier> listSuplier (){
         try{

             SQLiteDatabase db = this.getWritableDatabase();

             Cursor cursor = db.rawQuery("Select * FROM fornecedores ORDER BY id DESC", null);

             ArrayList<Suplier> linhas = new ArrayList<Suplier>();

             cursor.moveToLast();
             int size =cursor.getPosition();
             cursor.moveToFirst();

             for(int i=0; i<=size;i++){
                 linhas.add(new Suplier(cursor.getString(0), cursor.getString(1)));
                 cursor.moveToNext();
             }

                return linhas;
         }catch(Exception e){
             e.printStackTrace();Log.e("frag","Erro ao listar fornecedores: "+e.getMessage());
         }
         return null;
     }

    public ArrayList<String> listSuplierForInput (String suplier_id){
        try{

            SQLiteDatabase db = this.getWritableDatabase();

            if(suplier_id != "" && !TextUtils.isEmpty(suplier_id)){
                suplier_id = " WHERE f.id != " +suplier_id;
            }

            Cursor cursor = db.rawQuery("Select * FROM fornecedores as f " + suplier_id + " ORDER BY nome ASC", null);

            cursor.moveToLast();
            int size =cursor.getPosition();
            cursor.moveToFirst();

            ArrayList<String> linhas = new ArrayList<String>();

            for(int i=0; i<=size;i++){
                linhas.add( cursor.getString(0) + " - " + cursor.getString(1));
                cursor.moveToNext();
            }

            return linhas;
        }catch(Exception e){
            e.printStackTrace();
            Log.e("frag","Erro ao listar fornecedores: "+e.getMessage());
        }
        return null;
    }

    public ArrayList<Product> listProducts (){
        try{

            SQLiteDatabase db = this.getWritableDatabase();

            Cursor cursor = db.rawQuery("Select p.id, p.nome, f.id, f.nome FROM produtos as p INNER JOIN fornecedores as f ON f.id = p.fornecedor_id ORDER BY p.id DESC", null);

            ArrayList<Product> linhas = new ArrayList<Product>();

            cursor.moveToLast();
            int size = cursor.getPosition();
            cursor.moveToFirst();

            for(int i=0; i<=size;i++){
                linhas.add(new Product(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
                cursor.moveToNext();
            }

            return linhas;
        }catch(Exception e){
            e.printStackTrace();
            Log.e("frag","Erro ao listar produtos: "+e.getMessage());
        }
        return null;
    }

    public void insert (String table, ArrayList<String> campos,  ArrayList<String> valores,Context context){
        try{
           SQLiteDatabase db = this.getWritableDatabase();

            String stringCampos = "";
            String stringValores = "";

            for(int i=0; i < campos.size(); i++){

                if(campos.size()>1 && ( i + 1 ) != campos.size()){
                    stringCampos += campos.get(i) + ", ";
                    stringValores += "'"+valores.get(i) + "', ";
                }else{
                    stringCampos += campos.get(i) ;
                    stringValores += "'"+valores.get(i)+"'";
                }
            }

            db.execSQL("INSERT INTO "+table+
                    "(" + stringCampos + ") VALUES " +
                    "(" + stringValores + ")"
                    );
            Toast.makeText(
                    context, "Registro inserido com sucesso!",
                    Toast.LENGTH_SHORT
            ).show();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(
                    context, "Não foi possivel atualizar o registro!",
                    Toast.LENGTH_LONG
            ).show();
            Log.e("frag","Erro ao adicionar os/as " +table+": " +e.getMessage());
        }
    }

    public void update (String table, ArrayList<String> campos,  ArrayList<String> valores, Context context){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            String newValues = "";

            for(int i=1; i < campos.size(); i++){
                if(campos.size() > 2 && ( i + 1 ) != campos.size()){
                    newValues += campos.get(i) + " = '" + valores.get(i) + "', ";
                }else{
                    newValues += campos.get(i) + " = '" + valores.get(i)+"'";
                }
            }
            Log.w("frag","Update "+table+ " SET " + newValues +
                    " WHERE id = " + valores.get(0));
            db.execSQL("Update "+table+ " SET " + newValues +
                    " WHERE id = " + valores.get(0)
            );
            Toast.makeText(
                    context, "Registro atualizado com sucesso!",
                    Toast.LENGTH_SHORT
            ).show();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(
                    context, "Não foi possivel atualizar o registro!",
                    Toast.LENGTH_LONG
            ).show();
            Log.e("frag","Erro ao atualiar os/as " +table+": "+e.getMessage());
        }
    }

    public void delete (String table, String id, Context context){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            int size = -1;
            if(table == "fornecedores"){
                Cursor cursor = db.rawQuery("Select * FROM produtos WHERE fornecedor_id = " + id, null);

                cursor.moveToLast();
                size = cursor.getPosition();
            }

            if(size == -1) {
                db.execSQL("DELETE FROM " + table + " WHERE ID = " + id);

                Toast.makeText(
                        context, "Registro excluido com sucesso!",
                        Toast.LENGTH_SHORT
                ).show();
            }else{
                Toast.makeText(
                        context, "Não foi possivel excluir pois ha um produto cadastrado com esse fornecedor!",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(
                    context, "Não foi possivel excluir o registro!",
                    Toast.LENGTH_LONG
            ).show();
            Log.e("frag","Erro ao excluir o/a " +table+": " +e.getMessage());
        }
    }
}
