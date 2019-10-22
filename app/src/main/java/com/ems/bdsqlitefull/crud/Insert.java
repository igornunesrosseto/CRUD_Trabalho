package com.ems.bdsqlitefull.crud;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


import com.ems.bdsqlitefull.pojo.Aluno;
import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.utils.Message;

public class Insert extends AppCompatActivity {
    EditText ra, nome, curso, campus;
    Button btInserir;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        // Abertura ou criação do Banco de Dados
        db = openOrCreateDatabase("db_aluno", Context.MODE_PRIVATE, null);

        // Cria a tabela se não existir, senão carrega a tabela para uso
        db.execSQL("CREATE TABLE IF NOT EXISTS aluno(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ra VARCHAR NOT NULL, " +
                "nome VARCHAR NOT NULL, " +
                "curso VARCHAR NOT NULL, " +
                "campus VARCHAR NOT NULL);");

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD DB SQLite - Inserir");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        ra = findViewById(R.id.editRa);
        nome = findViewById(R.id.editNome);
        curso = findViewById(R.id.editCurso);
        campus = findViewById(R.id.editCampus);
        btInserir = findViewById(R.id.btInserir);

        ra.requestFocus();

        btInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cria um objeto Aluno para receber os dados
                Aluno aluno = new Aluno();
                aluno.setRa(ra.getText().toString());
                aluno.setNome(nome.getText().toString());
                aluno.setCurso(curso.getText().toString());
                aluno.setCampus(campus.getText().toString());

                // Coleta os dados digitados nos campos
                ContentValues values = new ContentValues();
                values.put("ra", aluno.getRa());
                values.put("nome", aluno.getNome());
                values.put("curso", aluno.getCurso());
                values.put("campus", aluno.getCampus());

                // Insere os dados na tabela
                db.insert("aluno", null, values);

                // Cria uma caixa de mensagem e mostra os dados incluídos
                Message message = new Message(Insert.this);
                message.show(
                        "Dados incluídos com sucesso!",
                        aluno.getDados(),
                        R.drawable.ic_add);

                Handler waitTime = new Handler(); // método para aguardar tempo
                waitTime.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent main = new Intent(getApplicationContext(), ListAll.class);
                        startActivity(main);
                    }
                }, 1000);

                // Limpa os campos de entrada
                // clearText();
            }
        });
    }

    // Configura o botão (seta) na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Limpa os campos de entrada e fecha o teclado
     */
    public void clearText() {
        ra.setText("");
        nome.setText("");
        curso.setText("");
        campus.setText("");
        ra.requestFocus();

        // fecha o teclado virtual
        ((InputMethodManager) Insert.this.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                getCurrentFocus().getWindowToken(), 0);
    }
}
