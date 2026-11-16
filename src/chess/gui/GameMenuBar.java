package chess.gui;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/** Menu bar with New Game, Save Game, and Load Game. */
public class GameMenuBar extends JMenuBar {
    /** Builds the menu and connects each item to its action. */
    public GameMenuBar(Runnable onNewGame, Runnable onSave, Runnable onLoad) {
        JMenu menu = new JMenu("Game Menu");
        menu.add(item("New Game", KeyEvent.VK_N, onNewGame));
        menu.add(item("Save Game", KeyEvent.VK_S, onSave));
        menu.add(item("Load Game", KeyEvent.VK_O, onLoad));
        add(menu);
    }

    /** Creates one menu item with a Ctrl/Cmd shortcut. */
    private JMenuItem item(String text, int key, Runnable action) {
        JMenuItem item = new JMenuItem(text);
        item.setAccelerator(KeyStroke.getKeyStroke(key, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        item.addActionListener(e -> action.run());
        return item;
    }
}
