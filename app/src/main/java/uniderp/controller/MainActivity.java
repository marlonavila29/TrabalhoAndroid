package uniderp.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uniderp.appeventos.R;


public class MainActivity extends AppCompatActivity {

    CalendarView calendar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener()
        {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();


            }
        });


    }
    public void visualizarCompromissos(View v){
        Intent actCompromissos = new Intent(this, ActCompromissos.class);
        startActivity(actCompromissos);
    }
    public void cadastrarCompromisso(View v){
        Intent actCadastro = new Intent(this, ActCadastro.class);
        startActivity(actCadastro);
    }
    public void expurgarCompromissos(View v){
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        View promptView = layoutInflater.inflate(R.layout.input_nova_repeticao, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // PEgar a data selecionada no calendario

                        //Trazer do banco todos os compromisso (getCompromisso)

                        //Passar Elementos do tipo CUrsor para Array List

                        //Ver na arralist quais datas são menor ou igual a data selecionada

                        //Pesquisar no banco o ID do compromisso passando a Data como parametro

                        //removerCompromisso()


                    }
                })
                .setNegativeButton("cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
}
