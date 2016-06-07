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

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Date;
    import java.util.List;

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
            if(compromisso.getIsRepeticao()== -1) {
                initialValuesCompromisso.put(Conexao.IS_REPETICAO, -1);
            }
            else if(compromisso.getIsRepeticao() == -2){

                //cadastra em IS_REPETICAO o codigo do compromisso PAI - Para no futuro poder excluir e alterar
                initialValuesCompromisso.put(Conexao.IS_REPETICAO, compromisso.getIdCompromisso());
            }
            return  db.insert(Conexao.TABELA_COMPROMISSO, null, initialValuesCompromisso);
        }
        public long insereTipoEvento(String tipoEvento){
            ContentValues initialValuesTipoEvento = new ContentValues();
            initialValuesTipoEvento.put(Conexao.TIPO_EVENTO, tipoEvento);
            return db.insert(Conexao.TABELA_TIPO_EVENTO, null, initialValuesTipoEvento);
        }

        public Cursor getCompromissos() throws SQLException
        {
            Cursor mCursor =  db.query(Conexao.TABELA_COMPROMISSO, new String[] {Conexao.ID_COMPROMISSO, Conexao.DATA_EVENTO,Conexao.HORA_INICIO
                    ,Conexao.HORA_FIM,Conexao.LOCAL_REALIZACAO,Conexao.DESCRICAO,Conexao.PARTICIPANTES, Conexao.IS_REPETICAO,Conexao.ID_TIPO_EVENTO}, null,null, null,null, null, null);
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
        public Cursor getCompromissoById(int idCompromisso) throws SQLException
        {
            String whereClause = Conexao.ID_COMPROMISSO+" = '"+idCompromisso+"'";
            Cursor mCursor =  db.query(Conexao.TABELA_COMPROMISSO, new String[] {Conexao.ID_COMPROMISSO, Conexao.DATA_EVENTO,Conexao.HORA_INICIO
                    ,Conexao.HORA_FIM,Conexao.LOCAL_REALIZACAO,Conexao.DESCRICAO,Conexao.PARTICIPANTES, Conexao.IS_REPETICAO,Conexao.ID_TIPO_EVENTO}, whereClause,null, null,null, null, null);
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
    public boolean exupurgarCompromissos(int idCompromisso) throws SQLException {
        long result=  db.delete(Conexao.TABELA_COMPROMISSO,Conexao.ID_COMPROMISSO+" = "+idCompromisso,null);
        if(result == -1){
            return false;
        }
        return true;
    }
        public boolean removerCompromissos(Compromisso compromisso) throws SQLException
        {
            List<Integer> listidCompromissoRepeticoes = new ArrayList<Integer>();
            // Ao excluir, ver se tem repeção ou se é repetiçao, se for, deleta todas as repetições
            if(compromisso.getIsRepeticao() == -1){
                //Não é uma repetição, porém ver se tem repetições DELE
                Cursor mCursor =  getCompromissos();
                if (mCursor != null) {
                    mCursor.moveToFirst();
                }
                do {
                    if(mCursor.getInt(7) == compromisso.getIdCompromisso() ){
                        // a data precisa ser maior ou igual
                        String dataEventoStr = mCursor.getString(1);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dataEvento = null;
                        Date dataEventoRepeticao = null;
                        try {
                             dataEvento = sdf.parse(compromisso.getDataEvento());
                             dataEventoRepeticao = sdf.parse(dataEventoStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(dataEventoRepeticao.before(dataEvento)  || dataEventoRepeticao.equals(dataEvento)) {
                            listidCompromissoRepeticoes.add(mCursor.getInt(0));
                        }
                    }
                } while (mCursor.moveToNext());
            }
            else{
                // ele é uma repetição, devo excluir as outras
                Cursor mCursor =  getCompromissos();
                if (mCursor != null) {
                    mCursor.moveToFirst();
                }
                do {
                    if(mCursor.getInt(7) == compromisso.getIsRepeticao() ){
                        // a data precisa ser maior ou igual
                        String dataEventoStr = mCursor.getString(1);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dataEvento = null;
                        Date dataEventoRepeticao = null;
                        try {
                            dataEvento = sdf.parse(compromisso.getDataEvento());
                            dataEventoRepeticao = sdf.parse(dataEventoStr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(dataEventoRepeticao.before(dataEvento)  || dataEventoRepeticao.equals(dataEvento)) {
                            listidCompromissoRepeticoes.add(mCursor.getInt(0));
                        }
                    }
                } while (mCursor.moveToNext());

            }
            long result=  db.delete(Conexao.TABELA_COMPROMISSO,Conexao.ID_COMPROMISSO+" = "+compromisso.getIdCompromisso(),null);
            if(result == -1){
                return false;
            }
            int i = listidCompromissoRepeticoes.size();
            while(!listidCompromissoRepeticoes.isEmpty()){
                 db.delete(Conexao.TABELA_COMPROMISSO,Conexao.ID_COMPROMISSO+" = "+listidCompromissoRepeticoes.get(i),null);
                i--;
            }

            return true;
        }


        public boolean updateTipoEvento(int idTipoEvento, String tipoEvento)
        {
            ContentValues args = new ContentValues();
            args.put(Conexao.TIPO_EVENTO, tipoEvento);
            return db.update(Conexao.TABELA_TIPO_EVENTO, args, Conexao.ID_EVENTO + " = " +idTipoEvento, null) > 0;
        }


        public boolean updateCompromisso(Compromisso compromisso) {
            List<Integer> listidCompromissoRepeticoes = new ArrayList<Integer>();
            ContentValues args = new ContentValues();
            args.put(Conexao.DATA_EVENTO, String.valueOf(compromisso.getDataEvento()));
            args.put(Conexao.DESCRICAO, compromisso.getDescricao());
            args.put(Conexao.HORA_INICIO, compromisso.getHoraInicio());
            args.put(Conexao.HORA_FIM, compromisso.getHoraInicio());
            args.put(Conexao.LOCAL_REALIZACAO, compromisso.getLocalRealizacao());
            args.put(Conexao.PARTICIPANTES, compromisso.getParticipantes());
            args.put(Conexao.ID_TIPO_EVENTO, compromisso.getIdTipoEvento());

            //verificar todos as repeticoes para tal e alterar também

            Cursor mCursor = getCompromissos();
            if (mCursor != null) {
                mCursor.moveToFirst();
            }
            do {
                if (mCursor.getInt(7) == compromisso.getIdCompromisso()) {
                    // a data precisa ser maior ou igual
                    String dataEventoStr = mCursor.getString(1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date dataEvento = null;
                    Date dataEventoRepeticao = null;
                    try {
                        dataEvento = sdf.parse(compromisso.getDataEvento());
                        dataEventoRepeticao = sdf.parse(dataEventoStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (dataEventoRepeticao.before(dataEvento) || dataEventoRepeticao.equals(dataEvento)) {
                        listidCompromissoRepeticoes.add(mCursor.getInt(0));
                    }
                }
            } while (mCursor.moveToNext());
            int i = listidCompromissoRepeticoes.size();
            boolean result =  db.update(Conexao.TABELA_COMPROMISSO, args, Conexao.ID_COMPROMISSO + " = " +compromisso.getIdCompromisso(), null) > 0;
            while(!listidCompromissoRepeticoes.isEmpty()){
                boolean resultadoRepeticao = db.update(Conexao.TABELA_COMPROMISSO, args, Conexao.ID_COMPROMISSO + " = " + listidCompromissoRepeticoes.get(i), null) > 0;
                i--;
            }

            return result;

        }



}
