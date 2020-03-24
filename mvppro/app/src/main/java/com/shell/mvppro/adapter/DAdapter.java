package com.shell.mvppro.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 */

public abstract class DAdapter<T,V extends DViewHolder> extends RecyclerView.Adapter<V>{

    private static final int VIEW_ITEM = 0;
    private static final int VIEW_FOOTER = 1;
    private static final int VIEW_TOP = 2;

    private List mItems;
    public abstract V newViewHolder(ViewGroup viewGroup, int type);

    public V newFooterViewHolder(ViewGroup viewGroup, int type){
        return null;
    }
    public V newTopViewHolder(ViewGroup viewGroup, int type){
        return null;
    }
    protected View inflate(ViewGroup viewGroup, int layout){
        return LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
    }
    public int getCustomViewType(int position) {
        return VIEW_ITEM;
    }
    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List items) {
        mItems = items;
        notifyDataSetChanged();
    }
    @Override
    public int getItemViewType(int position) {
        return getCustomViewType(position);
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        V holder;
//        if(viewType == VIEW_TOP){
//            holder = newTopViewHolder(parent,viewType);
//            holder.setAdapter(this);
//        }else if(viewType == VIEW_FOOTER){
//            holder = newFooterViewHolder(parent,viewType);
//            holder.setAdapter(this);
//        }else{
            holder = newViewHolder(parent,viewType);
            holder.setAdapter(this);
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {
        holder.bindData(getItem(position),position);
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    public T getItem(int position){
        if(position == -1){
            return null;
        }
        return (T) mItems.get(position);
    }

    public void add(int position, Object object) {
        if (object == null || getItems() == null || position < 0 || position > getItems().size()) {
            return;
        }
        mItems.add(position, object);
        notifyItemInserted(position);
    }

    public void add(Object object) {
        if (object == null || getItems() == null) {
            return;
        }
        mItems.add(object);
        notifyItemInserted(mItems.size());

    }
    public void update(int position, Object object){
        if (object == null || getItems() == null) {
            return;
        }
        if(mItems.size() > position){
            mItems.remove(position);
            mItems.add(position,object);
            notifyItemChanged(position);
        }
    }
    public void addList(int position, List list) {
        if (list == null || list.size() < 1 || getItems() == null || position < 0 || position > getItems().size()) {
            return;
        }
        mItems.addAll(position, list);
        notifyItemRangeInserted(position, list.size());

    }
    public void move(int fromPosition, int toPosition, Object object){
        mItems.remove(fromPosition);
        mItems.add(toPosition,object);
        notifyItemMoved(fromPosition,toPosition);
    }

    /**
     * 使用notifyItemRangeInserted是局部刷新，会存在闪烁
     * @param list
     */
    public void addList(List list) {
        if (list == null || list.size() < 1 || getItems() == null) {
            return;
        }
        int postion = getItemCount();
        mItems.addAll(list);
        notifyItemRangeInserted(postion, list.size());
    }

    /**
     * 使用notifyDataSetChanged不会有闪烁问题
     * @param list
     */
    public void addLists(List list){
        if (list == null || list.size() < 1 || getItems() == null) {
            return;
        }
        int postion = getItemCount();
        mItems.addAll(list);
        notifyDataSetChanged();
    }
    public void delete(int position) {
        if (getItems() == null || position < 0 || getItems().size() < position) {
            return;
        }
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public void delete(List list) {
        if (list == null || list.size() < 1 || getItems() == null) {
            return;
        }
        int position = getItemCount();
        mItems.removeAll(list);
        notifyItemRangeRemoved(position, list.size());
    }

    public void delete(int position, List list) {
        if (list == null || list.size() < 1 || getItems() == null) {
            return;
        }
        mItems.removeAll(list);
        notifyItemRangeRemoved(position, list.size());
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void updateData() {
        notifyDataSetChanged();
    }

    /**
     * 获取适配器
     *
     * @return 返回值
     */
    protected DAdapter getAdapter() {
        return this;
    }
}
