package com.example.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //objeto Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    //Objeto Coordenadas
    Coordenadas coord = new Coordenadas();

    //Declaracion de objetos a relacionar

    private EditText latitud;
    private EditText longitud;
    private EditText temp;
    private Spinner sp_temp;
    private Spinner sp_clima;
    private Button guardar;

    private String temp_selecc;
    private String clima_selecc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciar();
    }

    private void iniciar() {
        //Enlazado con XML

        latitud = (EditText) findViewById(R.id.id_latitud);
        longitud = (EditText) findViewById(R.id.id_longitud);
        temp = (EditText) findViewById(R.id.id_temp);
        sp_temp = (Spinner) findViewById(R.id.spinner_temp);
        sp_clima = (Spinner) findViewById(R.id.spinner_clima);
        guardar = (Button) findViewById(R.id.button);

        //Spinner action
        ArrayAdapter adapter_temp = ArrayAdapter.createFromResource(this,
                R.array.temp_list, android.R.layout.simple_spinner_item);
        sp_temp.setAdapter(adapter_temp);
        sp_temp.setOnItemSelectedListener(this);

        ArrayAdapter adapter_clima = ArrayAdapter.createFromResource(this,
                R.array.clima_list, android.R.layout.simple_spinner_item);
        sp_clima.setAdapter(adapter_clima);
        sp_clima.setOnItemSelectedListener(this);


        //Asignar listener para el clic del boton
        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //temp_selecc = parent.getItemAtPosition(position).toString();
        //clima_selecc = parent.getItemAtPosition(position).toString();
        sp_temp.getSelectedItem().toString();
        sp_clima.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void guardar(){
        coord.setLatitud(latitud.getText().toString());
        coord.setLongitud(longitud.getText().toString());
        coord.setTemperatura(temp.getText().toString());
        coord.setClima(sp_clima.getSelectedItem().toString());

        //Guardado en Firebase
        Map<String, Object> user = new HashMap<>();
        //La informacion se manda traer de la clase Lugar
        user.put("1. LATITUD", coord.getLatitud());
        user.put("2. LONGITUD", coord.getLongitud());
        user.put("3. TEMPERATURA", coord.getTemperatura() + sp_temp.getSelectedItem().toString());
        user.put("4. CLIMA", coord.getClima());

        // Add a new document with a generated ID
        db.collection("COORDENADAS")
                .add(user)

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

        Toast.makeText(MainActivity.this, "¡Información guardada!", Toast.LENGTH_SHORT).show();

    }

}





