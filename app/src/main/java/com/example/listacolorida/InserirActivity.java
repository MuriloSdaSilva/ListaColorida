package com.example.listacolorida;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class InserirActivity extends AppCompatActivity {

    private Button irTelarInicio;
    private EditText txtTexto;
    private Button btnInsere;

    private RadioButton radVermelho;
    private RadioButton radVerde;
    private RadioButton radAzul;

    SQLiteDatabase bd;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);

        txtTexto = findViewById(R.id.txtTexto);
        btnInsere = findViewById(R.id.btnInsere);
        radVermelho = findViewById(R.id.radVermelho);
        radVerde = findViewById(R.id.radVerde);
        radAzul = findViewById(R.id.radAzul);
        Toast.makeText(this," " , Toast.LENGTH_SHORT).show();
        btnInsere.setOnClickListener(new EscutadorBotaoInserir());
    }

    private class EscutadorBotaoInserir implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int cor = -1;
            if(TextUtils.isEmpty(txtTexto.getText().toString())){
                mensagemTexto();
            }else{
                if((TextUtils.isEmpty(radVermelho.getText().toString()) || !TextUtils.isEmpty(radVerde.getText().toString()) || !TextUtils.isEmpty(radAzul.getText().toString()))){
                    mensagemCor();
                }else{
                    if(!TextUtils.isEmpty(txtTexto.getText().toString()) || (!TextUtils.isEmpty(radVermelho.getText().toString()) || !TextUtils.isEmpty(radVerde.getText().toString()) || !TextUtils.isEmpty(radAzul.getText().toString()))){
                        if(radVermelho.isChecked()){
                            cor = 0;
                        }else if(radVerde.isChecked()){
                            cor = 1;
                        }else if(radAzul.isChecked()){
                            cor = 2;
                        }

                        bd = openOrCreateDatabase( "textos", MODE_PRIVATE, null );
                        String sql = "INSERT INTO textos (texto, cor) values (?,?)";
                        SQLiteStatement stmt = bd.compileStatement(sql);

                        stmt.bindString(1, txtTexto.getText().toString());
                        stmt.bindString(2, String.valueOf(cor));
                        stmt.executeInsert();
                        bd.close();
                        irTelarInicio(view);
                    }
                }

            }
        }
    }

    public void mensagemTexto(){
        Toast.makeText(this, "Digite algum texto", Toast.LENGTH_SHORT).show();
    }


    public void mensagemCor(){
        Toast.makeText(this, "Selecione alguma cor", Toast.LENGTH_SHORT).show();
    }

    public void irTelarInicio(View view){
        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent1);
    }




}