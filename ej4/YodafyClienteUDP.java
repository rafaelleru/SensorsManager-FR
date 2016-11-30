//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class YodafyClienteUDP {

	public static void main(String[] args) {

		byte[] buferEnvio= new byte[256];
		byte[] buferRecepcion = new byte[256];

		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8888;

		InetAddress direccion = null;
		DatagramPacket paqueteRecibido = null;
		DatagramPacket paqueteEnviado = null;
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket();
		}	catch(IOException e){
				System.err.println("Error: no se pudo establecer la conexion.");
			}
			try{
				direccion = InetAddress.getByName(host);
			}catch(IOException e){ System.err.println("Error al obtener la direccion");}

			//Recibir un paquete
			try{
				//Enviar un paquete
				buferEnvio="Al monte del volcan debes ir sin demora".getBytes();

				paqueteEnviado = new DatagramPacket(buferEnvio,buferEnvio.length, direccion, port);
				socket.send(paqueteEnviado);
				//Es necesario cerrar el socket

				paqueteRecibido = new DatagramPacket(buferRecepcion, buferRecepcion.length);
				socket.receive(paqueteRecibido);
				socket.close();
				// Excepciones:
			} catch (UnknownHostException e) {
				System.err.println("Error: Nombre de host no encontrado.");
			} catch (IOException e) {
				System.err.println("Error de entrada/salida al abrir el socket.");
			}

			String fraseRecibida = new String(paqueteRecibido.getData());
			System.out.println(fraseRecibida);
	}
}
