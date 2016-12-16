package com.thiagoyf.lembretedeleituradelivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import adapter.LivroAdapter;
import dao.LivroDao;
import model.Livro;

public class ListarLivrosActivity extends AppCompatActivity {

    private ListView listView;
    private List<Livro> livros;
    private LivroAdapter livroAdapter;
    private LivroDao livroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_livros);

        livroDao = new LivroDao(this);
        livros = livroDao.listarLivro();

        livroAdapter = new LivroAdapter(this, livros);

        listView = (ListView) findViewById(R.id.listarLivros_listaDeLivros);
        listView.setAdapter(livroAdapter);
    }

    @Override
    protected void onDestroy() {
        livroDao.close();
        super.onDestroy();
    }
}
