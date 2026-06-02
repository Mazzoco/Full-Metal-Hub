package rpg.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("⚔ Sistema RPG de Mesa");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        getContentPane().setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());

        // Header
        JPanel header = buildHeader();
        add(header, BorderLayout.NORTH);

        // Tabs
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.setBackground(Theme.BG_PANEL);
        tabs.setForeground(Theme.TEXT_PRIMARY);
        tabs.setFont(Theme.fontBody(14));
        tabs.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        tabs.addTab("👤  Personagens", new PersonagemPanel());
        tabs.addTab("🐾  Crias",        new CriaPanel());
        tabs.addTab("🔧  Oficina",      new OficinaPanel());
        tabs.addTab("🎒  Inventário",   new InventarioPanel());

        // Tab styling
        UIManager.put("TabbedPane.selected",            Theme.BG_CARD);
        UIManager.put("TabbedPane.background",          Theme.BG_PANEL);
        UIManager.put("TabbedPane.foreground",          Theme.TEXT_PRIMARY);
        UIManager.put("TabbedPane.contentAreaColor",    Theme.BG_PANEL);
        UIManager.put("TabbedPane.tabInsets",           new Insets(10, 14, 10, 14));

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Theme.BG_PANEL);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Theme.ACCENT));
        p.setPreferredSize(new Dimension(0, 52));

        JLabel title = new JLabel("  ⚔  SISTEMA RPG DE MESA");
        title.setFont(Theme.fontTitle(18));
        title.setForeground(Theme.ACCENT);
        p.add(title, BorderLayout.WEST);

        JLabel sub = new JLabel("Gerenciador de Fichas & Crias  ");
        sub.setFont(Theme.fontBody(12));
        sub.setForeground(Theme.TEXT_MUTED);
        p.add(sub, BorderLayout.EAST);

        return p;
    }
}
