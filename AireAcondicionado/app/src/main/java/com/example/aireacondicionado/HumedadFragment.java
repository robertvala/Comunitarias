package com.example.aireacondicionado;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 *Inicio de la programacion del fragmento Humedad
 */
public class HumedadFragment extends Fragment {

    // paramatros de inicializacion del fragmento
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //Inicio de elementos del fragmento
    ProgressBar pbHumedad;
    TextView txtHumedad;
    //Inicio de las referencias de la base de FireBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public HumedadFragment() {
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
    public static HumedadFragment newInstance(String param1, String param2) {
        HumedadFragment fragment = new HumedadFragment();
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
        //Colocar la referencia de cada una de los elementos del fragmento
        View root= inflater.inflate(R.layout.fragment_humedad, container, false);
        txtHumedad=root.findViewById(R.id.txtHumedad);
        pbHumedad=root.findViewById(R.id.pbHumedad);
        //Llamado de metodos necesarios
        cargarDatos();
        return root;
    }
    /**
     * Metodo no recibe ningun parametro
     * Lectura del dato de humedad
     *De la base de datos en tiempo real
     */
    void cargarDatos(){
        DatabaseReference myRef = database.getReference("Control");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                if(dataSnapshot.exists()) {
                    String value = dataSnapshot.child("Humedad").getValue().toString();
                    pbHumedad.setProgress(Integer.parseInt(value));
                    txtHumedad.setText(value+"%");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }


}