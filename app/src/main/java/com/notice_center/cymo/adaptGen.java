package com.notice_center.cymo;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class adaptGen extends FirebaseRecyclerAdapter<fileinfo,adaptGen.genViewHolder> {


    public adaptGen(@NonNull @NotNull FirebaseRecyclerOptions<fileinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull adaptGen.genViewHolder holder, int position, @NonNull @NotNull fileinfo model) {
        holder.pdfHeaderGen.setText(model.getFilename());

        holder.pdfDateGen.setText(model.getDate());

        holder.pdfGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (holder.pdfGen.getContext(),viewPdf.class);
                intent.putExtra("filename",model.getFilename());
                intent.putExtra("fileurl",model.getFileurl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pdfGen.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public genViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowgen,parent,false);
        return new genViewHolder(view);
    }

    public class genViewHolder extends RecyclerView.ViewHolder{
        ImageView pdfGen;
        TextView pdfHeaderGen;
        TextView pdfDateGen;

        public genViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pdfGen = (ImageView) itemView.findViewById(R.id.img2);
            pdfHeaderGen = (TextView)itemView.findViewById(R.id.header2);
            pdfDateGen = (TextView) itemView.findViewById(R.id.date2);
        }
    }
}
