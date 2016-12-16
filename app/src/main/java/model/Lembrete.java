package model;

/**
 * Created by thiagoyf on 12/12/16.
 */

public class Lembrete {
    private int id;
    private String datahora;
    private int estado;
    private Livro livro;
    private int repete;

    public Lembrete() {
    }

    public Lembrete(int id, String datahora, Livro livro, int estado, int repete){
        this.id = id;
        this.datahora = datahora;
        this.livro = livro;
        this.estado = estado;
        this.repete = repete;
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

    public int getRepete() {
        return this.repete;
    }

    public void setRepete(int repete) {
        this.repete = repete;
    }

    public String getRepeteDia() {
        switch (repete){
            case 0:
                return "Nunca";
            case 1:
                return "Todos os domingos";
            case 2:
                return "Todas as segundas-feiras";
            case 3:
                return "Todas as terças-feiras";
            case 4:
                return "Todas as quartas-feiras";
            case 5:
                return "Todas as quintas-feiras";
            case 6:
                return "Todas as sextas-feiras";
            case 7:
                return "Todos os sabados";
            case 8:
                return "Todos os dias";
            default:
                return "";
        }
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
