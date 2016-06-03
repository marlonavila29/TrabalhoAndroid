package uniderp.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_editar_compromisso);

        spinnerTipoEvento = (Spinner)findViewById(R.id.spinnerTipoEvento);
        editDataEvento = (EditText)findViewById(R.id.editDataEvento);




    }



    public void preencheDados()
    {
        editDataEvento.setText(compromisso.getDataEvento());
    }

}
