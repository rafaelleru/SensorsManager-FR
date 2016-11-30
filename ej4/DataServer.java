import java.io.*;
import java.net.*;

public class DataServer {

    public static void main(String[] args) {
	
	// Puerto de escucha
	int port=8888;
	
	try {
	    
	    DatagramSocket serverSocket = new DatagramSocket(port);
	    DatagramPacket paquete = null;
	    
	    do {
                byte[] bufer = new byte[256];
                paquete = new DatagramPacket(bufer, bufer.length);
		serverSocket.receive(paquete);
		DataServerThread procesador = new DataServerThread(serverSocket, paquete);
		procesador.start();
	    } while (true);
	} catch (IOException e) {
	    System.err.println("Error al escuchar en el puerto "+port);
	}
	
    }
}
