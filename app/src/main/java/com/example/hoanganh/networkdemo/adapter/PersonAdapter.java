package com.example.hoanganh.networkdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoanganh.networkdemo.R;
import com.example.hoanganh.networkdemo.entity.Person;

import java.util.List;

/**
 * Created by HoangAnh on 4/1/2016.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, int resource, List<Person> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_person_listview, null);

            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) v.findViewById(R.id.image1);
            viewHolder.txtName = (TextView) v.findViewById(R.id.txtName);
            viewHolder.txtProject = (TextView) v.findViewById(R.id.txtProject);
            viewHolder.txtRole = (TextView) v.findViewById(R.id.txtRole);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        Person p = getItem(position);

        if (p != null) {
            viewHolder.imageView.setImageResource(p.getGender() == 1 ? R.drawable.male : R.drawable.female);
            viewHolder.txtName.setText(String.format("Name: %s", p.getName()));
            if (p.getProject().getName() != null) {
                viewHolder.txtProject.setText(String.format("Project: %s", p.getProject().getName()));
                viewHolder.txtRole.setText(String.format("Role: %s", p.getProject().getRole()));
            } else {
                viewHolder.txtProject.setText("");
                viewHolder.txtRole.setText("");
            }
        }

        return v;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView txtName, txtProject, txtRole;
    }

}
