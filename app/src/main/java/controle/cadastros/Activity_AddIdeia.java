package controle.cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.socitybusiness.R;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.UUID;
import modelo.IdeiaModelo;

public class Activity_AddIdeia extends AppCompatActivity {

    private EditText descricaodadideia;
    private Button concluir;
    private EditText conteudo;
    private TextView nome;
    private DatabaseReference databaseReference;
    private ImageView imagePerfilusuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_ideia);
        inicializarcompnetente();
        cliquesdebotoes();
        inicializarfirebase();


    }

    public void inicializarfirebase() {
        FirebaseApp.initializeApp(Activity_AddIdeia.this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();







    }

    private void cliquesdebotoes() {


        concluir.setOnClickListener(adicionarIdeia);
    }

    private void inicializarcompnetente() {
        conteudo = findViewById(R.id.conteudoAddIdeia);
        nome = findViewById(R.id.txtusename);
        descricaodadideia = findViewById(R.id.descricao);
        concluir = findViewById(R.id.btnConcluirAddIdeia);
        imagePerfilusuario = findViewById(R.id.imgUserAddNovaIdeia);

    }

    View.OnClickListener adicionarIdeia = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                IdeiaModelo ideia = new IdeiaModelo();
                ideia.setConteudo(conteudo.getText().toString());
                ideia.setNomeuser(nome.getText().toString());
                ideia.setImguser(imagePerfilusuario.toString());
                ideia.setDescricao(descricaodadideia.getText().toString());
                ideia.setId(UUID.randomUUID().toString());
                databaseReference.child("Ideias").child(ideia.getId()).setValue(ideia);

                Toast.makeText(Activity_AddIdeia.this, "Ideia Adicionada com Sucesso", Toast.LENGTH_LONG).show();
            } catch (RuntimeException err) {
                Toast.makeText(Activity_AddIdeia.this, "Erro  " + err, Toast.LENGTH_LONG).show();

            }
        }
    };
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }
    private void RecuperarEmpreendedor() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            nome.setText(user.getEmail());
        } else {

            Toast.makeText(Activity_AddIdeia.this,"Erro ao recuperar email",Toast.LENGTH_LONG).show();
        }


        Picasso.get()
                .load("gs://socity-business.appspot.com/images/52e437fc-aed7-417f-941e-26fb259184b6")
                .resize(50,50)
                .error(R.drawable.ic_person_black_24dp)
                .into(imagePerfilusuario);
    }
        @Override
    protected void onStart() {
        super.onStart();
        RecuperarEmpreendedor();

    }
}