package com.zeeshanmac.zeeshan.spoofandroidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeeshanmac.zeeshan.spoofandroidapp.EditItemActivity;
import com.zeeshanmac.zeeshan.spoofandroidapp.R;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedItemsAdapter extends RecyclerView.Adapter<SelectedItemsAdapter.MyViewHolder> {
    private List<Items> selectedItemsList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemNameTVD)
        TextView nameTV;
        @BindView(R.id.itemCountTVD)
        TextView itemCountTV;
        @BindView(R.id.editIVD)
        ImageView editIV;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public SelectedItemsAdapter(Context context1, List<Items> selectedItemsList) {
        this.selectedItemsList = selectedItemsList;
        context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.selected_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Items item = selectedItemsList.get(position);
        holder.nameTV.setText(item.getItemName());
        holder.itemCountTV.setText(item.getItemQuantity()+"");
        holder.editIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditItemActivity.class);
                intent.putExtra("selcted_item",item);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedItemsList.size();
    }

    public void updateList(int position, String str) {
        selectedItemsList.get(position).setItemName(str);
        notifyDataSetChanged();
    }

    public void updateList(List<Items> selectedItemsList1) {
        this.selectedItemsList = selectedItemsList1;
        notifyDataSetChanged();
    }
}
