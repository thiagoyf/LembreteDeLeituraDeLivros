package com.thiagoyf.lembretedeleituradelivros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import dao.LembreteDao;
import model.Lembrete;
import model.Livro;
import util.DataUtil;

public class LembreteActivity extends AppCompatActivity {

    private TextView txtData;
    private TextView txtHora;
    private TextView txtLivro;
    private TextView txtRepete;

    private Livro livro;
    private Lembrete lembrete;

    private LembreteDao lembreteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembrete);

        txtData = (TextView) findViewById(R.id.activity_lembrete_data);
        txtHora = (TextView) findViewById(R.id.activity_lembrete_hora);
        txtLivro = (TextView) findViewById(R.id.activity_lembrete_livro);
        txtRepete = (TextView) findViewById(R.id.activity_lembrete_repete);

        lembreteDao = new LembreteDao(this);

        setFields();
    }

    private void setFields() {
        livro = new Livro();
        lembrete = new Lembrete();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            buildLivro(extras);
            buildLembrete(extras);

            String data = getString(R.string.listarLembretes_txtData) + DataUtil.formatDataDbPt
                    (lembrete.getDatahora());
            String hora = getString(R.string.listarLembretes_txtHora) + DataUtil.formatHoraDbPt
                    (lembrete.getDatahora());
            String tituloLivro = getString(R.string.listarLembretes_txtLivro) + lembrete.getLivro
                    ().getNome();
            String repete = getString(R.string.listarLembretes_txtRepetir) + lembrete
                    .getRepeteDia();

            txtData.setText(data);
            txtHora.setText(hora);
            txtLivro.setText(tituloLivro);
            txtRepete.setText(repete);
        }
    }

    private void buildLivro(Bundle extras) {
        livro.setId(extras.getInt("LIVRO_ID"));
        livro.setNome(extras.getString("LIVRO_NOME"));
        livro.setTotalPaginas(extras.getInt("LIVRO_TOTALPAGINAS"));
        livro.setFoto(extras.getByteArray("LIVRO_FOTO"));
    }

    private void buildLembrete(Bundle extras) {
        lembrete.setId(extras.getInt("LEMBRETE_ID"));
        lembrete.setDatahora(extras.getString("LEMBRETE_DATAHORA"));
        lembrete.setEstado(extras.getInt("LEMBRETE_ESTADO"));
        lembrete.setLivro(livro);
        lembrete.setRepete(extras.getInt("LEMBRETE_REPETE"));
    }

    public void completar(View view) {
        lembrete.setEstado(0);
        long result = lembreteDao.atualizarLembrete(lembrete);

        finish();
        startActivity(new Intent(this, ListarLembretesActivity.class));
    }

    @Override
    protected void onDestroy() {
        lembreteDao.close();
        super.onDestroy();
    }
}
