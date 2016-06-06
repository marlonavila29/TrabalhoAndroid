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
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;

public class ActCompromissos extends AppCompatActivity
{
    ListView listCompromissos;
    ArrayAdapter<String> adaptador;
    List<String> arrayListCompromissos;
    List<Integer> arrayCodCompromissos = new ArrayList<Integer>();
    EditText editDataInicio;
    EditText editDataFim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_compromissos);
        final AcessoBanco db = new AcessoBanco(this);
        editDataInicio = (EditText) findViewById(R.id.dataInicio);
        editDataFim = (EditText) findViewById(R.id.dataFim);

        editDataInicio.addTextChangedListener(Mask.insert(Mask.DATA_MASK, editDataInicio));
        editDataFim.addTextChangedListener(Mask.insert(Mask.DATA_MASK, editDataFim));

        // Get ListView object from xml
        listCompromissos = (ListView) findViewById(R.id.listCompromissos);

        // Defined Array values to show in ListView
        arrayListCompromissos = new ArrayList<String>();
        db.open();
        Cursor c = db.getCompromissos();
        db.close();
        if (c != null && c.getCount() > 0) {
            do {

                arrayListCompromissos.add(c.getString(1)+" às: "+c.getString(2)+" horas, local: "+c.getString(4)+", Desc.: "+c.getString(5) );
                arrayCodCompromissos.add(c.getInt(0));
            } while (c.moveToNext());
        }

        adaptador = new ArrayAdapter<String>(ActCompromissos.this, android.R.layout.simple_list_item_1, arrayListCompromissos);
        listCompromissos.setAdapter(adaptador);

        listCompromissos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Compromisso compromisso = new Compromisso();
                db.open();
                Cursor c = db.getCompromissoById(arrayCodCompromissos.get(position));
                db.close();
                compromisso.setIdCompromisso(c.getInt(0));
                compromisso.setDataEvento(c.getString(1));
                compromisso.setHoraInicio(c.getInt(2));
                compromisso.setHoraFim(c.getInt(3));
                compromisso.setLocalRealizacao(c.getString(4));
                compromisso.setDescricao(c.getString(5));
                compromisso.setParticipantes(c.getString(6));
                compromisso.setIsRepeticao(c.getInt(7));
                compromisso.setIdTipoEvento(c.getInt(8));

                Intent params = new Intent(getApplicationContext(), ActEditarCompromisso.class);
                params.putExtra("COMPROMISSO",  compromisso);
                startActivity(params);
            }
        });

    }

    public void limparFiltros(View v){
        editDataInicio.setText("");
        editDataFim.setText("");
        // Defined Array values to show in ListView
        adaptador = new ArrayAdapter<String>(ActCompromissos.this, android.R.layout.simple_list_item_1, arrayListCompromissos);
        listCompromissos.setAdapter(adaptador);
    }

    public  void filtrarCompromissos(View v){
        //Comparar data inicil com data fim com a lista de compromissos

        EditText editDataInicio = (EditText) findViewById(R.id.dataInicio);
        EditText editDataFim = (EditText) findViewById(R.id.dataFim);

        if (!editDataInicio.getText().toString().isEmpty() && !editDataFim.getText().toString().isEmpty() ) {

            List<String> arrayCompromisso = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date dataInicio = null;
            Date dataFim = null;
            String dataCompromissoStr = "";frw4
            Date dataCompromisso = null;

            try {
                dataInicio = sdf.parse(editDataInicio.getText().toString());
                dataFim = sdf.parse(editDataFim.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(int i=0;i<arrayListCompromissos.size();i++){
                dataCompromissoStr = arrayListCompromissos.get(i);
                                //data esta entre data inicio e data fim ?
                try {
                    dataCompromisso = sdf.parse(dataCompromissoStr);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                if (dataCompromisso.after(dataInicio) && dataCompromisso.before(dataFim)) {
                    arrayCompromisso.add(arrayListCompromissos.get(i));
                }
            }

            // Defined Array values to show in ListView
            adaptador = new ArrayAdapter<String>(ActCompromissos.this, android.R.layout.simple_list_item_1, arrayCompromisso);
            listCompromissos.setAdapter(adaptador);
        }
        else{
            Toast.makeText(this,"Preencha os campos para a pesquisa",Toast.LENGTH_SHORT).show();
        }

    }


    public void voltarMain(){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }



}


