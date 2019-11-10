package controle.cadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.socitybusiness.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import controle.firebase.Conexao;

public class Activity_ResetSenha extends AppCompatActivity {
    private EditText editEmail;
    private Button btnreset;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__reset_senha);
        inicializarComponentes();
        eventosDecliques();

    }

    private void eventosDecliques() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  editEmail.getText().toString().trim();
                resetSenha(email);
            }
        });

    }

    private void resetSenha(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(Activity_ResetSenha.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(Activity_ResetSenha.this,"Um e-mail foi enviado para alterar sua senha!",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(Activity_ResetSenha.this,"E-mail errado ou não cadastrado",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void inicializarComponentes() {
        editEmail = findViewById(R.id.editEmail);
        btnreset = findViewById(R.id.btnreset);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
