package com.mradking.pdfspliter.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.mradking.pdfspliter.R;
import com.mradking.pdfspliter.activity.Pdf_view_act;
import com.mradking.pdfspliter.database.DatabaseHelper;
import com.mradking.pdfspliter.modal.Modal;

import java.io.File;
import java.util.List;

public class SaveFileAdapter extends RecyclerView.Adapter<SaveFileAdapter.ViewHolder>{

    private Context mCtx;

    List<Modal> noteslist;

    public SaveFileAdapter(Context mCtx, List<Modal> noteslist) {
        this.mCtx = mCtx;
        this.noteslist = noteslist;
    }

    @NonNull
    @Override
    public SaveFileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.save_file_row, parent, false);
        mCtx = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaveFileAdapter.ViewHolder holder,  int position) {

        holder.name_file.setText(noteslist.get(position).getName());
        holder.date.setText(noteslist.get(position).getDate());

        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File file = new File(noteslist.get(position).getPaths());
                file.delete();
                holder.databaseHelper.deleteContact(String.valueOf(noteslist.get(position).get_id()));
                noteslist.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, noteslist.size());


            }
        });


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Uri pdfUri_1 = FileProvider.getUriForFile(mCtx, mCtx.getPackageName() + ".provider", new File(noteslist.get(position).getPaths()));

                Intent intent = new Intent(mCtx, Pdf_view_act.class);
                intent.putExtra("key",pdfUri_1);
                mCtx.startActivity(intent);



            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri pdfUri_1 = FileProvider.getUriForFile(mCtx, mCtx.getPackageName() + ".provider", new File(noteslist.get(position).getPaths()));
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("application/pdf");
                shareIntent.putExtra(Intent.EXTRA_STREAM, pdfUri_1);
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                mCtx. startActivity(shareIntent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return noteslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        View mview;
        TextView name_file,date;
        CardView bt,view,share;
        DatabaseHelper databaseHelper;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mview=itemView;

            name_file=mview.findViewById(R.id.file_name);
            view=mview.findViewById(R.id.view);
            bt=mview.findViewById(R.id.bt);
            databaseHelper=new DatabaseHelper(mCtx);
            share=mview.findViewById(R.id.share);
            date=mview.findViewById(R.id.date);

        }
    }
}
