package uniderp.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;




public class ActEditarCompromisso extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        Spinner spinnerTipoEvento;
        List<String> opcoesTipoEvento = new ArrayList<String>();;
        Compromisso compromisso  = new Compromisso();
        EditText editDataEvento;
        EditText editHoraEvento;
        EditText editHoraFim ;
        EditText editLocalEvento;
        EditText editDescricao ;
        EditText editParticipantes ;
        int idTipoEvento;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_act_editar_compromisso);

            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                if (bundle.containsKey("COMPROMISSO")) {
                    compromisso = (Compromisso) bundle.get("COMPROMISSO");
                }
            }

            editDataEvento = (EditText) findViewById(R.id.editDataEvento);
            editHoraEvento = (EditText) findViewById(R.id.editHoraEvento);
            editHoraFim = (EditText) findViewById(R.id.editHoraFim);
            editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
            editParticipantes = (EditText) findViewById(R.id.editParticipantes);
            editDescricao = (EditText) findViewById(R.id.editDescricao);

            //Colocando valores cadastrados na tela
            editDataEvento.setText(compromisso.getDataEvento());
            editHoraEvento.setText(String.valueOf(compromisso.getHoraInicio()));
            editHoraFim.setText(String.valueOf(compromisso.getHoraFim()));
            editLocalEvento.setText(compromisso.getLocalRealizacao());
            editParticipantes.setText(compromisso.getParticipantes());
            editDescricao.setText(compromisso.getDescricao());
            spinnerTipoEvento = (Spinner) findViewById(R.id.spinnerTipoEvento);

            carregarSpinner(compromisso.getIdTipoEvento());



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

    public void carregarSpinner(int idTipoEventoSelecionado){
        opcoesTipoEvento = insereTipoEventoPadrao();
        // Spinner click listener
        spinnerTipoEvento.setOnItemSelectedListener(this);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opcoesTipoEvento);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner

        spinnerTipoEvento.setAdapter(dataAdapter);

        if(idTipoEventoSelecionado != 0 ) {
            spinnerTipoEvento.setSelection(idTipoEventoSelecionado);
        }

    }

    public void cadastrarTipoEvento(View v){
        final AcessoBanco db = new AcessoBanco(this);
        LayoutInflater layoutInflater = LayoutInflater.from(ActEditarCompromisso.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_tipo_evento, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActEditarCompromisso.this);
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
                            carregarSpinner(0);
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
        LayoutInflater layoutInflater = LayoutInflater.from(ActEditarCompromisso.this);
        View promptView = layoutInflater.inflate(R.layout.input_dialog_tipo_evento, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ActEditarCompromisso.this);
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
                            carregarSpinner(0);
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
            Toast.makeText(this,"Erro ao deletar registro",Toast.LENGTH_SHORT).show();Toast.makeText(this,"Erro ao deletar registro",Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this,"Regristro deletado com sucesso",Toast.LENGTH_SHORT).show();
        db.close();

        carregarSpinner(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final AcessoBanco db = new AcessoBanco(this);
        String item = parent.getItemAtPosition(position).toString();
        db.open();
        Cursor c = db.getTipoEventoByName(item);
        db.close();
        //[ID_TIPO EVENTO, TIPO_EVENTO]
        compromisso.setIdTipoEvento(c.getInt(0));
        // Showing selected spinner item
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void salvarCompromisso(View v){

        editHoraEvento = (EditText) findViewById(R.id.editHoraFim);
        editHoraFim = (EditText) findViewById(R.id.editHoraEvento);
        editLocalEvento = (EditText) findViewById(R.id.editLocalEvento);
        editDescricao = (EditText) findViewById(R.id.editDescricao);
        editParticipantes = (EditText) findViewById(R.id.editParticipantes);
        final AcessoBanco db = new AcessoBanco(this);

        if(isCamposPreenchidos() && validacoes()){
            int idCompromisso =compromisso.getIdCompromisso();
            compromisso = Compromisso.cadastrarCompromisso(editDataEvento,editHoraEvento,editHoraFim,editLocalEvento,editDescricao, editParticipantes, idTipoEvento);
            compromisso.setIdCompromisso(idCompromisso);
            //Não é repeticao
            compromisso.setIsRepeticao(-1);
            db.open();
            boolean resultado = db.updateCompromisso(compromisso);
            ;           db.close();
            if (resultado == false)
                Toast.makeText(this,"Erro ao alterar registro",Toast.LENGTH_SHORT).show();
            else {
                Toast.makeText(this, "Evento e suas repetições alteradas com sucesso!", Toast.LENGTH_SHORT).show();
                cancelar(v);
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

    public void cancelar(View v){
        Intent it = new Intent(this, ActCompromissos.class);
        startActivity(it);
    }

    public void cancelarCompromisso(View v){
        AcessoBanco db = new AcessoBanco(this);
        db.open();
        db.removerCompromissos(compromisso);
        db.close();
        Intent it = new Intent(this, ActCompromissos.class);
        startActivity(it);
    }

}
