import java.awt.EventQueue;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerFrame {

    JFrame serverFrame;
    JPanel panel = new JPanel();

    public JPanel getPanel() {
        return panel;
    }

    public ServerFrame() {
        serverFrame = new JFrame();
        serverFrame.getContentPane().setFont(new Font("Bahnschrift", Font.PLAIN, 12));
        serverFrame.setTitle("Server");
        serverFrame.setBounds(100, 100, 560, 590);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.getContentPane().setLayout(null);

        JButton receiveButton = new JButton("Arreter");
        receiveButton.setBounds(250, 479, 110, 37);
        serverFrame.getContentPane().add(receiveButton);
        receiveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverFrame.dispose();
            }
        });

        panel.setForeground(new Color(0, 0, 0));
        panel.setBackground(Color.WHITE);
        panel.setBounds(10, 11, 524, 452);
        serverFrame.getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    }
}
