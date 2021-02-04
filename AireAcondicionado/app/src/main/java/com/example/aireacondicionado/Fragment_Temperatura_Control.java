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
public class Fragment_Temperatura_Control extends Fragment {

    // paramatros de inicializacion del fragmento
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    //Inicio de elementos del fragmento
    private ProgressBar mProgresbar;
    TextView txtTemperatura;
    ImageButton btnMas,btnMenos;
    Button btnEnviar;
    EditText editTextTemperatura;
    //Inicio de variable temperatura
    int temperatura=0;

    //Inicio de las referencias de la base de FireBase
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    public Fragment_Temperatura_Control() {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento

        View root= inflater.inflate(R.layout.fragment__temperatura__control,container,false);
        //Colocar la referencia de cada una de los elementos del fragmento
        txtTemperatura=root.findViewById(R.id.txtTemp);
        btnMas=root.findViewById(R.id.btnMas);
        btnMenos=root.findViewById(R.id.btnMenos);
        btnEnviar=root.findViewById(R.id.btnEnviar);
        editTextTemperatura=root.findViewById(R.id.editTextTemperatura);
        // Configuracion de los elementos
        mProgresbar= root.findViewById(R.id.pbTemperatura);
        mProgresbar.setMax(30);
        mProgresbar.setMin(15);
        btnMas.setEnabled(false);
        btnMenos.setEnabled(false);
        //Llamado de metodos necesarios
        cargarDatos();
        botonMas();
        botonMens();
        enviar();
        return root ; }

    /**
     * Metodo no recibe ningun parametro
     * se encarga de setear una accion al boton Enviar
     *Envia las variables a la base de datos
     */

    public void enviar(){
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text= editTextTemperatura.getText().toString();
                int temp=Integer.parseInt(text);
                if(temp>=16 && temp<=30){
                    DatabaseReference myRef = database.getReference("Control").child("Temperatura");
                    myRef.setValue(temp);
                }

                else{
                    Toast toast= Toast.makeText(getContext(),"Fuera del los limites ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    /**
     * Metodo no recibe ningun parametro
     * Aumenta una unidad el valor de temperatura
     *Y sube el valor en la base de datos
     */
    public void botonMas(){
        btnMas.setEnabled(true);
        btnMenos.setEnabled(true);
        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("Control").child("Temperatura");
                if(temperatura<30){
                    myRef.setValue(temperatura+1);}

                else{
                    Toast toast= Toast.makeText(getContext(),"Fuera del los limites ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }

    /**
     * Metodo no recibe ningun parametro
     * Disminuye una unidad el valor de temperatura
     *Y sube el valor en la base de datos
     */
    void botonMens(){
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference myRef = database.getReference("Control").child("Temperatura");
                if(temperatura>16){
                    myRef.setValue(temperatura-1);}

                else{
                    Toast toast= Toast.makeText(getContext(),"Fuera del los limites ", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
    /**
     * Metodo no recibe ningun parametro
     * Lectura del dato de temperatura
     *De la base de tiempo real FireBase
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