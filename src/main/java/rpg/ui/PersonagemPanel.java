package rpg.ui;

import rpg.model.Personagem;
import rpg.service.AppService;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class PersonagemPanel extends JPanel {
    private final AppService svc = AppService.getInstance();
    private DefaultTableModel tableModel;
    private JTable table;

    private JTextField txtNome, txtIdade, txtArquetipo, txtEscola;
    private JLabel lblImagem;
    private JButton btnSelecionarImagem;
    private String imagemSelecionada;

    public PersonagemPanel() {
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
        p.add(Theme.makeTitleLabel("👤 Personagens"));
        JLabel sub = Theme.makeLabel("  Crie e gerencie suas fichas de personagem");
        p.add(sub);
        return p;
    }

    private JScrollPane buildTable() {
        String[] cols = {"Nome", "Idade", "Arquétipo", "Escola"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        Theme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) preencherFormulario();
        });

        refreshTable();
        JScrollPane sp = Theme.makeScroll(table);
        sp.setPreferredSize(new Dimension(600, 0));
        return sp;
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
        JLabel formTitle = new JLabel("Ficha de Personagem");
        formTitle.setFont(Theme.fontTitle(15));
        formTitle.setForeground(Theme.ACCENT);
        g.gridy = row++; card.add(formTitle, g);

        g.gridy = row++; card.add(Theme.makeLabel("Nome"), g);
        txtNome = Theme.makeField();
        g.gridy = row++; card.add(txtNome, g);

        g.gridy = row++; card.add(Theme.makeLabel("Idade"), g);
        txtIdade = Theme.makeField();
        g.gridy = row++; card.add(txtIdade, g);

        g.gridy = row++; card.add(Theme.makeLabel("Arquétipo"), g);
        txtArquetipo = Theme.makeField();
        g.gridy = row++; card.add(txtArquetipo, g);

        g.gridy = row++; card.add(Theme.makeLabel("Escola"), g);
        txtEscola = Theme.makeField();
        g.gridy = row++; card.add(txtEscola, g);

        g.gridy = row++;
        card.add(Theme.makeLabel("Imagem"), g);
        lblImagem = new JLabel("Sem imagem", SwingConstants.CENTER);
        lblImagem.setPreferredSize(new Dimension(150,150));
        lblImagem.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        g.gridy = row++;
        card.add(lblImagem, g);
        btnSelecionarImagem =
        Theme.makeButton("Selecionar Imagem", Theme.ACCENT);
        btnSelecionarImagem.addActionListener(e -> selecionarImagem());
        g.gridy = row++;
        card.add(btnSelecionarImagem, g);

        // Spacer
        JPanel spacer = new JPanel(); spacer.setOpaque(false);
        g.gridy = row++; g.weighty = 1; card.add(spacer, g);
        g.weighty = 0;

        JPanel btnPanel = new JPanel(new GridLayout(3, 1, 4, 4));
        btnPanel.setOpaque(false);
        JButton btnCriar   = Theme.makeButton("＋ Criar",   Theme.SUCCESS);
        JButton btnEditar  = Theme.makeButton("✎ Editar",  Theme.ACCENT2);
        JButton btnExcluir = Theme.makeButton("✕ Excluir", Theme.DANGER);

        btnCriar.addActionListener(e -> criar());
        btnEditar.addActionListener(e -> editar());
        btnExcluir.addActionListener(e -> excluir());

        btnPanel.add(btnCriar);
        btnPanel.add(btnEditar);
        btnPanel.add(btnExcluir);

        g.gridy = row++; card.add(btnPanel, g);

        // View button
        JButton btnView = Theme.makeButton("🔍 Visualizar", Theme.ACCENT);
        btnView.setForeground(Theme.BG_DARK);
        btnView.addActionListener(e -> visualizar());
        g.gridy = row++; card.add(btnView, g);

        return card;
    }

    private void criar() {
        if (!validar()) return;
        Personagem p = new Personagem(
    txtNome.getText().trim(),
    parseIdade(),
    txtArquetipo.getText().trim(),
    txtEscola.getText().trim()
);
    p.setCaminhoImagem(imagemSelecionada);
        svc.getPersonagens().adicionar(p);
        limparForm();
        refreshTable();
        showMsg("Personagem criado com sucesso!");
    }

    private void editar() {
        int row = table.getSelectedRow();
        if (row < 0) { showWarn("Selecione um personagem."); return; }
        if (!validar()) return;
        Personagem p = svc.getPersonagens().get(row);
        p.setNome(txtNome.getText().trim());
        p.setIdade(parseIdade());
        p.setArquetipo(txtArquetipo.getText().trim());
        p.setEscola(txtEscola.getText().trim());
        p.setCaminhoImagem(imagemSelecionada);
        refreshTable();
        showMsg("Personagem atualizado!");
    }

    private void excluir() {
        int row = table.getSelectedRow();
        if (row < 0) { showWarn("Selecione um personagem."); return; }
        int confirm = JOptionPane.showConfirmDialog(this,
            "Excluir personagem selecionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            svc.getPersonagens().remover(svc.getPersonagens().get(row));
            limparForm();
            refreshTable();
        }
    }

    private void visualizar() {
        int row = table.getSelectedRow();
        if (row < 0) { showWarn("Selecione um personagem."); return; }
        Personagem p = svc.getPersonagens().get(row);
        String info = String.format(
            "<html><b style='color:#B48230'>%s</b><br><br>" +
            "<b>Idade:</b> %d<br>" +
            "<b>Arquétipo:</b> %s<br>" +
            "<b>Escola:</b> %s</html>",
            p.getNome(), p.getIdade(), p.getArquetipo(), p.getEscola()
        );
        JOptionPane.showMessageDialog(this, info, "Ficha de " + p.getNome(), JOptionPane.INFORMATION_MESSAGE);
    }

    private void selecionarImagem() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(
        new javax.swing.filechooser.FileNameExtensionFilter(
            "Imagens",
            "png",
            "jpg",
            "jpeg"
        )
    );
    int resultado = chooser.showOpenDialog(this);
    if(resultado == JFileChooser.APPROVE_OPTION){
        imagemSelecionada =
            chooser.getSelectedFile().getAbsolutePath();
        ImageIcon icon =
            new ImageIcon(imagemSelecionada);
        Image img =
            icon.getImage().getScaledInstance(
                150,
                150,
                Image.SCALE_SMOOTH
            );
        lblImagem.setIcon(new ImageIcon(img));
        lblImagem.setText("");
    }
}

    private void preencherFormulario() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        Personagem p = svc.getPersonagens().get(row);
        txtNome.setText(p.getNome());
        txtIdade.setText(String.valueOf(p.getIdade()));
        txtArquetipo.setText(p.getArquetipo());
        txtEscola.setText(p.getEscola());
        imagemSelecionada = p.getCaminhoImagem();
if(imagemSelecionada != null &&
   !imagemSelecionada.isBlank()) {
    ImageIcon icon =
        new ImageIcon(imagemSelecionada);
    Image img =
        icon.getImage().getScaledInstance(
            150,
            150,
            Image.SCALE_SMOOTH
        );
    lblImagem.setIcon(new ImageIcon(img));
    lblImagem.setText("");
} else {
    lblImagem.setIcon(null);
    lblImagem.setText("Sem imagem");
}
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Personagem p : svc.getPersonagens().listar()) {
            tableModel.addRow(new Object[]{p.getNome(), p.getIdade(), p.getArquetipo(), p.getEscola()});
        }
    }

    private void limparForm() {
        txtNome.setText(""); txtIdade.setText("");
        txtArquetipo.setText(""); txtEscola.setText("");
        table.clearSelection();
        imagemSelecionada = null;
        lblImagem.setIcon(null);
        lblImagem.setText("Sem imagem");
    }

    private boolean validar() {
        if (txtNome.getText().isBlank()) { showWarn("Nome é obrigatório."); return false; }
        try { Integer.parseInt(txtIdade.getText().trim()); }
        catch (NumberFormatException e) { showWarn("Idade deve ser um número."); return false; }
        return true;
    }

    private int parseIdade() {
        try { return Integer.parseInt(txtIdade.getText().trim()); }
        catch (Exception e) { return 0; }
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg, "OK", JOptionPane.INFORMATION_MESSAGE);
    }
    private void showWarn(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
}
