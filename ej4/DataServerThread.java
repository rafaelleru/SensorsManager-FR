import java.io.*;
import java.net.*;


public class DataServerThread  extends Thread{
    private DatagramSocket socketServicio;
    private DatagramPacket paqueteRecibido, paqueteEnviado;
    
    
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
	bufer = sendToServer(msj);
        
	System.out.println("-------------------------------------------");
	System.out.println(bufer);
	
    }
    
    private String sendToServer(String peticion) {
        String f;
	String[] s = peticion.split(":"); 
	f = "Fecha: "+s[0]+"\nLocalizacion: "+s[1]+"\nTemperatura: "+s[2]+"\n";
	return f;
	
    }
    
    public void run(){
	procesa();
    }
}
