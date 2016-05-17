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

        public static final String CODIGO = "codigo";
        private static final String NOME_BD = "trabalho";
        private static final String NOME_TABELA = "compromisso";

        public static String  DATA_COMPROMISSO;
        public static int  HORA_INICIO;
        public static int  HORA_FIM;
        public static String LOCAL_REALIZACAO;
        public static String DESCRICAO;
        public static String PARTICIPANTES;
        public static int TIPO_EVENTOS;
        public static String REPETICAO;
        public static String ID;


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

        public long inserePessoa(String dataCompromisso/* int horaInicio, int horaFim,
        String localRealizacao, String descricao, String participantes, int tipoEventos, String repeticao*/)
        {
            ContentValues initialValues = new ContentValues();
            initialValues.put(DATA_COMPROMISSO, dataCompromisso);
            /* initialValues.put(HORA_INICIO, dataCompromisso);
            initialValues.put(HORA_FIM, dataCompromisso);
            initialValues.put(LOCAL_REALIZACAO, dataCompromisso);
            */
            return db.insert(NOME_TABELA, null, initialValues);
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
        public Cursor getData(long cod) throws SQLException
        {
            Cursor mCursor =
                    db.query(true, NOME_TABELA, new String[] {CODIGO, DATA_COMPROMISSO}, CODIGO + "=" + cod, null,
                            null, null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;
        }
        public boolean updatePessoa(long cod, String name, String email)
        {
            ContentValues args = new ContentValues();
          //  args.put(NOME, name);
            return db.update(NOME_BD, args, CODIGO + "=" + cod, null) > 0;
        }
    }
