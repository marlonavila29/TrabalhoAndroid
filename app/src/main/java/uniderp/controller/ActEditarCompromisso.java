package uniderp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import uniderp.appeventos.R;
import uniderp.model.Compromisso;




public class ActEditarCompromisso extends AppCompatActivity {
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
        CheckBox checkApos;
        CheckBox checkTermina;
        CheckBox checkIndeterminado;
        EditText editQtdeOcorrencia;
        EditText editRepete;
        EditText editDataTermino;


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
            CheckBox checkIndeterminado = (CheckBox) findViewById(R.id.checkIndeterminado);
            checkApos = (CheckBox) findViewById(R.id.checkApos);
            checkTermina = (CheckBox) findViewById(R.id.checkTermina);
            editQtdeOcorrencia = (EditText) findViewById(R.id.editQtdeOcorrencia);
            editRepete = (EditText) findViewById(R.id.editRepete);
            editDataTermino = (EditText) findViewById(R.id.editDataTermino);
        }
}
