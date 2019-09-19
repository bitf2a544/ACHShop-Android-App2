package com.zeeshanmac.zeeshan.spoofandroidapp.adapter;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zeeshanmac.zeeshan.spoofandroidapp.R;
import com.zeeshanmac.zeeshan.spoofandroidapp.model.ParentCellData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParentExpandableAdapter extends RecyclerView.Adapter<ParentExpandableAdapter.MyViewHolder> {
    private List<ParentCellData> dataList;
    Context context;
    ChildListAdapter childListAdapter;
    ChildListAdapter.onChildClickListener onChildClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.headingTVD)
        TextView headingTV;
        @BindView(R.id.child_recyclerViewD)
        RecyclerView childRecyclerView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public ParentExpandableAdapter(Context context1, List<ParentCellData> dataList, ChildListAdapter.onChildClickListener onChildClickListener1) {
        this.dataList = dataList;
        context = context1;
        onChildClickListener = onChildClickListener1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.parent_list_item_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ParentCellData item = dataList.get(position);
        holder.headingTV.setText(item.getTitle());
        holder.headingTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isOpen()) {
                    item.setOpen(false);
                    holder.childRecyclerView.setVisibility(View.GONE);
                } else {
                    item.setOpen(true);
                    holder.childRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
        if (item.getChildGroceryItemList() != null && item.getChildGroceryItemList().size() > 0) {

            childListAdapter = new ChildListAdapter(context, item.getChildGroceryItemList(), onChildClickListener);

            holder.childRecyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
            holder.childRecyclerView.setLayoutManager(mLayoutManager);
            holder.childRecyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
            holder.childRecyclerView.setItemAnimator(new DefaultItemAnimator());

            holder.childRecyclerView.setAdapter(childListAdapter);

        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateList(List<ParentCellData> dataList1) {
        this.dataList = dataList1;
        notifyDataSetChanged();
    }
}

