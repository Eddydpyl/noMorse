package dpyl.eddy.nomorse.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import dpyl.eddy.nomorse.R;
import dpyl.eddy.nomorse.view.adapter.SettingAdapter;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GridView gridView = (GridView) inflater.inflate(R.layout.fragment_settings, container, false);
        gridView.setAdapter(new SettingAdapter(getActivity()));
        return gridView;
    }
}
