package com.bbs.bemobile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CCompleteBillListAdapter extends BaseAdapter {
    private List<CCompleteBill> m_listData;
    private LayoutInflater m_layoutInflater;
    private Context m_context;

    public CCompleteBillListAdapter(Context aContext, List<CCompleteBill> listData)
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
        CCompleteBillListAdapter.ViewHolder holder;
        if(convertView==null)
        {
            convertView = m_layoutInflater.inflate(R.layout.listitem_bill_item,null);
            holder = new CCompleteBillListAdapter.ViewHolder();
            holder.itemNameView = (TextView) convertView.findViewById(R.id.billitem_textView_itemName);
            holder.itemPriceView = (TextView) convertView.findViewById(R.id.billitem_textView_itemPrice);
            convertView.setTag(holder);
        }else{
            holder=(CCompleteBillListAdapter.ViewHolder) convertView.getTag();
        }

        CCompleteBill bi =(CCompleteBill)this.getItem(position);
        holder.itemNameView.setText(bi.getDateString());
        holder.itemPriceView.setText(StaticValues.getConvertedValue(bi.getTotal()));

        return convertView;
    }

    static class ViewHolder {
        TextView itemNameView;
        TextView itemPriceView;
    }
}
