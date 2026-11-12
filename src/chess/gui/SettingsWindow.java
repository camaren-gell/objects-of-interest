
package chess.gui;

import javax.swing.*;
import java.awt.*;

public class SettingsWindow extends JDialog {

    private JComboBox<String> boardStyle;
    private JComboBox<String> pieceStyle;
    private JComboBox<String> boardSize;

    public SettingsWindow(ChessboardGUI chessboard) {

        super(chessboard, "Chess Settings", false);

        setLayout(new BorderLayout(10, 10));

        JPanel settingsPanel = new JPanel(
                new GridLayout(3, 2, 10, 15)
        );

        settingsPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // Board Style
        settingsPanel.add(
                new JLabel("Board Style:")
        );

        boardStyle = new JComboBox<>(
                new String[]{
                        "Spider-Man",
                        "Wooden",
                        "Marble"
                }
        );

        boardStyle.setSelectedItem("Spider-Man");

        settingsPanel.add(boardStyle);

        // Chess Piece Colors
        settingsPanel.add(
                new JLabel("Piece Colors:")
        );

        pieceStyle = new JComboBox<>(
                new String[]{
                        "Black & Green",
                        "Black & White",
                        "Gold & Silver",
                        "Blue & Red"
                }
        );

        pieceStyle.setSelectedItem("Black & Green");

        settingsPanel.add(pieceStyle);

        // Board Size
        settingsPanel.add(
                new JLabel("Board Size:")
        );

        boardSize = new JComboBox<>(
                new String[]{
                        "Small",
                        "Medium",
                        "Large"
                }
        );

        boardSize.setSelectedItem("Medium");

        settingsPanel.add(boardSize);

        add(settingsPanel, BorderLayout.CENTER);

        // Apply Button
        JButton applyButton = new JButton("Apply");

        applyButton.addActionListener(e -> {

            chessboard.applySettings(
                    (String) boardStyle.getSelectedItem(),
                    (String) pieceStyle.getSelectedItem(),
                    (String) boardSize.getSelectedItem()
            );

        });

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(applyButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();

        setResizable(false);

        setLocationRelativeTo(chessboard);
    }
}
