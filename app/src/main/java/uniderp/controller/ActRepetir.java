package uniderp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uniderp.appeventos.R;
import uniderp.model.Repeticao;

public class ActRepetir extends AppCompatActivity {

    CheckBox checkIndeterminado;
    CheckBox checkApos;
    CheckBox checkTermina;
    EditText editDataTermino;
    EditText editQtdeOcorrencia;
    String repeticao = "";
    RadioButton radioDiario;
    RadioButton radioSemanal;
    RadioButton radioMensal;
    RadioButton radioAnual;
    EditText editDataEvento;
    EditText editRepete ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_repetir);
        Intent intent = getIntent();
        if(intent != null){
            Bundle params = intent.getExtras();
            if(params != null){
                String dataInicio = params.getString("dataEvento");
                TextView textInicio = (TextView) findViewById(R.id.textInicio);
                textInicio.setText(dataInicio);
            }

        }
        checkIndeterminado = (CheckBox) findViewById(R.id.checkIndeterminado);
        checkApos = (CheckBox) findViewById(R.id.checkApos);;
        checkTermina =(CheckBox) findViewById(R.id.checkTermina);

        editQtdeOcorrencia = (EditText) findViewById(R.id.editQtdeOcorrencia);
        editRepete = (EditText) findViewById(R.id.editRepete);
        editDataTermino =  (EditText) findViewById(R.id.editDataTermino);
    }
    /*Se radio button "indeterminado" estiver checado, os dois campos a baixo dele deverao estar
    desabilitados*/
    public  void onClickCheckInterminado(View v){
        if(checkIndeterminado.isChecked()){
            editQtdeOcorrencia.setEnabled(false);
            editDataTermino.setEnabled(false);
            checkApos.setEnabled(false);
            checkTermina.setEnabled(false);
        }
        else{
            editQtdeOcorrencia.setEnabled(true);
            editDataTermino.setEnabled(true);
            checkApos.setEnabled(true);
            checkTermina.setEnabled(true);
        }
    }

    public  void onClickCheckApos(View v){
        if(checkApos.isChecked()){
            checkIndeterminado.setEnabled(false);
            checkTermina.setEnabled(false);
            editDataTermino.setEnabled(false);
        }
        else{
            checkIndeterminado.setEnabled(true);
            checkTermina.setEnabled(true);
            editDataTermino.setEnabled(true);
        }
    }

    public  void onClickCheckTermina(View v){
        if(checkTermina.isChecked()){
            editQtdeOcorrencia.setEnabled(false);
            checkApos.setEnabled(false);
            checkIndeterminado.setEnabled(false);

        }
        else{
            editQtdeOcorrencia.setEnabled(true);
            checkApos.setEnabled(true);
            checkIndeterminado.setEnabled(true);
        }
    }

    public void concluido(View v){
        radioDiario = (RadioButton) findViewById(R.id.radioDiario);
        radioSemanal = (RadioButton) findViewById(R.id.radioSemanal);
        radioMensal = (RadioButton) findViewById(R.id.radioMensal);
        radioAnual = (RadioButton) findViewById(R.id.radioAnual);
        editDataEvento = (EditText) findViewById(R.id.editDataEvento);

        if(radioDiario.isChecked()){
            repeticao = "Diariamente";
        }
        else if(radioSemanal.isChecked()){
            repeticao = "Semanalmente";
        }
        else if(radioMensal.isChecked()){
            repeticao = "Mensalmente";
        }
        else if(radioAnual.isChecked()){
            repeticao = "Anualmente";
        }

        Intent intent = new Intent();
        Bundle params = new Bundle();
        params.putString("repeticao", repeticao);
        params.putString("qtdeOcorrencia", editQtdeOcorrencia.getText().toString());
        params.putBoolean("indeterminado",checkIndeterminado.isChecked() );
        params.putInt("repete", Integer.parseInt(editRepete.getText().toString()));
        params.putString("dataTermino", editDataTermino.getText().toString());

        intent.putExtras(params);
        setResult(1, intent);
        super.finish();
    }
    public void cancelar(View v){
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

}
