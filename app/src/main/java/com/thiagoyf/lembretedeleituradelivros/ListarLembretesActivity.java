package com.thiagoyf.lembretedeleituradelivros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import adapter.LembreteAdapter;
import dao.LembreteDao;
import dao.LivroDao;
import model.Lembrete;
import model.Livro;

public class ListarLembretesActivity extends AppCompatActivity {

    private ListView listView;
    private List<Lembrete> lembretes;
    private LembreteAdapter lembreteAdapter;

    private LembreteDao lembreteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lembrete);

        listView = (ListView) findViewById(R.id.listarLembrete_listaDeLembretes);

        lembreteDao = new LembreteDao(this);
        lembretes = lembreteDao.listarLembrete();

        lembreteAdapter = new LembreteAdapter(this, lembretes);
        listView.setAdapter(lembreteAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Lembrete lembrete = (Lembrete) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(ListarLembretesActivity.this, LembreteActivity.class);
                intent.putExtra("LEMBRETE_ID", lembrete.getId());
                intent.putExtra("LEMBRETE_DATAHORA", lembrete.getDatahora());
                intent.putExtra("LEMBRETE_REPETE", lembrete.getRepete());
                intent.putExtra("LEMBRETE_ESTADO", lembrete.getEstado());
                intent.putExtra("LIVRO_ID", lembrete.getLivro().getId());
                intent.putExtra("LIVRO_NOME", lembrete.getLivro().getNome());
                intent.putExtra("LIVRO_TOTALPAGINAS", lembrete.getLivro().getTotalPaginas());
                intent.putExtra("LIVRO_FOTO", lembrete.getLivro().getFoto());

                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        lembreteDao.close();
        super.onDestroy();
    }
}
