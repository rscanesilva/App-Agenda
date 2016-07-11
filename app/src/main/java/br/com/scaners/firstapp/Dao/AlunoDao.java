package br.com.scaners.firstapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.scaners.firstapp.modelo.Aluno;

/**
 * Created by Scaners on 09/07/2016.
 */
public class AlunoDao extends SQLiteOpenHelper{

    public AlunoDao(Context context) {
        super(context, "Agenda" , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE ALUNOS (id INTEGER PRIMARY KEY AUTO INCREMENT, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS ALUNOS";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }

    public void insert(Aluno aluno) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = getContentValues(aluno);
        writableDatabase.insert("Alunos", null,contentValues);
    }

    public List<Aluno> buscaAlunos() {
        SQLiteDatabase conn =  getReadableDatabase();
        Cursor cursor = conn.rawQuery("SELECT * FROM ALUNOS", null);
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            alunos.add(aluno);
        }
        cursor.close();
        return alunos;
    }

    public void delete(Aluno aluno) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        String[] parameters = {aluno.getId().toString()};
        writableDatabase.delete("ALUNOS", "id = ?", parameters);
    }

    public void update(Aluno aluno) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = getContentValues(aluno);
        String[] parameters = {aluno.getId().toString()};
        writableDatabase.update("ALUNOS", contentValues, "id = ?", parameters);
    }

    @NonNull
    private ContentValues getContentValues(Aluno aluno) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", aluno.getNome());
        contentValues.put("endereco", aluno.getEndereco());
        contentValues.put("telefone",  aluno.getTelefone());
        contentValues.put("site", aluno.getSite());
        contentValues.put("nota", aluno.getNota());
        return contentValues;
    }
}
