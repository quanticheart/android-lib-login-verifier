package com.master.killercode.loginverifier;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.master.killercode.loginverifier.Dao.Dao;
import com.master.killercode.loginverifier.ListActivity.ListActivity;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class LoginVerifier extends Dao {

    private Activity activity;

    public LoginVerifier(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
    }

    private String getFirstID(String nameDataBase) {
        String rowId = "";
        Cursor cursor = db.query(nameDataBase, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            rowId = cursor.getString(cursor.getColumnIndex(_ID));
        }

        return rowId;
    }

    public void inLogin() {
        //
        abrirBanco();

        try {

            ContentValues cv = new ContentValues();
            //
            String filtro = _ID + " = ? ";
            String[] argumentos = {getFirstID(TABLE_LOGIN)};
            //
            cv.put(LOG, "1");
            if (argumentos[0].equals("")) {
                db.insert(TABLE_LOGIN, null, cv);
            } else {
                db.update(TABLE_LOGIN, cv, filtro, argumentos);
            }

        } catch (Exception e) {
        }
        fecharBanco();

        //
    }

    public void inLogin(String login) {
        //
        Map<String, String> data = new ArrayMap<>();
        data.put(LOGIN, login);
        insertData(data);
        //
    }

    public void inLogin(String login, String passWord) {
        //
        Map<String, String> data = new ArrayMap<>();
        data.put(LOGIN, login);
        data.put(PASSWORD, passWord);
        insertData(data);
        //
    }

    public void inLogin(String userName, String login, String passWord) {
        //
        Map<String, String> data = new ArrayMap<>();
        data.put(USER_NAME, userName);
        data.put(LOGIN, login);
        data.put(PASSWORD, passWord);
        insertData(data);
        //
    }

    private void insertData(Map<String, String> data) {

        String userName = "";
        if (data.containsKey(USER_NAME)) {
            userName = data.get(USER_NAME);
        }

        String login = "";
        if (data.containsKey(LOGIN)) {
            login = data.get(LOGIN);
        }

        String passWord = "";
        if (data.containsKey(PASSWORD)) {
            passWord = md5(userName, data.get(PASSWORD));
        }


        abrirBanco();

        try {

            ContentValues cv = new ContentValues();
            ContentValues cvUser = new ContentValues();

            //
            String filtro = _ID + " = ? ";
            String[] argumentos = {getFirstID(TABLE_LOGIN)};
            //
            cv.put(LOG, "1");
            cv.put(ID_USER, login);
            if (argumentos[0].equals("")) {
                db.insert(TABLE_LOGIN, null, cv);
            } else {
                db.update(TABLE_LOGIN, cv, filtro, argumentos);
            }

            String query = "select * from " + TABLE_USER + " where " + LOGIN + "= ?";

            Cursor name = db.rawQuery(query, new String[]{login});

            if (name.getCount() == 0) {

                cvUser.put(USER_NAME, userName);
                cvUser.put(LOGIN, login);
                cvUser.put(PASSWORD, passWord);
                cvUser.put(DATE_LOGIN, getTime());
                cvUser.put(DATE_LOGOUT, "");
                cvUser.put(ACTIVATED, "1");

                db.insert(TABLE_USER, null, cvUser);

                Log.w("ac", "0");
            } else {
                String ac = "";

                while (name.moveToNext()) {
                    ac = name.getString(name.getColumnIndex(ACTIVATED));
                }

                if (ac.equals("0")) {

//                    Log.w("ac", "0");

                    ContentValues cvU = new ContentValues();
                    cvU.put(PASSWORD, passWord);
                    cvU.put(ACTIVATED, "1");
                    db.update(TABLE_USER, cvU, LOGIN + "= ?", new String[]{login});

                } else {

                    ContentValues cvU = new ContentValues();
                    cvU.put(PASSWORD, passWord);
                    db.update(TABLE_USER, cvU, LOGIN + "= ?", new String[]{login});

                    Log.w("ac", "1");

                }

                updateTime(1, login);
            }


        } catch (Exception e) {
        }
        fecharBanco();
    }

    private void updateTime(int i, String login) {
        ContentValues cvUser = new ContentValues();
        String filtro2 = LOGIN + " = ? ";
        if (i == 1) {
            cvUser.put(DATE_LOGIN, getTime());
        } else {
            cvUser.put(DATE_LOGOUT, getTime());
        }

        db.update(TABLE_USER, cvUser, filtro2, new String[]{login});
    }

    private static String getTime() {
        java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void inLogout() {
        //
        closeLogin();
        //
    }

    public void inLogout(Class nextActivity) {
        //
        closeLogin();
        //
        Intent intent = new Intent(activity, ListActivity.class);
        intent.putExtra("class", nextActivity);
        activity.startActivity(intent);
        activity.finish();

    }

    private void closeLogin() {
        abrirBanco();

        try {

            ContentValues cv = new ContentValues();
            //
            String filtro = _ID + " = ? ";
            String[] argumentos = {getFirstID(TABLE_LOGIN)};
            //
            cv.put(LOG, "0");
            if (argumentos[0].equals("")) {
                db.insert(TABLE_LOGIN, null, cv);
            } else {
                db.update(TABLE_LOGIN, cv, filtro, argumentos);
            }

            String comando = "select * from " + TABLE_LOGIN + " where " + _ID + " = ? ";
            Cursor logout = db.rawQuery(comando, argumentos);

            while (logout.moveToNext()) {
                String user = logout.getString(logout.getColumnIndex(ID_USER));
//                Toast.makeText(activity, user, Toast.LENGTH_SHORT).show();
                updateTime(2, user);
            }

        } catch (Exception e) {
        }
        fecharBanco();

    }

    public boolean getLogin(Class mainActivity) {

        abrirBanco();
        //
        Cursor cursor = null;

        String id = getFirstID(TABLE_LOGIN);
        String dados = "";
        //
        try {
            String comando = " select * from " + TABLE_LOGIN + " where " + _ID + " = ? ";
            String[] argumentos = {id};

            cursor = db.rawQuery(comando.toLowerCase(), argumentos);

            while (cursor.moveToNext()) {
                dados = cursor.getString(cursor.getColumnIndex(LOG));
            }

            cursor.close();
            cursor = null;

        } catch (Exception e) {
        }
        //
        fecharBanco();
        //

        if (dados.equals("1")) {
            Toast.makeText(activity, "Logued", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, mainActivity);
            activity.startActivity(intent);
            activity.finish();
            return true;
        } else {
            return false;
        }

    }

    public boolean getLogin(Class mainActivity, Class loginActivity) {

        int number = 0;

        abrirBanco();
        //
        Cursor cursor = null;

        String id = getFirstID(TABLE_LOGIN);
        String dados = "";
        //
        try {
            String comando = " select * from " + TABLE_LOGIN + " where " + _ID + " = ? ";
            String[] argumentos = {id};

            cursor = db.rawQuery(comando.toLowerCase(), argumentos);

            while (cursor.moveToNext()) {
                dados = cursor.getString(cursor.getColumnIndex(LOG));
            }

            cursor.close();
            cursor = null;

            String comando2 = "select * from " + TABLE_USER + " where " + ACTIVATED + " = 1";

            cursor = db.rawQuery(comando2, null);
            number = cursor.getCount();
            cursor.close();
            cursor = null;

        } catch (Exception e) {
        }
        //
        fecharBanco();
        //

        if (dados.equals("1")) {
//            Toast.makeText(activity, "Logued", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, mainActivity);
            activity.startActivity(intent);
            activity.finish();
            return true;
        } else {

            if (number > 0) {
//            Toast.makeText(activity, String.valueOf(number), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, ListActivity.class);
                intent.putExtra("class", loginActivity);
                activity.startActivity(intent);
                activity.finish();
//            Intent intent2 = new Intent(activity, loginActivity);
//            activity.startActivity(intent2);
            } else {
                Intent intent = new Intent(activity, loginActivity);
                activity.startActivity(intent);
                activity.finish();
            }
            return false;
        }

    }

    private String key = activity.getResources().getString(R.string.app_name);
    private static String split = "::";

    private String md5(String user, String pass) {
        String base64 = "";

        String md5 = pass + split + key + split + user;

        try {
            byte[] data1 = md5.getBytes("UTF-8");
            base64 = Base64.encodeToString(data1, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        Log.w("Encode", base64);
        return base64;
    }

    public static String md5Decode(String base64) {
        String rawDecoded = "";
        try {
            byte[] data2 = Base64.decode(base64, Base64.DEFAULT);
            rawDecoded = new String(data2, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
//        Log.w("Raw Decode", rawDecoded);

        String array[] = rawDecoded.split(split);

//        Log.w("Decode", array[0]);
        return array[0];
    }

    public boolean getStatusLogin() {

        abrirBanco();
        //
        Cursor cursor = null;

        String id = getFirstID(TABLE_LOGIN);
        String dados = "";
        //
        try {
            String comando = " select * from " + TABLE_LOGIN + " where " + _ID + " = ? ";
            String[] argumentos = {id};

            cursor = db.rawQuery(comando.toLowerCase(), argumentos);

            while (cursor.moveToNext()) {
                dados = cursor.getString(cursor.getColumnIndex(LOG));
            }

            cursor.close();
            cursor = null;

        } catch (Exception e) {
        }
        //
        fecharBanco();
        //

        if (dados.equals("1")) {
            return true;
        } else {
            return false;
        }

    }


}
