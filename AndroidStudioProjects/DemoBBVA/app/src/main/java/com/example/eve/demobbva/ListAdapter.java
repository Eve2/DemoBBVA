package com.example.eve.demobbva;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<BankInfo> bankInfos;

    public ListAdapter(Context mContext, ArrayList<BankInfo> bankInfos) {
        this.mContext = mContext;
        this.bankInfos = bankInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final BankInfo data = bankInfos.get(position);
        holder.name.setText("Name: " + data.getName());
        holder.address.setText("Address: " + data.getFormatted_address());
        Picasso.with(mContext).load(data.getIcon()).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return bankInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView address;
        private ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            address = itemView.findViewById(R.id.tv_address);
            icon = itemView.findViewById(R.id.image_icon);
        }
    }
}
