//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente!
//
public class ProcesadorYodafy  extends Thread{
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private DatagramSocket socketServicio;
	private DatagramPacket paqueteRecibido, paqueteEnviado;

	private byte[] bufer= new byte[256];
	private int port;
	private InetAddress address;
	private String msj;
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramSocket socketServicio, DatagramPacket paquete) {
		this.socketServicio=socketServicio;
		this.paqueteRecibido = paquete;
		random=new Random();
	}


	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		try {
			msj = new String(paqueteRecibido.getData());
			address = paqueteRecibido.getAddress();
			port = paqueteRecibido.getPort();
			System.out.println("Paquete recibido para yodaficar");
			bufer = (yodaDo(msj)).getBytes();
			paqueteEnviado = new DatagramPacket(bufer, bufer.length, address, port);
			System.out.println(bufer);
		} catch (IOException e) {
			System.err.println("Error al enviar el paquete ");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		String[] s = peticion.split(":");
		return "Fecha: "+s[0]+"\nLocalización: "+s[1]+"\nTemperatura: "+s[2]+"\n";
		
	}
	public void run(){
		procesa();
	}
}
