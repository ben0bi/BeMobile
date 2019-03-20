package com.bbs.bemobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CBillItemListAdapter extends BaseAdapter
{
    private List<CBillItem> m_listData;
    private LayoutInflater m_layoutInflater;
    private Context m_context;

    public CBillItemListAdapter(Context aContext, List<CBillItem> listData)
    {
        m_context=aContext;
        m_listData = listData;
        m_layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {return m_listData.size();}

    @Override
    public Object getItem(int position) {return m_listData.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if(convertView==null)
        {
            convertView = m_layoutInflater.inflate(R.layout.listitem_bill_item,null);
            holder = new ViewHolder();
            holder.itemNameView = (TextView) convertView.findViewById(R.id.billitem_textView_itemName);
            holder.itemPriceView = (TextView) convertView.findViewById(R.id.billitem_textView_itemPrice);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        CBillItem bi = this.m_listData.get(position);
        holder.itemNameView.setText(bi.getName());
        holder.itemPriceView.setText(bi.getValue().toString());

        return convertView;
    }

    static class ViewHolder {
        TextView itemNameView;
        TextView itemPriceView;
    }
}
