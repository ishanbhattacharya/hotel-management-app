package com.ishanbhattacharya.hotelmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RoomAdapter extends ArrayAdapter<String> {
    String[] names;
    String[] price;
    int[] images;
    Context context;

    public RoomAdapter(Context context, String[] roomList, String[] priceList, int[] imgList) {
        super(context, R.layout.room_card);
        this.names = roomList;
        this.price = priceList;
        this.images = imgList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
// First let’s verify the convertView is not null
        if (convertView == null) {
// This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.room_card, null);
// Now we can fill the layout with the right values
            TextView titleView = (TextView) convertView.findViewById(R.id.titleText);
            TextView priceView = (TextView) convertView.findViewById(R.id.priceText);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.roomImage);

            holder.roomH = titleView;
            holder.priceH = priceView;
            holder.imageH = imageView;

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();

        holder.roomH.setText(names[position]);
        holder.priceH.setText("$" + price[position]);
        holder.imageH.setImageResource(images[position]);

        return convertView;
    }

    static class ViewHolder{
        TextView roomH;
        TextView priceH;
        ImageView imageH;
    }
}
