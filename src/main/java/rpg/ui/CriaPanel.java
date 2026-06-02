package rpg.ui;

import rpg.model.Cria;
import rpg.service.AppService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CriaPanel extends JPanel {
    private final AppService svc = AppService.getInstance();
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtNome, txtTipo;
    private JTextArea txtPersonalidade;
    private JComboBox<Cria.Chassi> cmbChassi;

    public CriaPanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(buildTitle(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildForm(), BorderLayout.EAST);
    }

    private JPanel buildTitle() {
        JPanel p = Theme.makeDarkPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel t = Theme.makeTitleLabel("🐾 Criador de Crias");
        p.add(t);
        p.add(Theme.makeLabel("  Crie e gerencie suas criaturas"));
        return p;
    }

    private JScrollPane buildTable() {
        String[] cols = {"Nome", "Chassi", "Tipo", "Personalidade"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        Theme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherForm();
        });
        refreshTable();
        JScrollPane sp = Theme.makeScroll(table);
        sp.setPreferredSize(new Dimension(600, 0));
        return sp;
    }

    private JPanel buildForm() {
        JPanel card = Theme.makeCard();
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(290, 0));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 5, 5, 5);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0; g.weightx = 1;

        int row = 0;
        JLabel title = new JLabel("Dados da Cria");
        title.setFont(Theme.fontTitle(15));
        title.setForeground(Theme.ACCENT);
        g.gridy = row++; card.add(title, g);

        g.gridy = row++; card.add(Theme.makeLabel("Nome"), g);
        txtNome = Theme.makeField();
        g.gridy = row++; card.add(txtNome, g);

        g.gridy = row++; card.add(Theme.makeLabel("Chassi"), g);
        cmbChassi = Theme.makeCombo();
        for (Cria.Chassi c : Cria.Chassi.values()) cmbChassi.addItem(c);
        g.gridy = row++; card.add(cmbChassi, g);

        g.gridy = row++; card.add(Theme.makeLabel("Tipo"), g);
        txtTipo = Theme.makeField();
        g.gridy = row++; card.add(txtTipo, g);

        g.gridy = row++; card.add(Theme.makeLabel("Personalidade"), g);
        txtPersonalidade = Theme.makeArea(4, 20);
        g.gridy = row++; card.add(Theme.makeScroll(txtPersonalidade), g);

        JPanel spacer = new JPanel(); spacer.setOpaque(false);
        g.gridy = row++; g.weighty = 1; card.add(spacer, g);
        g.weighty = 0;

        JPanel btns = new JPanel(new GridLayout(3, 1, 4, 4));
        btns.setOpaque(false);
        JButton btnCriar   = Theme.makeButton("＋ Criar",   Theme.SUCCESS);
        JButton btnEditar  = Theme.makeButton("✎ Editar",  Theme.ACCENT2);
        JButton btnExcluir = Theme.makeButton("✕ Excluir", Theme.DANGER);
        btnCriar.addActionListener(e -> criar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());
        btns.add(btnCriar); btns.add(btnEditar); btns.add(btnExcluir);
        g.gridy = row++; card.add(btns, g);

        JButton btnVer = Theme.makeButton("🔍 Visualizar", Theme.ACCENT);
        btnVer.addActionListener(e -> visualizar());
        g.gridy = row++; card.add(btnVer, g);

        return card;
    }

    private void criar() {
        if (txtNome.getText().isBlank()) { warn("Nome é obrigatório."); return; }
        Cria c = new Cria(
            txtNome.getText().trim(),
            (Cria.Chassi) cmbChassi.getSelectedItem(),
            txtPersonalidade.getText().trim(),
            txtTipo.getText().trim()
        );
        svc.getCrias().adicionar(c);
        limpar(); refreshTable();
        msg("Cria criada com sucesso!");
    }

    private void editar() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Selecione uma Cria."); return; }
        if (txtNome.getText().isBlank()) { warn("Nome é obrigatório."); return; }
        Cria c = svc.getCrias().get(row);
        c.setNome(txtNome.getText().trim());
        c.setChassi((Cria.Chassi) cmbChassi.getSelectedItem());
        c.setTipo(txtTipo.getText().trim());
        c.setPersonalidade(txtPersonalidade.getText().trim());
        refreshTable(); msg("Cria atualizada!");
    }

    private void excluir() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Selecione uma Cria."); return; }
        int ok = JOptionPane.showConfirmDialog(this, "Excluir esta Cria?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            Cria c = svc.getCrias().get(row);
            svc.removerOficina(c.getId());
            svc.getCrias().remover(c);
            limpar(); refreshTable();
        }
    }

    private void visualizar() {
        int row = table.getSelectedRow();
        if (row < 0) { warn("Selecione uma Cria."); return; }
        Cria c = svc.getCrias().get(row);
        String info = String.format(
            "<html><b style='color:#B48230'>%s</b><br><br>" +
            "<b>Chassi:</b> %s<br><b>Tipo:</b> %s<br>" +
            "<b>Personalidade:</b><br>%s</html>",
            c.getNome(), c.getChassi(), c.getTipo(), c.getPersonalidade()
        );
        JOptionPane.showMessageDialog(this, info, "Cria: " + c.getNome(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void preencherForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Cria c = svc.getCrias().get(row);
        txtNome.setText(c.getNome());
        cmbChassi.setSelectedItem(c.getChassi());
        txtTipo.setText(c.getTipo());
        txtPersonalidade.setText(c.getPersonalidade());
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Cria c : svc.getCrias().listar()) {
            tableModel.addRow(new Object[]{c.getNome(), c.getChassi(), c.getTipo(),
                c.getPersonalidade() != null && c.getPersonalidade().length() > 40
                    ? c.getPersonalidade().substring(0, 40) + "…" : c.getPersonalidade()});
        }
    }

    private void limpar() {
        txtNome.setText(""); txtTipo.setText(""); txtPersonalidade.setText("");
        cmbChassi.setSelectedIndex(0); table.clearSelection();
    }

    private void msg(String m)  { JOptionPane.showMessageDialog(this, m, "OK",  JOptionPane.INFORMATION_MESSAGE); }
    private void warn(String m) { JOptionPane.showMessageDialog(this, m, "Atenção", JOptionPane.WARNING_MESSAGE); }
}
