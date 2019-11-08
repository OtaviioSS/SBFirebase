package com.example.socitybusiness;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import modelo.IdeiaModelo;

public class Activity_AddIdeia extends AppCompatActivity {

    private static  final int SELECAO_GALERIA = 200;
    private ImageButton addimg;
    private Button concluir;
    private TextView nome;
    private EditText conteudo;
    private ImageView imguser;
    private DatabaseReference databaseReference;
    private Uri imagemSelecionada;
    private String urlImagemSelecionada ="";
    StorageReference storageReference;
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
        nome= findViewById(R.id.username);
        conteudo = findViewById(R.id.conteudoAddIdeia);
        addimg = findViewById(R.id.btnImgAddIdeia);
        concluir = findViewById(R.id.btnConcluirAddIdeia);

    }

    View.OnClickListener selecionarimagem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if(intent.resolveActivity(getPackageManager())!=null){
                startActivityForResult( intent,SELECAO_GALERIA);

            }
        }
    };

    View.OnClickListener adicionarIdeia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                IdeiaModelo ideia = new IdeiaModelo();
            ideia.setIdEM(UUID.randomUUID().toString());
            ideia.setConteudo(conteudo.getText().toString());
            databaseReference.child("Ideias").child(ideia.getIdEM()).setValue(ideia);
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


        String filename = UUID.randomUUID().toString();
        final StorageReference ref = FirebaseStorage.getInstance().getReference("/images/" + filename);
        ref.putFile(imagemSelecionada)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                        firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("Sucesso", uri.toString());
                                urlImagemSelecionada = uri.toString();

                            }


                        });
    }
});
        }}