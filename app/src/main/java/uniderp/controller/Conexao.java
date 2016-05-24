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

        public static final String ID = "_id";
        public static final String NOME_BD = "trabalho.db";
        public static final int DATABASE_VERSION = 8;

        //Tabela "compromisso"
        public static final String TABELA_COMPROMISSO = "compromisso";
        //dados da tabela compromisso
        public static final String  DATA_EVENTO = "data_evento";
        public static final String  HORA_INICIO = "hora_inicio";
        public static final String  HORA_FIM = "hora_fim";
        public static final String LOCAL_REALIZACAO = "local_realizacao";
        public static final String DESCRICAO = "descricao";
        public static final String PARTICIPANTES = "participantes";

        //Tabela "tipo_evento"
        public static final String TABELA_TIPO_EVENTO = "tipo_evento";
        //dados da tabela tipo_evento
        public static final String TIPO_EVENTO = "tipo_evento";

        //Tabela "tabela_repeticao"
        public static final String TABELA_REPETICAO = "repeticao";
        //dados da tabela repeticao
        public static final String TIPO_REPETICAO = "tipo_repeticao";
        public static final String INTERVALO_REPETICAO = "intervalo_repeticao";
        public static final String DATA_INICIO = "data_inicio";
        public static final String TERMINO_INDETERMINADO = "termino_indeterminado";
        public static final String DATA_PARA_TERMINO = "data_para_termino";
        public static final String TERMINO_QTDE_OCORRENCIA = "termino_qtde_ocorrencia";



        private static final String DATABASE_CREATE = "CREATE TABLE " +TABELA_COMPROMISSO+" ( " + ID + " integer primary key autoincrement, "
                + DATA_EVENTO + " text, " + HORA_INICIO + " integer, " + HORA_FIM + " integer, " +
                LOCAL_REALIZACAO + " text, " + DESCRICAO + " text, " + PARTICIPANTES + " text" +  " )";

        private static final String DATABASE_CREATE1 = "CREATE TABLE " +TABELA_TIPO_EVENTO+" ( " + ID + " integer primary key autoincrement, "
                + TIPO_EVENTO + " text " +  " )";

        private static final String DATABASE_CREATE2 = "CREATE TABLE " +TABELA_TIPO_EVENTO+" ( " + ID + " integer primary key autoincrement, "
                + TIPO_REPETICAO + " text, " + INTERVALO_REPETICAO + " integer, " +
                DATA_INICIO + " text, " + TERMINO_INDETERMINADO + " integer, " + DATA_PARA_TERMINO + " text, " + TERMINO_QTDE_OCORRENCIA + " integer " + " )";


        Conexao(Context context)
        {
            super(context, NOME_BD, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE1);
                db.execSQL(DATABASE_CREATE2);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABELA_COMPROMISSO);
            db.execSQL("DROP TABLE IF EXISTS " + TABELA_REPETICAO);
            db.execSQL("DROP TABLE IF EXISTS " + TABELA_TIPO_EVENTO);

            onCreate(db);
        }

    }
