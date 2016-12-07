import java.io.*;
import java.net.*;


public class DataServerThread  extends Thread{
  private DatagramSocket socketServicio;
  private DatagramPacket paqueteRecibido, paqueteEnviado;
  String host="localhost";

  private int port;
  private InetAddress address;

    String bufer="";
    String msj="";

  public DataServerThread(DatagramSocket socketServicio, DatagramPacket paquete) {
    this.socketServicio=socketServicio;
    this.paqueteRecibido = paquete;
  }


  void procesa(){
    msj = new String(paqueteRecibido.getData());
    address = paqueteRecibido.getAddress();
    port = paqueteRecibido.getPort();
    //System.out.println(msj);

    if(msj.contains("data_"))
      sendDataFromLocation(msj.substring(5, msj.length()));
    else if(msj.contains("alldat_"))
      enviarArchivo(msj.substring(7,msj.length()));
    else{
      bufer = sendToServer(msj);
    }
  }

  private void sendDataFromLocation(String location){

    byte[] buffer = new byte[2000];
    BufferedReader br = null;
    String filename = location+".txt";
    try{
       br = new BufferedReader(new FileReader(filename));
    }catch(FileNotFoundException e){
      System.err.println("No se encuentra el archivo: " + filename);
    }
    String line, mensaje_completo="";
    try{
      while ((line = br.readLine()) != null) {
        mensaje_completo += line;
        mensaje_completo+="\n";
      }
      br.close();
      System.out.println(mensaje_completo);
      buffer = mensaje_completo.getBytes();

      paqueteEnviado = new DatagramPacket(buffer, buffer.length, address, port);
      System.out.println("Mensaje enviado");
      socketServicio.send(paqueteEnviado);
    }catch(IOException e){
      System.err.println("Error leyendo el archivo");
    }
  }


  private String sendToServer(String peticion) {
    String f;
    String[] s = peticion.split(":");
    f = "Fecha: "+s[0]+"\nLocalizacion: "+s[1]+"\nTemperatura: "+s[2];
    System.out.println(f);
    saveData(f, s[1]);
    return f;

  }

  private void  saveData(String t, String location){
    FileWriter archivo = null;
    PrintWriter escribir = null;

    try{
      archivo = new FileWriter("./" + location + ".txt", true);
      escribir = new PrintWriter(archivo);
      escribir.println("\n------------------------------");
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
  private void enviarArchivo(String location){
    //Crear server TCP
    System.out.println(location);
    String file = "./" + location + ".txt";
    try{
      ServerSocket socketServidor = new ServerSocket(8889);
      Socket cliente = socketServidor.accept();

      BufferedOutputStream bos= new BufferedOutputStream(cliente.getOutputStream());

    //Enviar archivo
      File myFile = new File(file);

      byte[] arrayBuffer = new byte[2000];
      FileInputStream fis= new FileInputStream(myFile);
      BufferedInputStream bis = new BufferedInputStream(fis);
      bis.read(arrayBuffer, 0, arrayBuffer.length);
      bos.write(arrayBuffer, 0, arrayBuffer.length);
      System.out.println("archivo enviado");

      bos.flush();
      bos.close();
      socketServidor.close();
    //Cerrar TCP
  }catch(IOException e){}
  }
  public void run(){
    procesa();
  }
}
