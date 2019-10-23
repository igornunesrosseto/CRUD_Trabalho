package com.ems.bdsqlitefull.crud;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ems.bdsqlitefull.R;
import com.ems.bdsqlitefull.pojo.Aluno;
import com.ems.bdsqlitefull.utils.Message;

public class Details extends AppCompatActivity {
    Button btExcluir;
    Button btEditar;
    TextView id, ra, nome, curso, campus;
    SQLiteDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Mostra um botão na Barra Superior para voltar
        getSupportActionBar().setTitle("CRUD DB SQLite - Detalhes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        id = findViewById(R.id.id);
        ra = findViewById(R.id.ra);
        nome = findViewById(R.id.nome);
        curso = findViewById(R.id.curso);
        campus = findViewById(R.id.campus);
        btEditar = findViewById(R.id.btSalvar);
        btExcluir = findViewById(R.id.btExcluir);

        Intent itAluno = getIntent();
        final Aluno aluno = (Aluno) itAluno.getExtras().getSerializable("objAluno");
        id.setText(String.valueOf(aluno.getId()));
        ra.setText(aluno.getRa());
        nome.setText(aluno.getNome());
        curso.setText(aluno.getCurso());
        campus.setText(aluno.getCampus());

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editar = new Intent(getApplicationContext(), EditRecord.class);
                editar.putExtra("objAluno", aluno);
                startActivity(editar);
            }
        });

        btExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = openOrCreateDatabase("db_aluno", Context.MODE_PRIVATE, null);
                db.execSQL("DELETE FROM aluno WHERE " +

                        "id=" + aluno.getId()
                );
                // Cria uma caixa de mensagem e mostra os dados excluídos
                Message message = new Message(Details.this);
                message.show("Dados apagados com Sucesso!", aluno.getDados(), R.drawable.ic_add);


                // chamando o activity main com delay de 1s.

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