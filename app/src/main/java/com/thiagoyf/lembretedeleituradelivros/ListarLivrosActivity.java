package com.thiagoyf.lembretedeleituradelivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
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
        //livros = livroDao.listarLivro();

        Livro livro1 = new Livro(1, "teste1", 1);
        Livro livro2 = new Livro(2, "teste2", 2);
        Livro livro3 = new Livro(3, "teste3", 3);

        livros = new ArrayList<Livro>();
        livros.add(livro1);
        livros.add(livro2);
        livros.add(livro3);

        livroAdapter = new LivroAdapter(this, livros);

        listView = (ListView) findViewById(R.id.listarLivros_listaDeLivros);
        listView.setAdapter(livroAdapter);
    }
}
