package com.doommes.learn;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.doommes.learn.ArchitectureComponents_14.LiveDataActivity;
import com.doommes.learn.Handler_eight.HandlerActivity;
import com.doommes.learn.PicLoad_Six.BitmapActivity;
import com.doommes.learn.PicLoad_Six.DiskLruCacheActivity;
import com.doommes.learn.PicLoad_Six.PhotoActivity;
import com.doommes.learn.Rxjava.RxActivity;
import com.doommes.learn.Thread_nine.ThreadActivity;
import com.doommes.learn.View.ViewActivity;

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
        viewHolder.mBtn.setOnClickListener(v -> {
            Intent intent = null;
            switch (i){
                case 0:
                    intent = getIntent(BitmapActivity.class);
                    break;
                case 1:
                    intent = getIntent(DiskLruCacheActivity.class);
                    break;
                case 2:
                    intent = getIntent(PhotoActivity.class);
                    break;
                case 3:
                    intent = getIntent(HandlerActivity.class);
                    break;
                case 4:
                    intent = getIntent(ThreadActivity.class);
                    break;
                case 5:
                    intent = getIntent(LiveDataActivity.class);
                    break;
                case 6:
                    intent = getIntent(RxActivity.class);
                    break;
                case 7:
                    intent = getIntent(ViewActivity.class);
                    break;
                case 8:
                    break;
                default:
                    break;
            }
            mContext.startActivity(intent);
        });
    }

    private Intent getIntent(Class<?> aClass) {
        return new Intent(mContext, aClass);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Button mBtn;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.mBtn = (Button) itemView.findViewById(R.id.btn);

        }
    }
}
