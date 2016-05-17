package uniderp.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Edithimar on 21/04/2016.
 */
public class Compromisso {

    private Date dataCompromisso;
    private int  horaInicio;
    private int  horaFim;
    private String localRealizacao;
    private String descricao;
    private String participantes;
    private ArrayList<String> tipoEventos = new ArrayList<String>();
    private String repeticao;

    public void cadastrarCompromisso(Compromisso compromisso){

    }

    public void alterarCompromisso(Compromisso compromisso){

    }

    public void vizualizarCompromisso(Compromisso compromisso){

    }

    public void expurgarCompromisso(Compromisso compromisso){

    }

    public void cancelarCompromisso(Compromisso compromisso){

    }

    public Date getDataCompromisso() {
        return dataCompromisso;
    }

    public void setDataCompromisso(Date dataCompromisso) {
        this.dataCompromisso = dataCompromisso;
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

    public ArrayList<String> getTipoEventos() {
        return tipoEventos;
    }

    public void setTipoEventos(ArrayList<String> tipoEventos) {
        this.tipoEventos = tipoEventos;
    }

    public String getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(String repeticao) {
        this.repeticao = repeticao;
    }
}
