package model;

/**
 * Created by thiagoyf on 12/10/16.
 */

public class Livro {

    private int id;
    private String nome;
    private int totalPaginas;

    public Livro(){ }

    public Livro(int id, String nome, int totalPaginas){
        this.id = id;
        this.nome = nome;
        this.totalPaginas = totalPaginas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

}
