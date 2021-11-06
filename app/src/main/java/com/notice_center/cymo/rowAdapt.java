package com.notice_center.cymo;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class rowAdapt extends FirebaseRecyclerAdapter<fileinfo,rowAdapt.myViewHolder> {

    public rowAdapt(@NonNull @NotNull FirebaseRecyclerOptions<fileinfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull rowAdapt.myViewHolder holder, int position, @NonNull @NotNull fileinfo fileinfo) {

        holder.pdfHeader.setText(fileinfo.getFilename());

        holder.pdfDate.setText(fileinfo.getDate());

        holder.pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (holder.pdf.getContext(),viewPdf.class);
                intent.putExtra("filename",fileinfo.getFilename());
                intent.putExtra("fileurl",fileinfo.getFileurl());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.pdf.getContext().startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.pdf.getContext());
                builder.setTitle("Delete Panel");
                builder.setMessage("Are you sure ?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Bsc CS")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ImageView pdf;
        TextView pdfHeader;
        TextView pdfDate;
        ImageView delete;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            pdf = (ImageView) itemView.findViewById(R.id.img1);
            pdfHeader = (TextView)itemView.findViewById(R.id.header);
            pdfDate = (TextView) itemView.findViewById(R.id.dateData);
            delete = (ImageView) itemView.findViewById(R.id.deletePdf);

        }
    }
}
