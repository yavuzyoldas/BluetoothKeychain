package com.bluetoothkeychainapp54.bluetoothkeychain;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    //list of image
    public int[] list_images = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4};
    //list of title
    public String[] list_title = {"1.Adım", "2.Adım", "3.Adım", "4.Adım"};
    // list of decriptions
    public  String[] list_description = {
            "İlk olarak telefoununuzun bluetooth özelliğini açın.Kullanılabilir cihazlardan bağlanmak istediğiniz cihazı seçin.",
            "Daha sonra eşleşme için bir PIN kodu istenecektir.",
            "Bu PIN kodu için 1234 değeri girin ve tamam seçeneğini seçin. ",
            "Bu işlemden sonra artık cihazınız başarılı ie eşleştirilmiştir ve artık eşleşen cihazlar listesinde görünecektir."
    };
    public  int[]  list_color = {Color.rgb(0,191,255), Color.rgb(15,22,36), Color.rgb(0,191,255), Color.rgb(15,22,36)
    };
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater  = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  = layoutInflater.inflate(R.layout.slide,container,false);
        LinearLayout layoutslide = view.findViewById(R.id.slidelinearlayout);
        ImageView imgslide = (ImageView) view.findViewById(R.id.image);
        TextView txtttitle = view.findViewById(R.id.title);
        TextView txttdesc = view.findViewById(R.id.description);
        layoutslide.setBackgroundColor(list_color[position]);
        imgslide.setImageResource(list_images[position]);
        txtttitle.setText(list_title[position]);
        txttdesc.setText(list_description[position]);
        container.addView(view);
        return view;

    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { container.removeView((LinearLayout)object); }
    public SlideAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return list_title.length;
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (LinearLayout)object);
    }
}

