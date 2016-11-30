import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
public class YodafyServidorIterativo {

	public static void main(String[] args) {

		// Puerto de escucha
		int port=8888;
		byte[] bufer = new byte[256];

		try {

			DatagramSocket serverSocket = new DatagramSocket(port);
			DatagramPacket paquete = null;
			// Mientras ... siempre!
			do {
				paquete = new DatagramPacket(bufer, bufer.length);
				serverSocket.receive(paquete);
				System.out.println("Paquete recibido");
				ProcesadorYodafy procesador = new ProcesadorYodafy(serverSocket, paquete);
				procesador.start();
			} while (true);
		} catch (IOException e) {
			System.err.println("Error al escuchar en el puerto "+port);
		}

	}
}
