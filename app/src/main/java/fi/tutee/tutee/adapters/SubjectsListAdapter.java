package fi.tutee.tutee.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.tutee.tutee.R;

public class SubjectsListAdapter extends SimpleAdapter {

    private List<Map<String, String>> subjects;
    //alternate white and lightgrey background
    private int[] colors = new int[] { 0x30ffffff, 0x30eaeaea };


    public SubjectsListAdapter(Context context,
                               List<Map<String, String>> subjects,
                               int resource,
                               String[] from,
                               int[] to) {
        super(context, subjects, resource, from, to);
        this.subjects = (List<Map<String,String>>) subjects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }

}

