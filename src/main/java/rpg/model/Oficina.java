package rpg.model;

import java.util.HashMap;
import java.util.Map;

public class Oficina {
    private Cria cria;
    // slot label -> Peca equipada
    private Map<String, Peca> slots;

    public Oficina(Cria cria) {
        this.cria = cria;
        this.slots = new HashMap<>();
        initDefaultSlots();
    }

    private void initDefaultSlots() {
        slots.put("Membro Superior Esq.", null);
        slots.put("Membro Superior Dir.", null);
        slots.put("Membro Inferior Esq.", null);
        slots.put("Membro Inferior Dir.", null);
    }

    public void adicionarSlotExtra(String nome) {
        slots.put(nome, null);
    }

    public void equiparPeca(String slotNome, Peca peca) {
        if (slots.containsKey(slotNome)) {
            slots.put(slotNome, peca);
        }
    }

    public void removerPeca(String slotNome) {
        if (slots.containsKey(slotNome)) {
            slots.put(slotNome, null);
        }
    }

    public Map<String, Peca> getSlots() { return slots; }
    public Cria getCria() { return cria; }
    public void setCria(Cria cria) { this.cria = cria; }

    public Peca getPecaNoSlot(String slot) {
        return slots.get(slot);
    }
}
