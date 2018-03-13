package e.sri_pt1682.realestateapp.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import e.sri_pt1682.realestateapp.DatabaseHelper;

public class MyContentProvider extends ContentProvider
{
    private static final String AUTH="e.sri_pt1682.realestateapp.provider.MyContentProvider";
    public static final Uri USER_URI=Uri.parse("content://"+AUTH+"/user");
    public static final Uri PREFERENCES_URI=Uri.parse("content://"+AUTH+"/preferences");
    public static final Uri FURNISHINGS_URI=Uri.parse("content://"+AUTH+"/furnishings");
    public static final Uri AMENITIES_URI=Uri.parse("content://"+AUTH+"/amenities");
    public static final Uri LOCATION_URI=Uri.parse("content://"+AUTH+"/location");
    public static final Uri PHOTOS_URI=Uri.parse("content://"+AUTH+"/photos");
    public static final Uri PROPERTY_URI=Uri.parse("content://"+AUTH+"/property");
    public static final Uri CONTACTED_PEOPLE_DETAILS_URI=Uri.parse("content://"+AUTH+"/contacted_people_details");
    public static final Uri CONTACTED_PROPERTIES_URI=Uri.parse("content://"+AUTH+"/contacted_properties_list");
    public static final Uri WISHLIST_URI=Uri.parse("content://"+AUTH+"/wish_list");
    public static final Uri RECENT_VIEWS_URI=Uri.parse("content://"+AUTH+"/recent_views");
    public static final Uri RESIDENTIAL_URI=Uri.parse("content://"+AUTH+"/residential_property");
    public static final Uri RENTAL_RESIDENTIAL_URI=Uri.parse("content://"+AUTH+"/rental_residential_property");
    public static final Uri PROPERTY_QUERIES_URI=Uri.parse("content://"+AUTH+"/property_queries");
    public static final Uri COMM_AGRI_LAND_URI=Uri.parse("content://"+AUTH+"/commercial_agricultural_land");


    private DatabaseHelper mDatabaseHelper;

    public MyContentProvider() {}

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db=mDatabaseHelper.getWritableDatabase();
        int noOfRowsDeleted=db.delete(uri.getLastPathSegment(),selection,selectionArgs);
        return noOfRowsDeleted;
    }

    @Override
    public String getType(Uri uri) {return null;}

    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        SQLiteDatabase db=mDatabaseHelper.getWritableDatabase();
        long id=db.insert(uri.getLastPathSegment(),null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        //return Uri.parse(uri+"/"+id);
        return null;
    }

    @Override
    public boolean onCreate()
    {
        mDatabaseHelper=new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
    {
        SQLiteDatabase db=mDatabaseHelper.getWritableDatabase();
        Cursor cursor=db.query(uri.getLastPathSegment(),projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db=mDatabaseHelper.getWritableDatabase();
        int noOfRowsUpdated=db.update(uri.getLastPathSegment(),values,selection,selectionArgs);
        return noOfRowsUpdated;
    }
}
