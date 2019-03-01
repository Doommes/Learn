package com.doommes.learn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doommes.learn.Item.BitmapActivity;
import com.doommes.learn.Item.DiskLruCacheActivity;
import com.doommes.learn.Item.PhotoActivity;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private String[] mItems;
    private Context mContext;

    public ItemAdapter(String[] items, Context context) {
        mItems = items;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mBtn.setText(mItems[i]);
        viewHolder.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                switch (i){
                    case 0:
                        intent = getIntent(BitmapActivity.class);
                        break;
                    case 1:
                        intent = getIntent(DiskLruCacheActivity.class);
                    case 2:
                        intent = getIntent(PhotoActivity.class);
                    default:
                        break;
                }
                mContext.startActivity(intent);
            }
        });
    }

    private Intent getIntent(Class<?> aClass) {
        return new Intent(mContext, aClass);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button mBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mBtn = (Button) itemView.findViewById(R.id.btn);

        }
    }
}