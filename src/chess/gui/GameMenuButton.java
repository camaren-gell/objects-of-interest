package chess.gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/** A "Game Menu" button that opens New Game, Save Game, and Load Game. */
public class GameMenuButton extends JButton {
    /** Builds the button and its dropdown, copying the look of an existing button (such as Settings). */
    public GameMenuButton(JButton lookLike, Runnable onNewGame, Runnable onSave, Runnable onLoad) {
        super("Game Menu");

        setFont(lookLike.getFont());
        setForeground(lookLike.getForeground());
        setMargin(lookLike.getMargin());

        JPopupMenu popup = new JPopupMenu();
        popup.add(item("New Game", KeyEvent.VK_N, onNewGame));
        popup.add(item("Save Game", KeyEvent.VK_S, onSave));
        popup.add(item("Load Game", KeyEvent.VK_O, onLoad));

        // Open the dropdown just below the button
        addActionListener(e -> popup.show(this, 0, getHeight()));
    }

    /** Creates one menu item with a Ctrl/Cmd shortcut. */
    private JMenuItem item(String text, int key, Runnable action) {
        JMenuItem item = new JMenuItem(text);
        item.setAccelerator(KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        item.addActionListener(e -> action.run());
        return item;
    }
}