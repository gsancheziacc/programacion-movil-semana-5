package com.example.gabriel_sanchez_s5.ui.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gabriel_sanchez_s5.R;
import com.example.gabriel_sanchez_s5.model.PersonalModel;
import com.example.gabriel_sanchez_s5.utilities.DbBitmapUtility;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private static LayoutInflater inflater =null;
    Context context;
    ArrayList<PersonalModel> personalList;
    public ListViewAdapter(Context context, ArrayList<PersonalModel> personalList){
        this.context = context;
        this.personalList = personalList;
        inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final View view = inflater.inflate(R.layout.personal_list_item, null);
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvChargeType = (TextView) view.findViewById(R.id.tvChargeType);
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        tvName.setText(personalList.get(i).getName());
        tvChargeType.setText(personalList.get(i).getChargeType());
        Bitmap bm = DbBitmapUtility.getImage(personalList.get(i).getImageData());
        imageView.setImageResource(R.drawable.person);
        //imageView.setImageBitmap(bm);
        imageView.setTag(i);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.click_sound);
                mediaPlayer.start();
            }
        });
        return view;
    }
    @Override
    public int getCount() {
        return personalList.size();
    }
    @Override
    public PersonalModel getItem(int i) {
        return personalList.get(i);
    }
    @Override
    public long getItemId(int i) {
        return personalList.get(i).getId();
    }
}
