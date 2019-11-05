package modelo.ideia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import modelo.IdeiaModelo;
import modelo.IdeiasHolder;


public class Activity_Inicio extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    private FloatingActionButton btnmais;
    private RecyclerView orecyclerView;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<IdeiaModelo, IdeiasHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<IdeiaModelo> options;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        inicializarcomponentes();
        cliquesdebotao();
        orecyclerView = findViewById(R.id.recyclerIicio);
        inicializarfirebase();
        eventoDatabase();

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        databaseReference = firebaseDatabase.getReference("Ideias");



    }



    private void eventoDatabase() {
      options = new FirebaseRecyclerOptions.Builder<IdeiaModelo>().setQuery(databaseReference,IdeiaModelo.class).build();

      firebaseRecyclerAdapter =  new FirebaseRecyclerAdapter<IdeiaModelo, IdeiasHolder>(options) {
          @Override
          protected void onBindViewHolder(@NonNull IdeiasHolder holder, int position, @NonNull IdeiaModelo model) {

              holder.setDetails(getApplicationContext(),model.getNomeuser(),model.getImguser(),model.getConteudo(),model.getImgpub()    );

          }

          @NonNull
          @Override
          public IdeiasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

              View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_nf,parent,false);

              IdeiasHolder viewHolder = new IdeiasHolder(view);

              viewHolder.setOnClickListener(new IdeiasHolder.ClickListener() {
                  @Override
                  public void onItemClick(View view, int position) {
                      Toast.makeText(Activity_Inicio.this,"Olá",Toast.LENGTH_LONG).show();
                  }

                  @Override
                  public void onItemLongClick(View view, int position) {

                      Toast.makeText(Activity_Inicio.this,"Clique Longue",Toast.LENGTH_LONG).show();
                  }
              });

              return viewHolder;
          }
      };

      orecyclerView.setLayoutManager(linearLayoutManager);
      firebaseRecyclerAdapter.startListening();
      orecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter !=null){
            firebaseRecyclerAdapter.startListening();
        }
    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Inicio.this);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void cliquesdebotao() {
        btnmais.setOnClickListener(abrirAddIdeias);
    }

    private void inicializarcomponentes() {
        btnmais = findViewById(R.id.floatingAdd);


    }

    View.OnClickListener abrirAddIdeias = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_Inicio.this, Activity_AddIdeia.class);
            startActivity(intent);

        }
    };


    @Override
    public void onBackPressed() {
    }
    }
