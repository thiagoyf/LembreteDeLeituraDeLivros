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
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private String[] projecao = {
            DatabaseHelper.LIVRO_ID,
            DatabaseHelper.LIVRO_NOME,
            DatabaseHelper.LIVRO_TOTAL_PAGINAS,
            DatabaseHelper.LIVRO_FOTO
    };

    public LivroDao(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public SQLiteDatabase getDatabase() {
        if (sqLiteDatabase == null) {
            sqLiteDatabase = databaseHelper.getWritableDatabase();
        }

        return sqLiteDatabase;
    }

    public List<Livro> listarLivro() {
        Cursor cursor = getDatabase().query(DatabaseHelper.LIVRO_TABLE, projecao,
                null, null, null, null, null);

        List<Livro> livros = new ArrayList<Livro>();
        while (cursor.moveToNext()) {
            Livro livro = getLivro(cursor);
            livros.add(livro);
        }

        cursor.close();
        return livros;
    }

    public long salvarLivro(Livro livro) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.LIVRO_NOME, livro.getNome());
        valores.put(DatabaseHelper.LIVRO_TOTAL_PAGINAS, livro.getTotalPaginas());
        valores.put(DatabaseHelper.LIVRO_FOTO, livro.getFoto());

        return getDatabase().insert(DatabaseHelper.LIVRO_TABLE, null, valores);
    }

    public long atualizarLivro(Livro livro) {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.LIVRO_ID, livro.getId());
        valores.put(DatabaseHelper.LIVRO_NOME, livro.getNome());
        valores.put(DatabaseHelper.LIVRO_TOTAL_PAGINAS, livro.getTotalPaginas());
        valores.put(DatabaseHelper.LIVRO_FOTO, livro.getFoto());

        return getDatabase().update(DatabaseHelper.LIVRO_TABLE, valores, DatabaseHelper
                .LEMBRETE_ID + " = ?", new String[]{String.valueOf(livro.getId())});
    }

    public Livro buscarLivroPorId(int id) {
        Cursor cursor = getDatabase().query(DatabaseHelper.LIVRO_TABLE, projecao, "id = ?",
                new String[]{Integer.toString(id)}, null, null, null);

        Livro livro = null;
        if (cursor.moveToFirst()) {
            livro = getLivro(cursor);
        }

        cursor.close();
        return livro;
    }

    public Livro buscarLivroPorNome(String nome) {
        Cursor cursor = getDatabase().query(DatabaseHelper.LIVRO_TABLE, projecao,
                DatabaseHelper.LIVRO_NOME + " = ?", new String[]{nome}, null, null, null);

        Livro livro = null;
        if (cursor.moveToFirst()) {
            livro = getLivro(cursor);
        }

        cursor.close();
        return livro;
    }

    private Livro getLivro(Cursor cursor) {
        return new Livro(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LIVRO_ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.LIVRO_NOME)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.LIVRO_TOTAL_PAGINAS)),
                cursor.getBlob(cursor.getColumnIndex(DatabaseHelper.LIVRO_FOTO))
        );
    }

    public void close() {
        databaseHelper.close();
        sqLiteDatabase = null;
    }
}
