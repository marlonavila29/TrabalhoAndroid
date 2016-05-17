package uniderp.controller;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void save(View v){
        final AcessoBanco db = new AcessoBanco(this);
        Button buttonSalvar = (Button) findViewById(R.id.buttonSalvar);
        EditText editDataEvento = (EditText) findViewById(R.id.editDataEvento);
        EditText editHoraEvento = (EditText) findViewById(R.id.editHoraEvento);
        EditText editHoraFim = (EditText) findViewById(R.id.editHoraFim);
        EditText editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
        EditText editDescricao = (EditText) findViewById(R.id.editDescricao);
        EditText editParticipantes = (EditText) findViewById(R.id.editParticipantes);

        db.open();
        db.inserePessoa(editDataEvento.toString());
        db.close();

        db.open();
        Cursor c = db.getData(1);

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
                "Codigo: " + c.getString(0) + "\n" +
                        "Nome: " + c.getString(1) + "\n",
                Toast.LENGTH_SHORT).show();
    }

    public void repetir(View v){




    }

}
