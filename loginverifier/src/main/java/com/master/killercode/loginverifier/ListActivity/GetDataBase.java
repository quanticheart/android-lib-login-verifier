package com.master.killercode.loginverifier.ListActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;

import com.master.killercode.loginverifier.Dao.Dao;

import java.util.ArrayList;
import java.util.List;

import static com.master.killercode.loginverifier.LoginVerifier.md5Decode;


public class GetDataBase extends Dao {

    private Activity activity;

    public GetDataBase(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    public List<UserModel> getList() {
        List<UserModel> dados = new ArrayList<>();
        UserModel init = new UserModel();
        init.setTypeIntent(String.valueOf(1));
        dados.add(init);
        //
        abrirBanco();
        //
        Cursor cursor = null;
        //
        try {
            String comando = " select * from " + TABLE_USER + " where " + ACTIVATED + " = 1";

            cursor = db.rawQuery(comando.toLowerCase(), null);

            while (cursor.moveToNext()) {
                UserModel model = new UserModel();

                model.setId(cursor.getString(cursor.getColumnIndex(_ID)));
                model.setUser(cursor.getString(cursor.getColumnIndex(LOGIN)));
                model.setUserName(cursor.getString(cursor.getColumnIndex(USER_NAME)));
                model.setPass(md5Decode(cursor.getString(cursor.getColumnIndex(PASSWORD))));
                model.setDateLog(cursor.getString(cursor.getColumnIndex(DATE_LOGIN)));
                model.setDateLogout(cursor.getString(cursor.getColumnIndex(DATE_LOGOUT)));
                model.setTypeIntent(String.valueOf(0));

                dados.add(model);
            }

            cursor.close();
            cursor = null;


        } catch (Exception e) {
        }
        //
        fecharBanco();
        //
        return dados;
    }

//    public void removeUser(String id) {
//
//        abrirBanco();
//
//        Cursor cursor = null;
//
//        try {
//            String where = _ID + " =? ";
//            db.delete(TABLE_USER, where, new String[]{id});
//
//
//        } catch (Exception e) {
//
//        }
//
//        fecharBanco();
//
//    }

    public void removeUser(String id) {

        abrirBanco();

        Cursor cursor = null;

        try {
            String where = _ID + " =? ";
            ContentValues cv = new ContentValues();

            cv.put(PASSWORD, "");
            cv.put(ACTIVATED, "0");

            db.update(TABLE_USER, cv, where, new String[]{id});


        } catch (Exception e) {

        }

        fecharBanco();

    }

    public void removePass(String id) {

        abrirBanco();

        Cursor cursor = null;

        try {
            String where = _ID + " =? ";
            ContentValues cv = new ContentValues();

            cv.put(PASSWORD, "");

            db.update(TABLE_USER, cv, where, new String[]{id});


        } catch (Exception e) {

        }

        fecharBanco();

    }

//    public void deleteDataBaseUser() {
//
//        abrirBanco();
//        try {
//            StringBuilder sb = new StringBuilder();
//            //
//            sb.append("DROP TABLE IF EXISTS " + TABLE_USER + ";");
//
//            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");
//
//            String[] comandos = sb.toString().split(";");
//
//            for (int i = 0; i < comandos.length; i++) {
//                db.execSQL(comandos[i].toLowerCase());
//            }
//        } catch (Exception e) {
//        }
//        fecharBanco();
//    }

    public void deleteDataBaseUser() {

        abrirBanco();
        try {
            Cursor delete = db.rawQuery("select * from " + TABLE_USER, null);

            while (delete.moveToNext()) {
                ContentValues cvD = new ContentValues();
                cvD.put(ACTIVATED, "0");
                cvD.put(PASSWORD, "");
                db.update(TABLE_USER, cvD, null, null);
            }
        } catch (Exception e) {
        }
        fecharBanco();
    }


}
