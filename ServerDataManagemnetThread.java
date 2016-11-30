//
// Servidor Gestor Datos 
//

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.ArrayList;

public class DataServerThread extends Thread{
    private Data unData;
    private DataServerThread server = null;
    private Socket socket = null;
    private int port = -1;
    private DataInputStream intput = null;
    private DataOutputStream output = null;

    public DataServerThread(DataServer unServer, Socket unSocket){
	super();
	server = unServer;
	socket = unSocket;
	port = this.socket.getPort();
    }

    public boolean verifyData(Data unData){
	boolean verify = false;
	Date fechaData = unData.getDate();
	Date fechaOld = new Date(116, 11, 1, 00, 00, 00);

	if(fechaData.after(fechaOld)){
	    verify = true;
	}

	return verify;
    }

    public void recibeData(){
	ServerSocket socketServidor;
	int puerto=888;

	try {
	    socketServidor = new ServerSocket(puerto);
	} catch (IOException e) {
	    System.out.println("Error:  no  se  pudo  atender  en  el puerto "+puerto);
	}

	socketServidor.accept();
	BufferedReader inReader = new BufferedReader(new InputStreamReader(socketServicio.getInputStream()));
    }


    
}
