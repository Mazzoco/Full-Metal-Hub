package rpg.model;

import java.util.UUID;

public class Personagem {
    private String id;
    private String nome;
    private int idade;
    private String arquetipo;
    private String escola;

    public Personagem() {
        this.id = UUID.randomUUID().toString();
    }

    public Personagem(String nome, int idade, String arquetipo, String escola) {
        this();
        this.nome = nome;
        this.idade = idade;
        this.arquetipo = arquetipo;
        this.escola = escola;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }
    public String getArquetipo() { return arquetipo; }
    public void setArquetipo(String arquetipo) { this.arquetipo = arquetipo; }
    public String getEscola() { return escola; }
    public void setEscola(String escola) { this.escola = escola; }

    @Override
    public String toString() { return nome; }
}
