package rpg.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("⚔ Full Metal Cria RPG");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1100, 720);
        setMinimumSize(new Dimension(900, 600));
        setLocationRelativeTo(null);

        getContentPane().setBackground(Theme.BG_DARK);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);

        JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);

        tabs.setFont(Theme.fontBody(15));
        tabs.setBackground(Theme.BG_PANEL);
        tabs.setForeground(Color.WHITE);

        tabs.setUI(new BasicTabbedPaneUI() {

            @Override
            protected void paintTabBackground(
                    Graphics g,
                    int tabPlacement,
                    int tabIndex,
                    int x,
                    int y,
                    int w,
                    int h,
                    boolean isSelected) {

                Graphics2D g2 = (Graphics2D) g;

                g2.setColor(
                        isSelected
                                ? Theme.ACCENT
                                : Theme.BG_CARD
                );

                g2.fillRect(x, y, w, h);
            }

            @Override
            protected void paintText(
                    Graphics g,
                    int tabPlacement,
                    Font font,
                    FontMetrics metrics,
                    int tabIndex,
                    String title,
                    Rectangle textRect,
                    boolean isSelected) {

                Graphics2D g2 = (Graphics2D) g;

                g2.setFont(font.deriveFont(Font.BOLD));

                g2.setColor(
                        isSelected
                                ? Color.BLACK
                                : Color.WHITE
                );

                g2.drawString(
                        title,
                        textRect.x,
                        textRect.y + metrics.getAscent()
                );
            }

            @Override
            protected Insets getTabInsets(
                    int tabPlacement,
                    int tabIndex) {

                return new Insets(
                        15,
                        20,
                        15,
                        20
                );
            }

            @Override
            protected void paintFocusIndicator(
                    Graphics g,
                    int tabPlacement,
                    Rectangle[] rects,
                    int tabIndex,
                    Rectangle iconRect,
                    Rectangle textRect,
                    boolean isSelected) {
                // Remove borda de foco
            }
        });

        tabs.addTab("👤  Personagens", new PersonagemPanel());
        tabs.addTab("🐾  Crias", new CriaPanel());
        tabs.addTab("📜 Ficha", new FichaPersonagemPanel());
        tabs.addTab("🎒  Inventário", new InventarioPanel());

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel buildHeader() {

        JPanel header = new JPanel(new BorderLayout());

        header.setBackground(Theme.BG_PANEL);

        header.setBorder(
                BorderFactory.createMatteBorder(
                        0,
                        0,
                        2,
                        0,
                        Theme.ACCENT
                )
        );

        header.setPreferredSize(
                new Dimension(
                        0,
                        60
                )
        );

        JLabel title = new JLabel("  FULL METAL CRIA RPG");

        title.setFont(
                Theme.fontTitle(20)
        );

        title.setForeground(
                Theme.ACCENT
        );

        JLabel subtitle = new JLabel(
                "Gerenciador de Fichas & Crias  "
        );

        subtitle.setFont(
                Theme.fontBody(13)
        );

        subtitle.setForeground(
                Theme.TEXT_LIGHT
        );

        header.add(
                title,
                BorderLayout.WEST
        );

        header.add(
                subtitle,
                BorderLayout.EAST
        );

        return header;
    }
}