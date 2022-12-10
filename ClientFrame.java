import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class ClientFrame {

    private JFrame clientFrame;
    private JTextField pathText;
    private JTextField ipField;
    Socket server = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ClientFrame window = new ClientFrame();
                    window.clientFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ClientFrame() {
        initialize();
    }

    private void initialize() {
        clientFrame = new JFrame();
        clientFrame.setTitle("match basket\r\n");
        clientFrame.setBounds(100, 100, 730, 460);
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        clientFrame.getContentPane().setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, -22, 714, 443);
        clientFrame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel chooseFile = new JLabel("Choose a file");
        chooseFile.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        chooseFile.setHorizontalAlignment(SwingConstants.CENTER);
        chooseFile.setBounds(10, 37, 122, 26);
        panel.add(chooseFile);

        JLabel state = new JLabel("");
        state.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        state.setHorizontalAlignment(SwingConstants.CENTER);
        state.setBounds(142, 100, 200, 26);
        panel.add(state);

        pathText = new JTextField();
        pathText.setHorizontalAlignment(SwingConstants.CENTER);
        pathText.setForeground(SystemColor.inactiveCaptionBorder);
        pathText.setBackground(SystemColor.controlShadow);
        pathText.setBounds(142, 36, 383, 26);
        panel.add(pathText);
        pathText.setColumns(20);

        JButton sendButton = new JButton("Send");

        sendButton.setBounds(596, 36, 108, 26);
        panel.add(sendButton);

        JButton browseButton = new JButton("...");
        browseButton.setBounds(537, 38, 49, 23);
        panel.add(browseButton);
        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser choose = new JFileChooser(
                        FileSystemView
                                .getFileSystemView()
                                .getHomeDirectory()
                );

                choose.showOpenDialog(null);
                File file = choose.getSelectedFile();
                String n = file.getAbsolutePath();
                pathText.setText(n);

            }
        });

        JLabel lblNewLabel_1 = new JLabel("Enter server name");
        lblNewLabel_1.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(24, 323, 155, 35);
        panel.add(lblNewLabel_1);

        ipField = new JTextField();
        ipField.setBounds(189, 329, 175, 20);
        panel.add(ipField);
        ipField.setColumns(10);

        JButton connectButton = new JButton("Connect");
        connectButton.setBounds(374, 328, 89, 23);
        panel.add(connectButton);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                System.out.println(ip);
                try {
                    server = new Socket(ip, 9999);
                    state.setText("Connected to" + ip);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (server == null) {
                    state.setText("Not connected to a server");
                    return;
                }
                try {
                    DataOutputStream dataOutputStream = new DataOutputStream(server.getOutputStream());
                    File file = new File(pathText.getText());
                    byte[] filename = file.getName().getBytes();

                    dataOutputStream.writeInt(filename.length);
                    dataOutputStream.write(filename);

                    byte[] data = new byte[(int) file.length()];
                    data = Files.readAllBytes(file.toPath());
                    dataOutputStream.writeInt(data.length);
                    dataOutputStream.write(data);
                    pathText.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}