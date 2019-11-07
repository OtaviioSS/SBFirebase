package modelo.ideia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socitybusiness.R;

import java.util.ArrayList;

import modelo.IdeiaModelo;

public class IdeaAdapter extends RecyclerView.Adapter<IdeaAdapter.ViewHolderIdeia> {



    ArrayList<IdeiaModelo> listadeideias;

    public IdeaAdapter(ArrayList<IdeiaModelo> listadeideias) {
        this.listadeideias = listadeideias;
    }

    @NonNull
    @Override
    public ViewHolderIdeia onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_nf,null,false);
        return new ViewHolderIdeia(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderIdeia holder, int position) {

        holder.nome.setText(listadeideias.get(position).getNomeuser());
        holder.conteudo.setText(listadeideias.get(position).getConteudo());
        holder.fotouser.setImageResource(listadeideias.get(position).getImguser());
        holder.fotopub.setImageResource(listadeideias.get(position).getImgpub());

    }

    @Override
    public int getItemCount() {
        return listadeideias.size();
    }

    public class ViewHolderIdeia extends RecyclerView.ViewHolder {

        TextView nome;
        TextView conteudo;
        ImageView fotopub,fotouser;

        public ViewHolderIdeia(@NonNull View itemView) {
            super(itemView);
            nome =(TextView) itemView.findViewById(R.id.username);
            conteudo = (TextView) itemView.findViewById(R.id.conteudo);
            fotouser = (ImageView) itemView.findViewById(R.id.userid);
            fotopub = (ImageView) itemView.findViewById(R.id.imgpostTL);
        }
    }
}
