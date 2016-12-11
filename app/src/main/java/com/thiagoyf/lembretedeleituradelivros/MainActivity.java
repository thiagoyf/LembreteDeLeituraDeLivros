package com.thiagoyf.lembretedeleituradelivros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()) {
            case R.id.main_btnCadastroDeLivros:
                intent.setClass(this, CadastrarLivroActivity.class);
                break;

            case R.id.main_btnListaDeLivros:
                intent.setClass(this, ListarLivrosActivity.class);
                break;

            case R.id.main_btnCadastroDeLembretes:
                break;

            case R.id.main_btnListaDeLembretes:
                break;

            default:
                break;
        }

        startActivity(intent);
    }
}
