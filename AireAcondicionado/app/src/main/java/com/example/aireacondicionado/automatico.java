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
 * A simple {@link Fragment} subclass.
 * Use the {@link automatico#newInstance} factory method to
 * create an instance of this fragment.
 */
public class automatico extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editTextInicio,editTextFinal,editTextTemperatura;
    TextView txtrepeticion;
    Spinner spRepetecion;
    Button btnEnviarAuto;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Control").child("Automatico");

    public automatico() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment automatico.
     */
    // TODO: Rename and change types and number of parameters
    public static automatico newInstance(String param1, String param2) {
        automatico fragment = new automatico();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_automatico, container, false);
        editTextFinal=root.findViewById(R.id.editTextFinal);
        editTextInicio=root.findViewById(R.id.editTextInicio);
        editTextTemperatura=root.findViewById(R.id.editTextControl);
        btnEnviarAuto=root.findViewById(R.id.btnEnviarAuto);
        txtrepeticion=root.findViewById(R.id.txtRepeticion);
        spRepetecion=root.findViewById(R.id.spRepeticion);
        String [] opciones={"Diariamente","Una sola vez"};
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,opciones);
        spRepetecion.setAdapter(adapter);
        String repeticion=spRepetecion.getSelectedItem().toString();
        txtrepeticion.setText(repeticion);
        enviar();
        return root;
    }

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