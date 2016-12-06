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
	
        
	saveData("------------------------------");
	saveData(bufer);
	
    }
    
    private String sendToServer(String peticion) {
        String f;
	String[] s = peticion.split(":"); 
	f = "Fecha: "+s[0]+"\nLocalizacion: "+s[1]+"\nTemperatura: "+s[2]+"\n";
	return f;
	
    }

    private void  saveData(String t){
	FileWriter archivo = null;
	PrintWriter escribir = null;

	try{
	    archivo = new FileWriter("./DatosTemperatura.txt", true);
	    escribir = new PrintWriter(archivo);
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
