package com.zeeshanmac.zeeshan.spoofandroidapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeeshanmac.zeeshan.spoofandroidapp.R;
import com.zeeshanmac.zeeshan.spoofandroidapp.SearchActivity;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.Items;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.MyViewHolder> {
    private List<Items> childDataList;
    Context context;

    onChildClickListener onChildClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.lblListItem)
        TextView labelTV;


        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ChildListAdapter(Context context1, List<Items> childDataList, onChildClickListener onChildClickListener1) {
        this.childDataList = childDataList;
        context = context1;
        onChildClickListener = onChildClickListener1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Items item = childDataList.get(position);
        holder.labelTV.setText(item.getItemName());
        holder.labelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.getIsChecked()) {
                    item.setIsChecked(false);
                    if(onChildClickListener instanceof SearchActivity == false) {
                        holder.labelTV.setBackgroundColor(context.getResources().getColor(R.color.dark_gray_color));
                    }
                } else {
                    item.setIsChecked(true);
                    if(onChildClickListener instanceof SearchActivity == false) {
                        holder.labelTV.setBackgroundColor(context.getResources().getColor(R.color.light_red_color));
                    }
                }
                onChildClickListener.onClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return childDataList.size();
    }

    public void updateList(List<Items> childDataList1) {
        childDataList = childDataList1;
        notifyDataSetChanged();
    }


    public void updateChildsList(int position, String str) {
        childDataList.get(position).setItemName(str);
        notifyDataSetChanged();
    }

    public interface onChildClickListener {
        void onClick(Items childGroceryItem);
    }
}
