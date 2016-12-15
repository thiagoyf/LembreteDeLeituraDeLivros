package com.thiagoyf.lembretedeleituradelivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private List<Livro> livros;
    private List<Lembrete> lembretes;
    private LembreteAdapter lembreteAdapter;

    private LivroDao livroDao;
    private LembreteDao lembreteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lembrete);

        listView = (ListView) findViewById(R.id.listarLembrete_listaDeLembretes);

        livroDao = new LivroDao(this);
        lembreteDao = new LembreteDao(this);
        lembretes = lembreteDao.listarLembrete();
        listaLivros();

        lembreteAdapter = new LembreteAdapter(this, livros, lembretes);
        listView.setAdapter(lembreteAdapter);
    }

    private void listaLivros(){
        livros = new ArrayList<Livro>();
        List<Livro> dbLivros = livroDao.listarLivro();

        for (Lembrete lembrete : lembretes){
            for (Livro livro : dbLivros){
                if (livro.getId() == lembrete.getLivroId()) {
                    livros.add(livro);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        lembreteDao.close();
        super.onDestroy();
    }
}
