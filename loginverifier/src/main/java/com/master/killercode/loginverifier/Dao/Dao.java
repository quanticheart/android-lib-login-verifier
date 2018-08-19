package com.master.killercode.loginverifier.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by nalmir on 24/11/2017.
 */

public class Dao extends Constantes_Banco {

    private Context context;
    protected SQLiteDatabase db;

    public Dao(Context context) {
        this.context = context;
    }

    protected void abrirBanco() {
        SQLiteHelper helper = new SQLiteHelper(
                context,
                BANCO,
                null,
                VERSAO
        );

        this.db = helper.getWritableDatabase();

    }

    protected void fecharBanco() {
        if (db != null) {
            db.close();
        }
    }
}
