package com.example.aireacondicionado;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 *Inicio de la programacion del fragmento temperatura
 */
public class TemperaturaFragment extends Fragment {
    // paramatros de inicializacion del fragmento
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //Inicio de elementos del fragmento
    private ProgressBar mProgresbar;
    TextView txtTemperatura;
    //Inicio de variable temperatura
    int temperatura=0;

    //Inicio de las referencias de la base de FireBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public TemperaturaFragment() {
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
    public static TemperaturaFragment newInstance(String param1, String param2) {
        TemperaturaFragment fragment = new TemperaturaFragment();
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
        //WebView myWebView = root.findbyV
        //myWebView.loadUrl("http://www.example.com");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


        }
    }
    //Metodo que se ejecuta para crear la vista del fragmento
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento

        View root= inflater.inflate(R.layout.fragment_temperatura,container,false);
        //Colocar la referencia de cada una de los elementos del fragmento
        txtTemperatura=root.findViewById(R.id.txtTemp);
        mProgresbar= root.findViewById(R.id.pbTemperatura);
        // Configuracion de los elementos
        mProgresbar.setMax(30);
        mProgresbar.setMin(15);

        //Llamado de metodos necesarios
        cargarDatos();
        return root ; }

        void cargarDatos(){
            DatabaseReference myRef = database.getReference("Control");
            // Read from the database
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    if(dataSnapshot.exists()) {
                        String value = dataSnapshot.child("Temperatura").getValue().toString();
                        txtTemperatura.setText(value+" °C");
                        mProgresbar.setProgress(Integer.parseInt(value));
                        temperatura=Integer.parseInt(value);
                    }

                }

                @Override
                public void onCancelled(DatabaseError error) {

                }
            });
        }
}