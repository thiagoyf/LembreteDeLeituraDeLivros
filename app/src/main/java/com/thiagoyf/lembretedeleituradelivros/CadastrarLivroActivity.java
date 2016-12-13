package com.thiagoyf.lembretedeleituradelivros;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import dao.LivroDao;
import model.Livro;

public class CadastrarLivroActivity extends AppCompatActivity {

    private EditText edtLivroNome;
    private EditText edtLivroTotalPaginas;

    private LivroDao livroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastar_livro);

        edtLivroNome = (EditText) findViewById(R.id.cadastrarLivro_edtNome);
        edtLivroTotalPaginas = (EditText) findViewById(R.id.cadastrarLivro_edtTotalDePaginas);

        livroDao = new LivroDao(this);
    }

    public void cadastrarLivro(View view){
        String nome = edtLivroNome.getText().toString();
        String totalPaginas = edtLivroTotalPaginas.getText().toString();

        Boolean validacao = true;
        if (nome == null || nome.equals("")) {
            validacao = false;
            edtLivroNome.setError(getString(R.string.campoObrigatório));
        }

        if (totalPaginas == null || totalPaginas.equals("")) {
            validacao = false;
            edtLivroTotalPaginas.setError(getString(R.string.campoObrigatório));
        }

        if (validacao){
            Livro livro = new Livro();
            livro.setNome(nome);
            livro.setTotalPaginas(Integer.parseInt(totalPaginas));

            if (livroDao.buscarLivroPorNome(nome) == null) {
                long resultado = livroDao.salvarLivro(livro);

                if (resultado != -1){
                    Toast.makeText(this, getString(R.string.livroCadastradoSucesso),
                            Toast.LENGTH_LONG);

                } else {
                    Toast.makeText(this, getString(R.string.erroCadastro),
                            Toast.LENGTH_LONG);
                }
            } else {
                Toast.makeText(this, getString(R.string.livroJaCadastrado), Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    protected void onDestroy() {
        livroDao.close();
        super.onDestroy();
    }
}
