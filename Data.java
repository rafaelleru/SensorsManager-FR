//
// Clase Data
//

import java.util.Date;

public class Data{
    private Date fecha; //(año, mes, dia, hora, min, seg)
    private String text;
    private String location;

    public Data(){
	this.fecha = new Date(00, 00, 00, 00, 00, 00);
	this.text = "\n unknown";
	this.location = "\n unknown";
    }

    public Data(Date f, String t, String l){
	this.fecha = f;
	this.text = t;
	this.location = l;
    }

    public Date getDate(){
	return this.fecha;
    }

    public void setDate(Date f){
	this.fecha = f;
    }

    public String getText(){
	return this.text;
    }

    public void setText(String t){
	this.text = t;
    }

    public String getLocation(){
	return this.location;
    }

    public void setLocation(String l){
	this.location = l;
    }
}
