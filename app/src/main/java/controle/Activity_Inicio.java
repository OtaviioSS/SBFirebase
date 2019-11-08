package controle;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;



import com.example.socitybusiness.Activity_AddIdeia;
import com.example.socitybusiness.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import modelo.IdeiaModelo;
import modelo.ideia.IdeiaAdapter;


public class Activity_Inicio extends AppCompatActivity {

    private FloatingActionButton btnmais;
    private FloatingActionButton btnfsair;
    private RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IdeiaAdapter ideiaAdapter;


    List<IdeiaModelo> listadeideias = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        inicializarcomponentes();
        inicializarfirebase();
        cliquesdebotao();

        recyclerView = findViewById(R.id.recyclerIicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ideiaAdapter = new IdeiaAdapter(listadeideias);
        recyclerView.setAdapter(ideiaAdapter);

        RecuperarIdeias();






    }

    private void RecuperarIdeias() {
        DatabaseReference empresaRef = databaseReference.child("Ideias");
        empresaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                listadeideias.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    listadeideias.add(ds.getValue(IdeiaModelo.class) );
                }

                ideiaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private void inicializarcomponentes() {
        btnmais = findViewById(R.id.floatingAdd);
        btnfsair = findViewById(R.id.btnsair);


    }
    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Inicio.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void cliquesdebotao() {
        btnmais.setOnClickListener(abrirAddIdeias);
        btnfsair.setOnClickListener(sairdoApp);
    }







    View.OnClickListener abrirAddIdeias = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_Inicio.this, Activity_AddIdeia.class);
            startActivity(intent);

        }
    }; View.OnClickListener sairdoApp = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Activity_Inicio.this, MainActivity.class);
            startActivity(intent);

    }};


    private  void singOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();;


    }







}