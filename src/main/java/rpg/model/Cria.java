package rpg.model;

import java.util.UUID;

public class Cria {
    public enum Chassi {
        SHOTO, SHOOTER, BEAST, LANCER
    }

    private String id;
    private String nome;
    private Chassi chassi;
    private String personalidade;
    private String tipo;
    private String caminhoImagem;

    public Cria() {
        this.id = UUID.randomUUID().toString();
    }

    public Cria(String nome, Chassi chassi, String personalidade, String tipo) {
        this();
        this.nome = nome;
        this.chassi = chassi;
        this.personalidade = personalidade;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Chassi getChassi() {
        return chassi;
    }

    public void setChassi(Chassi chassi) {
        this.chassi = chassi;
    }

    public String getPersonalidade() {
        return personalidade;
    }

    public void setPersonalidade(String personalidade) {
        this.personalidade = personalidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaminhoImagem() {
    return caminhoImagem;
}

    public void setCaminhoImagem(String caminhoImagem) {
    this.caminhoImagem = caminhoImagem;
}

    @Override
    public String toString() {
        return nome + " [" + (chassi != null ? chassi.name() : "?") + "]";
    }
}