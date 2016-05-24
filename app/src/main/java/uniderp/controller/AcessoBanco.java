package uniderp.controller;

/**
 * Created by edilene on 26/04/16.
 */

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.widget.EditText;

    import uniderp.model.Compromisso;

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
        public long insereCompromisso(Compromisso compromisso)
        {
            ContentValues initialValuesCompromisso = new ContentValues();
            initialValuesCompromisso.put(Conexao.DATA_EVENTO, String.valueOf(compromisso.getDataEvento()));
            initialValuesCompromisso.put(Conexao.DESCRICAO, compromisso.getDescricao());
            initialValuesCompromisso.put(Conexao.HORA_INICIO, compromisso.getHoraInicio());
            initialValuesCompromisso.put(Conexao.HORA_FIM, compromisso.getHoraInicio());
            initialValuesCompromisso.put(Conexao.LOCAL_REALIZACAO, compromisso.getLocalRealizacao());
            initialValuesCompromisso.put(Conexao.PARTICIPANTES, compromisso.getParticipantes());

            return db.insert(Conexao.TABELA_COMPROMISSO, null, initialValuesCompromisso);

        }
        public long insereTipoEvento(EditText tipoEvento){
            ContentValues initialValuesTipoEvento = new ContentValues();
            initialValuesTipoEvento.put(Conexao.TIPO_EVENTO, tipoEvento.getText().toString());
            return db.insert(Conexao.TABELA_TIPO_EVENTO, null, initialValuesTipoEvento);
        }

        public long insereRepeticao(EditText tipoEvento){
            ContentValues initialValuesTipoEvento = new ContentValues();
            initialValuesTipoEvento.put(Conexao.TIPO_EVENTO, tipoEvento.getText().toString());
            return db.insert(Conexao.TABELA_TIPO_EVENTO, null, initialValuesTipoEvento);
        }

        public Cursor getCompromissos() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA_COMPROMISSO, new String[] {Conexao.DATA_EVENTO}, null,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }

        public Cursor getTipoEvento() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA_TIPO_EVENTO, new String[] {Conexao.ID,Conexao.TIPO_EVENTO}, null,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }
        public Cursor getRepeticao() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA_REPETICAO, new String[] {Conexao.ID,Conexao.TIPO_EVENTO}, null,null, null,null, null, null);
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
