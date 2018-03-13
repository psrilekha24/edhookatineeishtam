package e.sri_pt1682.realestateapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import e.sri_pt1682.realestateapp.provider.MyContentProvider;

/**
 * Created by sri-pt1682 on 26/02/18.
 */

public class LoginOrSignup extends AppCompatActivity
{
    private DatabaseHelper mDatabaseHelper;
    TextView mLoginOrSignupTextview,mErrorMsg;
    EditText mName,mPhoneNumber,mUsername,mPassword;
    Button mLoginOrSignupButton;
    public static LoginOrSignup newInstance()
    {
        return new LoginOrSignup();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_or_signup_layout);
        mDatabaseHelper = new DatabaseHelper(this);
        mLoginOrSignupTextview = findViewById(R.id.login_or_signup_textview);
        mErrorMsg = findViewById(R.id.error_msg);
        mName = findViewById(R.id.name_text_view);
        mPhoneNumber = findViewById(R.id.phone_number_text_view);
        mUsername = findViewById(R.id.username_text_view);
        mPassword = findViewById(R.id.password_text_view);
        mLoginOrSignupButton = findViewById(R.id.login_or_signup_button);
        if(getIntent().getExtras().getInt("edit_profile")==0)
        {
            mName.setVisibility(View.INVISIBLE);
            mPhoneNumber.setVisibility(View.INVISIBLE);
        }
        else
        {
            User user=mDatabaseHelper.getUser(MainActivity.mCurrentUser.getId());
            mUsername.setText(user.getUsername());
            mName.setText(user.getName());
            mPhoneNumber.setText(""+user.getPhno());
            mPassword.setText(user.getPassword());
            mLoginOrSignupButton.setText("SAVE");
            mLoginOrSignupTextview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();

        mLoginOrSignupTextview.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mLoginOrSignupButton.getText()=="SIGN UP")
                {
                    mLoginOrSignupButton.setText("LOGIN");
                    mName.setVisibility(View.INVISIBLE);
                    mPhoneNumber.setVisibility(View.INVISIBLE);
                    mLoginOrSignupTextview.setText(R.string.not_login_so_signup);
                }
                else
                {
                    mLoginOrSignupButton.setText("SIGN UP");
                    mName.setVisibility(View.VISIBLE);
                    mPhoneNumber.setVisibility(View.VISIBLE);
                    mLoginOrSignupTextview.setText(R.string.not_signup_so_login);
                }
            }
        });

        mLoginOrSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLoginOrSignupButton.getText().equals("SIGN UP"))
                {
                    if(mName.getText().length()==0)
                    {
                        mErrorMsg.setText("Name cannot be empty");
                    }
                    else if(mPhoneNumber.getText().length()!=10)
                    {
                        mErrorMsg.setText("Invalid phone number");
                    }
                    else if(mUsername.getText().length()<6)
                    {
                        mErrorMsg.setText("Username should be atleast 6 characters long");
                    }
                    else if(mDatabaseHelper!=null && mDatabaseHelper.checkUsername(mUsername.getText().toString()))
                    {
                        mErrorMsg.setText("Username already exists.. Choose a different one!");
                    }
                    else if(mPassword.getText().length()<6)
                    {
                        mErrorMsg.setText("Password should be atleast 6 characters long");
                    }
                    else
                    {
                        mErrorMsg.setText("");
                        mDatabaseHelper.addUser(new User(mName.getText().toString(),mUsername.getText().toString(),mPassword.getText().toString(),Long.valueOf(mPhoneNumber.getText().toString())),1);
                        updateNavBar();
                    }
                }
                else if(mLoginOrSignupButton.getText().equals("LOGIN"))
                {
                    boolean success=mDatabaseHelper.checkPassword(mUsername.getText().toString(),mPassword.getText().toString());
                    if(success)
                    {
                        Toast.makeText(LoginOrSignup.this,"Correct",Toast.LENGTH_SHORT).show();
                        updateNavBar();
                    }
                    else
                    {
                        mUsername.setText("");mPassword.setText("");
                        mErrorMsg.setText("Username or password wrongly entered");
                    }
                }
                else
                {
                    mDatabaseHelper.addUser(new User(mName.getText().toString(),MainActivity.mCurrentUser.getId(),mUsername.getText().toString(),mPassword.getText().toString(),Long.valueOf((mPhoneNumber.getText().toString()))),0);
                    Toast.makeText(getApplicationContext(),"Data updated successfully",Toast.LENGTH_SHORT).show();
                    updateNavBar();
                }
            }
        });
    }
    public void updateNavBar()
    {
        User user;
        Cursor cursor=getContentResolver().query(MyContentProvider.USER_URI,null,"username = ?",new String[]{mUsername.getText().toString()},null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            user= new User(cursor.getString(1),cursor.getString(0),cursor.getString(3),cursor.getString(4),cursor.getString(2),cursor.getLong(5));
            cursor.close();
        }
        else {return;}
        Intent intent=new Intent();
        intent.putExtra("name",user.getName());
        intent.putExtra("phn",user.getPhno());
        intent.putExtra("photo",user.getPhoto());
        intent.putExtra("id",user.getId());
        intent.putExtra("username",user.getUsername());
        intent.putExtra("password",user.getPassword());
        setResult(0,intent);
        finish();
    }
}
