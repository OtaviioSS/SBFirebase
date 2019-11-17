package controle;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bumptech.glide.Glide;
import com.example.socitybusiness.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import modelo.Empreendedor;
import modelo.IdeiaModelo;
import controle.cadastros.Activity_AddIdeia;
import controle.firebase.Conexao;
import controle.ideiaControle.IdeiaAdapter;

public class Activity_Inicio extends AppCompatActivity {
    private MenuItem btnmais;
    private FloatingActionButton btnfsair;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private IdeiaAdapter ideiaAdapter;
    private ImageView imgposts;
    FirebaseUser mFirebaseUser;
    private ImageButton btngostei;
    private String tipo;

    List<IdeiaModelo> listadeideias = new ArrayList<>();
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__inicio);
        inicializarcomponentes();
        inicializarfirebase();
        cliquesdebotao();
        setUpGoogleApiClient();
        recyclerView = findViewById(R.id.recyclerIicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ideiaAdapter = new IdeiaAdapter(listadeideias);
        recyclerView.setAdapter(ideiaAdapter);
        RecuperarIdeias();
        baixarimagens();
        verificarAutenticacao();
        BottomNavigationView navigationView = findViewById(R.id.navegtion);
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private void verificarAutenticacao() {
        if(FirebaseAuth.getInstance().getUid() == null){
          Intent intent =  new Intent(Activity_Inicio.this,MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
    private void abriraddideais() {
        Intent intent = new Intent(Activity_Inicio.this,Activity_AddIdeia.class);
        startActivity(intent);
    }






    private void baixarimagens() {
    }
    public void setUpGoogleApiClient(){
        //GoogleSignInOptions
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void RecuperarIdeias() {
        DatabaseReference ideiaRef = databaseReference.child("Ideias");
        ideiaRef.addValueEventListener(new ValueEventListener() {
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
    }
    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Inicio.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.addiadeiamenu:
                    abriraddideais();
                    return  true;
                case R.id.sair:
                    FirebaseAuth.getInstance().signOut();
                    verificarAutenticacao();
                    return true;
                case R.id.salvos:
                    break;
            }
            return false;
        }
    };
    private void cliquesdebotao() {
    }



    }
