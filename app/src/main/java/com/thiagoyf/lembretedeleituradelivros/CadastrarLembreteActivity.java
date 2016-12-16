package com.thiagoyf.lembretedeleituradelivros;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import adapter.SpinnerLivroAdapter;
import dao.LembreteDao;
import dao.LivroDao;
import model.Lembrete;
import model.Livro;
import util.DataUtil;

public class CadastrarLembreteActivity extends AppCompatActivity {

    private EditText edtData, edtHora;
    private Spinner spinnerLivros, spinnerFrquencia;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private List<Livro> livros;

    // por imagem spinner?
    // private SpinnerLivroAdapter spinnerLivroAdapter;

    private LivroDao livroDao;
    private LembreteDao lembreteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_lembrete);

        edtData = (EditText) findViewById(R.id.cadastrarLembrete_data);
        edtHora = (EditText) findViewById(R.id.cadastrarLembrete_tempo);

        spinnerLivros = (Spinner) findViewById(R.id.cadastrarLembrete_livro);
        spinnerFrquencia = (Spinner) findViewById(R.id.cadastrarLembrete_repeticao);

        livroDao = new LivroDao(this);
        lembreteDao = new LembreteDao(this);

        setSpinners();
    }

    public void selecionaData(View view) {
        final Calendar c = Calendar.getInstance();

        final DecimalFormat fourDigits = new DecimalFormat("0000");
        final DecimalFormat twoDigits = new DecimalFormat("00");

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edtData.setText(twoDigits.format(dayOfMonth) + "-" + twoDigits.format
                                (monthOfYear + 1) + "-" + fourDigits.format(year));

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        edtData.setError(null);
    }

    public void selecionaHora(View view) {
        final Calendar c = Calendar.getInstance();

        final DecimalFormat twoDigits = new DecimalFormat("00");

        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        edtHora.setText(twoDigits.format(hourOfDay) + ":" + twoDigits.format
                                (minute));
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
        edtHora.setError(null);
    }

    public void cadastrarLembrete(View view) {
        String data = null;

        if (edtData.isEnabled()) {
            data = edtData.getText().toString();
        }

        String hora = edtHora.getText().toString();

        boolean validacao = validacao();

        if (validacao) {
            Lembrete lembrete = new Lembrete();
            lembrete.setDatahora(DataUtil.formatPtDb(data, hora));
            lembrete.setEstado(1);
            lembrete.setLivro((Livro) spinnerLivros.getSelectedItem());

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

    private boolean validacao() {
        boolean validacao = true;

        if (edtData.isEnabled() && edtData.getText().toString().equals("")) {
            validacao = false;
            edtData.setError(getString(R.string.campoObrigatório));
        }

        if (edtHora.getText().toString().equals("")) {
            validacao = false;
            edtHora.setError(getString(R.string.campoObrigatório));
        }

        if (validacao) {
            if (!DataUtil.dataFutura(edtData.getText().toString(), edtHora.getText().toString())) {
                validacao = false;
                Toast.makeText(this, getString(R.string.selecioneDataFutura), Toast.LENGTH_LONG)
                        .show();
            }
        }

        if (spinnerLivros == null || spinnerLivros.getSelectedItem() == null) {
            validacao = false;
            Toast.makeText(this, getString(R.string.semLivro), Toast.LENGTH_LONG).show();
        }

        return validacao;
    }

    private void setSpinners(){
        setSpinnerLivros();
        setSpinnerFrequencia();
    }

    private void setSpinnerLivros() {
        livros = livroDao.listarLivro();
        livroDao.close();

        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, livros);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLivros.setAdapter(spinnerArrayAdapter);
    }

    private void setSpinnerFrequencia() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cadastrarLembrete_frequencia, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrquencia.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        lembreteDao.close();
        super.onDestroy();
    }
}
