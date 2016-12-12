package dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    // Nome do DataBase
    private static final String DATABASE_NAME = "lembreteDeLeituraDeLivros.db";
    private static final int DATABASE_VERSION = 1;

    // Nome das tabelas
    public static final String LIVRO_TABLE = "livro";
    public static final String LEMBRETE_TABLE = "lembrete";

    // Tabela Livro
    public static final String LIVRO_ID = "id";
    public static final String LIVRO_NOME = "livro_nome";
    public static final String LIVRO_TOTAL_PAGINAS = "total_paginas";

    // Tabela Lembrete
    public static final String LEMBRETE_ID = "id";
    public static final String LEMBRETE_DATA = "data_hora";
    public static final String LEMBRETE_LIVRO = "livro_id";

    // CREATE TABLES
    public static final String CREATE_LIVRO_TABLE = "CREATE TABLE IF NOT EXISTS "
            + LIVRO_TABLE + "("
            + LIVRO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LIVRO_NOME + " TEXT, "
            + LIVRO_TOTAL_PAGINAS + " INTEGER" +
            ")";
    public static final String CREATE_LEMBRETE_TABLE = "CREATE TABLE IF NOT EXISTS"
            + LEMBRETE_TABLE + "("
            + LEMBRETE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + LEMBRETE_DATA + " TEXT, "
            + LEMBRETE_LIVRO + " INTEGER, " +
            "FOREIGN KEY("
            + LEMBRETE_LIVRO + ") REFERENCES "
            + LIVRO_TABLE + "("
            + LIVRO_ID + "))";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase sqLiteDatabase) {
        super.onConfigure(sqLiteDatabase);
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_LIVRO_TABLE);
        sqLiteDatabase.execSQL(CREATE_LEMBRETE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
