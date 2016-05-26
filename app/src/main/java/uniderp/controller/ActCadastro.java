package uniderp.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;

public class ActCadastro extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinnerTipoEvento;
    int idTipoEvento;
    EditText editDataEvento;
    EditText editHoraEvento;
    EditText editHoraFim ;
    EditText editLocalEvento;
    List<String> opcoesTipoEvento = new ArrayList<String>();;
    EditText editDescricao ;
    EditText editParticipantes ;
    Compromisso compromisso  = new Compromisso();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_cadastro);

        editDataEvento = (EditText) findViewById(R.id.editDataEvento);
        editDataEvento.addTextChangedListener(Mask.insert(Mask.DATA_MASK, editDataEvento));
        RadioButton radioCadastrarTipoEvento = (RadioButton) findViewById(R.id.radioCadastrarTipoEvento);
        RadioButton radioAlterarTipoEvento = (RadioButton) findViewById(R.id.radioAlterarTipoEvento);
        RadioButton radioDeletarTipoEvento = (RadioButton) findViewById(R.id.radioDeletarTipoEvento);

        spinnerTipoEvento = (Spinner) findViewById(R.id.spinnerTipoEvento);

        carregarSpinner();
    }
    public void carregarSpinner(){
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

    public void cadastrarTipoEvento(View v){
        final AcessoBanco db = new AcessoBanco(this);
        LayoutInflater layoutInflater = LayoutInflater.from(ActCadastro.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_tipo_evento, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActCadastro.this);
        alertDialogBuilder.setView(promptView);

        final EditText editTipoEvento = (EditText) promptView.findViewById(R.id.editTipoEvento);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(editTipoEvento.getText().toString().isEmpty() == false) {
                            db.open();
                            db.insereTipoEvento(editTipoEvento.getText().toString());
                            db.close();
                            carregarSpinner();
                        }
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void alterarTipoEvento(View v){
        final AcessoBanco db = new AcessoBanco(this);
        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(ActCadastro.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_tipo_evento, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActCadastro.this);
        alertDialogBuilder.setView(promptView);

        final EditText editTipoEvento = (EditText) promptView.findViewById(R.id.editTipoEvento);
        db.open();
        Cursor c = db.getTipoEventoById(compromisso.getIdTipoEvento());
        db.close();
        final String tipoEventoTemp;
        editTipoEvento.setText(c.getString(0));
        // setup a dialog window

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Alterar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(editTipoEvento.getText().toString().isEmpty() == false) {
                            db.open();
                            db.updateTipoEvento(compromisso.getIdTipoEvento(),editTipoEvento.getText().toString());
                            db.close();
                            carregarSpinner();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                });
        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public void deletarTipoEvento(View v){
        final AcessoBanco db = new AcessoBanco(this);
        db.open();
        if(db.removerTipoEvento(compromisso.getIdTipoEvento()) == false){
            Toast.makeText(this,"Erro ao deletar registro",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Regristro deletado com sucesso",Toast.LENGTH_SHORT).show();
        db.close();

        carregarSpinner();
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

        editHoraEvento = (EditText) findViewById(R.id.editHoraEvento);
        editHoraFim = (EditText) findViewById(R.id.editHoraFim);
        editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editParticipantes = (EditText) findViewById(R.id.editParticipantes);
        final AcessoBanco db = new AcessoBanco(this);

        if(isCamposPreenchidos() && validacoes()){
            compromisso = Compromisso.cadastrarCompromisso(editDataEvento,editHoraEvento,editHoraFim,editLocalEvento,editDescricao, editParticipantes, idTipoEvento);
            db.open();
            long resultado = db.insereCompromisso(compromisso);
            db.close();
            if (resultado == -1)
                Toast.makeText(this,"Erro ao inserir registro",Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Evento criado com sucesso!", Toast.LENGTH_SHORT).show();

                LayoutInflater layoutInflater = LayoutInflater.from(ActCadastro.this);
                View promptView = layoutInflater.inflate(R.layout.input_nova_repeticao, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActCadastro.this);
                alertDialogBuilder.setView(promptView);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Criar repetição", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                repetir();

                            }
                        })
                        .setNegativeButton("Não criar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();


            }
        }

    }

    public boolean validacoes(){
        if(!validarDataEvento(editDataEvento.getText().toString())){
            Toast.makeText(this,"Data do evento inválida!",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(Integer.parseInt(editHoraEvento.getText().toString()) >= 24
                || Integer.parseInt(editHoraEvento.getText().toString()) < 0){
            Toast.makeText(this,"Digite uma hora válida!",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    public boolean isCamposPreenchidos(){
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

    public void repetir(){
            Intent params = new Intent(this, ActRepetir.class);
            params.putExtra("COMPROMISSO",  compromisso);
            startActivity(params);
    }

    public void cancelar(View v){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

}
