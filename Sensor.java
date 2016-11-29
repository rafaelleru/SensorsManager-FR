//Autor: Rafael Leyva Ruiz rafaelleru95103@gmail.com

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/*import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
*/

import java.net.*;
import java.io.*;

//package SensorsManagement;

class Sensor{
    private Data d;
    private String location;

    public Sensor(String l){
	this.location = l;
	//start();
    }

    public void getDataFromAPI(){
	//llamar a la api
	/*String[] data = this.getWeatherDataFromJson(this.location, 1);

	for(String s: data){
	    d.addData(s);
	    }*/

	d.addData("fake data, 0º/23º");
    }

    public void sendDataToServer(){
	//Aqui se hace la conexion TCP y se manda d;
	Socket socket = null;
	int puerto=8888;
	String anfitrion="www.tumaebotcompany.ddns.net";

	try {
	    socket=new Socket (anfitrion,puerto); //Abrimos el socket
	} catch(UnknownHostException e) {
	    System.err.println("Error: equipo desconocido");
	} catch(IOException e) {
	    System.err.println("Error:  no  se  pudo  establecer  la conexión");
	}

	OutputStream outputStream = null;
	
	try{
	    outputStream = socket.getOutputStream();
	}catch (IOException ioe){
	    System.out.println("error: " + ioe.getMessage());
	}
	    
	PrintWriter outPrinter = new PrintWriter(outputStream,true);

	//Enviamos los datos del sensor

	if(this.location == null){
	    outPrinter.print("LOCATION:" + d.getLocation() + "DATA:" + d.getData());
	}else{
	    outPrinter.println(this.location + "DATA:" + d.getData());
	}

	//for(String s: d.getData()){
	//outPrinter.println(d.getData());
	    //}
	try{
	    outputStream.close();
	}catch(IOException ioe){
	    System.out.println("error: " + ioe.getMessage());
	}
	
	try{
	    socket.close();
	}catch(IOException ioe){
	    System.out.println("error: " + ioe.getMessage());
	}
    }

    public int verifiRequest(){
	//hay que recibir datos por tcp
	//Si se recibe
	return 1;
    }

    public static void main(String args[]){
	Sensor sensor = null;
	if(args.length != 1)
	    System.out.println("Uso: java sensor (localizacion del sensor)");
	else{
	    sensor = new Sensor(args[0]);
	}
    }
}


    /*private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        
        //Formatear datos en funcion de unidades
	String highLowStr = roundedHigh + "/" + roundedLow;
	return highLowStr;
    }

    /**
     * Take the String representing the complete forecast in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     *
     
     private String[] getWeatherDataFromJson(String forecastJsonStr, int numDays)
     throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "list";
        final String OWM_WEATHER = "weather";
        final String OWM_TEMPERATURE = "temp";
        final String OWM_MAX = "max";
        final String OWM_MIN = "min";
        final String OWM_DESCRIPTION = "main";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);
        JSONArray weatherArray = forecastJson.getJSONArray(OWM_LIST);

        // OWM returns daily forecasts based upon the local time of the city that is being
        // asked for, which means that we need to know the GMT offset to translate this data
        // properly.

        // Since this data is also sent in-order and the first day is always the
        // current day, we're going to take advantage of that to get a nice
        // normalized UTC date for all of our weather.

        Time dayTime = new Time();
        dayTime.setToNow();

        // we start at the day returned by local time. Otherwise this is a mess.
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        // now we work exclusively in UTC
        dayTime = new Time();

        String[] resultStrs = new String[numDays];
        for(int i = 0; i < weatherArray.length(); i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String description;
            String highAndLow;

            // Get the JSON object representing the day
            JSONObject dayForecast = weatherArray.getJSONObject(i);

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime;
            // Cheating to convert this to UTC time, which is what we want anyhow
            dateTime = dayTime.setJulianDay(julianStartDay+i);
            day = getReadableDateString(dateTime);

            // description is in a child array called "weather", which is 1 element long.
            JSONObject weatherObject = dayForecast.getJSONArray(OWM_WEATHER).getJSONObject(0);
            description = weatherObject.getString(OWM_DESCRIPTION);

            // Temperatures are in a child object called "temp".  Try not to name variables
            // "temp" when working with temperature.  It confuses everybody.
            JSONObject temperatureObject = dayForecast.getJSONObject(OWM_TEMPERATURE);
            double high = temperatureObject.getDouble(OWM_MAX);
            double low = temperatureObject.getDouble(OWM_MIN);

            highAndLow = formatHighLows(high, low);
            resultStrs[i] = day + " - " + description + " - " + highAndLow;
        }

        for (String s : resultStrs) {
	    // Log.v(LOG_TAG, "Forecast entry: " + s);
        }
        return resultStrs;

    }

    private void UpdateForecast(){
        GetForecastJSON getJSON = new GetForecastJSON();
	String location_user = this.location;
        getJSON.execute(location_user); //CP GRANADA 6357709
    }

    @Override
    public void onStart(){
        super.onStart();
        UpdateForecast();
    }


    private class GetForecastJSON{
	private final String LOG_TAG = GetForecastJSON.class.getSimpleName();

	@Override
	protected String[] getData(String location){
	    // These two need to be declared outside the try/catch
	    // so that they can be closed in the finally block.
	    HttpURLConnection urlConnection = null;
	    BufferedReader reader = null;
	    String forescastJSON = null;

	    //URL ulr_base_api = new URL("http://api.openweathermap.org/data/2.5/weather?q=");
	    String _cp = location;
	    //String _apiKey = "&appid=31675daf31677c07f01c32adb05d0bd6";

	    try {
		//openweather API call

		Uri.Builder url_builder = new Uri.Builder();
		url_builder.scheme("http");
		url_builder.authority("api.openweathermap.org");
		url_builder.appendPath("data");
		url_builder.appendPath("2.5");
		url_builder.appendPath("forecast");
		url_builder.appendPath("daily");
		url_builder.appendQueryParameter("q", _cp);
		url_builder.appendQueryParameter("mode", "json");
		url_builder.appendQueryParameter("units", "metric");
		url_builder.appendQueryParameter("cnt", "10");
		url_builder.appendQueryParameter("appid", "31675daf31677c07f01c32adb05d0bd6");

		//Log.v("URL", url_builder.toString());
		URL url = new URL(url_builder.toString());

		//Abrimos la peticion
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.connect();

		//Leemos el JSON que devuelve
		InputStream inputStream = urlConnection.getInputStream();
		StringBuffer stringBuffer = new StringBuffer();

		if (inputStream == null) {
		    //no hacemos nada
		    return null;
		}

		reader = new BufferedReader((new InputStreamReader(inputStream)));

		String line;
		while ((line = reader.readLine()) != null) {
		    stringBuffer.append(line + "\n");
		}

		if (stringBuffer.length() == 0) {
		    //no lo hemos podido parsear
		    return null;
		}

		forescastJSON = stringBuffer.toString();
		//Log.v("FORECAST JSON: ", forescastJSON);
	    } catch (IOException e){
		//Log.e(LOG_TAG, "Error losdatos no se han obtenido correctamente", e);
		return null;
	    }finally {
		if(urlConnection != null){
		    //cerrramos la conexion
		    urlConnection.disconnect();
		}

		if(reader != null){
		    try{
			reader.close();
		    }catch (final IOException e){
			//Log.e("reader null", "Error", e);
		    }
		}
	    }

	    //WeatherDataParser dataParser = new WeatherDataParser();
	    String[] data = {};
	    try {
		for (String s: data) {
		    //Log.v(LOG_TAG, s);
		}
		return  getWeatherDataFromJson(forescastJSON, 10);
	    } catch (JSONException e) {
		e.printStackTrace();
	    }

	    return null;
	}
    }*/

