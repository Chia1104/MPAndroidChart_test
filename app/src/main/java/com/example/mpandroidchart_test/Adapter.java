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

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    LayoutInflater inflater;
    List<Dcard> dcards;
    private Context mContext;

    public Adapter(Context context, List<Dcard> dcards) {
        this.mContext = context;
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
        holder.mLv1.setText(dcards.get(position).getLv1());
        holder.mLv2.setText(dcards.get(position).getLv2());
        holder.mLv3.setText(dcards.get(position).getLv3());
        holder.mId.setText(dcards.get(position).getId());
        switch (holder.mClass.getText().toString()){
            case "Positive":
                holder.mScore.setTextColor(Color.parseColor("#33FFAA"));
                holder.mClass.setTextColor(Color.parseColor("#33FFAA"));
                break;
            case "Neutral":
                holder.mScore.setTextColor(Color.parseColor("#FFDD55"));
                holder.mClass.setTextColor(Color.parseColor("#FFDD55"));
                break;
            case "Negative":
                holder.mScore.setTextColor(Color.parseColor("#FFA488"));
                holder.mClass.setTextColor(Color.parseColor("#FFA488"));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dcards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mTitle, mDate, mContent, mScore, mClass, mLv1, mLv2, mLv3, mId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.title_txtView);
            mDate = itemView.findViewById(R.id.date_txtView);
            mContent = itemView.findViewById(R.id.content_txtView);
            mScore = itemView.findViewById(R.id.score_txtView);
            mClass = itemView.findViewById(R.id.class_txtView);
            mLv1 = itemView.findViewById(R.id.lv1_txtView);
            mLv2 = itemView.findViewById(R.id.lv2_txtView);
            mLv3 = itemView.findViewById(R.id.lv3_txtView);
            mId = itemView.findViewById(R.id.id_txtView);

            itemView.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(mContext, DcardDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("title", mTitle.getText().toString());
                    intent.putExtra("content", mContent.getText().toString());
                    intent.putExtra("date", mDate.getText().toString());
                    intent.putExtra("saclass", mClass.getText().toString());
                    intent.putExtra("sascore", mScore.getText().toString());
                    intent.putExtra("lv1", mLv1.getText().toString());
                    intent.putExtra("lv2", mLv2.getText().toString());
                    intent.putExtra("lv3", mLv3.getText().toString());
                    intent.putExtra("id", mId.getText().toString());
                    mContext.startActivity(intent);
                }
                catch(Exception e) {
                    Toast.makeText(mContext,
                            "error" + e,Toast.LENGTH_SHORT).show();
                }

            });

        }
    }

    public void filterList(ArrayList<Dcard> filteredList) {
        dcards = filteredList;
        notifyDataSetChanged();
    }

    public void filterList1(ArrayList<Dcard> filteredList1) {
        dcards = filteredList1;
        notifyDataSetChanged();
    }
}
