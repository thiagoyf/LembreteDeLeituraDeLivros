package com.thiagoyf.lembretedeleituradelivros;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import dao.LivroDao;
import model.Livro;
import util.ImagemUtil;

public class CadastrarLivroActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST = 1;

    private EditText edtLivroNome;
    private EditText edtLivroTotalPaginas;
    private ImageView imgLivro;

    private LivroDao livroDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastar_livro);

        edtLivroNome = (EditText) findViewById(R.id.cadastrarLivro_edtNome);
        edtLivroTotalPaginas = (EditText) findViewById(R.id.cadastrarLivro_edtTotalDePaginas);
        imgLivro = (ImageView) findViewById(R.id.cadastrarLivro_imgLivro);
        imgLivro.setBackgroundResource(R.drawable.no_image_available);

        livroDao = new LivroDao(this);
    }

    public void cadastrarLivro(View view) {
        String nome = edtLivroNome.getText().toString();
        String totalPaginas = edtLivroTotalPaginas.getText().toString();
        byte[] image = imageViewToByte(imgLivro);

        boolean validacao = true;
        if (nome.equals("")) {
            validacao = false;
            edtLivroNome.setError(getString(R.string.campoObrigatório));
        }

        if (totalPaginas.equals("")) {
            validacao = false;
            edtLivroTotalPaginas.setError(getString(R.string.campoObrigatório));
        }

        if (validacao) {
            Livro livro = new Livro();
            livro.setNome(nome);
            livro.setTotalPaginas(Integer.parseInt(totalPaginas));
            livro.setFoto(image);

            if (livroDao.buscarLivroPorNome(nome) == null) {
                long resultado = livroDao.salvarLivro(livro);

                if (resultado != -1) {
                    Toast.makeText(this, getString(R.string.livroCadastradoSucesso),
                            Toast.LENGTH_LONG).show();

                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                } else {
                    Toast.makeText(this, getString(R.string.erroCadastro),
                            Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, getString(R.string.livroJaCadastrado),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public void pegaImagem(View view) {
        Intent intentPegaImagem = new Intent(Intent.ACTION_PICK);

        File diretorioImagem = Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_PICTURES);
        String caminhoDiretorioImagem = diretorioImagem.getPath();

        Uri uri = Uri.parse(caminhoDiretorioImagem);
        intentPegaImagem.setDataAndType(uri, "image/*");


        startActivityForResult(intentPegaImagem, GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == GALLERY_REQUEST && data != null) {
            Uri imageUri = data.getData();

            InputStream inputStream;

            try {
                inputStream = getContentResolver().openInputStream(imageUri);

                Bitmap imagem = BitmapFactory.decodeStream(inputStream);
                imgLivro.setImageBitmap(imagem);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("ImagePicker", getString(R.string.erroImagem));
                Toast.makeText(this, getString(R.string.erroImagem),
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private static byte[] imageViewToByte(ImageView imageView) {
        if (imageView.getDrawable() == null) {
            return null;
        }

        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        return ImagemUtil.bitmapToByte(bitmap);
    }

    @Override
    protected void onDestroy() {
        livroDao.close();
        super.onDestroy();
    }
}
