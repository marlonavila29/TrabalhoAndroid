package uniderp.controller;

/**
 * Created by edilene on 26/04/16.
 */

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;

    public class AcessoBanco {

        private final Context context;

        private Conexao conector;
        private SQLiteDatabase db;

        public AcessoBanco(Context ctx)
        {
            this.context = ctx;
            conector = new Conexao(context);
        }

        public AcessoBanco open() throws SQLException
        {
            db = conector.getWritableDatabase();
            return this;
        }

        public void close()
        {
            conector.close();
        }
        /* int horaInicio, int horaFim,
                String localRealizacao, String descricao, String participantes, int tipoEventos, String repeticao*/
        public long insereCompromisso(String dataCompromisso)
        {
            ContentValues initialValues = new ContentValues();
            initialValues.put(Conexao.DATA_COMPROMISSO, dataCompromisso);
            return db.insert(Conexao.TABELA, null, initialValues);

           // db.execSQL("INSERT INTO "+ Conexao.TABELA + " (" + Conexao.DATA_COMPROMISSO + ")" + " VALUES " + "(" + dataCompromisso +");"  );
        }
/*
        public boolean remmovePessoa(long cod)
        {
            return db.delete(NOME_TABELA, CODIGO + "=" + cod, null) > 0;
        }

        public Cursor getCadastros()
        {
            return db.query(NOME_TABELA, new String[] {CODIGO, NOME}, null, null, null, null, null);
        }
*/
        public Cursor getCompromissos() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA, new String[] {Conexao.DATA_COMPROMISSO}, null,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }
        public boolean updatePessoa(long cod, String name, String email)
        {
            ContentValues args = new ContentValues();
          //  args.put(NOME, name);
            return db.update(Conexao.NOME_BD, args, Conexao.ID + "=" + cod, null) > 0;
        }
    }
