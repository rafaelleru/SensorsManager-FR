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
public class ProcesadorYodafy {
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
			socketServicio.send(paqueteEnviado);
			System.out.println("Paquete yodificado enviado");
		} catch (IOException e) {
			System.err.println("Error al enviar el paquete ");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";

		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];

			s[j]=s[k];
			s[k]=tmp;
		}

		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}

		return resultado;
	}
}
