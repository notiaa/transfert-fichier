import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    String fileName;
    ServerFrame frame;


    public ClientHandler(Socket clientSocket, ServerFrame frame){
        setClientSocket(clientSocket);
        this.frame = frame;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        while(true) {
            try {
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());

                int nameLength = dataInputStream.readInt();
                byte[] name = new byte[nameLength];
                dataInputStream.readFully(name, 0, nameLength);
                setFileName(new String(name));
                frame.panel.add(new JLabel("Receiving" + fileName));
                int bytes = 0;
                FileOutputStream fileOutputStream = new FileOutputStream(fileName);

                int size = dataInputStream.readInt();  //read file size
                byte[] buffer = new byte[41024];
                while (size != 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
                    //Here we write the file using write method
                    fileOutputStream.write(buffer, 0, bytes);
                    size -= bytes;  //read upto file size
                }
                //Here we received file
                fileOutputStream.close();
                //Here we received file
                System.out.println("File is Received");
                frame.panel.add(new JLabel("File is Received"));
                fileOutputStream.close();
            } catch (Exception e) {
                frame.panel.add(new JLabel("client disconnected"));
                System.out.println("client disconnected");
                return;
            }
        }
    }
}
