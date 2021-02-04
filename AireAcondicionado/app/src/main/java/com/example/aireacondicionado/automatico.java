package com.example.aireacondicionado;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
*Inicio de la programacion del fragmento que muestra la configuracion automatica
 * Del aire acondicionado
 */
public class automatico extends Fragment {

    // paramatros de inicializacion del fragmento
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    //Inicio de elementos del fragmento
    EditText editTextInicio,editTextFinal,editTextTemperatura;
    TextView txtrepeticion;
    Spinner spRepetecion;
    Button btnEnviarAuto;

    //Inicio de las referencias de la base de FireBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Control").child("Automatico");

    public automatico() {
        // Se requiere crear un contructor vacio que sea vacio
    }

    /**
     * Se crea este metodo para crear una nueva instancia del
     * fragmento utilizando los parametros:
     *
     * @param param1 Parametro 1.
     * @param param2 Parametro 2.
     * @returna una nueva instancia de este fragmento
     */

    public static automatico newInstance(String param1, String param2) {
        automatico fragment = new automatico();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    //Metodo que se ejecuta al iniciar el fragmento
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
//Metodo que se ejecuta para crear la vista del fragmento
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View root=inflater.inflate(R.layout.fragment_automatico, container, false);

        //Colocar la referencia de cada una de los elementos del fragmento
        editTextFinal=root.findViewById(R.id.editTextFinal);
        editTextInicio=root.findViewById(R.id.editTextInicio);
        editTextTemperatura=root.findViewById(R.id.editTextControl);
        btnEnviarAuto=root.findViewById(R.id.btnEnviarAuto);
        txtrepeticion=root.findViewById(R.id.txtRepeticion);
        spRepetecion=root.findViewById(R.id.spRepeticion);

        //Configuracion del spinner
        String [] opciones={"Diariamente","Una sola vez"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,opciones);
        spRepetecion.setAdapter(adapter);
        String repeticion=spRepetecion.getSelectedItem().toString();
        txtrepeticion.setText(repeticion);

        //Llamado del metodo enviar
        enviar();
        return root;
    }


    /**
     * Metodo no recibe ningun parametro
     * se encarga de setear una accion al boton Enviar
     *Envia los parametros a la base de datos

     */
    void enviar(){
        btnEnviarAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inicio=editTextInicio.getText().toString();
                String fin=editTextFinal.getText().toString();
                String temperatura=editTextTemperatura.getText().toString();

                myRef.child("HoraInicial").setValue(inicio);
                myRef.child("HoraFinal").setValue(fin);
                myRef.child("TemperaturaControl").setValue(temperatura);

                String repeticion=spRepetecion.getSelectedItem().toString();
                txtrepeticion.setText(repeticion);

                myRef.child("Repeticion").setValue(repeticion);


                Toast.makeText(getContext(), "Enviado con exito", Toast.LENGTH_SHORT).show();


            }
        });
    }
}