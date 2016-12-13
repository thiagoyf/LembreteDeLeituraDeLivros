package com.thiagoyf.lembretedeleituradelivros;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CadastrarLembreteActivity extends AppCompatActivity {

    private EditText edtData;
    private EditText edtTempo;
    private Spinner spinner;

    private int mYear, mMonth, mDay, mHour, mMinute;
    private List<String> livros;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_lembrete);

        edtData = (EditText) findViewById(R.id.cadastrarLembrete_data);
        edtTempo = (EditText) findViewById(R.id.cadastrarLembrete_tempo);

        spinner = (Spinner) findViewById(R.id.cadastrarLembrete_livro);
        livros = new ArrayList<String>();
        String livro1 = "teste1";
        String livro2 = "teste2";
        String livro3 = "teste3";

        livros.add(livro1);
        livros.add(livro2);
        livros.add(livro3);

        arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, livros);

        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        spinner.setAdapter(arrayAdapter);
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

                        edtData.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void selecionaHora(View view) {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        edtTempo.setText(hourOfDay + ":" + minute);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
