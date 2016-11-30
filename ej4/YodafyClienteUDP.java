//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class YodafyClienteUDP  extends Thread{

	public static void main(String[] args) {

		byte[] buferEnvio;

		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8888;
        Data informacion = new Data();
		InetAddress direccion = null;
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
                //Coger informacion
                informacion.setLocation(args[0]);
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH-mm-ss");
                Date date = new Date();
                informacion.setDate(dateFormat.format(date));
                informacion.addData("20c/10c");
				//Enviar un paquete
				buferEnvio=informacion.toString().getBytes();
				System.out.println(buferEnvio.length);
				paqueteEnviado = new DatagramPacket(buferEnvio,buferEnvio.length, direccion, port);
				socket.send(paqueteEnviado);
				//Es necesario cerrar el socket

				socket.close();
				
				// Excepciones:
			} catch (UnknownHostException e) {
				System.err.println("Error: Nombre de host no encontrado.");
			} catch (IOException e) {
				System.err.println("Error de entrada/salida al abrir el socket.");
			}
	}
}
