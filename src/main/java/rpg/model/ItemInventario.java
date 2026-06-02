package rpg.model;

import java.util.UUID;

public class ItemInventario {
    public enum Categoria { FERRAMENTA, PECA, ITEM, ITEM_CHAVE }

    private String id;
    private String nome;
    private String descricao;
    private Categoria categoria;
    private int quantidade;

    public ItemInventario() {
        this.id = UUID.randomUUID().toString();
    }

    public ItemInventario(String nome, String descricao, Categoria categoria, int quantidade) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() { return nome + " (" + categoria + ") x" + quantidade; }
}
