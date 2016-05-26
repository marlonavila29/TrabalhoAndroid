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
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;

public class ActCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerTipoEvento;
    ArrayAdapter<String> adaptador;
    int idTipoEvento;
    EditText editDataEvento;
    EditText editHoraEvento;
    EditText editHoraFim ;
    EditText editLocalEvento;
    EditText editDescricao ;
    EditText editParticipantes ;

    Compromisso compromisso  = new Compromisso();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro);
        spinnerTipoEvento = (Spinner) findViewById(R.id.spinnerTipoEvento);
        List<String> opcoesTipoEvento = new ArrayList<String>();
        opcoesTipoEvento = insereTipoEventoPadrao();

        // Spinner click listener
        spinnerTipoEvento.setOnItemSelectedListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesTipoEvento);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerTipoEvento.setAdapter(dataAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        final AcessoBanco db = new AcessoBanco(this);
        String item = parent.getItemAtPosition(position).toString();
        db.open();
        Cursor c = db.getTipoEventoByName(item);
        db.close();
        //[ID_TIPO EVENTO, TIPO_EVENTO]
        compromisso.setIdTipoEvento(c.getInt(0));
        // Showing selected spinner item
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    public void salvarCompromisso(View v){

        editDataEvento = (EditText) findViewById(R.id.editDataEvento);
        editHoraEvento = (EditText) findViewById(R.id.editHoraEvento);
        editHoraFim = (EditText) findViewById(R.id.editHoraFim);
        editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editParticipantes = (EditText) findViewById(R.id.editParticipantes);
        final AcessoBanco db = new AcessoBanco(this);

        if(isCampoPreenchido() && validacoes()){
            compromisso = Compromisso.cadastrarCompromisso(editDataEvento,editHoraEvento,editHoraFim,editLocalEvento,editDescricao, editParticipantes, idTipoEvento);
            db.open();
            long resultado = db.insereCompromisso(compromisso);
            db.close();
            if (resultado == -1)
                Toast.makeText(this,"Erro ao inserir registro",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this,"Registro Inserido com sucesso",Toast.LENGTH_SHORT).show();
        }

    }
    public boolean validacoes(){
        if(!validarDataEvento(editDataEvento.getText().toString())){
            Toast.makeText(this,"Data do evento inválida!",Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;

    }


    public boolean isCampoPreenchido(){
        if(editDataEvento.getText().toString().isEmpty()){
            Toast.makeText(this,"Insira a data do evento!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editHoraEvento.getText().toString().isEmpty()){
            Toast.makeText(this,"Insira a hora do evento!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editHoraFim.getText().toString().isEmpty()){
            Toast.makeText(this,"Insira a hora final do evento!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editLocalEvento.getText().toString().isEmpty()){
            Toast.makeText(this,"Insira o local do evento!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editDescricao.getText().toString().isEmpty()){
            Toast.makeText(this,"Insira a data do evento!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editParticipantes.getText().toString().isEmpty()){
            Toast.makeText(this,"Insira os participantes",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean validarDataEvento(String dataString){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        if(dataString.length()!=10)
            return false;
        try {
            Date dataHoje = new Date(System.currentTimeMillis());
            Date data = sdf.parse(dataString);
            if(data.before(dataHoje)) {
                Toast.makeText(this, "Escolha uma data maior ou igual que hoje!", Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        } catch(ParseException e) {
            return false;
        }
    }
    public List<String> insereTipoEventoPadrao()
    {
        final AcessoBanco db = new AcessoBanco(this);
        List<String> opcoesTipoEvento = new ArrayList<String>();
        db.open();
        Cursor c = db.getTipoEvento();
        db.close();
        c.moveToFirst();
        if(c == null || c.getCount()<=0){
            opcoesTipoEvento.add("Saúde");
            opcoesTipoEvento.add("Família");
            opcoesTipoEvento.add("Escola");
            opcoesTipoEvento.add("Trabalho");
            opcoesTipoEvento.add("Lazer");
            db.open();
            for (String tipoEvento:opcoesTipoEvento) {
                db.insereTipoEvento(tipoEvento);
            }
            db.close();
        }
        else {
            do {
                opcoesTipoEvento.add(c.getString(1));
            } while (c.moveToNext());
        }
         return opcoesTipoEvento;

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
                boolean termino_indeterminado = params.getBoolean("indeterminado");
                int intervaloRepeticao = params.getInt("repete");
                String dataParaTermino = params.getString("dataTermino");

                if(tipoRepeticao == "Diariamente"){
                    //cadastrar com base no término "x" compromissos até o fim *A CADA intervaloRepeticao

                    if(termino_indeterminado == true){
                    }
                    else if(!qtdeOcorrencia.isEmpty()){
                    }
                    else if(!dataParaTermino.isEmpty()){
                        //(dataInicio - dataTermino)/intervalo
                        /*
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
                        Date dateObject = null;
                        try {
                            dateObject =  formatter.parse(dataParaTermino);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date dataInicio = compromisso.getDataEvento();
                        int intervalo = compromisso.getDataEvento().getDay() - dateObject.getDay();
                        while (intervalo>= 0){
                            Compromisso compromisso1= new Compromisso();
                            //A cada qtdeOcorrencias dias gravo um compromisso
                            compromisso.getDataEvento().setDate(dataInicio.getDay()+qtdeOcorrencia);
                            compromisso1.setDataEvento();
                            intervalo--;
                        }
                        */
                    }
                }
                else if(tipoRepeticao == "Semanalmente"){
                    //cadastrar com base no término "x" compromissos até o fim *A CADA intervaloRepeticao
                }
                else if(tipoRepeticao == "Mensalmente"){
                    //cadastrar com base no término "x" compromissos até o fim *A CADA intervaloRepeticao
                }
                else if(tipoRepeticao == "Anualmente"){
                    //cadastrar com base no término "x" compromissos até o fim *A CADA intervaloRepeticao
                }


            }
        }

    }


}
