package uniderp.model;

import android.widget.EditText;
import android.widget.RadioButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Edithimar on 22/05/2016.
 */
public class Repeticao {

    private String tipoRepeticao;
    private int intervaloRepeticao;
    private Date dataInicio;
    private boolean terminoIndeterminado;
    private int terminoQtdeOcorrencia;
    private Date dataParaTermino;



    public static Repeticao cadastrarRepeticao(String tipoRepeticao,int intervaloRepeticao,
                                               String dataInicio, boolean terminoIndeterminado,
                                                int terminoQtdeOcorrencia, String dataParaTermino){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); // Make sure user insert date into edittext in this format.
        Repeticao repeticaoObj = new Repeticao();
        Date dateInicio = null;
        Date dateTermino = null;
        try {
            dateTermino =  formatter.parse(dataParaTermino);
            dateInicio =  formatter.parse(dataInicio);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(terminoIndeterminado == true)
        {
            repeticaoObj.setTerminoIndeterminado(true);
        }
        else
        {
            repeticaoObj.setTerminoIndeterminado(false);
        }
        repeticaoObj.setDataInicio(dateInicio);
        repeticaoObj.setDataParaTermino(dateTermino);
        repeticaoObj.setTerminoQtdeOcorrencia(terminoQtdeOcorrencia);
        repeticaoObj.setIntervaloRepeticao(intervaloRepeticao);
        repeticaoObj.setTipoRepeticao(tipoRepeticao);

        return repeticaoObj;
    }

    public String getTipoRepeticao() {
        return tipoRepeticao;
    }

    public void setTipoRepeticao(String tipoRepeticao) {
        this.tipoRepeticao = tipoRepeticao;
    }

    public int getIntervaloRepeticao() {
        return intervaloRepeticao;
    }

    public void setIntervaloRepeticao(int intervaloRepeticao) {
        intervaloRepeticao = intervaloRepeticao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public boolean isTerminoIndeterminado() {
        return terminoIndeterminado;
    }

    public void setTerminoIndeterminado(boolean terminoIndeterminado) {
        terminoIndeterminado = terminoIndeterminado;
    }

    public int getTerminoQtdeOcorrencia() {
        return terminoQtdeOcorrencia;
    }

    public void setTerminoQtdeOcorrencia(int terminoQtdeOcorrencia) {
        terminoQtdeOcorrencia = terminoQtdeOcorrencia;
    }

    public Date getDataParaTermino() {
        return dataParaTermino;
    }

    public void setDataParaTermino(Date dataParaTermino) {
        this.dataParaTermino = dataParaTermino;
    }
}
