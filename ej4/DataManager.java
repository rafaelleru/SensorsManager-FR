import java.io.*;
import java.net.*;
import java.util.Scanner;

public class DataManager{
    //Socket UDP para el envio de peticiones al servidor
	
    // Nombre del host donde se ejecuta el servidor:
    String host="localhost";
    // Puerto en el que espera el servidor:
    static int port=8888;
    Data informacion = new Data();
    static InetAddress direccion = null;
    static DatagramSocket socket = null;
    

    private static Scanner scanner = new Scanner(System.in);
    
    public DataManager(){
	this.port = 8888;
	//this.scanner = new Scanner(System.in);

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
	InetAddress  direccionget = null;
	try{
	    direccionget = InetAddress.getByName("localhost");
	} catch(IOException e){
		System.err.println("Error obteniendo la direccion");
	    }
	if(opt == 1){
	    System.out.println("Que localizacion quieres consultar: ");
	    String location = scanner.next();
	
	    //    try{

	    //Enivar peticion al servidor
	    //} catch(IOException e){
	    //	System.err.println("Error en la comunicacion con el servidor: " + e);
	    //}
	    //Lanzar metodo que recoja los datos.
	    //try{
	    byte[] buferEnvio;
	    buferEnvio = ("data_" + location).getBytes();
	    DatagramPacket peticion = null;
	    peticion = new DatagramPacket(buferEnvio, buferEnvio.length, direccionget, port);
	    getAndPrintData();
		/*} catch(IOException e){
		System.err.println("Error haciendo la peticion al servidor");
	    }*/
	}
	    /*
	if(option == 2){
	    System.out.println("Que localizacion quieres consultar asqueroso: ");
	    String location = scanner.next();

	    try{
		//Enivar peticion al servidor
	    } catch(IOException e)
		System.err.println("Error en la comunicacion con el servidor: " + e);
	    //Lanzar metodo que recoja los datos.
	    getAndPrintData();
	}
	   */ 

	//	if(option == 3){}
	//	if(option == -1)
	//        return 0;
    }

    public static void getAndPrintData(){
	int port = 9999;
	InetAddress addres = null;

	try{
	    DatagramSocket datasocket = new DatagramSocket(port);
	    DatagramPacket datapacket = null;

	    try{
		addres = InetAddress.getByName("localhost");
	    } catch(IOException e){
		System.err.println("Error obteniendo la direccion");
	    }
	
	do{
	    byte[] buffer = new byte[256];
	    datapacket = new DatagramPacket(buffer, buffer.length);
	    datasocket.receive(datapacket);

	    //Imprimir los datos
	    System.out.println(datapacket.getData());
	    System.out.println("********************");
	}while(true);

	} catch(IOException e){
	    System.err.println("Error recibiendo los datos" + e);
	}
    }
	    
    public static void main(String[] args){
	printMenu();

	//executeOption(option);
	
    }
}
