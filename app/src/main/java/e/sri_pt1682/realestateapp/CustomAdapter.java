package e.sri_pt1682.realestateapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sri-pt1682 on 13/03/18.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
{
    private ArrayList<String> mArrayList;
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mCheckBox;
        public ViewHolder(View itemView)
        {
            super(itemView);
            mCheckBox=itemView.findViewById(R.id.checkbox_recycler_item);
        }
    }

    public CustomAdapter(ArrayList<String> arrayList)
    {
        mArrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        View v= LayoutInflater.from(context).inflate(R.layout.recycler_view_individual_item,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.mCheckBox.setText(mArrayList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return (mArrayList!=null)?mArrayList.size():0;
    }
}

