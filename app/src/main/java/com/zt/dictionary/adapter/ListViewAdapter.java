package com.zt.dictionary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zt.dictionary.R;
import com.zt.dictionary.model.db.Word;

import java.util.List;

/**
 * @user: zt
 * @describe:
 */

public class ListViewAdapter extends BaseAdapter {
    private List<Word> list;
    private Context context;

    public ListViewAdapter(Context context,List<Word> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_words,null);
            viewHolder = new ViewHolder();
            viewHolder.showWords = (TextView) convertView.findViewById(R.id.showWord);
            viewHolder.showTranslate = (TextView) convertView.findViewById(R.id.showTranslate);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.showWords.setText(list.get(position).getWord());
        viewHolder.showTranslate.setText(list.get(position).getDetailTranslate());
        return convertView;
    }

    private static class ViewHolder{
        private TextView showWords;
        private TextView showTranslate;
    }
}
