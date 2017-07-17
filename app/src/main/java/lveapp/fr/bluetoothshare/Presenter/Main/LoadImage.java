package lveapp.fr.bluetoothshare.Presenter.Main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Maranatha on 17/07/2017.
 */

public class LoadImage extends AsyncTask<Void, Void, String> {

    private String codeRetour = "";
    private URL url = null;
    private HttpURLConnection httpURLConnection;

    private HashMap<String, String> postDataParams;
    private Bitmap imageBitmap;
    private String imageFileName;

    private String imageRealPath;
    private String urlPostData;
    private MainView.OnLoadImageFinished loadImageFinished;

    public void LoadImageData(MainView.OnLoadImageFinished loadImageFinished, String urlPostData, String imageRealPath, Bitmap imageBitmap){
        this.loadImageFinished = loadImageFinished;
        this.urlPostData = urlPostData;
        this.imageRealPath = imageRealPath;
        this.imageBitmap = imageBitmap;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... voids) {

        createFileName();

        Log.e("TAG_ERREUR", imageRealPath);

        if(imageBitmap==null){
            Log.e("TAG_ERREUR", "Erreur Bitmap NULL");
        }

        postDataParams = new HashMap<>();
        String base64 = getBase64ImageString(imageBitmap);
        postDataParams.put("base64", base64);
        postDataParams.put("imageName", imageFileName);

        try{
            url = new URL(urlPostData);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                while ((line=br.readLine()) != null){
                    codeRetour+=line;
                }
            }
        }
        catch (Exception e)
        {}

        httpURLConnection.disconnect();

        return codeRetour;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s.contains("Error"))
            loadImageFinished.onLoadError(s);
        else
            loadImageFinished.onLoadSuccess(urlPostData+"images/"+imageFileName);
    }


    /**
     * Create image file
     * @return
     * @throws IOException
     */
    private void createFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File file = new File(imageRealPath);
        imageFileName = timeStamp+"-"+file.getName().trim().replace(" ", "-").replace(".jpg", "").replace(".jpeg", "").replace(".png", "").replace(".gif", "");
    }

    /**
     * Get string from post data
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder resultat = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if(first){
                first = false;
            }
            else {
                resultat.append("&");
            }
            resultat.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            resultat.append("=");
            resultat.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return resultat.toString();
    }


    /**
     * Decode bitmap to string width ratio
     * @param bitmap
     * @return
     */
    private String getBase64ImageString(Bitmap bitmap) {

        Bitmap imageBitmap = null;
        float maxWidth = 512.0f;
        float newWidth=bitmap.getWidth();
        float newHeight=bitmap.getHeight();
        float rapportWidth = maxWidth/bitmap.getWidth();
        if(rapportWidth < 1){
            newWidth=bitmap.getWidth()*rapportWidth;
            newHeight=bitmap.getHeight()*rapportWidth;
        }

        imageBitmap = Bitmap.createScaledBitmap(bitmap, (int)newWidth, (int)newHeight, false);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=null;
        try{
            System.gc();
            temp= Base64.encodeToString(b, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }catch(OutOfMemoryError e){
            baos=new  ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
            b=baos.toByteArray();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
            Log.e("TAG_ERROR", "Out of memory error catched");
        }
        return temp;
    }

    /**
     * Decode bitmap to string without ratio
     * @param bitmap
     * @return
     */
    private String getBase64ImageStringOriginalSize(Bitmap bitmap) {
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=null;
        try{
            System.gc();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
        }catch(Exception e){
            e.printStackTrace();
        }catch(OutOfMemoryError e){
            baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,50, baos);
            b=baos.toByteArray();
            temp=Base64.encodeToString(b, Base64.DEFAULT);
            Log.e("TAG_ERROR", "Out of memory error catched");
        }
        return temp;
    }

    /**
     * Decode string image to bitmap
     * @param input
     * @return
     */
    private Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
