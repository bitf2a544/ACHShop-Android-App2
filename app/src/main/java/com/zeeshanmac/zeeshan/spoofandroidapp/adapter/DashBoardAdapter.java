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
import com.zeeshanmac.zeeshan.spoofandroidapp.MainActivity;
import com.zeeshanmac.zeeshan.spoofandroidapp.R;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zeeshanmac.zeeshan.spoofandroidapp.MainActivity.selectedItemsList;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {
    private List<Items> itemsList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.articleTVD)
        TextView articleTV;
        @BindView(R.id.shareIVD)
        ImageView shareTextIV;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public DashBoardAdapter(Context context1, List<Items> itemsList) {
        this.itemsList = itemsList;
        context = context1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dash_board_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Items item = itemsList.get(position);
     //   holder.articleTV.setText(item.getItemName());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void updateList(int position, String str) {
        itemsList.get(position).setItemName(str);
        notifyDataSetChanged();
    }

    public void updateList(List<Items> itemsList1) {
        this.itemsList = itemsList1;
        notifyDataSetChanged();
    }
}

