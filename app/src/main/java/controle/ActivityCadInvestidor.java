package controle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import modelo.Investidor;



public class ActivityCadInvestidor extends AppCompatActivity {


    private EditText txtnome;
    private EditText txtemail;
    private EditText txtsenha;
    private Button concluir;
    private FirebaseAuth auth;
    private EditText txtpatrimonio;
    private DatabaseReference databaseReference;
    private ImageButton addImgIN;
    private Uri imagemSelecionada;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_ia_ctivity);
        inicializarcomponentes();
        concluir.setOnClickListener(registro);
        addImgIN.setOnClickListener(selecionarimagem);

        inicializarfirebase();

    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(ActivityCadInvestidor.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializarcomponentes() {
        txtnome = findViewById(R.id.barnomeCadIN);
        txtpatrimonio = findViewById(R.id.vlpratimonio);
        txtemail = findViewById(R.id.baremailCadIN);
        txtsenha = findViewById(R.id.barsenhaCadIN);
        concluir = findViewById(R.id.btnconcluirCadIN);
        addImgIN = findViewById(R.id.btnselcfoto);



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
        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(ActivityCadInvestidor.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Investidor invest = new Investidor();
                    invest.setId(UUID.randomUUID().toString());
                    invest.setEmail(txtemail.getText().toString());
                    invest.setNome(txtnome.getText().toString());
                    invest.setPatrimoio(Integer.parseInt(txtpatrimonio.getText().toString()));
                    uploadimg();
                    databaseReference.child("Investidor").child(invest.getId()).setValue(invest);
                    limparcampos();
                    Toast.makeText(ActivityCadInvestidor.this,"Cadastrao com Sucesso",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ActivityCadInvestidor.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(ActivityCadInvestidor.this,"Erro ao cadastrar",Toast.LENGTH_SHORT).show();


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
        txtpatrimonio.setText("");
        txtsenha.setText("");
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            imagemSelecionada = data.getData();
            addImgIN.setImageURI(imagemSelecionada);

        }

    }

}
