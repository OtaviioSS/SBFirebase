package controle.cadastros;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socitybusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import controle.Activity_Inicio;
import controle.MainActivity;
import controle.firebase.Conexao;
import modelo.Empreendedor;



public class Activity_Cad_EM extends AppCompatActivity {


    private EditText txtnome;
    private EditText txtemail;
    private EditText txtsenha;
    private Button concluir;
    private ImageButton addFotoEM;
    private Uri imagemSelecionada;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;
    private Uri url;
    private String tipo;
    Empreendedor empreendedor = new Empreendedor();
    MainActivity mainActivity = new MainActivity();



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad__em_);
        tipo = mainActivity.tipo;
        inicializarcomponentes();
        concluir.setOnClickListener(registro);
        addFotoEM.setOnClickListener(selecionarimagem);
        inicializarfirebase();


    }



    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_Cad_EM.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    private void inicializarcomponentes() {
        txtnome = findViewById(R.id.barnomeCadEm);
        txtemail = findViewById(R.id.baremailCadEm);
        txtsenha = findViewById(R.id.barsenhaCadEm);
        concluir = findViewById(R.id.btnconcluirCadEm);
        addFotoEM = findViewById(R.id.btnselcFotoCadEm);


    }
    View.OnClickListener selecionarimagem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult( intent,0);
        }
    };
    @Override
    protected void onStart(){
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
    View.OnClickListener registro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = txtemail.getText().toString().trim();
            String senha = txtsenha.getText().toString().trim();
            criarUser(email,senha);

        }
    };



    private void criarUser(String email,String senha) {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(Activity_Cad_EM.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try{
                if (task.isSuccessful()){
                    empreendedor.setId(UUID.randomUUID().toString());
                    empreendedor.setEmail(txtemail.getText().toString());
                    empreendedor.setNome(txtnome.getText().toString());
                    uploadimg();
                    empreendedor.setImguser(url.toString());
                    databaseReference.child("Empreendedor").child(empreendedor.getId()).setValue(empreendedor);
                    Intent intent = new Intent(Activity_Cad_EM.this, Activity_Inicio.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    limparcampos();
                    Toast.makeText(Activity_Cad_EM.this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(Activity_Cad_EM.this, "Erro ao Cadastrar", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }



    private void uploadimg() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(imagemSelecionada)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Toast.makeText(Activity_Cad_EM.this,"Sucesso ao carregar a imagem",Toast.LENGTH_SHORT).show();
                                url = uri;


                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("Erro", e.getMessage(), e);

            }
        });




    }


    private void limparcampos() {
        txtemail.setText("");
        txtnome.setText("");
        txtsenha.setText("");
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            imagemSelecionada = data.getData();
            addFotoEM.setImageURI(imagemSelecionada);

        }

    }

}
