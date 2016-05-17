package uniderp.controller;

/**
 * Created by edilene on 26/04/16.
 */

    import android.content.Context;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    public class Conexao extends SQLiteOpenHelper {

        private static final String TAG = "DBAdapter";

        private static final String NOME_BD = "trabalho";
        private static final int DATABASE_VERSION = 2;
        private static final String TABELA= "compromisso";

        public static String  DATA_COMPROMISSO;
        public static int  HORA_INICIO;
        public static int  HORA_FIM;
        public static String LOCAL_REALIZACAO;
        public static String DESCRICAO;
        public static String PARTICIPANTES;
        public static int TIPO_EVENTOS;
        public static String REPETICAO;
        public static String ID;

        private static final String DATABASE_CREATE = "CREATE TABLE" +TABELA+"(" + ID + "integer primary key autoincrement,"
                + DATA_COMPROMISSO + "text," + HORA_INICIO + "integer," + HORA_FIM + "integer," +
                LOCAL_REALIZACAO + "text," + DESCRICAO + "text," + PARTICIPANTES + "text," +
                TIPO_EVENTOS + "integer," + REPETICAO + "text" + ")";

        Conexao(Context context)
        {
            super(context, NOME_BD, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Atualizando banco de dados da versão " + oldVersion + " para "
                    + newVersion + ". Todos os dados serão destruidos.");
            db.execSQL("DROP TABLE IF EXISTS pessoas");
            onCreate(db);
        }

    }
