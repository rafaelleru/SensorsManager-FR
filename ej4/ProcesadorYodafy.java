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

	
	private int port;
	private InetAddress address;
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
		String bufer="";
        String msj="";

			msj = new String(paqueteRecibido.getData());
			
			address = paqueteRecibido.getAddress();
			port = paqueteRecibido.getPort();
			bufer = yodaDo(msj);
            
            System.out.println(msj);

			//paqueteEnviado = new DatagramPacket(bufer, bufer.length, address, port);
			System.out.println("-------------------------------------------");
			System.out.println(bufer);
			
	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
        String f;
		String[] s = peticion.split(":"); 
		f = "Fecha: "+s[0]+"\nLocalizacion: "+s[1]+"\nTemperatura: "+s[2]+"\n";
		return f;
		
	}
	public void run(){
		procesa();
	}
}
