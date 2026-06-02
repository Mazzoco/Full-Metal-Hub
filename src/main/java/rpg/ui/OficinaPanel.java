package rpg.ui;

import rpg.model.*;
import rpg.service.AppService;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class OficinaPanel extends JPanel {
    private final AppService svc = AppService.getInstance();

    private JComboBox<Cria> cmbCria;
    private JPanel slotsPanel;
    private Oficina oficina;
    private JLabel lblChassi;

    public OficinaPanel() {
        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        add(buildTop(), BorderLayout.NORTH);
        slotsPanel = new JPanel();
        slotsPanel.setBackground(Theme.BG_DARK);
        add(slotsPanel, BorderLayout.CENTER);
        add(buildRight(), BorderLayout.EAST);
    }

    private JPanel buildTop() {
        JPanel p = Theme.makeDarkPanel();
        p.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 8));

        p.add(Theme.makeTitleLabel("🔧 Oficina de Crias"));

        p.add(Theme.makeLabel("  Cria:"));
        cmbCria = Theme.makeCombo();
        cmbCria.setPreferredSize(new Dimension(200, 30));
        refreshCriaCombo();
        p.add(cmbCria);

        JButton btnCarregar = Theme.makeButton("Carregar", Theme.ACCENT2);
        btnCarregar.addActionListener(e -> carregarOficina());
        p.add(btnCarregar);

        lblChassi = Theme.makeLabel("");
        lblChassi.setFont(Theme.fontBody(12).deriveFont(Font.BOLD));
        lblChassi.setForeground(Theme.ACCENT);
        p.add(lblChassi);

        JButton btnSlotExtra = Theme.makeButton("＋ Slot Extra", Theme.TEXT_MUTED);
        btnSlotExtra.setForeground(Theme.BG_DARK);
        btnSlotExtra.addActionListener(e -> adicionarSlotExtra());
        p.add(btnSlotExtra);

        return p;
    }

    private JPanel buildRight() {
        JPanel card = Theme.makeCard();
        card.setPreferredSize(new Dimension(220, 0));
        card.setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6, 4, 6, 4);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.gridx = 0; g.weightx = 1;

        JLabel t = new JLabel("Peças do Inventário");
        t.setFont(Theme.fontTitle(13));
        t.setForeground(Theme.ACCENT);
        g.gridy = 0; card.add(t, g);

        JLabel hint = Theme.makeLabel("Selecione um slot e uma peça para equipar.");
        hint.setFont(Theme.fontBody(11));
        g.gridy = 1; card.add(hint, g);

        return card;
    }

    private void carregarOficina() {
        Cria cria = (Cria) cmbCria.getSelectedItem();
        if (cria == null) {
            warn("Nenhuma Cria disponível. Crie uma primeiro.");
            return;
        }
        oficina = svc.getOficina(cria);
        lblChassi.setText("  Chassi: " + cria.getChassi());
        renderSlots();
    }

    private void renderSlots() {
        slotsPanel.removeAll();
        if (oficina == null) { slotsPanel.revalidate(); slotsPanel.repaint(); return; }

        slotsPanel.setLayout(new GridBagLayout());
        slotsPanel.setBackground(Theme.BG_DARK);

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.BOTH;
        g.weightx = 1; g.weighty = 1;

        // Section header
        Map<String, Peca> slots = oficina.getSlots();
        java.util.List<String> slotNames = new ArrayList<>(slots.keySet());

        int col = 0, row = 0;
        // Group: superior
        g.gridx = 0; g.gridy = 0; g.gridwidth = 2;
        JLabel hSup = Theme.makeLabel("▸ MEMBROS SUPERIORES");
        hSup.setFont(Theme.fontBody(11).deriveFont(Font.BOLD));
        hSup.setForeground(Theme.ACCENT2);
        slotsPanel.add(hSup, g);
        g.gridwidth = 1; row = 1;

        for (String slotName : slotNames) {
            if (!slotName.toLowerCase().contains("superior")) continue;
            JPanel slotCard = buildSlotCard(slotName, slots.get(slotName));
            g.gridx = col % 2; g.gridy = row + col / 2;
            slotsPanel.add(slotCard, g);
            col++;
        }

        int nextRow = row + (col + 1) / 2;

        // Group: inferior
        g.gridx = 0; g.gridy = nextRow; g.gridwidth = 2;
        JLabel hInf = Theme.makeLabel("▸ MEMBROS INFERIORES");
        hInf.setFont(Theme.fontBody(11).deriveFont(Font.BOLD));
        hInf.setForeground(Theme.ACCENT2);
        slotsPanel.add(hInf, g);
        g.gridwidth = 1;

        col = 0;
        for (String slotName : slotNames) {
            if (!slotName.toLowerCase().contains("inferior")) continue;
            JPanel slotCard = buildSlotCard(slotName, slots.get(slotName));
            g.gridx = col % 2; g.gridy = nextRow + 1 + col / 2;
            slotsPanel.add(slotCard, g);
            col++;
        }

        int extraRow = nextRow + 1 + (col + 1) / 2;
        col = 0;
        boolean hasExtra = false;
        for (String slotName : slotNames) {
            if (slotName.toLowerCase().contains("superior") || slotName.toLowerCase().contains("inferior")) continue;
            if (!hasExtra) {
                g.gridx = 0; g.gridy = extraRow; g.gridwidth = 2;
                JLabel hEx = Theme.makeLabel("▸ SLOTS EXTRAS");
                hEx.setFont(Theme.fontBody(11).deriveFont(Font.BOLD));
                hEx.setForeground(Theme.ACCENT);
                slotsPanel.add(hEx, g);
                g.gridwidth = 1;
                extraRow++;
                hasExtra = true;
            }
            JPanel slotCard = buildSlotCard(slotName, slots.get(slotName));
            g.gridx = col % 2; g.gridy = extraRow + col / 2;
            slotsPanel.add(slotCard, g);
            col++;
        }

        // Spacer
        JPanel sp = new JPanel(); sp.setOpaque(false);
        g.gridx = 0; g.gridy = 99; g.gridwidth = 2; g.weighty = 5;
        slotsPanel.add(sp, g);

        slotsPanel.revalidate();
        slotsPanel.repaint();
    }

    private JPanel buildSlotCard(String slotName, Peca peca) {
        JPanel card = Theme.makeCard();
        card.setLayout(new BorderLayout(6, 6));
        card.setPreferredSize(new Dimension(220, 100));

        JLabel lblSlot = Theme.makeLabel(slotName);
        lblSlot.setFont(Theme.fontBody(11).deriveFont(Font.BOLD));
        card.add(lblSlot, BorderLayout.NORTH);

        String pecaTxt = peca != null
            ? "⚙ " + peca.getNome()
            : "— vazio —";
        JLabel lblPeca = new JLabel(pecaTxt);
        lblPeca.setFont(Theme.fontMono(13));
        lblPeca.setForeground(peca != null ? Theme.SUCCESS : Theme.TEXT_MUTED);
        card.add(lblPeca, BorderLayout.CENTER);

        JPanel btns = new JPanel(new GridLayout(1, 2, 4, 0));
        btns.setOpaque(false);

        JButton btnEquipar = Theme.makeButton("Equipar", Theme.ACCENT2);
        btnEquipar.setFont(Theme.fontBody(11));
        btnEquipar.addActionListener(e -> equiparPeca(slotName));

        JButton btnRemover = Theme.makeButton("Remover", Theme.DANGER);
        btnRemover.setFont(Theme.fontBody(11));
        btnRemover.addActionListener(e -> { oficina.removerPeca(slotName); renderSlots(); });

        btns.add(btnEquipar);
        btns.add(btnRemover);
        card.add(btns, BorderLayout.SOUTH);
        return card;
    }

    private void equiparPeca(String slotName) {
        if (oficina == null) { warn("Carregue uma Cria primeiro."); return; }
        java.util.List<Peca> pecaList = svc.getPecas().listar();
        if (pecaList.isEmpty()) { warn("Nenhuma peça no inventário. Adicione peças na aba Inventário."); return; }

        Peca[] arr = pecaList.toArray(new Peca[0]);
        Peca escolha = (Peca) JOptionPane.showInputDialog(
            this, "Escolha uma peça para: " + slotName,
            "Equipar Peça", JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]
        );
        if (escolha != null) {
            oficina.equiparPeca(slotName, escolha);
            renderSlots();
        }
    }

    private void adicionarSlotExtra() {
        if (oficina == null) { warn("Carregue uma Cria primeiro."); return; }
        String nome = JOptionPane.showInputDialog(this, "Nome do novo slot extra:");
        if (nome != null && !nome.isBlank()) {
            oficina.adicionarSlotExtra(nome.trim());
            renderSlots();
        }
    }

    private void refreshCriaCombo() {
        cmbCria.removeAllItems();
        for (Cria c : svc.getCrias().listar()) cmbCria.addItem(c);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        refreshCriaCombo();
    }

    private void warn(String m) { JOptionPane.showMessageDialog(this, m, "Atenção", JOptionPane.WARNING_MESSAGE); }
}
