package e.sri_pt1682.realestateapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sri-pt1682 on 09/03/18.
 */

public class NotifyAndAboutFragment extends Fragment
{
    private int frag_type;
    public static NotifyAndAboutFragment newInstance(int type)
    {
        Bundle args = new Bundle();
        args.putInt("type",type);

        NotifyAndAboutFragment fragment = new NotifyAndAboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle bundle=getArguments();
        frag_type=(int)bundle.get("type");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v;
        if(frag_type==1)
            v=inflater.inflate(R.layout.notification_settings,container,false);
        else
            v=inflater.inflate(R.layout.about_layout,container,false);
        return v;
    }
}
