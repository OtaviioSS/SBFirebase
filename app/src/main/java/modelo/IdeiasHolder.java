package modelo;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socitybusiness.R;
import com.squareup.picasso.Picasso;

public class IdeiasHolder extends RecyclerView.ViewHolder {

        View mView;


    public IdeiasHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                oclickListener.onItemClick(view,getAdapterPosition());

            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                oclickListener.onItemClick(view,getAdapterPosition());

                return true;
            }
        });
    }


    public void setDetails(Context context,String nome,int imguser,String conteudo, int imgpub){

        TextView nomeusuario = mView.findViewById(R.id.username);
          TextView cont = mView.findViewById(R.id.conteudo);
         ImageView imgusuario = mView.findViewById(R.id.userid);
          ImageView imgempub = mView.findViewById(R.id.imgpostTL);


          nomeusuario.setText(nome);
          cont.setText(conteudo);

        Picasso.get().load(imgpub).into(imgempub);
        Picasso.get().load(imguser).into(imgusuario);

    }

    private IdeiasHolder.ClickListener oclickListener;

    public  interface  ClickListener {

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }

    public void setOnClickListener(IdeiasHolder.ClickListener clickListener){

         oclickListener = clickListener;
    }
}
