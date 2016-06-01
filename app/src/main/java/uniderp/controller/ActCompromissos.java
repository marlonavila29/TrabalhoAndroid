package uniderp.controller;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uniderp.appeventos.R;

public class ActCompromissos extends AppCompatActivity {
    ListView listCompromissos;
    ArrayAdapter<String> adaptador;
    List<String> arrayListCompromissos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_compromissos);
        final AcessoBanco db = new AcessoBanco(this);

        // Get ListView object from xml
        listCompromissos = (ListView) findViewById(R.id.listCompromissos);

        // Defined Array values to show in ListView
        arrayListCompromissos = new ArrayList<String>();
        db.open();
        Cursor c = db.getCompromissos();
        db.close();
        if (c != null && c.getCount() > 0) {
            do {
                arrayListCompromissos.add(c.getString(1));
            } while (c.moveToNext());
        }

        adaptador = new ArrayAdapter<String>(ActCompromissos.this, android.R.layout.simple_list_item_1, arrayListCompromissos);
        listCompromissos.setAdapter(adaptador);
        listCompromissos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        navegarInternet();
                        break;
                    case 1:
                        fazerLigacao();
                        break;
                    case 2:
                        exibirSobre();
                        break;
                    case 3:
                        finish();
                        break;
                }
            }
        });
    }

    private void exibirSobre() {

    }

    private void fazerLigacao() {

    }

    private void navegarInternet() {

    }

    public  void filtarCompromissos(View v){
        //Comparar data inicil com data fim com a lista de compromissos

        EditText editDataInicio = (EditText) findViewById(R.id.dataInicio);
        EditText editDataFim = (EditText) findViewById(R.id.dataFim);
        List<String> arrayCompromisso = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateInicio = null;
        Date dateFim = null;
        String dataCompromisso = "";
        try {
            dateInicio = sdf.parse(editDataInicio.getText().toString());
            dateFim = sdf.parse(editDataFim.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date dateCompromisso = null;
        for(int i=0;i<arrayListCompromissos.size();i++){
            dataCompromisso = arrayListCompromissos.get(i);
            //data esta entre data inicio e data fim ?
            try {
                dateCompromisso = sdf.parse(dataCompromisso);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (dateCompromisso.after(dateInicio) && dateCompromisso.before(dateFim)) {
                arrayCompromisso.add(dateCompromisso.toString());

            }
        }
        arrayListCompromissos = arrayCompromisso;
        // Defined Array values to show in ListView
        adaptador = new ArrayAdapter<String>(ActCompromissos.this, android.R.layout.simple_list_item_1, arrayListCompromissos);
        listCompromissos.setAdapter(adaptador);
        listCompromissos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        navegarInternet();
                        break;
                    case 1:
                        fazerLigacao();
                        break;
                    case 2:
                        exibirSobre();
                        break;
                    case 3:
                        finish();
                        break;
                }
            }
        });


    }




}


