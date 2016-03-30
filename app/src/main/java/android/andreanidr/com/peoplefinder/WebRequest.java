package android.andreanidr.com.peoplefinder;


import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;

/**
 * Created by cmte on 3/29/16.
 */



public class WebRequest extends AsyncTask<String, Void, Void> {

    public static final String TAG = "request";
    public static final String _url = "http://192.168.1.112:8080/";
    private String result;

    private HttpURLConnection connection;

    public String  getResult()
    {
        return result;
    }


    public String request(String pageParams) throws IOException {
        URL url = null;
        String result="";
        url = new URL(_url + pageParams);

        connection = (HttpURLConnection) url.openConnection();
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            char[] buffer = new char[1024];
            int size=reader.read(buffer);
            int bytesRead=0;
            do
            {
                result += String.copyValueOf(buffer, 0, size);
                size=reader.read(buffer);
            }while (size>0);
        }

        connection.disconnect();
        return result;

    }


    @Override
    protected Void doInBackground(String... params) {
        String param = params[0];
        try {
            this.result = request(param);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.result = this.getResult();
    }
}
