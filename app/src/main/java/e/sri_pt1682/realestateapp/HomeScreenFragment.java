package e.sri_pt1682.realestateapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by sri-pt1682 on 09/03/18.
 */

public class HomeScreenFragment extends Fragment implements View.OnClickListener
{
    RelativeLayout mRentView,mSaleView,mAgriView,mLandView;
    public static HomeScreenFragment newInstance()
    {
        Bundle args = new Bundle();
        HomeScreenFragment fragment = new HomeScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v=inflater.inflate(R.layout.home_fragment,container,false);
        mRentView=v.findViewById(R.id.rent_image_view);mRentView.setOnClickListener(this);
        mSaleView=v.findViewById(R.id.sale_image_view);mSaleView.setOnClickListener(this);
        mAgriView=v.findViewById(R.id.agri_image_view);mAgriView.setOnClickListener(this);
        mLandView=v.findViewById(R.id.lands_image_view);mLandView.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view)
    {
        getActivity().setContentView(R.layout.activity_main);
        switch(view.getId())
        {
            case R.id.sale_image_view:getFragmentManager().beginTransaction().replace(R.id.main_container,PropertyFragment.newInstance(0)).commit();
                break;
            case R.id.rent_image_view:getFragmentManager().beginTransaction().replace(R.id.main_container,PropertyFragment.newInstance(1)).commit();
                break;
            case R.id.agri_image_view:getFragmentManager().beginTransaction().replace(R.id.main_container,PropertyFragment.newInstance(2)).commit();
                break;
            case R.id.lands_image_view:getFragmentManager().beginTransaction().replace(R.id.main_container,PropertyFragment.newInstance(3)).commit();
                break;
        }
    }
}
