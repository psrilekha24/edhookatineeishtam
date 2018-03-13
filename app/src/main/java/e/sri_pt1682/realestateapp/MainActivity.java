package e.sri_pt1682.realestateapp;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import e.sri_pt1682.realestateapp.provider.MyContentProvider;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    NavigationView navigationView;
    View navHeader;
    TextView name,phn;
    Button login_or_logout;
    ImageView prof_pic,edit_profile;
    static User mCurrentUser=new User();
    SharedPreferences preferences;
    boolean alreadyLoaded;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_screen,HomeScreenFragment.newInstance()).commit();

        navigationView = findViewById(R.id.nav_view);
        navHeader=navigationView.getHeaderView(0);
        name=navHeader.findViewById(R.id.profile_user_name);
        phn=navHeader.findViewById(R.id.phone_number_profile);
        prof_pic=navHeader.findViewById(R.id.profile_image);
        edit_profile=navHeader.findViewById(R.id.edit_profile_details);
        login_or_logout=navHeader.findViewById(R.id.login_or_logout_button);

        alreadyLoaded=preferences.getBoolean("already_loaded",false);
        Log.d("thisOne","already : "+alreadyLoaded);
        if(!alreadyLoaded)
        {
            try
            {
                JSONObject object=new JSONObject(loadJSONFromAsset());
                JSONArray user_array=object.getJSONArray("user");
                ContentValues values = new ContentValues();
                for (int i=0;i<5;i++)
                {
                    JSONObject jsonObject = user_array.getJSONObject(i);
                    Log.d("thisOne",jsonObject.getString("username")+"\t"+jsonObject.getString("password"));
                    values.put("user_id", jsonObject.getString("user_id"));
                    values.put("phone", Long.valueOf(jsonObject.getString("phone")));
                    values.put("username", jsonObject.getString("username"));
                    values.put("password", jsonObject.getString("password"));
                    values.put("name", jsonObject.getString("name"));
                    getContentResolver().insert(MyContentProvider.USER_URI,values);
                }
                preferences.edit().putBoolean("already_loaded",true).apply();
            }
            catch (Exception e){}
        }

        String logged_in_user=preferences.getString("pref_name","");
        if(!logged_in_user.equals(""))
        {
            addProfImageClickListener();
            addEditProfileClickListener();
            name.setText(logged_in_user);
            phn.setText(preferences.getString("pref_phn",""));
            Glide.with(this)
                    .load(preferences.getString("pref_photo",""))
                    .apply(new RequestOptions().circleCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground))
                    .into(prof_pic);
            login_or_logout.setText("LOGOUT");
            mCurrentUser.setId(preferences.getString("curr_user",""));
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });

        login_or_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(login_or_logout.getText().equals("LOGIN / SIGN UP"))
                {
                    Intent intent=new Intent(MainActivity.this,LoginOrSignup.class);
                    intent.putExtra("edit_profile",0);
                    startActivityForResult(intent,0);
                }
                else
                {
                    login_or_logout.setText("LOGIN / SIGN UP");
                    name.setText("Guest");
                    phn.setText("");
                    edit_profile.setVisibility(View.INVISIBLE);
                    prof_pic.setImageResource(R.drawable.ic_launcher_foreground);
                    preferences.edit().putString("pref_name","").putString("pref_phn","").putString("pref_photo","").putString("curr_user","").apply();
                }

            }
        });

        navigationView.setNavigationItemSelectedListener(this);
    }
    private void addProfImageClickListener()
    {
        prof_pic.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view)
            {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {}
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                    return;
                }
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),1);
            }
        });
    }

    private void addEditProfileClickListener()
    {
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(MainActivity.this,LoginOrSignup.class);
                intent.putExtra("edit_profile",1);
                startActivityForResult(intent,0);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==0)
        {
            mCurrentUser.setId(data.getStringExtra("id"));
            edit_profile.setVisibility(View.VISIBLE);
            name.setText(data.getStringExtra("name"));
            phn.setText(String.valueOf(data.getLongExtra("phn", 0)));
            login_or_logout.setText("LOGOUT");
            Glide.with(navHeader.getContext()).load(data.getStringExtra("photo"))
                        .apply(new RequestOptions().circleCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground))
                        .into(prof_pic);
            addProfImageClickListener();
            addEditProfileClickListener();
            preferences.edit().putString("pref_name",data.getStringExtra("name")).putString("pref_phn",phn.getText().toString())
                    .putString("pref_photo",data.getStringExtra("photo")).putString("curr_user",mCurrentUser.getId()).apply();
        }
        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            String image_uri=data.getData().toString();
            Log.d("thisOne",image_uri);
            Glide.with(navHeader.getContext()).load(image_uri)
                    .apply(new RequestOptions().circleCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_foreground))
                    .into(prof_pic);
            preferences.edit().putString("pref_photo",data.getData().toString()).apply();
            ContentValues values=new ContentValues();
            values.put("photo",data.getData().toString());
            getContentResolver().update(MyContentProvider.USER_URI,values,"user_id = ?",new String[]{mCurrentUser.getId()});
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_screen,HomeScreenFragment.newInstance()).commit();break;
            case R.id.nav_notify_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_screen,NotifyAndAboutFragment.newInstance(1)).commit();break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.main_screen,NotifyAndAboutFragment.newInstance(2)).commit();break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {

            InputStream is = getAssets().open("sample_data.json");
            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
