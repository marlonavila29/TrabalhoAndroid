package uniderp.controller;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uniderp.appeventos.R;

public class ActCadastro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro);
    }
    public void salvarCompromisso(View v){
        final AcessoBanco db = new AcessoBanco(this);
       // Button buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
        EditText editDataEvento = (EditText) findViewById(R.id.editDataEvento);
        /*EditText editHoraEvento = (EditText) findViewById(R.id.editHoraEvento);
        EditText editHoraFim = (EditText) findViewById(R.id.editHoraFim);
        EditText editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
        EditText editDescricao = (EditText) findViewById(R.id.editDescricao);
        EditText editParticipantes = (EditText) findViewById(R.id.editParticipantes);
*/
        db.open();
        long resultado = db.insereCompromisso(editDataEvento.getText().toString());
        db.close();
        if (resultado == -1)
            Toast.makeText(this,"Erro ao inserir registro",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Registro Inserido com sucesso",Toast.LENGTH_SHORT).show();
        db.open();
        Cursor c = db.getCompromissos();

        if (c.moveToFirst())
        {
            do {
                mostraRegistro(c);

            } while (c.moveToNext());
        }
        db.close();

    }

    public void mostraRegistro(Cursor c)
    {
        Toast.makeText(this,
                "Data: " + c.getString(0) + "\n", Toast.LENGTH_SHORT).show();
    }

    public void repetir(View v){




    }

}
