package android.andreanidr.com.peoplefinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    private Button btnBuscar;
    private Button btnCadastrar;
    private EditText identificador;
    private EditText nome;
    private EditText idade;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBuscar = (Button) findViewById(R.id.buscar);
        btnCadastrar = (Button) findViewById(R.id.cadastrar);

        identificador = (EditText) findViewById(R.id.identificador);
        nome = (EditText) findViewById(R.id.name);
        idade = (EditText) findViewById(R.id.idade);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar(v);
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar(v);
            }
        });

    }


    public void buscar(View v) {

        String result="";

        WebRequest web = new WebRequest();
        AsyncTask<String, Void, Void> r = web.execute("getPerson?identifier=" + ((EditText) findViewById(R.id.identificador)).getText().toString());
        try {
            r.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        result = web.getResult();



        String[] parsed = result.split(", ");
        nome.setText(parsed[1]);
        idade.setText(parsed[2]);
    }

    public void cadastrar(View v) {

        String name = this.nome.getText().toString();
        String idade = this.idade.getText().toString();
        String identificador = this.identificador.getText().toString();

        WebRequest web = new WebRequest();
        AsyncTask<String, Void, Void> r = web.execute("add?name="+name+"&age="+idade+"&identifier="+identificador);

        try {
            r.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Pessoa Cadastrada!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
}
