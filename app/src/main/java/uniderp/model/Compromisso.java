package uniderp.model;

import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import uniderp.controller.AcessoBanco;

/**
 * Created by Edithimar on 21/04/2016.
 */
public class Compromisso implements Serializable {

    private int idCompromisso;
    private String dataEvento;
    private int  horaInicio;
    private int  horaFim;
    private String localRealizacao;
    private String descricao;
    private String participantes;
    private String repeticao;
    private int idTipoEvento;

    public static Compromisso cadastrarCompromisso(EditText editDataEvento,EditText editHoraEvento,EditText editHoraFim,
                                            EditText editLocalEvento,EditText editDescricao,
                                            EditText editParticipantes, int idTipoEvento){

        Compromisso compromisso = new Compromisso();
        compromisso.setDataEvento(editDataEvento.getText().toString());
        compromisso.setHoraInicio(Integer.parseInt(editHoraEvento.getText().toString()));
        compromisso.setHoraFim(Integer.parseInt(editHoraFim.getText().toString()));
        compromisso.setLocalRealizacao(editLocalEvento.getText().toString());
        compromisso.setDescricao(editDescricao.getText().toString());
        compromisso.setParticipantes(editParticipantes.getText().toString());
        compromisso.setIdTipoEvento(idTipoEvento);


        return compromisso;
    }

    public void alterarCompromisso(Compromisso compromisso){

    }

    public void vizualizarCompromisso(Compromisso compromisso){

    }

    public void expurgarCompromisso(Compromisso compromisso){

    }

    public void cancelarCompromisso(Compromisso compromisso){

    }



    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(int horaFim) {
        this.horaFim = horaFim;
    }

    public String getLocalRealizacao() {
        return localRealizacao;
    }

    public void setLocalRealizacao(String localRealizacao) {
        this.localRealizacao = localRealizacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(String repeticao) {
        this.repeticao = repeticao;
    }

    public int getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(int idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
    }

    public int getIdCompromisso() {
        return idCompromisso;
    }

    public void setIdCompromisso(int idCompromisso) {
        this.idCompromisso = idCompromisso;
    }
}
