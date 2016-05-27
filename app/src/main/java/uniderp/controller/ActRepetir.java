package uniderp.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;

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
    EditText editRepete;
    Compromisso compromisso = new Compromisso();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_repetir);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("COMPROMISSO")) {
                compromisso = (Compromisso) bundle.get("COMPROMISSO");
                TextView textInicio = (TextView) findViewById(R.id.textInicio);
                textInicio.setText(compromisso.getDataEvento());
            }
        }
        checkIndeterminado = (CheckBox) findViewById(R.id.checkIndeterminado);
        checkApos = (CheckBox) findViewById(R.id.checkApos);
        ;
        checkTermina = (CheckBox) findViewById(R.id.checkTermina);
        editQtdeOcorrencia = (EditText) findViewById(R.id.editQtdeOcorrencia);
        editRepete = (EditText) findViewById(R.id.editRepete);
        editDataTermino = (EditText) findViewById(R.id.editDataTermino);
        editDataTermino.addTextChangedListener(Mask.insert(Mask.DATA_MASK, editDataTermino));
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

    public void onClickCheckInterminado(View v) {
        if (checkIndeterminado.isChecked()) {
            editQtdeOcorrencia.setEnabled(false);
            editDataTermino.setEnabled(false);
            checkApos.setEnabled(false);
            checkTermina.setEnabled(false);
            //zerando outros campos
            editQtdeOcorrencia.setText("");
            editDataTermino.setText("");
        } else {
            editQtdeOcorrencia.setEnabled(true);
            editDataTermino.setEnabled(true);
            checkApos.setEnabled(true);
            checkTermina.setEnabled(true);
        }
    }

    public void onClickCheckApos(View v) {
        if (checkApos.isChecked()) {
            checkIndeterminado.setEnabled(false);
            checkTermina.setEnabled(false);
            editDataTermino.setEnabled(false);
            editDataTermino.setText("");
        } else {
            checkIndeterminado.setEnabled(true);
            checkTermina.setEnabled(true);
            editDataTermino.setEnabled(true);
        }
    }

    public void onClickCheckTermina(View v) {
        if (checkTermina.isChecked()) {
            editQtdeOcorrencia.setEnabled(false);
            checkApos.setEnabled(false);
            checkIndeterminado.setEnabled(false);
            editQtdeOcorrencia.setText("");
        } else {
            editQtdeOcorrencia.setEnabled(true);
            checkApos.setEnabled(true);
            checkIndeterminado.setEnabled(true);
        }
    }

    public void salvarRepeticao(View v) {
        if (isCamposPreenchidos()) {

            radioDiario = (RadioButton) findViewById(R.id.radioDiario);
            radioSemanal = (RadioButton) findViewById(R.id.radioSemanal);
            radioMensal = (RadioButton) findViewById(R.id.radioMensal);
            radioAnual = (RadioButton) findViewById(R.id.radioAnual);
            editDataEvento = (EditText) findViewById(R.id.editDataEvento);

            if (radioDiario.isChecked()) {
                repeticao = "Diariamente";
            } else if (radioSemanal.isChecked()) {
                repeticao = "Semanalmente";
            } else if (radioMensal.isChecked()) {
                repeticao = "Mensalmente";
            } else if (radioAnual.isChecked()) {
                repeticao = "Anualmente";
            }
            if (checkApos.isChecked()) {
                cadastrarApos();
            }
            else if(checkIndeterminado.isChecked()){
                final int PADRAO_SISTEMA_CHECK_INDETERMINADO = 30;
                cadastrarIndeterminado(PADRAO_SISTEMA_CHECK_INDETERMINADO);
            }
            else if(checkTermina.isChecked()) {
                cadastrarDataTermino();
            }

        }
    }
    public void cadastrarDataTermino(){
        final AcessoBanco db = new AcessoBanco(this);
        editDataTermino = (EditText) findViewById(R.id.editDataTermino);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateInicio = null;
        Date dataTermino = null;

        int tamIntervalo = Integer.parseInt(editRepete.getText().toString());
        int diasDiff;

        try {
            dateInicio = sdf.parse(compromisso.getDataEvento());
            dataTermino = sdf.parse(editDataTermino.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (diasDiff = 0; dataTermino.after(dateInicio); diasDiff++) {
            dataTermino.setDate(dataTermino.getDate() - 1);
        }
        int qtdeOcorrencias = diasDiff/tamIntervalo;
        if (repeticao.equals("Diariamente")) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (qtdeOcorrencias > 0) {
                dateInicio.setDate(dateInicio.getDate() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        }
        else if (repeticao.equals("Semanalmente")) {
            //1 semana tem 7 dias
            tamIntervalo = tamIntervalo * 7;
            qtdeOcorrencias = qtdeOcorrencias/7;
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (qtdeOcorrencias > 0) {
                dateInicio.setDate(dateInicio.getDate() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Mensalmente")) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (qtdeOcorrencias > 0) {
                dateInicio.setMonth(dateInicio.getMonth() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Anualmente")) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            while (qtdeOcorrencias > 0) {
                dateInicio.setYear(dateInicio.getYear() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
            }
            qtdeOcorrencias--;
        }
    }
    public void cadastrarApos() {
        final AcessoBanco db = new AcessoBanco(this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date dateInicio = null;
        try {
            dateInicio = sdf.parse(compromisso.getDataEvento());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        int tamIntervalo = Integer.parseInt(editRepete.getText().toString());
        int qtdeOcorrencias = Integer.parseInt(editQtdeOcorrencia.getText().toString());
        if (repeticao.equals("Diariamente")) {
            while (qtdeOcorrencias > 0) {
                dateInicio.setDate(dateInicio.getDate() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Semanalmente")) {
            //1 semana tem 7 dias
            tamIntervalo = tamIntervalo * 7;
            qtdeOcorrencias = qtdeOcorrencias/7;
            while (qtdeOcorrencias > 0) {
                dateInicio.setDate(dateInicio.getDate() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Mensalmente")) {
            while (qtdeOcorrencias > 0) {
                dateInicio.setMonth(dateInicio.getMonth() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Anualmente")) {
            while (qtdeOcorrencias > 0) {
                dateInicio.setYear(dateInicio.getYear() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
            }
            qtdeOcorrencias--;
        }
    }
    public void cadastrarIndeterminado(final int PADRAO_SISTEMA_CHECK_INDETERMINADO) {
        final AcessoBanco db = new AcessoBanco(this);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        Date dateInicio = null;
        try {
            dateInicio = sdf.parse(compromisso.getDataEvento());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int tamIntervalo = Integer.parseInt(editRepete.getText().toString());
        int qtdeOcorrencias = PADRAO_SISTEMA_CHECK_INDETERMINADO;
        if (repeticao.equals("Diariamente")) {
            while (qtdeOcorrencias > 0) {
                dateInicio.setDate(dateInicio.getDate() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Semanalmente")) {
            //1 semana tem 7 dias
            tamIntervalo = tamIntervalo * 7;
            qtdeOcorrencias = qtdeOcorrencias/7;
            while (qtdeOcorrencias > 0) {
                dateInicio.setDate(dateInicio.getDate() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Mensalmente")) {
            while (qtdeOcorrencias > 0) {
                dateInicio.setMonth(dateInicio.getMonth() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
                qtdeOcorrencias--;
            }
        } else if (repeticao.equals("Anualmente")) {
            while (qtdeOcorrencias > 0) {
                dateInicio.setYear(dateInicio.getYear() + tamIntervalo);
                String reportDate = df.format(dateInicio);
                compromisso.setDataEvento(reportDate);
                db.open();
                long resultado = db.insereCompromisso(compromisso);
                db.close();
                if (resultado == -1)
                    Toast.makeText(this, "Erro ao inserir registro!", Toast.LENGTH_SHORT).show();
                else {
                    Toast.makeText(this, "Repetição(ões) criada(s) com sucesso!", Toast.LENGTH_SHORT).show();
                    //Ao salvar, vai para tela inicial
                    Intent it = new Intent(this, MainActivity.class);
                    startActivity(it);
                }
            }
            qtdeOcorrencias--;
        }
    }

    public boolean isCamposPreenchidos() {
        radioDiario = (RadioButton) findViewById(R.id.radioDiario);
        radioSemanal = (RadioButton) findViewById(R.id.radioSemanal);
        radioMensal = (RadioButton) findViewById(R.id.radioMensal);
        radioAnual = (RadioButton) findViewById(R.id.radioAnual);

        if (radioDiario.isChecked() == false && radioSemanal.isChecked() == false
                && radioMensal.isChecked() == false && radioAnual.isChecked() == false) {
            Toast.makeText(this, "Escolha alguma repetição!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editRepete.getText().toString().isEmpty()) {
            Toast.makeText(this, "Insira o número de intervalos!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkApos.isChecked() && !checkIndeterminado.isChecked() && !checkTermina.isChecked()) {
            Toast.makeText(this, "Marque alguma opção de término!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (checkApos.isChecked()) {
            if (editQtdeOcorrencia.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite a quantidade de ocorrências", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (checkTermina.isChecked()) {
            if (editDataTermino.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite a data de termino", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    public void cancelar(View v) {
        Intent it = new Intent(this, MainActivity.class);
        startActivity(it);
    }

}
