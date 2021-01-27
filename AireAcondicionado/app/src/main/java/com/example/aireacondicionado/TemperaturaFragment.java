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
 * A simple {@link Fragment} subclass.
 * Use the {@link TemperaturaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TemperaturaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressBar mProgresbar;
    TextView txtTemperatura;
    ImageButton btnMas,btnMenos;
    Button btnEnviar;
    EditText editTextTemperatura;
    int temperatura=0;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TemperaturaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TemperaturaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TemperaturaFragment newInstance(String param1, String param2) {
        TemperaturaFragment fragment = new TemperaturaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root= inflater.inflate(R.layout.fragment_temperatura,container,false);
        txtTemperatura=root.findViewById(R.id.txtTemp);
        // Write a message to the database
        mProgresbar= root.findViewById(R.id.pbTemperatura);
        mProgresbar.setMax(30);
        mProgresbar.setMin(15);
        btnMas=root.findViewById(R.id.btnMas);
        btnMenos=root.findViewById(R.id.btnMenos);
        btnEnviar=root.findViewById(R.id.btnEnviar);
        editTextTemperatura=root.findViewById(R.id.editTextTemperatura);
        btnMas.setEnabled(false);
        btnMenos.setEnabled(false);
        cargarDatos();
        botonMas();
        botonMens();
        enviar();
        return root ; }

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