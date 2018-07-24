package com.master.killercode.loginverifier.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.master.killercode.loginverifier.Dao.Constantes_Banco.ACTIVATED;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.DATE_LOGIN;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.DATE_LOGOUT;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.ID_USER;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.LOG;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.PASSWORD;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.TABLE_LOGIN;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.TABLE_USER;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.LOGIN;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco.USER_NAME;
import static com.master.killercode.loginverifier.Dao.Constantes_Banco._ID;

/**
 * Created by nalmir on 23/11/2017.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE IF NOT EXISTS [" + TABLE_LOGIN + "] (\n" +
                    "  [" + _ID + "] INTEGER, \n" +
                    "  [" + LOG + "] TEXT, \n" +
                    "  [" + ID_USER + "] TEXT, \n" +
                    "  CONSTRAINT [] PRIMARY KEY ([" + _ID + "]));");

            sb.append("CREATE TABLE IF NOT EXISTS [" + TABLE_USER + "] (\n" +
                    "  [" + _ID + "] INTEGER, \n" +
                    "  [" + USER_NAME + "] TEXT, \n" +
                    "  [" + LOGIN + "] TEXT, \n" +
                    "  [" + PASSWORD + "] TEXT, \n" +
                    "  [" + DATE_LOGIN + "] TEXT, \n" +
                    "  [" + DATE_LOGOUT + "] TEXT, \n" +
                    "  [" + ACTIVATED + "] TEXT, \n" +
                    "  CONSTRAINT [] PRIMARY KEY ([" + _ID + "]));");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            StringBuilder sb = new StringBuilder();
            //
//            sb.append("DROP TABLE IF EXISTS  [contatos];");
//
//            //Segunda Tabela
//            sb.append("DROP TABLE IF EXISTS  [contatos_n];");

//            sb.append("ALTER TABLE ["+TABLE_USER+"] ADD COLUMN ["+ACTIVATED+"] TEXT");

//            sb.append("CREATE TABLE IF NOT EXISTS ["+TABLE_USER+"] (\n" +
//                    "  ["+_ID+"] INTEGER, \n" +
//                    "  ["+LOGIN+"] TEXT, \n" +
//                    "  ["+DATE+"] TEXT, \n" +
//                    "  ["+ACTIVATED+"] TEXT, \n" +
//                    "  CONSTRAINT [] PRIMARY KEY (["+_ID+"]));");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception e) {
        }

        onCreate(db);
    }
}
