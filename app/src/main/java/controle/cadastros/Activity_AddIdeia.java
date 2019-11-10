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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import modelo.IdeiaModelo;

public class Activity_AddIdeia extends AppCompatActivity {
    private ImageButton addimg;
    private Button concluir;
    private EditText conteudo;
    private DatabaseReference databaseReference;
    private Uri imagemSelecionada;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_ideia);
        inicializarcompnetente();
        cliquesdebotoes();
        inicializarfirebase();



    }

    private void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_AddIdeia.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


    }

    private void cliquesdebotoes() {

        addimg.setOnClickListener(selecionarimagem);
        concluir.setOnClickListener(adicionarIdeia);
    }

    private void inicializarcompnetente() {
        conteudo = findViewById(R.id.conteudoAddIdeia);
        addimg = findViewById(R.id.btnImgAddIdeia);
        concluir = findViewById(R.id.btnConcluirAddIdeia);

    }

    View.OnClickListener selecionarimagem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult( intent,0);
        }
    };
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
    View.OnClickListener adicionarIdeia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                IdeiaModelo ideia = new IdeiaModelo();
                ideia.setIdEM(UUID.randomUUID().toString());
                ideia.setConteudo(conteudo.getText().toString());
                databaseReference.child("Ideias").child(ideia.getIdEM()).setValue(ideia);
                uploadimg();
                Toast.makeText(Activity_AddIdeia.this,"Ideia Adicionada com Sucesso",Toast.LENGTH_LONG).show();
            }catch (RuntimeException err){
                Toast.makeText(Activity_AddIdeia.this,"Erro" +err,Toast.LENGTH_LONG).show();

            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            imagemSelecionada = data.getData();
            addimg.setImageURI(imagemSelecionada);

        }

    }
}