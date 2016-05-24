package uniderp.model;

import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uniderp.controller.AcessoBanco;

/**
 * Created by Edithimar on 21/04/2016.
 */
public class Compromisso {

    private Date dataEvento;
    private int  horaInicio;
    private int  horaFim;
    private String localRealizacao;
    private String descricao;
    private String participantes;
    private int tipoEventos;
    private String repeticao;

    public static Compromisso cadastrarCompromisso(EditText editDataEvento,EditText editHoraEvento,EditText editHoraFim,
                                            EditText editLocalEvento,EditText editDescricao,
                                            EditText editParticipantes, int idTipoEvento){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
        Date dateObject = null;
        try {
             dateObject =  formatter.parse(editDataEvento.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Compromisso compromisso = new Compromisso();
        compromisso.setDataEvento(dateObject);
        compromisso.setHoraInicio(Integer.parseInt(editHoraEvento.getText().toString()));
        compromisso.setHoraFim(Integer.parseInt(editHoraFim.getText().toString()));
        compromisso.setLocalRealizacao(editLocalEvento.getText().toString());
        compromisso.setDescricao(editDescricao.getText().toString());
        compromisso.setParticipantes(editParticipantes.getText().toString());
        compromisso.setTipoEventos(idTipoEvento);


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

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
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

    public int getTipoEventos() {
        return tipoEventos;
    }

    public void setTipoEventos(int tipoEventos) {
        this.tipoEventos = tipoEventos;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(String repeticao) {
        this.repeticao = repeticao;
    }
}
