package com.giulia.myapplication3.app;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.giulia.myapplication3.R;
import com.giulia.myapplication3.adapter.Myadapter;

import org.json.JSONArray;
import org.json.JSONObject;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    File filepath = null;
    FileWriter writer = null;
    private TextView tv;
    String jsonStr = null;

    // definisco un ArrayList con il nome delle cartelle e/o file
    ArrayList<String> listp;
    //ARRAY DI IMMAGINI
    ArrayList<Integer> list_i;
    ArrayList<Myobject> arrayobj;
    Myobject o1, o2, o3, o4;
    JsonUtil ju = null;
    String s = null;
    FileInputStream stream = null;
    File yourFile = null;
    File root = null;
    FileChannel fc;
    MappedByteBuffer bb;
    private ListView lv;
    ArrayList<Myobject> arrayobj2;
    JSONArray directories;
    JSONArray nine;
    FileOutputStream outputStream;
    OutputStream bf;
    Permission p;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.view);
        tv.setText("lili");
        // String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        root = new File(Environment.getExternalStorageDirectory(), "Documents");
        filepath = new File(root, "giulia4.json");


        // if external memory exists and folder with name Documents
        // file path to save

        o1 = new Myobject();
        o2 = new Myobject();
        o3 = new Myobject();
        o4 = new Myobject();

        o1.setNome("cartella1");
        o1.setTipo("dir");
        o2.setNome("cartella2");
        o2.setTipo("dir");
        o3.setNome("file1");
        o3.setTipo("file");
        o4.setNome("file2");
        o4.setTipo("file");


        listp = new ArrayList<>();
        list_i = new ArrayList<>();
        arrayobj = new ArrayList<>();
        arrayobj2 = new ArrayList<>();
        arrayobj2.add(o1);
        arrayobj2.add(o2);
        arrayobj2.add(o3);
        arrayobj2.add(o4);


        lv = (ListView) findViewById(R.id.list);
        boolean r;
        r=p.isExternalStorageWritable();
        if (r==true) {
            try {
                writer = new FileWriter(filepath);
                ju = new JsonUtil();
                //la classe JsonUtil ha un metodo toJson che mi ritorna una stringa, passo al
                //metodo un oggetto e la prima volta il nome della cartella radice, anche se non ci sarebbe bisogno
                //nine è un array json
                nine = ju.toJSon(arrayobj2, Environment.getExternalStorageDirectory() + "/Documents");
                s = nine.toString();

                writer.append(s);
                writer.flush();
                writer.close();

            } catch (IOException e4) {
                e4.printStackTrace();
            }
        }else

        {

            Toast.makeText(getApplicationContext(),"Impossibile scrivere il dispositivo",Toast.LENGTH_SHORT);

        }
        //Lettura file giulia4.json

        r=p.isExternalStorageReadable();
        if(r==true){
        try {
            yourFile = new File(Environment.getExternalStorageDirectory(), "Documents/giulia4.json");
            stream = new FileInputStream(yourFile);

            try {
                fc = stream.getChannel();
                bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());

                jsonStr = Charset.defaultCharset().decode(bb).toString();
                tv.setText(jsonStr);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                stream.close();
            }

/*

            FileReader reader = new FileReader("giulia2.json");
            BufferedReader filebuf = new BufferedReader(reader);
            String nextStr;
            nextStr = filebuf.readLine();     // legge una riga del file
            while (nextStr != "]") {
                nextStr = filebuf.readLine();*/ // legge la prossima riga
            JSONArray jsonArray0=new JSONArray(jsonStr);
            //quello che leggo lo metto in un  array json

            //il primo elemento dell'array è un oggetto json
            //dovrei fare un ciclo che analizza tutti i componenti dell' array json principale
            JSONObject object2= jsonArray0.getJSONObject(0);



            // directories è un array json
            // getJSONArray(int index)-> Returns the value at index if it exists and is a JSONArray
            directories = object2.getJSONArray(Environment.getExternalStorageDirectory() + "/Documents");

            /*directories è l'array che è il valore del primo oggetto json dell' array json principale
            ottengo la sua lunghezza e analizzo ogni suo oggetto json per creare la visualizzazione sull' app
            ogni elemento di questo array è un oggetto json che ha due chiavi "nome" e "valore",
            quindi estraggo sottoforma di stringa il nome e il tipo corrispondente alle chiavi
            li passo a un oggetto "Myobject" per aggiungere poi questi elementi a un array che mi servirà per la listview
            la chiave "tipo" mi è necessaria per la visualizzazione dell' icona accanto a ogni nome di "file" generico che sia file
            directory */

            for (int i = 0; i < directories.length(); i++) {
                JSONObject c = directories.getJSONObject(i);
                String name = c.getString("name");
                String tipo = c.getString("tipo");
                Myobject o = new Myobject(name, tipo);
                arrayobj.add(o);
            }



            for (int z = 0; z < arrayobj.size(); z++) {
                listp.add(arrayobj.get(z).getNome());
            }


            for (int k = 0; k < arrayobj.size(); k++) {
                if (arrayobj.get(k).getTipo().equals("dir"))
                    list_i.add(R.drawable.folder);
                else
                    list_i.add(R.drawable.file);

            }

            //passaggio della mia lista di elementi all' adapter per la visualizzazione
            Myadapter myAdapter = new Myadapter(MainActivity.this, list_i, listp);
            lv.setAdapter(myAdapter);


        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch (Exception e3) {
            e3.printStackTrace();

        }


        //INSERIRE NUOVI OGGETTI NEL FILE JSON



            int flag = 0;
           JSONObject result;
            //se vogliamo inserire nuove cartelle all' interno di cartelle già esistenti
            //dobbiamo cercare la cartella con il nostro nome d' interesse
            //facciamolo generico, supponiamo che vogliamo aggiungere in cartella1, qualcosa e aggiornare il file json
            //e la vista del file json
try{
            for (int n = 0; n < directories.length() && flag == 0; n++) {

                JSONObject c = directories.getJSONObject(n);
                //Esiste una cartella che si chiama cartella1?

                if (c.getString("name").equals("cartella1") && c.getString("tipo").equals("dir")) {
                    flag = 1;
                    String uno;
                    String due;
                    uno = c.getString("name");
                    due = c.getString("tipo");
                    //Myobject o1=new Myobject(uno,due);
                    JsonUtil u2 = new JsonUtil();
                    // nine è l'array json principale che contiene tutto
                    result = u2.createJSON(nine,uno, due);


                    File externalStorageDir = Environment.getExternalStorageDirectory();
                    File myFile = new File(externalStorageDir , "Documents/giulia4.json");

                    if(myFile.exists())
                    {
                        try
                        {
                            FileOutputStream fOut =new FileOutputStream(myFile);
                            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                            myOutWriter.append((CharSequence) result);
                            myOutWriter.close();
                            fOut.close();

                        } catch(Exception e)
                        { e.printStackTrace();

                        }
                    }
                    else
                    {
                        myFile.createNewFile();


                }}
            }}catch (IOException e1) {
    e1.printStackTrace();
}
catch (Exception e5) {
    e5.printStackTrace();

}}
    else

    {

       Toast.makeText(getApplicationContext(),"Impossibile leggere il dispositivo",Toast.LENGTH_SHORT);

    }

    //vediamo se ha modificato il file come dico io










    }}

             // tmp hash map for single contact
               /* HashMap<String, String> contact = new HashMap<>();

                // adding each child node to HashMap key => value
                contact.put("name", name);
                             // adding contact to contact list
                items.add(contact);*/

