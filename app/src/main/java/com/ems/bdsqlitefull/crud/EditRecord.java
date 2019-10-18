package com.ems.bdsqlitefull.crud;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.MainActivity;
import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Aluno;
import com.ems.bdsqlitefull.utils.Message;

public class EditRecord extends AppCompatActivity {

    TextView id;
    EditText ra, nome, curso, campus;
    Button btSalvar;

    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD DB SQLite - Edição");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        id = findViewById(R.id.id);
        ra = findViewById(R.id.ra);
        nome = findViewById(R.id.nome);
        curso = findViewById(R.id.curso);
        campus = findViewById(R.id.campus);
        btSalvar = findViewById(R.id.btSalvar);

        final Intent itAluno = getIntent();
        final Aluno aluno = (Aluno) itAluno.getExtras().getSerializable("objAluno");
        id.setText(String.valueOf(aluno.getId()));
        ra.setText(aluno.getRa());
        nome.setText(aluno.getNome());
        curso.setText(aluno.getCurso());
        campus.setText(aluno.getCampus());

        btSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Coleta os dados digitados nos campos
                ContentValues values = new ContentValues();
                values.put("ra", ra.getText().toString());
                values.put("nome", nome.getText().toString());
                values.put("curso", curso.getText().toString());
                values.put("campus", campus.getText().toString());

                Aluno novosDados = new Aluno();
                novosDados.setRa(ra.getText().toString());
                novosDados.setNome(nome.getText().toString());
                novosDados.setCurso(curso.getText().toString());
                novosDados.setCampus(campus.getText().toString());

                // Atualiza os dados na tabela
                db = openOrCreateDatabase("db_aluno", Context.MODE_PRIVATE, null);
                db.execSQL("UPDATE aluno SET " +
                        "ra='" + novosDados.getRa() + "'," +
                        "nome='" + novosDados.getNome() + "'," +
                        "curso='" + novosDados.getCurso() + "'," +
                        "campus='" + novosDados.getCampus() + "' " +
                        "WHERE id=" + aluno.getId()
                );

                // Cria uma caixa de mensagem e mostra os dados incluídos
                Message message = new Message(EditRecord.this);
                message.show(
                        "Dados Atualizados com Sucesso!",
                        novosDados.getDados(),
                        R.drawable.ic_add);

                // chamando método main com delay 3s.

                Handler waitTime = new Handler();
                waitTime.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(main);
                    }
                }, 1000);
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
}