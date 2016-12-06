import java.io.*;
import java.net.*;
import java.util.Scanner;

public class DataManager{
    private Socket socket = null;
    private int port;
    private static Scanner scanner = new Scanner(System.in);
    
    public DataManager(){
	this.port = 8888;
	//this.scanner = new Scanner(System.in);
    }
    
    public static void printMenu(){
	System.out.println("Puede realizar las siguientes operaciones para consultar\n" +
			   "el los datos de los sensores:\n");
	System.out.println("[ 1 ] Consultar el historico de mediciones de una localizacion.");
	System.out.println("[ 2 ] Consultar todas las mediciones en una determinada fecha.");
	System.out.println("[ 3 ] Descargar una copia del fichero de mediciones de una determinada localizacion.");
	System.out.println("[ -1 ] Exit");
    }

    public static void executeOption(String option){
	int opt = Integer.parseInt(option);
	if(opt == 1){
	    System.out.println("Que localizacion quieres consultar asqueroso: ");
	    String location = scanner.next();
	    System.out.println(" ok mankina " +  location);
	}

	//if(option == 2){}

	//if(option == 3){}

    }

    public static void main(String[] args){
	printMenu();
	String option = scanner.next();
	executeOption(option);
	
    }
}
