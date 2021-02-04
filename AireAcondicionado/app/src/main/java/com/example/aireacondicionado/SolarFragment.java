package com.example.aireacondicionado;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 *Inicio de la programacion del fragmento SolarFragment
 */
public class SolarFragment extends Fragment {

    // paramatros de inicializacion del fragmento
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //Inicio de elementos del fragmento
    TextView txtBateria,txtPotencia,txtVida,txtLuz;
    ProgressBar pbBateria;
    //Inicio de las referencias de la base de FireBase
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Control").child("Sistema Solar");

    public SolarFragment() {
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
    public static SolarFragment newInstance(String param1, String param2) {
        SolarFragment fragment = new SolarFragment();
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
        View root=inflater.inflate(R.layout.fragment_solar, container, false);
        //Colocar la referencia de cada una de los elementos del fragmento
        txtBateria=root.findViewById(R.id.txtBateria);
        txtLuz=root.findViewById(R.id.txtLuz);
        txtPotencia=root.findViewById(R.id.txtPotencia);
        txtVida=root.findViewById(R.id.txtVida);
        pbBateria=root.findViewById(R.id.pbCarga);
        //Llamado de metodos necesarios
        obtenerBateria();
        obtenerPotencia();
        obtenerVida();
        return root;
    }
    /**
     * Metodo no recibe ningun parametro
     * Lectura del dato de bateria del sistema solar
     *Dependiendo del porcentaje se asigna color de la barra
     */
    void obtenerBateria(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.child("Bateria").getValue().toString();
                    txtBateria.setText(value+"%");
                    int bateria=Integer.parseInt(value);
                    pbBateria.setProgress(bateria);
                    if(bateria<=100 && bateria>=70){
                        txtLuz.setBackgroundColor(Color.GREEN);
                    }
                    else if(bateria<=69 && bateria>=30){
                        txtLuz.setBackgroundColor(Color.YELLOW);
                    }

                    else{
                        txtLuz.setBackgroundColor(Color.RED);
                        Toast.makeText(getContext(), "Alerta la bateria esta por agotarse", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
    /**
     * Metodo no recibe ningun parametro
     * Lectura del dato de vida del sistema solar
     *Coloca el valor en el TextView correspondiente
     */
    void obtenerVida(){

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.child("Vida").getValue().toString();
                    txtVida.setText(value+" horas");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }

    /**
     * Metodo no recibe ningun parametro
     * Lectura del dato de potencia del sistema solar
     *Coloca el valor en el TextView correspondiente
     */
    void obtenerPotencia(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.child("Potencia").getValue().toString();
                    txtPotencia.setText(value+" W");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}