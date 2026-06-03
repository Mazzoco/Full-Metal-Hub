package rpg.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class Theme {

    public static final Color BG_DARK  = new Color(8, 10, 15);
    public static final Color BG_PANEL = new Color(15, 20, 30);
    public static final Color BG_CARD  = new Color(22, 28, 40);
    public static final Color BG_INPUT = new Color(30, 38, 55);

    public static final Color ACCENT  = new Color(255, 196, 60);
    public static final Color ACCENT2 = new Color(0, 180, 216);

    public static final Color SUCCESS = new Color(46, 204, 113);
    public static final Color DANGER  = new Color(231, 76, 60);

    public static final Color TEXT_LIGHT   = new Color(245, 245, 245);
    public static final Color TEXT_LIGHT_2 = new Color(180, 190, 210);

    public static final Color TEXT_PRIMARY = TEXT_LIGHT;
    public static final Color TEXT_MUTED   = TEXT_LIGHT_2;

    public static final Color BORDER = new Color(50, 60, 80);

    public static Font fontTitle(float size) {
        return new Font("Segoe UI", Font.BOLD, (int) size);
    }

    public static Font fontBody(float size) {
        return new Font("Segoe UI", Font.PLAIN, (int) size);
    }

    public static Font fontMono(float size) {
        return new Font("Consolas", Font.PLAIN, (int) size);
    }

    public static JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);

        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);

        btn.setFont(fontBody(13).deriveFont(Font.BOLD));

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        btn.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        btn.setBorder(
                BorderFactory.createEmptyBorder(
                        8, 18, 8, 18
                )
        );

        return btn;
    }

    public static JTextField makeField() {
        JTextField field = new JTextField();
        styleInput(field);
        return field;
    }

    public static JTextArea makeArea(int rows, int cols) {
        JTextArea area = new JTextArea(rows, cols);

        styleInput(area);

        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        return area;
    }

    private static void styleInput(JComponent c) {

        c.setBackground(BG_INPUT);
        c.setForeground(TEXT_PRIMARY);

        c.setFont(fontBody(13));

        c.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        BorderFactory.createEmptyBorder(
                                6, 10, 6, 10
                        )
                )
        );

        if (c instanceof javax.swing.text.JTextComponent tc) {
            tc.setCaretColor(ACCENT);
        }
    }

    public static <T> JComboBox<T> makeCombo() {

        JComboBox<T> combo = new JComboBox<>();

        combo.setBackground(BG_INPUT);
        combo.setForeground(TEXT_PRIMARY);

        combo.setFont(fontBody(13));

        combo.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        combo.setFocusable(false);

        combo.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(
                    JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {

                JLabel label =
                        (JLabel) super.getListCellRendererComponent(
                                list,
                                value,
                                index,
                                isSelected,
                                cellHasFocus
                        );

                if (isSelected) {

                    label.setBackground(ACCENT2);
                    label.setForeground(Color.WHITE);

                } else {

                    label.setBackground(BG_CARD);
                    label.setForeground(TEXT_PRIMARY);
                }

                return label;
            }
        });

        return combo;
    }

    public static JLabel makeLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(TEXT_MUTED);
        label.setFont(fontBody(12));

        return label;
    }

    public static JLabel makeTitleLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(ACCENT);
        label.setFont(fontTitle(20));

        return label;
    }

    public static Border cardBorder() {

        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(
                        12, 14, 12, 14
                )
        );
    }

    public static JPanel makeCard() {

        JPanel panel = new JPanel();

        panel.setBackground(BG_CARD);
        panel.setBorder(cardBorder());

        return panel;
    }

    public static JPanel makeDarkPanel() {

        JPanel panel = new JPanel();

        panel.setBackground(BG_PANEL);

        return panel;
    }

    public static void styleTable(JTable table) {

        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);

        table.setFont(fontBody(13));

        table.setGridColor(BORDER);

        table.setRowHeight(30);

        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(Color.BLACK);

        JTableHeader header = table.getTableHeader();

        header.setBackground(BG_PANEL);
        header.setForeground(ACCENT);

        header.setFont(
                fontBody(12).deriveFont(Font.BOLD)
        );
    }

    public static void applyDark(JComponent c) {

        c.setBackground(BG_DARK);
        c.setForeground(TEXT_PRIMARY);
    }

    public static JScrollPane makeScroll(Component c) {

        JScrollPane sp = new JScrollPane(c);

        sp.setBackground(BG_PANEL);

        sp.getViewport().setBackground(BG_CARD);

        sp.setBorder(
                BorderFactory.createLineBorder(BORDER)
        );

        return sp;
    }
}