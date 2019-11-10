package controle.ideiaControle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socitybusiness.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        IdeiaModelo ideia = ideias.get(i);
        holder.nome.setText(ideia.getNomeuser());
        holder.conteudo.setText(ideia.getConteudo() );

        //Carregar imagem
        String urlImagem = ideia.getImguser();
        Picasso.get().load( urlImagem ).into( holder.imgUser );

    }

    @Override
    public int getItemCount() {
        return ideias.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView nome;
        TextView conteudo;
        ImageView imgpub;


        public MyViewHolder(View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.username);
            conteudo = itemView.findViewById(R.id.conteudo);
            imgUser = itemView.findViewById(R.id.userid);
            imgpub = itemView.findViewById(R.id.imgpostTL);


        }
    }
}
