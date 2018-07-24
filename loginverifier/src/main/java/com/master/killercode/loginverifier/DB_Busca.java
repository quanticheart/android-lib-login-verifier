package com.master.killercode.loginverifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.master.killercode.loginverifier.Dao.Dao;

import java.util.ArrayList;


/**
 * Created by nalmir on 24/11/2017.
 */

public class DB_Busca extends Dao {

    public DB_Busca(Context context) {
        super(context);
    }

    public static final String TABELA_HISTORICO_BUSCA = "tabela_historico_busca";


    //chamar esta função para verificar se o DB existe , se não existir , ele cria
    public void CriaBaseDeDadosCompleta() {
        DeletaBanco();
        CriaBaseDeDados();
    }

    public void CriaBaseDeDados() {

        abrirBanco();
        try {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS " + TABELA_HISTORICO_BUSCA + " (\n" +
                    "  [_id] INTEGER, \n" +
                    "  [texto] INT NOT NULL, \n" +
                    "  CONSTRAINT [] PRIMARY KEY([_id]));");


            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception e) {
        }
        fecharBanco();
    }

    // deleta a base de dados existente para segurança
    public void DeletaBanco() {

        abrirBanco();
        try {
            StringBuilder sb = new StringBuilder();
            //
            sb.append("DROP TABLE IF EXISTS " + TABELA_HISTORICO_BUSCA + ";");

            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception e) {
        }
        fecharBanco();
    }

    public void DeletaBanco_Historico_Busca() {

        abrirBanco();
        try {
            StringBuilder sb = new StringBuilder();
            //
            sb.append("DROP TABLE IF EXISTS " + TABELA_HISTORICO_BUSCA + ";");

            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception e) {
        }
        fecharBanco();
    }

    public void Inserir_Historico_Busca(String texto) {
        abrirBanco();
        //

        db.beginTransaction();
        //
        try {

//            db.rawQuery("", null);
//            db.execSQL("");
            ContentValues cv = new ContentValues();

            cv.clear();
            cv.put("texto", texto);
            //
            db.insert(TABELA_HISTORICO_BUSCA, null, cv);

            db.setTransactionSuccessful();
        } catch (Exception e) {
        } finally {
            db.endTransaction();
        }
        //
        fecharBanco();
    }

//    public ArrayList<HMAux> Obter_Dados_Historico_Busca() {
//        ArrayList<HMAux> dados = new ArrayList<>();
//        //
//        abrirBanco();
//        //
//        Cursor cursor = null;
//        //
//        try {
//            String comando = " select * from " + TABELA_HISTORICO_BUSCA;
//
//            cursor = db.rawQuery(comando.toLowerCase(), null);
//
//            while (cursor.moveToNext()) {
//                HMAux hmAux = new HMAux();
//
//                hmAux.put(
//                        HMAux.ID,
//                        cursor.getString(cursor.getColumnIndex("_id")))
//                ;
//
//                hmAux.put(
//                        HMAux.BUSCA_TEXTO_PESQUISA,
//                        cursor.getString(cursor.getColumnIndex("texto")))
//                ;
//
//                dados.add(hmAux);
//            }
//
//            cursor.close();
//            cursor = null;
//
//
//        } catch (Exception e) {
//        }
//        //
//        fecharBanco();
//        //
//        return dados;
//    }

    public void deleteFirstRow() {
        abrirBanco();
        Cursor cursor = db.query("DB_", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            String rowId = cursor.getString(cursor.getColumnIndex("_id"));

            db.delete("DB_", "_id" + "=?", new String[]{rowId});
        }
        fecharBanco();
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void Funcao_DeletaSQLITE(String nome_db) {

        abrirBanco();
        try {
            StringBuilder sb = new StringBuilder();
            //
            sb.append("DROP TABLE IF EXISTS DB_" + nome_db + ";");

            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }
        } catch (Exception e) {
        }
        fecharBanco();
    }

    public void Funcao_CriaSQLITE(String nome_db, String texto) {

        abrirBanco();

        try {
            StringBuilder sb = new StringBuilder();

            sb.append("CREATE TABLE IF NOT EXISTS " + "DB_" + nome_db + " (\n" +
                    "  [_id] INTEGER, \n" +
                    "  [" + nome_db + "] INT NOT NULL, \n" +
                    "  CONSTRAINT [] PRIMARY KEY([_id]));");


            //   sb.append("CREATE TABLE teste ( 'Field1' INTEGER );");

            String[] comandos = sb.toString().split(";");

            for (int i = 0; i < comandos.length; i++) {
                db.execSQL(comandos[i].toLowerCase());
            }

            Funcao_InsereSQlite(nome_db, texto);

        } catch (Exception e) {
        }
        fecharBanco();


    }

//    private void Funcao_InsereSQlite(String texto) {
//        abrirBanco();
//        //
//
//        db.beginTransaction();
//        //
//        try {
//
//            ContentValues cv = new ContentValues();
//
//            cv.clear();
//            cv.put(texto, texto);
//            //
//            db.insert("DB_" + texto, null, cv);
//
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//        } finally {
//            db.endTransaction();
//        }
//        //
//        fecharBanco();
//    }

    public void Funcao_InsereSQlite(String none_db, String texto) {
        //

        ContentValues cv = new ContentValues();
        //
        String filtro = "_id = ? ";
        String[] argumentos = {Funcao_FirstID(none_db)};
        //
        cv.put(none_db, texto);
        if (argumentos[0].equals("")) {
            db.insert("DB_" + none_db, null, cv);
        } else {
            db.update("DB_" + none_db, cv, filtro, argumentos);
        }
        //
    }

    public String Funcao_getSQlite(String none_db) {

        abrirBanco();
        //
        Cursor cursor = null;

        String id = Funcao_FirstID(none_db);
        String dados = "";
        //
        try {
            String comando = " select * from DB_" + none_db + " where _id = ? ";
            String[] argumentos = {String.valueOf(id)};

            cursor = db.rawQuery(comando.toLowerCase(), argumentos);

            while (cursor.moveToNext()) {
                dados = cursor.getString(cursor.getColumnIndex(none_db));
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

    private String Funcao_FirstID(String nome_db) {
        String rowId = "";
        Cursor cursor = db.query("DB_" + nome_db, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            rowId = cursor.getString(cursor.getColumnIndex("_id"));
        }

        return rowId;
    }

}
