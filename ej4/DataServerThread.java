import java.io.*;
import java.net.*;


public class DataServerThread  extends Thread{
    private DatagramSocket socketServicio;
    private DatagramPacket paqueteRecibido, paqueteEnviado;
    String host="localhost";
    
    private int port;
    private InetAddress address;

    
    public DataServerThread(DatagramSocket socketServicio, DatagramPacket paquete) {
	this.socketServicio=socketServicio;
	this.paqueteRecibido = paquete;
	
    }
    
    
    void procesa(){
	String bufer="";
	String msj="";
	
	msj = new String(paqueteRecibido.getData());
	
	address = paqueteRecibido.getAddress();
	port = paqueteRecibido.getPort();
	if(msj.contains("data_"))
	    sendDataFromLocation(msj.substring(5, msj.length()));
	   
	bufer = sendToServer(msj);
	
    }

    private void sendDataFromLocation(String location){
	int port = 9999;
	InetAddress addres = null;
	DatagramPacket dataToSend = null;

	//Creamos el socket
	try{
	    DatagramSocket datasocket = new DatagramSocket(port);
	}catch (IOException e){
	    System.err.println("Error al crear el socket para el envio de los datos");
	}

	try{
	    addres = InetAddress.getByName(host);
	} catch(IOException e){
	    System.err.println("Error obteniendo la direccion");
	}

	BufferedReader	br = null;
	try{
	     br = new BufferedReader(new FileReader("./"+location+".txt"));
	}catch(FileNotFoundException e){
	    System.err.println("No se encuentra el archivo: " + location + ".txt");
	}
	
	String line;
	try{
	    while ((line = br.readLine()) != null) {
		byte[] buffer = new byte[256];
		buffer = line.getBytes();
		dataToSend = new DatagramPacket(buffer, buffer.length, address, port);
	    }
	}catch(IOException e){
	    System.err.println("Error leyendo el archivo");
	}
    }
    
    
    private String sendToServer(String peticion) {
        String f;
	String[] s = peticion.split(":"); 
	f = "Fecha: "+s[0]+"\nLocalizacion: "+s[1]+"\nTemperatura: "+s[2]+"\n";
	saveData(f, s[1]);
	return f;
	
    }

    private void  saveData(String t, String location){
	FileWriter archivo = null;
	PrintWriter escribir = null;

	try{
	    archivo = new FileWriter("./" + location + ".txt", true);
	    escribir = new PrintWriter(archivo);
	    escribir.println("------------------------------");
	    escribir.println(t);	    
	}catch(Exception e){
	    e.printStackTrace();
	}finally{
	    try{
		if(archivo != null)
		    archivo.close();
	    }catch(Exception e){
		e.printStackTrace();
	    }
	}
    }
    
    public void run(){
	procesa();
    }
}
