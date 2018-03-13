package e.sri_pt1682.realestateapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sri-pt1682 on 12/03/18.
 */

public class PropertyFragment extends Fragment
{
    private int type;
    Button buttonInLayoutEnd;
    RelativeLayout purpose_layout,location_layout,prop_type_layout,bedroom_layout,budget_layout,area_layout,furnishings_layout,amenities_layout,occupancy_layout,constr_status_layout;
    public static PropertyFragment newInstance(int type)
    {

        Bundle args = new Bundle();
        args.putInt("type",type);
        PropertyFragment fragment = new PropertyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        type=(int)getArguments().get("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.preference_item,container,false);
        purpose_layout=v.findViewById(R.id.purpose_layout);
        location_layout=v.findViewById(R.id.location_layout);
        prop_type_layout=v.findViewById(R.id.prop_type_layout);
        bedroom_layout=v.findViewById(R.id.bedroom_layout);
        budget_layout=v.findViewById(R.id.budget_layout);
        area_layout=v.findViewById(R.id.area_layout);
        furnishings_layout=v.findViewById(R.id.furnishings_layout);
        amenities_layout=v.findViewById(R.id.amenities_layout);
        occupancy_layout=v.findViewById(R.id.occupancy_layout);
        constr_status_layout=v.findViewById(R.id.construction_status_layout);
        buttonInLayoutEnd=v.findViewById(R.id.button_in_layout_end);
        buttonInLayoutEnd.setText("SEARCH");

        RecyclerView furnishings_recycler_view=v.findViewById(R.id.furnishings_recycler_view);
        RecyclerView amenities_recycler_view=v.findViewById(R.id.amenities_recycler_view);
        RecyclerView occupancy_recycler_view=v.findViewById(R.id.occupancy_recycler_view);
        RecyclerView constr_status_recycler_view=v.findViewById(R.id.constr_status_recycler_view);

        ArrayList<String> furnishings_list=new ArrayList<>();
        ArrayList<String> amenities_list=new ArrayList<>();
        ArrayList<String> occupancy_list=new ArrayList<>();
        ArrayList<String> constr_stat_list=new ArrayList<>();

        Collections.addAll(furnishings_list,getResources().getStringArray(R.array.furnishing_items));
        Collections.addAll(amenities_list,getResources().getStringArray(R.array.amenities_items));
        Collections.addAll(occupancy_list,getResources().getStringArray(R.array.occupancy_items));
        Collections.addAll(constr_stat_list,getResources().getStringArray(R.array.constr_status_items));

        CustomAdapter furnishings_adapter=new CustomAdapter(furnishings_list);
        final CustomAdapter amenities_adapter=new CustomAdapter(amenities_list);
        CustomAdapter occupancy_adapter=new CustomAdapter(occupancy_list);
        CustomAdapter constr_stat_adapter=new CustomAdapter(constr_stat_list);

        furnishings_recycler_view.setAdapter(furnishings_adapter);
        amenities_recycler_view.setAdapter(amenities_adapter);
        occupancy_recycler_view.setAdapter(occupancy_adapter);
        constr_status_recycler_view.setAdapter(constr_stat_adapter);

        final RecyclerView prop_type_recycler_view=v.findViewById(R.id.prop_types_recycler_view);
        Spinner spinner=v.findViewById(R.id.prop_type_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id)
            {
                ArrayList<String> list=new ArrayList<>();
                if(pos==0)
                {
                    Collections.addAll(list,getResources().getStringArray(R.array.residential_property_type));
                    prop_type_recycler_view.setAdapter(new CustomAdapter(list));
                    if(type==1)
                    {
                        furnishings_layout.setVisibility(View.VISIBLE);
                        amenities_layout.setVisibility(View.VISIBLE);
                        occupancy_layout.setVisibility(View.VISIBLE);
                    }
                    else if(type==0)
                    {
                        amenities_layout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        furnishings_layout.setVisibility(View.GONE);
                        amenities_layout.setVisibility(View.GONE);
                        occupancy_layout.setVisibility(View.GONE);
                    }
                }
                else if(pos==1)
                {
                    Collections.addAll(list,getResources().getStringArray(R.array.commercial_property_type));
                    prop_type_recycler_view.setAdapter(new CustomAdapter(list));
                    furnishings_layout.setVisibility(View.GONE);
                    //amenities_layout.setVisibility(View.GONE);
                    occupancy_layout.setVisibility(View.GONE);
                }
                else
                {
                    prop_type_recycler_view.setAdapter(new CustomAdapter(null));
                    furnishings_layout.setVisibility(View.GONE);
                    amenities_layout.setVisibility(View.GONE);
                    occupancy_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {}
        });
        if(type==0 || type==1 || type==2 ||type==3)
        {
            purpose_layout.setVisibility(View.GONE);
            bedroom_layout.setVisibility(View.GONE);
            furnishings_layout.setVisibility(View.GONE);
            amenities_layout.setVisibility(View.GONE);
            occupancy_layout.setVisibility(View.GONE);
        }
        return v;
    }
}
