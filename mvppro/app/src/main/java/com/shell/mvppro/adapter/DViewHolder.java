package com.shell.mvppro.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 */

public abstract class DViewHolder<T> extends RecyclerView.ViewHolder {

    private DAdapter adapter;
    public DViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public abstract void bindData(T t,int position);

    public void setAdapter(DAdapter adapter){
        this.adapter = adapter;
    }

    public <T extends DAdapter> T getAdapter(){
        return (T) adapter;
    }
}
