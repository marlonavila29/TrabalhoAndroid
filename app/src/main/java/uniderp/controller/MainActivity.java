package uniderp.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;


public class MainActivity extends AppCompatActivity{

    CalendarView calendar ;
    final Calendar[] calendarSelecionado = new Calendar[1];
    Button buttonExpurgar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonExpurgar = (Button) findViewById(R.id.buttonExpurgar);
        buttonExpurgar.setEnabled(false);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendarSelecionado[0] = new GregorianCalendar( year, month, dayOfMonth );
                buttonExpurgar.setEnabled(true);
            }
        });

    }

    public void visualizarCompromissos(View v) {
        Intent actCompromissos = new Intent(this, ActCompromissos.class);
        startActivity(actCompromissos);
    }

    public void cadastrarCompromisso(View v) {
        Intent actCadastro = new Intent(this, ActCadastro.class);
        startActivity(actCadastro);
    }

    public void expurgarCompromissos(View v) {

        final Date dataSelecionada = calendarSelecionado[0].getTime();
        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
        final View promptView = layoutInflater.inflate(R.layout.input_expurgar, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilder.setView(promptView);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Declaração de variáveis
                        Cursor c = null;
                        Date dataEvento = null;
                        List<Integer> codigosCompromissoParaExcluir = new ArrayList<Integer>();
                        List<Compromisso> listaCompromisso = new ArrayList<Compromisso>();
                        Compromisso compromisso;
                        //Aqui é instanciado a Casse Acesso ao baco. Usa-se o "db" para chamar os métodos dessa classe
                        AcessoBanco db = new AcessoBanco(getApplicationContext());

                        //FAZER: Trazer do banco todos os compromisso  (getCompromisso) e "colocar" na variavel "c"
                        db.open();
                        c = db.getCompromissos();
                        db.close();
                        /*Pegando os compromissos cadastrados da variavel "c" que veio do banco e colocando no objeto e
                        inserindo cada compromisso numa lista de compromissos */

                        if (c != null && c.getCount() > 0) {
                            do {
                                compromisso = new Compromisso();
                                compromisso.setIdCompromisso(c.getInt(0));
                                compromisso.setDataEvento(c.getString(1));
                                compromisso.setHoraInicio(c.getInt(2));
                                compromisso.setHoraFim(c.getInt(3));
                                compromisso.setLocalRealizacao(c.getString(4));
                                compromisso.setDescricao(c.getString(5));
                                compromisso.setParticipantes(c.getString(6));
                                compromisso.setIdTipoEvento(c.getInt(7));
                                listaCompromisso.add(compromisso);
                            } while (c.moveToNext());
                        }
                        //Trazendo de dentro da lista, um por um, cada compromisso cadastrado para fazer a verificacao das datas
                        for (int i = 0; i < listaCompromisso.size(); i++) {
                            compromisso = listaCompromisso.get(i);
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            try {
                                dataEvento = sdf.parse(compromisso.getDataEvento());
                            } catch (ParseException e) {
                            }
                            //fazer Comparar se "dataEvento" é menor ou igual à "dataSelecionada"
                            // FAZER:Se for igual, execute isso: codigosCompromissoParaExcluir.add(compromisso.getIdCompromisso())
                            if (dataEvento.before(dataSelecionada) || dataEvento.equals(dataSelecionada)) {
                                codigosCompromissoParaExcluir.add(compromisso.getIdCompromisso());
                            }
                        }
                        //removendo os compromissos menores ou iguais a data selecionada no calendario
                        for (int i = 0; i < codigosCompromissoParaExcluir.size(); i++) {
                            //FAZER: Abra a conexao
                            db.open();
                            //FAZER: chame o método da Classe AcessoBanco "removerCompromisso() e passe como parâmetro isso:  codigosCompromissoParaExcluir.get(i);
                            db.exupurgarCompromissos(codigosCompromissoParaExcluir.get(i));
                            //FAZER: fechar conexao
                            db.close();

                        }
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