package controle;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import modelo.Empreendedor;



public class Cad_EM_Activity extends AppCompatActivity {


    private EditText txtnome;
    private EditText txtemail;
    private EditText txtsenha;
    private Button concluir;
    private ImageButton addFotoEM;
    private Uri imagemSelecionada;
    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad__em_);
        inicializarcomponentes();
        concluir.setOnClickListener(registro);
        addFotoEM.setOnClickListener(selecionarimagem);
        inicializarfirebase();


    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Cad_EM_Activity.this);
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
    protected void onStart() {
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

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(Cad_EM_Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                try{

                if (task.isSuccessful()) {
                    Empreendedor empreendedor = new Empreendedor();
                    empreendedor.setId(UUID.randomUUID().toString());
                    empreendedor.setEmail(txtemail.getText().toString());
                    empreendedor.setNome(txtnome.getText().toString());
                    uploadimg();
                    databaseReference.child("Empreendedor").child(empreendedor.getId()).setValue(empreendedor);
                    limparcampos();
                    Toast.makeText(Cad_EM_Activity.this, "Cadastrao com Sucesso", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Cad_EM_Activity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(Cad_EM_Activity.this, "Erro ao cadastrar", Toast.LENGTH_SHORT).show();


                }
            }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void uploadimg() {
        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" +filename);
        ref.putFile(imagemSelecionada)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Sucesso", uri.toString());
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
