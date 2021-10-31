package com.example.mpandroidchart_test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    LayoutInflater inflater;
    List<Dcard> dcards;
    Integer posCount = 0;
    Integer neuCount = 0;
    Integer negCount = 0;

    public Adapter(Context context, List<Dcard> dcards) {
        this.inflater = LayoutInflater.from(context);
        this.dcards = dcards;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.article_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mTitle.setText(dcards.get(position).getTitle());
        holder.mDate.setText(dcards.get(position).getDate());
        holder.mContent.setText(dcards.get(position).getContent());
        holder.mScore.setText(dcards.get(position).getSascore());
        holder.mClass.setText(dcards.get(position).getSaclass());
        switch (holder.mClass.getText().toString()){
            case "Positive":
                holder.mScore.setTextColor(Color.parseColor("#33FFAA"));
                holder.mClass.setTextColor(Color.parseColor("#33FFAA"));
                posCount += 1;
                break;
            case "Neutral":
                holder.mScore.setTextColor(Color.parseColor("#FFDD55"));
                holder.mClass.setTextColor(Color.parseColor("#FFDD55"));
                neuCount += 1;
                break;
            case "Negative":
                holder.mScore.setTextColor(Color.parseColor("#FFA488"));
                holder.mClass.setTextColor(Color.parseColor("#FFA488"));
                negCount += 1;
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dcards.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle, mDate, mContent, mScore, mClass;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_txtView);
            mDate = itemView.findViewById(R.id.date_txtView);
            mContent = itemView.findViewById(R.id.content_txtView);
            mScore = itemView.findViewById(R.id.score_txtView);
            mClass = itemView.findViewById(R.id.class_txtView);

//            itemView.setOnClickListener(v -> {
//                try {
//                    Intent intent = new Intent(mContext, DcardDetailActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("title", mTitle.getText().toString());
//                    intent.putExtra("content", mContent.getText().toString());
//                    intent.putExtra("date", mDate.getText().toString());
//                    intent.putExtra("sascore", mScore.getText().toString());
//                    intent.putExtra("saclass", mClass.getText().toString());
//                    mContext.startActivity(intent);
//                    Toast.makeText(mContext,
//                            "clicked",Toast.LENGTH_SHORT).show();
//                }
//                catch(Exception e) {
//                    Toast.makeText(mContext,
//                            "error" + e,Toast.LENGTH_SHORT).show();
//                }
//
//            });

        }
    }
}
