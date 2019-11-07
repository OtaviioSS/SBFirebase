package Bancos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import modelo.IdeiaModelo;

public class BacoSqlite extends SQLiteOpenHelper {
    private final Context context;

    public BacoSqlite(Context context) {
        super(context, "SB.db",null,1);
        this.context=context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists ideias("+
                "id Interger primary key autoincrement"+
                ",nome varchar(80)"+
                ",conteudo varchar"+
                ",imguser int"+
                ",imgpub int"+
                ")";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    ///criando tabela

    public  boolean salvarIdeia(IdeiaModelo modelo){
        Boolean retorno = false;
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("nome",modelo.getNomeuser());
            contentValues.put("conteudo",modelo.getConteudo());
            contentValues.put("imguser",modelo.getImguser());
            contentValues.put("imgpub",modelo.getImgpub());

            long i = getWritableDatabase().insert("ideias",null,contentValues);

            //getWritableDatabase() ESCREVER DADOS NO BANCO
            //getReadableDatabase() LER DADOS DO BANCO

        }catch (Exception e){
            e.printStackTrace(); //retorna a exeção sem para o app

        }

        return retorno;

    }

    public Boolean excluir(int id){
        Boolean retorno = false;
        try{
            String where = "id=" + id;
            int i = getWritableDatabase().delete("pessoas",where,null);
            retorno = (i>0 ? true:false);


        }catch (Exception e){
            e.printStackTrace();


        }

        return  retorno;

    }

    public List<IdeiaModelo> getAll(){
        List<IdeiaModelo> retorno = new ArrayList<>();
        try{
            //Cursor c1 =  getReadableDatabase().rawQuery("select * from pessoas",null);
            String nomeTabela = "ideias";
            String[] campos = new  String[]{"id","conteudo"};
            String where1 = null;
            String[] where2 = null;
            String groupby = null;
            String orderby = null;
            String having = null;

            Cursor c2 = getReadableDatabase().query(
                    nomeTabela
                    ,campos,where1
                    ,where2,groupby
                    ,orderby,having
            );

            while (c2.moveToNext()){
                int Colunaconteudo = c2.getColumnIndex("conteudo");
                String conteudo = c2.getColumnName(Colunaconteudo);
                int Colunanome = c2.getColumnIndex("conteudo");
                String nome = c2.getColumnName(Colunanome);
                int Colunaimguser = c2.getColumnIndex("imguser");



                int id = c2.getInt(c2.getColumnIndex("id"));
                IdeiaModelo i = new IdeiaModelo(nome,conteudo,0,0);
                retorno.add(i);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
        return retorno;
    }

}
