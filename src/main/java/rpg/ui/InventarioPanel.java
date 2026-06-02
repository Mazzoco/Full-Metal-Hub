package rpg.ui;

import rpg.model.*;
import rpg.service.AppService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class InventarioPanel extends JPanel {
    private final AppService svc = AppService.getInstance();
    private DefaultTableModel tableModel;
    private JTable table;
    private JComboBox<ItemInventario.Categoria> cmbFiltro;

    // Form fields
    private JTextField txtNome, txtQtd;
    private JTextArea txtDesc;
    private JComboBox<ItemInventario.Categoria> cmbCategoria;

    public InventarioPanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(buildTop(),   BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildForm(),  BorderLayout.EAST);
    }

    private JPanel buildTop() {
        JPanel p = Theme.makeDarkPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));
        p.add(Theme.makeTitleLabel("🎒 Inventário"));
        p.add(Theme.makeLabel("  Filtrar:"));

        cmbFiltro = Theme.makeCombo();
        cmbFiltro.addItem(null);
        for (ItemInventario.Categoria c : ItemInventario.Categoria.values()) cmbFiltro.addItem(c);
        cmbFiltro.setRenderer(new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object val, int idx, boolean sel, boolean focus) {
                super.getListCellRendererComponent(list, val, idx, sel, focus);
                setText(val == null ? "— Todos —" : val.toString());
                setBackground(sel ? Theme.ACCENT : Theme.BG_CARD);
                setForeground(sel ? Theme.BG_DARK : Theme.TEXT_PRIMARY);
                return this;
            }
        });
        cmbFiltro.setPreferredSize(new Dimension(160, 30));
        cmbFiltro.addActionListener(e -> refreshTable());
        p.add(cmbFiltro);

        // Quick-add Peca also to Peças repository
        JLabel hint = Theme.makeLabel("  * Itens do tipo PEÇA ficam disponíveis na Oficina.");
        p.add(hint);
        return p;
    }

    private JScrollPane buildTable() {
        String[] cols = {"Nome", "Categoria", "Qtd", "Descrição"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        Theme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherForm();
        });
        refreshTable();
        return Theme.makeScroll(table);
    }

    private JPanel buildForm() {
        JPanel card = Theme.makeCard();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(280, 0));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0; g.weightx = 1;

        int row = 0;
        JLabel t = new JLabel("Adicionar Item");
        t.setFont(Theme.fontTitle(15));
        t.setForeground(Theme.ACCENT);
        g.gridy = row++; card.add(t, g);

        g.gridy = row++; card.add(Theme.makeLabel("Nome"), g);
        txtNome = Theme.makeField();
        g.gridy = row++; card.add(txtNome, g);

        g.gridy = row++; card.add(Theme.makeLabel("Categoria"), g);
        cmbCategoria = Theme.makeCombo();
        for (ItemInventario.Categoria c : ItemInventario.Categoria.values()) cmbCategoria.addItem(c);
        cmbCategoria.setRenderer(buildCategoriaRenderer());
        g.gridy = row++; card.add(cmbCategoria, g);

        g.gridy = row++; card.add(Theme.makeLabel("Quantidade"), g);
        txtQtd = Theme.makeField();
        txtQtd.setText("1");
        g.gridy = row++; card.add(txtQtd, g);

        g.gridy = row++; card.add(Theme.makeLabel("Descrição"), g);
        txtDesc = Theme.makeArea(4, 20);
        g.gridy = row++; card.add(Theme.makeScroll(txtDesc), g);

        JPanel spacer = new JPanel(); spacer.setOpaque(false);
        g.gridy = row++; g.weighty = 1; card.add(spacer, g);
        g.weighty = 0;

        JPanel btns = new JPanel(new GridLayout(3, 1, 4, 4));
        btns.setOpaque(false);

        JButton btnAdicionar = Theme.makeButton("＋ Adicionar",   Theme.SUCCESS);
        JButton btnEditar    = Theme.makeButton("✎ Editar",       Theme.ACCENT2);
        JButton btnExcluir   = Theme.makeButton("✕ Remover",      Theme.DANGER);

        btnAdicionar.addActionListener(e -> adicionar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());

        btns.add(btnAdicionar); btns.add(btnEditar); btns.add(btnExcluir);
        g.gridy = row++; card.add(btns, g);

        // Quantity controls
        JPanel qtdCtrl = new JPanel(new GridLayout(1, 2, 4, 0));
        qtdCtrl.setOpaque(false);
        JButton btnMais  = Theme.makeButton("＋ Qtd", Theme.ACCENT);
        JButton btnMenos = Theme.makeButton("－ Qtd", Theme.TEXT_MUTED);
        btnMais.setForeground(Theme.BG_DARK);
        btnMenos.setForeground(Theme.BG_DARK);
        btnMais.addActionListener(e -> ajustarQtd(1));
        btnMenos.addActionListener(e -> ajustarQtd(-1));
        qtdCtrl.add(btnMais); qtdCtrl.add(btnMenos);
        g.gridy = row++; card.add(qtdCtrl, g);

        return card;
    }

    private DefaultListCellRenderer buildCategoriaRenderer() {
        return new DefaultListCellRenderer() {
            public Component getListCellRendererComponent(JList<?> list, Object val, int idx, boolean sel, boolean focus) {
                super.getListCellRendererComponent(list, val, idx, sel, focus);
                if (val instanceof ItemInventario.Categoria c) {
                    String icon = switch (c) {
                        case FERRAMENTA -> "🔨 ";
                        case PECA       -> "⚙ ";
                        case ITEM       -> "📦 ";
                        case ITEM_CHAVE -> "🗝 ";
                    };
                    setText(icon + c.toString());
                }
                setBackground(sel ? Theme.ACCENT : Theme.BG_CARD);
                setForeground(sel ? Theme.BG_DARK : Theme.TEXT_PRIMARY);
                return this;
            }
        };
    }

    private void adicionar() {
        if (!validar()) return;
        ItemInventario item = new ItemInventario(
            txtNome.getText().trim(),
            txtDesc.getText().trim(),
            (ItemInventario.Categoria) cmbCategoria.getSelectedItem(),
            parseQtd()
        );
        svc.getInventario().adicionar(item);
        // If it's a Peca, also add to pecas repository
        if (item.getCategoria() == ItemInventario.Categoria.PECA) {
            Peca p = new Peca(item.getNome(), item.getDescricao(), null, item.getQuantidade());
            svc.getPecas().adicionar(p);
        }
        limpar(); refreshTable();
        msg("Item adicionado!");
    }

    private void editar() {
        int row = getSelectedIndex();
        if (row < 0) { warn("Selecione um item."); return; }
        if (!validar()) return;
        ItemInventario item = getFilteredItem(row);
        if (item == null) return;
        item.setNome(txtNome.getText().trim());
        item.setCategoria((ItemInventario.Categoria) cmbCategoria.getSelectedItem());
        item.setQuantidade(parseQtd());
        item.setDescricao(txtDesc.getText().trim());
        refreshTable(); msg("Item atualizado!");
    }

    private void excluir() {
        int row = getSelectedIndex();
        if (row < 0) { warn("Selecione um item."); return; }
        int ok = JOptionPane.showConfirmDialog(this, "Remover este item?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            ItemInventario item = getFilteredItem(row);
            if (item != null) {
                svc.getInventario().remover(item);
                limpar(); refreshTable();
            }
        }
    }

    private void ajustarQtd(int delta) {
        int row = getSelectedIndex();
        if (row < 0) { warn("Selecione um item."); return; }
        ItemInventario item = getFilteredItem(row);
        if (item == null) return;
        int nova = Math.max(0, item.getQuantidade() + delta);
        item.setQuantidade(nova);
        refreshTable();
        // reselect row
        if (row < table.getRowCount()) {
            table.setRowSelectionInterval(row, row);
            preencherForm();
        }
    }

    private void preencherForm() {
        int row = getSelectedIndex();
        if (row < 0) return;
        ItemInventario item = getFilteredItem(row);
        if (item == null) return;
        txtNome.setText(item.getNome());
        cmbCategoria.setSelectedItem(item.getCategoria());
        txtQtd.setText(String.valueOf(item.getQuantidade()));
        txtDesc.setText(item.getDescricao());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        ItemInventario.Categoria filtro = (ItemInventario.Categoria) cmbFiltro.getSelectedItem();
        for (ItemInventario item : svc.getInventario().listar()) {
            if (filtro != null && item.getCategoria() != filtro) continue;
            String categoriaIcon = switch (item.getCategoria()) {
                case FERRAMENTA -> "🔨 " + item.getCategoria();
                case PECA       -> "⚙ "  + item.getCategoria();
                case ITEM       -> "📦 " + item.getCategoria();
                case ITEM_CHAVE -> "🗝 " + item.getCategoria();
            };
            String desc = item.getDescricao() != null && item.getDescricao().length() > 50
                ? item.getDescricao().substring(0, 50) + "…" : item.getDescricao();
            tableModel.addRow(new Object[]{item.getNome(), categoriaIcon, item.getQuantidade(), desc});
        }
    }

    private ItemInventario getFilteredItem(int tableRow) {
        ItemInventario.Categoria filtro = (ItemInventario.Categoria) cmbFiltro.getSelectedItem();
        int count = 0;
        for (ItemInventario item : svc.getInventario().listar()) {
            if (filtro != null && item.getCategoria() != filtro) continue;
            if (count == tableRow) return item;
            count++;
        }
        return null;
    }

    private int getSelectedIndex() { return table.getSelectedRow(); }

    private boolean validar() {
        if (txtNome.getText().isBlank()) { warn("Nome é obrigatório."); return false; }
        try { Integer.parseInt(txtQtd.getText().trim()); }
        catch (NumberFormatException e) { warn("Quantidade deve ser um número."); return false; }
        return true;
    }

    private int parseQtd() {
        try { return Math.max(0, Integer.parseInt(txtQtd.getText().trim())); }
        catch (Exception e) { return 0; }
    }

    private void limpar() {
        txtNome.setText(""); txtQtd.setText("1"); txtDesc.setText("");
        cmbCategoria.setSelectedIndex(0); table.clearSelection();
    }

    private void msg(String m)  { JOptionPane.showMessageDialog(this, m, "OK",      JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m) { JOptionPane.showMessageDialog(this, m, "Atenção", JOptionPane.WARNING_MESSAGE); }
}
