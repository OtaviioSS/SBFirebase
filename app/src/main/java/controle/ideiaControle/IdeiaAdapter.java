package controle.ideiaControle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socitybusiness.R;
import com.squareup.picasso.Picasso;

import java.nio.file.attribute.PosixFileAttributes;
import java.util.List;

import controle.Activity_Inicio;
import modelo.IdeiaModelo;
public class IdeiaAdapter extends RecyclerView.Adapter<IdeiaAdapter.MyViewHolder> {

    List<IdeiaModelo> ideias ;

    public IdeiaAdapter(List<IdeiaModelo> ideias) {
        this.ideias = ideias;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_nf, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        try {
            IdeiaModelo ideia = ideias.get(i);
            holder.nome.setText(ideia.getNomeuser());
            holder.conteudo.setText(ideia.getConteudo());
            holder.descricao.setText(ideia.getDescricao());
        }catch (Exception e){
            Log.i("Erro","erro",e);
        }


        //Carregar imagem

        String urlImagem = "https://firebasestorage.googleapis.com/v0/b/socity-business.appspot.com/o/images%2Fc07c8e2b-6178-474a-9e51-fe3d919e5e3f?alt=media&token=04d3020f-d153-4818-8c58-9569d179ccc7";

        Picasso.get()
                .load( urlImagem )
                .resize(50,50)
                .error(R.drawable.ic_person_black_24dp)
                .into(holder.imgUser);

    }

    @Override
    public int getItemCount() {
        return ideias.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUser;
        TextView nome;
        TextView conteudo;
        TextView descricao;


        MyViewHolder(View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.username);
            conteudo = itemView.findViewById(R.id.conteudo);
            imgUser = itemView.findViewById(R.id.userid);
            descricao = itemView.findViewById(R.id.descricaopost);

            }


        }


    }

