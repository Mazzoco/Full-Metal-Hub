package rpg.model;

import java.util.UUID;

public class Peca {
    public enum Slot { MEMBRO_SUPERIOR_ESQ, MEMBRO_SUPERIOR_DIR, MEMBRO_INFERIOR_ESQ, MEMBRO_INFERIOR_DIR, EXTRA }

    private String id;
    private String nome;
    private String descricao;
    private Slot slot;
    private int quantidade;

    public Peca() {
        this.id = UUID.randomUUID().toString();
    }

    public Peca(String nome, String descricao, Slot slot, int quantidade) {
        this();
        this.nome = nome;
        this.descricao = descricao;
        this.slot = slot;
        this.quantidade = quantidade;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Slot getSlot() { return slot; }
    public void setSlot(Slot slot) { this.slot = slot; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    @Override
    public String toString() { return nome; }
}
