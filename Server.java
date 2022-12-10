import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	static ArrayList<ClientHandler> handlers= new ArrayList();
	private static DataOutputStream dataOutputStream = null;
	private static DataInputStream dataInputStream = null;

	public static void main(String[] args) {
		ServerFrame frame = new ServerFrame();
		frame.serverFrame.setVisible(true);
		JPanel panel = frame.getPanel();
		panel.add(new JLabel("Server started on port 9999"));
		while (true){
			try (ServerSocket serverSocket = new ServerSocket(9999)) {

				Socket clientSocket = serverSocket.accept();
				ClientHandler client = new ClientHandler(clientSocket, frame);
				handlers.add(client);
				Thread thread = new Thread(client);
				thread.start();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}