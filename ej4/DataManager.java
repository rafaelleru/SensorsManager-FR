import java.io.*;
import java.net.*;
import java.util.Scanner;

public class DataManager{
	//Socket UDP para el envio de peticiones al servidor

	// Nombre del host donde se ejecuta el servidor:
	static String host="localhost";
	// Puerto en el que espera el servidor:
	static int port=8888;
	Data informacion = new Data();
	static InetAddress direccion = null;
	static DatagramSocket socket = null;
	static DatagramPacket recibido = null;

	private static Scanner scanner = new Scanner(System.in);

	public DataManager(){
		//this.scanner = new Scanner(System.in);

		try{
			socket = new DatagramSocket();
			System.out.println("Constructor");
		} catch(IOException e){
			System.err.println("Error creando el socket");
		}

		try{
			direccion = InetAddress.getByName(host);
		} catch(IOException e){
			System.err.println("Error obteniendo la direccion");
		}
	}

	public static void printMenu(){
		System.out.println("Puede realizar las siguientes operaciones para consultar\n" +
		"el los datos de los sensores:\n");
		System.out.println("[ 1 ] Consultar el historico de mediciones de una localizacion.");
		System.out.println("[ 2 ] Consultar todas las mediciones en una determinada fecha.");
		System.out.println("[ 3 ] Descargar una copia del fichero de mediciones de una determinada localizacion.");
		System.out.println("[ -1 ] Exit");
		String option = scanner.next();
		executeOption(option);
	}

	public static void executeOption(String option){
		int opt = Integer.parseInt(option);
		/*Opciones del menu */

		if(opt == 1){
			System.out.println("Que localizacion quieres consultar: ");
			String location = scanner.next();

			byte[] buferEnvio= new byte[256];
			buferEnvio = ("data_" + location).getBytes();
			DatagramPacket peticion = null;
			peticion = new DatagramPacket(buferEnvio, buferEnvio.length, direccion, port);
			try{
				socket.send(peticion);
			}catch(IOException e){
				System.out.println("Error al enviar el paquete");
			}
			getAndPrintData();

	}

			if(opt == 2){
				System.err.println("Not implemented");
			}
		//	if(option == 3){}
		//	if(option == -1)
		//        return 0;
	}

	public static void getAndPrintData(){
			try{
				byte[] bufer = new byte[256];
				recibido = new DatagramPacket(bufer, bufer.length);
				socket.receive(recibido);
				String msj = new String(recibido.getData());
				//Imprimir los datos
				System.out.println(msj);
				System.out.println("********************");
				socket.close();
			}catch(IOException e){}
	}

	public static void main(String[] args){

				try{
					socket = new DatagramSocket();
				} catch(IOException e){
					System.err.println("Error creando el socket");
				}

				try{
					direccion = InetAddress.getByName(host);
				} catch(IOException e){
					System.err.println("Error obteniendo la direccion");
				}
		printMenu();
	}
}
