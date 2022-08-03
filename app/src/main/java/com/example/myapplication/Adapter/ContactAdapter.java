package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activity.ContactDetailActivity;
import com.example.myapplication.Activity.UpdateChiTietActivity;
import com.example.myapplication.DAO.ContactDAO;
import com.example.myapplication.Model.ContactModel;
import com.example.myapplication.R;;

import java.util.List;

public class ContactAdapter<adapter> extends ArrayAdapter<ContactModel> {
    private Context mContext;
    private int mResources;
    private List<ContactModel> mList;

    public ContactAdapter(Context context, int resource, List<ContactModel> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResources = resource;
        this.mList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResources, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.contact_tv_Name);
            viewHolder.textViewPhone = convertView.findViewById(R.id.contact_tv_Phone);
            viewHolder.btnAvartar = convertView.findViewById(R.id.contact_item_btnAvartar);
            viewHolder.btnCall = convertView.findViewById(R.id.contact_item_iv_call);
            viewHolder.ivDelete = convertView.findViewById(R.id.item_iv_delete);
            viewHolder.btnUpdate = convertView.findViewById(R.id.item_iv_update);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ContactModel model = mList.get(position);
        viewHolder.textViewName.setText(String.valueOf(model.getUserContact()));
        viewHolder.textViewPhone.setText(String.valueOf(model.getPhoneNumber()));
        viewHolder.btnAvartar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ContactDetailActivity.class);
                intent.putExtra("ContactModel", model);
                mContext.startActivity(intent);
            }
        });
        viewHolder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel: " + model.getPhoneNumber()));
                mContext.startActivity(intent);

            }
        });
        viewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(model);
                new ContactDAO(mContext).deleteContact(model);
                notifyDataSetChanged();
            }
        });
        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, UpdateChiTietActivity.class);
                intent.putExtra("ContactModel", model);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        ImageView btnAvartar;
        ImageView ivDelete;
        ImageView btnCall;
        ImageView btnUpdate;
        TextView textViewName;
        TextView textViewPhone;

    }
}
