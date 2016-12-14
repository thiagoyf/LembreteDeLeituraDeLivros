package com.thiagoyf.lembretedeleituradelivros;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.SpinnerLivroAdapter;
import dao.LembreteDao;
import dao.LivroDao;
import model.Lembrete;
import model.Livro;
import util.FormataData;

public class CadastrarLembreteActivity extends AppCompatActivity {

    private EditText edtData;
    private EditText edtHora;
    private Spinner spinner;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private List<Livro> livros;
    private List<String> nomeLivros;
    private SpinnerLivroAdapter spinnerLivroAdapter;

    private LivroDao livroDao;
    private LembreteDao lembreteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_lembrete);

        edtData = (EditText) findViewById(R.id.cadastrarLembrete_data);
        edtHora = (EditText) findViewById(R.id.cadastrarLembrete_tempo);

        spinner = (Spinner) findViewById(R.id.cadastrarLembrete_livro);

        livroDao = new LivroDao(this);
        lembreteDao = new LembreteDao(this);

        setSpinner();
    }

    public void selecionaData(View view) {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edtData.setText(String.format("%02d", dayOfMonth) + "-" + String.format
                                ("%02d", (monthOfYear + 1)) + "-" + String.format("%04d", year));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        edtData.setError(null);
    }

    public void selecionaHora(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        edtHora.setText(String.format("%02d", hourOfDay) + ":" + String.format
                                ("%02d", minute));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
        edtHora.setError(null);
    }

    public void cadastrarLembrete(View view) {
        String data = edtData.getText().toString();
        String hora = edtHora.getText().toString();
        String livroSelecionado = null;
        int idLivroSelecionado = -1;

        Boolean validacao = true;
        if (data == null || data.equals("")) {
            validacao = false;
            edtData.setError(getString(R.string.campoObrigatório));
        }

        if (hora == null || hora.equals("")) {
            validacao = false;
            edtHora.setError(getString(R.string.campoObrigatório));
        }

        if (spinner != null && spinner.getSelectedItem() != null) {
            livroSelecionado = spinner.getSelectedItem().toString();
            idLivroSelecionado = getIdLivro(livroSelecionado);
        } else {
            validacao = false;
            Toast.makeText(this, getString(R.string.semLivro), Toast.LENGTH_LONG).show();
        }

        if (validacao) {
            Lembrete lembrete = new Lembrete();
            lembrete.setDatahora(FormataData.formatPtDb(data, hora));
            lembrete.setLivroId(idLivroSelecionado);

            long resultado = lembreteDao.salvarLembrete(lembrete);

            if (resultado != -1) {
                Toast.makeText(this, getString(R.string.lembreteCadastradoSucesso),
                        Toast.LENGTH_LONG).show();

                finish();
                startActivity(new Intent(this, MainActivity.class));
            } else {
                Toast.makeText(this, getString(R.string.erroCadastro),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setSpinner() {
        livros = livroDao.listarLivro();
        livroDao.close();

        nomeLivros = setNomeLivros();

        spinnerLivroAdapter = new SpinnerLivroAdapter(this, nomeLivros);
        spinner.setAdapter(spinnerLivroAdapter);
    }

    private List<String> setNomeLivros() {
        List<String> titulos = new ArrayList<String>();
        for (Livro livro : livros) {
            titulos.add(livro.getNome());
        }
        return titulos;
    }

    private int getIdLivro(String nome) {
        for (Livro livro : livros) {
            if (livro.getNome().equals(nome)) {
                return livro.getId();
            }
        }
        return -1;
    }

    @Override
    protected void onDestroy() {
        lembreteDao.close();
        super.onDestroy();
    }
}
