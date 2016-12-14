package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Livro;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class LivroDao {
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public LivroDao(Context context) {
        dataBaseHelper = new DataBaseHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        }

        return sqLiteDatabase;
    }

    public List<Livro> listarLivro() {
        String[] projecao = {
                DataBaseHelper.LIVRO_ID,
                DataBaseHelper.LIVRO_NOME,
                DataBaseHelper.LIVRO_TOTAL_PAGINAS
        };

        Cursor cursor = getDatabase().query(DataBaseHelper.LIVRO_TABLE, projecao,
                null, null, null, null, null);

        List<Livro> livros = new ArrayList<Livro>();
        while (cursor.moveToNext()) {
            Livro livro = new Livro(
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LIVRO_ID)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.LIVRO_NOME)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LIVRO_TOTAL_PAGINAS))
            );
            livros.add(livro);
        }

        cursor.close();
        return livros;
    }

    public long salvarLivro(Livro livro) {
        ContentValues valores = new ContentValues();
        valores.put(DataBaseHelper.LIVRO_NOME, livro.getNome());
        valores.put(DataBaseHelper.LIVRO_TOTAL_PAGINAS, livro.getTotalPaginas());

        return getDatabase().insert(DataBaseHelper.LIVRO_TABLE, null, valores);
    }

    public Livro buscarLivroPorId(int id) {
        String[] projecao = {
                DataBaseHelper.LIVRO_ID,
                DataBaseHelper.LIVRO_NOME,
                DataBaseHelper.LIVRO_TOTAL_PAGINAS
        };

        Cursor cursor = getDatabase().query(DataBaseHelper.LIVRO_TABLE, projecao, "id = ?",
                new String[]{Integer.toString(id)}, null, null, null);

        Livro livro = null;
        if (cursor.moveToFirst()){
            livro = new Livro(
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LIVRO_ID)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.LIVRO_NOME)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LIVRO_TOTAL_PAGINAS))
            );
        }

        cursor.close();
        return livro;
    }

    public Livro buscarLivroPorNome(String nome) {
        String[] projecao = {
                DataBaseHelper.LIVRO_ID,
                DataBaseHelper.LIVRO_NOME,
                DataBaseHelper.LIVRO_TOTAL_PAGINAS
        };

        Cursor cursor = getDatabase().query(DataBaseHelper.LIVRO_TABLE, projecao,
                DataBaseHelper.LIVRO_NOME + " = ?", new String[]{ nome }, null, null, null);

        Livro livro = null;
        if (cursor.moveToFirst()){
            livro = new Livro(
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LIVRO_ID)),
                    cursor.getString(cursor.getColumnIndex(DataBaseHelper.LIVRO_NOME)),
                    cursor.getInt(cursor.getColumnIndex(DataBaseHelper.LIVRO_TOTAL_PAGINAS))
            );
        }

        cursor.close();
        return livro;
    }

    public void close() {
        dataBaseHelper.close();
        sqLiteDatabase = null;
    }
}
