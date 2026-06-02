package rpg.ui;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Theme {
    // Colors
    public static final Color BG_DARK      = new Color(15, 15, 20);
    public static final Color BG_PANEL     = new Color(22, 24, 35);
    public static final Color BG_CARD      = new Color(30, 33, 48);
    public static final Color ACCENT       = new Color(180, 130, 60);   // amber/gold
    public static final Color ACCENT2      = new Color(100, 180, 200);  // teal
    public static final Color TEXT_PRIMARY = new Color(230, 220, 200);
    public static final Color TEXT_MUTED   = new Color(130, 125, 115);
    public static final Color DANGER       = new Color(200, 70, 70);
    public static final Color SUCCESS      = new Color(70, 180, 100);
    public static final Color BORDER       = new Color(50, 55, 75);

    // Fonts
    public static Font fontTitle(float size) {
        return new Font("Serif", Font.BOLD, (int) size);
    }
    public static Font fontBody(float size) {
        return new Font("SansSerif", Font.PLAIN, (int) size);
    }
    public static Font fontMono(float size) {
        return new Font("Monospaced", Font.PLAIN, (int) size);
    }

    // Builders
    public static JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(BG_DARK);
        btn.setFont(fontBody(13).deriveFont(Font.BOLD));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(8, 18, 8, 18));
        return btn;
    }

    public static JTextField makeField() {
        JTextField f = new JTextField();
        styleInput(f);
        return f;
    }

    public static JTextArea makeArea(int rows, int cols) {
        JTextArea a = new JTextArea(rows, cols);
        styleInput(a);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        return a;
    }

    public static <T> JComboBox<T> makeCombo() {
        JComboBox<T> c = new JComboBox<>();
        c.setBackground(BG_CARD);
        c.setForeground(TEXT_PRIMARY);
        c.setFont(fontBody(13));
        c.setBorder(BorderFactory.createLineBorder(BORDER));
        return c;
    }

    public static JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(TEXT_MUTED);
        l.setFont(fontBody(12));
        return l;
    }

    public static JLabel makeTitleLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(ACCENT);
        l.setFont(fontTitle(20));
        return l;
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(12, 14, 12, 14)
        );
    }

    public static JPanel makeCard() {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD);
        p.setBorder(cardBorder());
        return p;
    }

    public static JPanel makeDarkPanel() {
        JPanel p = new JPanel();
        p.setBackground(BG_PANEL);
        return p;
    }

    private static void styleInput(JComponent c) {
        c.setBackground(new Color(38, 42, 60));
        c.setForeground(TEXT_PRIMARY);
        c.setFont(fontBody(13));
        c.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        if (c instanceof javax.swing.text.JTextComponent tc) {
            tc.setCaretColor(ACCENT);
        }
    }

    public static void styleTable(javax.swing.JTable table) {
        table.setBackground(BG_CARD);
        table.setForeground(TEXT_PRIMARY);
        table.setFont(fontBody(13));
        table.setGridColor(BORDER);
        table.setRowHeight(28);
        table.setSelectionBackground(ACCENT);
        table.setSelectionForeground(BG_DARK);
        table.getTableHeader().setBackground(BG_PANEL);
        table.getTableHeader().setForeground(ACCENT);
        table.getTableHeader().setFont(fontBody(12).deriveFont(Font.BOLD));
    }

    public static void applyDark(JComponent c) {
        c.setBackground(BG_DARK);
        c.setForeground(TEXT_PRIMARY);
    }

    public static JScrollPane makeScroll(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.setBackground(BG_PANEL);
        sp.getViewport().setBackground(BG_CARD);
        sp.setBorder(BorderFactory.createLineBorder(BORDER));
        return sp;
    }
}
