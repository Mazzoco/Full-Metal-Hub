package rpg.repository;

import java.util.*;

public class Repository<T> {
    private final List<T> dados = new ArrayList<>();

    public void adicionar(T item) {
        dados.add(item);
    }

    public List<T> listar() {
        return Collections.unmodifiableList(dados);
    }

    public void remover(T item) {
        dados.remove(item);
    }

    public void atualizar(int index, T item) {
        if (index >= 0 && index < dados.size()) {
            dados.set(index, item);
        }
    }

    public int tamanho() {
        return dados.size();
    }

    public T get(int index) {
        return dados.get(index);
    }
}
