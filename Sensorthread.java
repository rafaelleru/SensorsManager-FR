import java.net.*;
import java.io.*;
            
public class Sensor extends Thread{

   private Socket socket   = null;
   private Sensor sensor   = null;
   private DataInputStream  streamIn = null;

   public SensorThread(ChatClient _sensor, Socket _socket){  
      sensor  = _sensor;
      socket   = _socket;
      open();  
      start();
   }

   public void open(){  
      try{  
         streamIn  = new DataInputStream(socket.getInputStream());
      }
      catch(IOException ioe){  
         System.out.println("Error al abrir input stream: " + ioe);
         client.stop();
      }
   }

   public void close(){  
      try{  
         if (streamIn != null) streamIn.close();
      }
      catch(IOException ioe){  
         System.out.println("Error al cerrar input stream: " + ioe);
      }
   }

   public void run(){  
      while (true){  
         try{  
	     System.out.println("sensor alive");
         }
         catch(IOException ioe){  
            System.out.println("error: " + ioe.getMessage());
            client.stop();
         }
      }
   }
}
