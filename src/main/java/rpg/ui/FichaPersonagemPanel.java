package rpg.ui;

import javax.swing.*;
import java.awt.*;

public class FichaPersonagemPanel extends JPanel {

    public FichaPersonagemPanel() {

        setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout(15,15));

        JLabel titulo = Theme.makeTitleLabel("📜 Ficha de Personagem");

        JPanel top = Theme.makeDarkPanel();
        top.setLayout(new FlowLayout(FlowLayout.LEFT));
        top.add(titulo);

        add(top, BorderLayout.NORTH);

        JPanel ficha = Theme.makeCard();

        ficha.setLayout(new GridBagLayout());

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5,5,5,5);
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JTextField txtNome = Theme.makeField();
        JTextField txtIdade = Theme.makeField();
        JTextField txtArquetipo = Theme.makeField();
        JTextField txtEscola = Theme.makeField();

        JTextField txtHP = Theme.makeField();
        JTextField txtEnergia = Theme.makeField();

        JTextField txtForca = Theme.makeField();
        JTextField txtAgilidade = Theme.makeField();
        JTextField txtInteligencia = Theme.makeField();
        JTextField txtDefesa = Theme.makeField();

        JTextArea txtHistoria = Theme.makeArea(6,20);
        JTextArea txtEquipamentos = Theme.makeArea(6,20);

        int row = 0;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(Theme.makeLabel("Nome"), g);

        g.gridx = 1;
        ficha.add(Theme.makeLabel("Idade"), g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(txtNome, g);

        g.gridx = 1;
        ficha.add(txtIdade, g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(Theme.makeLabel("Arquétipo"), g);

        g.gridx = 1;
        ficha.add(Theme.makeLabel("Escola"), g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(txtArquetipo, g);

        g.gridx = 1;
        ficha.add(txtEscola, g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(Theme.makeLabel("HP"), g);

        g.gridx = 1;
        ficha.add(Theme.makeLabel("Energia"), g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(txtHP, g);

        g.gridx = 1;
        ficha.add(txtEnergia, g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(Theme.makeLabel("Força"), g);

        g.gridx = 1;
        ficha.add(Theme.makeLabel("Agilidade"), g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(txtForca, g);

        g.gridx = 1;
        ficha.add(txtAgilidade, g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(Theme.makeLabel("Inteligência"), g);

        g.gridx = 1;
        ficha.add(Theme.makeLabel("Defesa"), g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(txtInteligencia, g);

        g.gridx = 1;
        ficha.add(txtDefesa, g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(Theme.makeLabel("História"), g);

        g.gridx = 1;
        ficha.add(Theme.makeLabel("Equipamentos"), g);

        row++;

        g.gridx = 0;
        g.gridy = row;
        ficha.add(new JScrollPane(txtHistoria), g);

        g.gridx = 1;
        ficha.add(new JScrollPane(txtEquipamentos), g);

        add(ficha, BorderLayout.CENTER);
    }
}