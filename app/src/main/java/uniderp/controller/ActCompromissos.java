package uniderp.controller;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
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
        arrayListCompromissos =  new ArrayList<String>();
        db.open();
        Cursor c = db.getCompromissos();
        db.close();
        if(c != null && c.getCount()>0) {
            do {
                arrayListCompromissos.add(c.getString(1));
            } while (c.moveToNext());
        }

        adaptador = new ArrayAdapter<String>(ActCompromissos.this, android.R.layout.simple_list_item_1, arrayListCompromissos);
        listCompromissos.setAdapter(adaptador);
        listCompromissos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0: navegarInternet();
                        break;
                    case 1: fazerLigacao();
                        break;
                    case 2: exibirSobre();
                        break;
                    case 3: finish();
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
}
