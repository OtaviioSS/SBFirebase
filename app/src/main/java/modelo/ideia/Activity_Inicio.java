package modelo.ideia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;



import com.example.socitybusiness.Activity_AddIdeia;
import com.example.socitybusiness.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
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

import Bancos.BacoSqlite;
import controle.MainActivity;
import modelo.IdeiaModelo;


public class Activity_Inicio extends AppCompatActivity {

    private FloatingActionButton btnmais;
    private FloatingActionButton btnfsair;
    private RecyclerView recyclerView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    ArrayList<IdeiaModelo> listadeideias;
    ArrayAdapter<IdeiaModelo> ideaAdapterArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        inicializarcomponentes();
        inicializarfirebase();
        cliquesdebotao();

        listadeideias = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerIicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CarregarIdeias();
        IdeaAdapter adapter =  new IdeaAdapter(listadeideias);
        recyclerView.setAdapter( adapter);

    }
    private void CarregarIdeias() {


        /*BacoSqlite bacoSqlite = new BacoSqlite(Activity_Inicio.this);
        bacoSqlite.getAll();
        listadeideias.set(bacoSqlite.getAll())*/

        Query query = databaseReference.child("Ideias");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot obj : dataSnapshot.getChildren()) {
                        IdeiaModelo ideia = dataSnapshot.getValue(IdeiaModelo.class);
                        listadeideias.add(ideia);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));
        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));
        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));
        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));
        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));
        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));
        listadeideias.add(new IdeiaModelo("otavio","kdksdkmskdmdkm",0,0));

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





}