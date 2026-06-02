package rpg.service;

import rpg.model.*;
import rpg.repository.Repository;

import java.util.*;

public class AppService {
    private static AppService instance;

    private final Repository<Personagem> personagens = new Repository<>();
    private final Repository<Cria> crias = new Repository<>();
    private final Repository<Peca> pecas = new Repository<>();
    private final Repository<ItemInventario> inventario = new Repository<>();
    private final Map<String, Oficina> oficinas = new HashMap<>(); // criaid -> oficina

    private AppService() {}

    public static AppService getInstance() {
        if (instance == null) instance = new AppService();
        return instance;
    }

    // Personagens
    public Repository<Personagem> getPersonagens() { return personagens; }

    // Crias
    public Repository<Cria> getCrias() { return crias; }

    // Peças
    public Repository<Peca> getPecas() { return pecas; }

    // Inventário
    public Repository<ItemInventario> getInventario() { return inventario; }

    // Oficina
    public Oficina getOficina(Cria cria) {
        return oficinas.computeIfAbsent(cria.getId(), k -> new Oficina(cria));
    }

    public void removerOficina(String criaId) {
        oficinas.remove(criaId);
    }
}
