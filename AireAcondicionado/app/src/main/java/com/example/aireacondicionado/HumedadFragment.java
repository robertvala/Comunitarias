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
 * A simple {@link Fragment} subclass.
 * Use the {@link HumedadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HumedadFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ProgressBar pbHumedad;
    TextView txtHumedad;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public HumedadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HumedadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HumedadFragment newInstance(String param1, String param2) {
        HumedadFragment fragment = new HumedadFragment();
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
            //txtMensaje=getActivity().findViewById(R.id.txtMensaje);

        }

        //txtMensaje.setText("Hola");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_humedad, container, false);
        txtHumedad=root.findViewById(R.id.txtHumedad);
        pbHumedad=root.findViewById(R.id.pbHumedad);
        cargarDatos();
        return root;
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