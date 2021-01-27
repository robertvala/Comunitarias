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
 * A simple {@link Fragment} subclass.
 * Use the {@link SolarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SolarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView txtBateria,txtPotencia,txtVida,txtLuz;
    ProgressBar pbBateria;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Control").child("Sistema Solar");

    public SolarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SolarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SolarFragment newInstance(String param1, String param2) {
        SolarFragment fragment = new SolarFragment();
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
        View root=inflater.inflate(R.layout.fragment_solar, container, false);
        txtBateria=root.findViewById(R.id.txtBateria);
        txtLuz=root.findViewById(R.id.txtLuz);
        txtPotencia=root.findViewById(R.id.txtPotencia);
        txtVida=root.findViewById(R.id.txtVida);
        pbBateria=root.findViewById(R.id.pbCarga);
        obtenerBateria();
        obtenerPotencia();
        obtenerVida();
        //txtVida.setText("3 horas");
        return root;
    }

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