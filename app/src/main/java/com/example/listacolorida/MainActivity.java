package com.example.listacolorida;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button btnTrocaTela;
    SQLiteDatabase bd;

    private ListView lvItens;

    private ArrayList<String> textos = new ArrayList<>();

    public ArrayList<Integer> arrayIds;

    public ArrayList<Integer> arrayCores;

    public ArrayList<String> arrayTextos;

    public Integer corSelecionada;

    public String textoSelecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItens = findViewById(R.id.lvItens);

        criarBd();
        listarDados();

        lvItens.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluirItem(i);
                return true;
            }
        });

        lvItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                corSelecionada = arrayCores.get(i);
                textoSelecionado = arrayTextos.get(i);
                mostrarCor(corSelecionada, textoSelecionado);
            }
        });
    }

    public void criarBd(){
        bd = openOrCreateDatabase( "textos", MODE_PRIVATE, null );
        String cmd;
        cmd = "CREATE TABLE IF NOT EXISTS textos ";
        cmd = cmd + "( id INTEGER PRIMARY KEY AUTOINCREMENT, texto VARCHAR, cor VARCHAR)";
        bd.execSQL( cmd );
    }


    @SuppressLint("Range")
    public void listarDados(){
        try{
            arrayIds = new ArrayList<>();
            arrayCores = new ArrayList<>();
            arrayTextos = new ArrayList<>();
            bd = openOrCreateDatabase( "textos", MODE_PRIVATE, null );
            Cursor cursor = bd.rawQuery("SELECT id, texto, cor FROM textos", null);

            ArrayList<String> linhas = new ArrayList<String>();

            ArrayAdapter adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_list_item_1, android.R.id.text1, linhas
            );
            lvItens.setAdapter(adapter);



            cursor.moveToFirst();
            while(cursor!=null){
                linhas.add(cursor.getString(1));
                arrayIds.add(cursor.getInt(0));
                arrayCores.add(cursor.getInt(2));
                arrayTextos.add(cursor.getString(1));
                cursor.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    

    public void excluirItem(Integer i){
        try {
            bd = openOrCreateDatabase( "textos", MODE_PRIVATE, null );
            String sql = "DELETE FROM TEXTOS WHERE ID = ?";
            SQLiteStatement stmt = bd.compileStatement(sql);
            stmt.bindLong(1, arrayIds.get(i));
            stmt.executeUpdateDelete();
            listarDados();
            bd.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void mostrarCor(Integer i, String textoSelecionado){
        String cor = "";
        try {

            if(i == 0){
                cor = "Vermelho";
            }else if(i == 1){
                cor = "Verde";
            }else if(i == 2){
                cor = "Azul";
            }
            Toast.makeText(this,textoSelecionado + " " + cor, Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void irTelarAdicionar(View view){
        Intent intent1 = new Intent(getApplicationContext(), InserirActivity.class);
        startActivity(intent1);
    }


}