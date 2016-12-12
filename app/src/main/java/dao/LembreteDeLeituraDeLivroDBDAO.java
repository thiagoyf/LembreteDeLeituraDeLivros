package dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class LembreteDeLeituraDeLivroDBDAO {
    protected SQLiteDatabase sqLiteDatabase;
    private DataBaseHelper dataBaseHelper;
    private Context context;

    public LembreteDeLeituraDeLivroDBDAO(Context context) {
        this.context = context;
        dataBaseHelper = new DataBaseHelper(context);

    }

    public void open() throws SQLException {
        if (dataBaseHelper == null)
            dataBaseHelper = new DataBaseHelper(context);
        sqLiteDatabase = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();
        sqLiteDatabase = null;
    }

}
