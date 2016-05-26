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
            initialValuesCompromisso.put(Conexao.ID_TIPO_EVENTO, compromisso.getIdTipoEvento());

            return db.insert(Conexao.TABELA_COMPROMISSO, null, initialValuesCompromisso);

        }
        public long insereTipoEvento(String tipoEvento){
            ContentValues initialValuesTipoEvento = new ContentValues();
            initialValuesTipoEvento.put(Conexao.TIPO_EVENTO, tipoEvento);
            return db.insert(Conexao.TABELA_TIPO_EVENTO, null, initialValuesTipoEvento);
        }

        public Cursor getCompromissos() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA_COMPROMISSO, new String[] {Conexao.ID_COMPROMISSO, Conexao.DATA_EVENTO,Conexao.HORA_INICIO
                    ,Conexao.HORA_FIM,Conexao.LOCAL_REALIZACAO,Conexao.DESCRICAO,Conexao.PARTICIPANTES, Conexao.ID_TIPO_EVENTO}, null,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }

        public Cursor getTipoEvento() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA_TIPO_EVENTO, new String[] {Conexao.ID_EVENTO,Conexao.TIPO_EVENTO}, null,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }
        public Cursor getTipoEventoByName(String tipoEvento) throws SQLException
        {
            String whereClause = Conexao.TIPO_EVENTO+" LIKE '"+tipoEvento+"'";
            Cursor mCursor =  db.query(Conexao.TABELA_TIPO_EVENTO, new String[] {Conexao.ID_EVENTO,Conexao.TIPO_EVENTO}, whereClause,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }
        public Cursor getTipoEventoById(int idTipoEvento) throws SQLException
        {
            String whereClause = Conexao.ID_EVENTO+" = '"+idTipoEvento+"'";
            Cursor mCursor =  db.query(Conexao.TABELA_TIPO_EVENTO, new String[] {Conexao.TIPO_EVENTO}, whereClause,null, null,null, null, null);
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            return mCursor;

        }

        public boolean removerTipoEvento(int idTipoEvento) throws SQLException
        {
           long result=  db.delete(Conexao.TABELA_TIPO_EVENTO,Conexao.ID_EVENTO+" = "+idTipoEvento,null);
            if(result == -1){
                return false;
            }

            return true;
        }

        public boolean updateTipoEvento(int idTipoEvento, String tipoEvento)
        {
            ContentValues args = new ContentValues();
            args.put(Conexao.TIPO_EVENTO, tipoEvento);
            return db.update(Conexao.TABELA_TIPO_EVENTO, args, Conexao.ID_EVENTO + " = " +idTipoEvento, null) > 0;
        }

    }
