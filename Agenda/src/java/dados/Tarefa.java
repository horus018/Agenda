package dados;

import java.io.Serializable;
import java.util.Date;

public class Tarefa implements Serializable {
    private String descricao;
    private String responsavel;
    private Date prazo;
    private boolean terminada = false;

    public Tarefa(String descricao, 
            String responsavel, Date prazo) {
        this.descricao = descricao;
        this.responsavel = responsavel;
        this.prazo = prazo;
    }

    public Tarefa() { }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public boolean isTerminada() {
        return terminada;
    }

    public void setTerminada(boolean terminada) {
        this.terminada = terminada;
    }
}
