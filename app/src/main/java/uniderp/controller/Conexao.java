package uniderp.controller;

/**
 * Created by edilene on 26/04/16.
 */

    import android.content.Context;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import java.util.Date;
    import java.util.StringTokenizer;

public class Conexao extends SQLiteOpenHelper {


        public static final String NOME_BD = "trabalho.db";
        public static final int DATABASE_VERSION = 14;

        //Tabela "compromisso"
        public static final String TABELA_COMPROMISSO = "tab_compromisso";
        //dados da tabela compromisso
        public static final String  DATA_EVENTO = "data_evento";
        public static final String  HORA_INICIO = "hora_inicio";
        public static final String  HORA_FIM = "hora_fim";
        public static final String LOCAL_REALIZACAO = "local_realizacao";
        public static final String DESCRICAO = "descricao";
        public static final String ID_COMPROMISSO = "id_compromisso";
        public static final String PARTICIPANTES = "participantes";
        public static final String ID_TIPO_EVENTO = "id_tipo_evento";

    //Tabela "tipo_evento"
        public static final String TABELA_TIPO_EVENTO = "tab_tipo_evento";
        //dados da tabela tipo_evento
        public static final String ID_EVENTO = "id_tipo_evento";
        public static final String TIPO_EVENTO = "tipo_evento";


        private static final String DATABASE_CREATE_COMPROMISSO = "CREATE TABLE " +TABELA_COMPROMISSO+" ( " + ID_COMPROMISSO + " integer PRIMARY KEY autoincrement, "
                + DATA_EVENTO + " text, " + HORA_INICIO + " integer, " + HORA_FIM + " integer, " +
                LOCAL_REALIZACAO + " text, " + DESCRICAO + " text, "
                + PARTICIPANTES + " text, " +ID_TIPO_EVENTO +" integer," +" FOREIGN KEY("+ID_TIPO_EVENTO+") REFERENCES "+TABELA_TIPO_EVENTO+
                "("+ID_EVENTO+")"+" )";


        private static final String DATABASE_CREATE_TIPO_EVENTO = "CREATE TABLE " +TABELA_TIPO_EVENTO+" ( " + ID_EVENTO + " integer primary key autoincrement, "
                + TIPO_EVENTO + " text " +  " )";


        Conexao(Context context)
        {
            super(context, NOME_BD, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE_COMPROMISSO);
                db.execSQL(DATABASE_CREATE_TIPO_EVENTO);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABELA_COMPROMISSO);
            db.execSQL("DROP TABLE IF EXISTS " + TABELA_TIPO_EVENTO);

            onCreate(db);
        }

    }
