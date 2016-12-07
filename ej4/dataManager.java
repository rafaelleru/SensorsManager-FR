import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.io.ByteArrayOutputStream;


public class dataManager{
  private static Scanner scanner = new Scanner(System.in);

  private static void getFile(){
    final String fileOutput = "baseDatos.txt";
    byte[] aByte = new byte[2000];
    int bytesRead;
    Socket clientSocket = null;
    InputStream is = null;
    try{
      clientSocket = new Socket("127.0.0.1", 8889);
      is = clientSocket.getInputStream();
    }catch(IOException e){e.printStackTrace();}

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try{
        FileOutputStream fos = new FileOutputStream(fileOutput);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        bytesRead = is.read(aByte, 0, aByte.length);

        do{
          baos.write(aByte);
            bytesRead = is.read(aByte);
        }while(bytesRead != -1);

        bos.write(baos.toByteArray());

        bos.flush();
        bos.close();
        clientSocket.close();
        System.out.println("Archivo recibido");
    }catch(IOException e){e.printStackTrace();}

  }

  	public static void printMenu(){
  		System.out.println("Puede realizar las siguientes operaciones para consultar\n" +
  		"el los datos de los sensores:\n");
  		System.out.println("[ 1 ] Consultar el historico de mediciones de una localizacion.");
  		System.out.println("[ 2 ] Consultar todas las mediciones en una determinada fecha.");
  		System.out.println("[ 3 ] Descargar una copia del fichero de mediciones de una determinada localizacion.");
  		System.out.println("[ -1 ] Exit");

  	}

    public static void main(String[] args) {

    	byte[] buferEnvio= new byte[256];
      byte[] bufer = new byte[2000];

    	// Nombre del host donde se ejecuta el servidor:
    	String host="localhost";
    	// Puerto en el que espera el servidor:
    	int port=8888;
    	InetAddress direccion = null;
    	DatagramPacket paqueteEnviado = null;
      DatagramPacket paqueteRecibido = null;
    	DatagramSocket socket = null;

    	try {
    	    socket = new DatagramSocket();
    	}	catch(IOException e){
    	    System.err.println("Error: no se pudo establecer la conexion.");
    	}
    	try{
    	    direccion = InetAddress.getByName(host);
    	}catch(IOException e){ System.err.println("Error al obtener la direccion");}
      int opt = 0;
      do{
        printMenu();
        opt = Integer.parseInt(scanner.next());
        try{
          if(opt == 1){
            System.out.println("Que localizacion quieres consultar: ");
            String location = scanner.next();
            buferEnvio = ("data_" + location).getBytes();
            paqueteEnviado = new DatagramPacket(buferEnvio, buferEnvio.length, direccion, port);
            socket.send(paqueteEnviado);
          }else if(opt == 2){
            System.err.println("Not implemented");
          }
          else if(opt == 3){
            System.out.println("Que localizacion quieres consultar: ");
            String location = scanner.next();
            buferEnvio = ("alldat_" + location).getBytes();
            paqueteEnviado = new DatagramPacket(buferEnvio, buferEnvio.length, direccion, port);
            socket.send(paqueteEnviado);
            socket.close();
            getFile();
          }
          paqueteRecibido = new DatagramPacket(bufer, bufer.length);
          socket.receive(paqueteRecibido);
          socket.close();

        }catch(IOException e){}
        String msj = new String(paqueteRecibido.getData());
        //Imprimir los datos
        System.out.println(msj);
        System.out.println("********************");
      }while(opt != -1)
}

}
