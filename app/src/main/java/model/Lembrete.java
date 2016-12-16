package model;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class Lembrete {
    private int id;
    private String datahora;
    private int estado;
    private Livro livro;

    public Lembrete() {
    }

    public Lembrete(int id, String datahora, Livro livro, int estado){
        this.id = id;
        this.datahora = datahora;
        this.livro = livro;
        this.estado = estado;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
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

    /**
     * Pega o estado do LembreteActivity, sendo 0 terminado/concluido e 1 em andamento
     *
     * @return o estado sendo 0 completado e 1 em andamento
     */
    public int getEstado(){
        return this.estado;
    }

    /**
     * Modifica o estado do LembreteActivity, sendo 0 terminado/concluido e 1 em andamento
     *
     * @param estado 0 completado e 1 em andamento
     */
    public void setEstado(int estado){
        this.estado = estado;
    }
}
