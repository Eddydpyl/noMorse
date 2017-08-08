package dpyl.eddy.nomorse.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import dpyl.eddy.nomorse.R;
import dpyl.eddy.nomorse.view.WhatsAppActivity;

public class SettingAdapter extends BaseAdapter {

    private Context context;

    public SettingAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1; // Adjust depending on the number of messaging clients currently covered
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    @SuppressLint("InflateParams")
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.fragment_settings_item, null);
            final ImageView imageView = view.findViewById(R.id.imageView_setting);
            final SettingViewHolder settingViewHolder = new SettingViewHolder(imageView);
            view.setTag(settingViewHolder);
        }
        final SettingViewHolder settingViewHolder = (SettingViewHolder) view.getTag();
        switch (i){
            case 0:
                settingViewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_whatsapp));
                settingViewHolder.imageView.setContentDescription(context.getString(R.string.desc_whats_app));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, WhatsAppActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            // Add here new messaging clients to the view, remember to update getCount()
            default:
                throw new RuntimeException("getCount() method in SettingAdapter class returns an incorrect number");
        }
        return view;
    }

    private class SettingViewHolder {

        private ImageView imageView;

        private SettingViewHolder(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
