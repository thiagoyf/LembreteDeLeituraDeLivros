package model;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class Lembrete {
    private int id;
    private String datahora;
    private int livroId;

    public Lembrete() {
    }

    public Lembrete(int id, String datahora, int livroId){
        this.id = id;
        this.datahora = datahora;
        this.livroId = livroId;
    }

    public int getLivroId() {
        return livroId;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatahora() {
        return datahora;
    }

    public void setDatahora(String datahora) {
        this.datahora = datahora;
    }

}
