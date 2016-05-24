package uniderp.controller;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;

public class ActCadastro extends AppCompatActivity {
    ListView lvOpcoes;
    List<String> opcoesTipoEvento;
    ArrayAdapter<String> adaptador;
    int idTipoEvento;
    EditText editDataEvento;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro);
        lvOpcoes = (ListView) findViewById(R.id.listViewTipoEvento);
/*
        final AcessoBanco db = new AcessoBanco(this);
        Cursor c = db.getTipoEvento();

        if (c.moveToFirst())
        {
            do {
                opcoesTipoEvento.add(c.getString(0));

            } while (c.moveToNext());
        }
*/
        opcoesTipoEvento = new ArrayList<String>();
        opcoesTipoEvento.add("Saúde");
        opcoesTipoEvento.add("Família");
        opcoesTipoEvento.add("Escola");
        opcoesTipoEvento.add("Trabalho");
        opcoesTipoEvento.add("Lazer");

        adaptador = new ArrayAdapter<String>(ActCadastro.this, android.R.layout.simple_list_item_1, opcoesTipoEvento);
        lvOpcoes.setAdapter(adaptador);
        lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: idTipoEvento=1;
                        break;
                    case 1: idTipoEvento=2;
                        break;
                    case 2: idTipoEvento=3;
                        break;
                    case 3: idTipoEvento=4;
                        break;
                }
            }
             });
    }
    public void salvarCompromisso(View v){
        final AcessoBanco db = new AcessoBanco(this);

        editDataEvento = (EditText) findViewById(R.id.editDataEvento);
        EditText editHoraEvento = (EditText) findViewById(R.id.editHoraEvento);
        EditText editHoraFim = (EditText) findViewById(R.id.editHoraFim);
        EditText editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
        EditText editDescricao = (EditText) findViewById(R.id.editDescricao);
        EditText editParticipantes = (EditText) findViewById(R.id.editParticipantes);

        Compromisso compromisso = new Compromisso();
        compromisso = Compromisso.cadastrarCompromisso(editDataEvento,editHoraEvento,editHoraFim,editLocalEvento,editDescricao, editParticipantes, idTipoEvento);
        db.open();
        long resultado = db.insereCompromisso(compromisso);
        db.close();
        if (resultado == -1)
            Toast.makeText(this,"Erro ao inserir registro",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Registro Inserido com sucesso",Toast.LENGTH_SHORT).show();
        db.open();

    }

    public void mostraRegistro(Cursor c)
    {
        Toast.makeText(this,
                "Data: " + c.getString(0) + "\n", Toast.LENGTH_SHORT).show();
    }

    public void repetir(View v){
        Intent meuIntent = new Intent(this, ActRepetir.class);
        Bundle params = new Bundle();
        editDataEvento = (EditText) findViewById(R.id.editDataEvento);
        params.putString("dataEvento",editDataEvento.getText().toString() );
        meuIntent.putExtras(params);
        startActivityForResult(meuIntent, 1);

    }

    public void cancelar(View v){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            Bundle params = data.getExtras();
            if(params != null){
               String tipoRepeticao = params.getString("repeticao");
               String qtdeOcorrencia = params.getString("qtdeOcorrencia");
               boolean indeterminado = params.getBoolean("indeterminado");
               int IntervaloRepeticao = params.getInt("repete");
               String dataParaTermino = params.getString("dataTermino");
            }
        }

    }
}
