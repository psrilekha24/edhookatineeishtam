package e.sri_pt1682.realestateapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import e.sri_pt1682.realestateapp.provider.MyContentProvider;

/**
 * Created by sri-pt1682 on 28/02/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    private ContentResolver mContentResolver;
    private static final String DATABASE_NAME="realestate.db";
    private static final String TABLE_USER="user",TABLE_PROPERTY="property",TABLE_CONTACTED_PEOPLE_DETAILS="contacted_people_details",
            TABLE_RESIDENTIAL="residential_property",TABLE_RENTAL_RESIDENTIAL="rental_residential_property",TABLE_COMM_AGRI_LAND="commercial_agricultural_land",
            TABLE_PROP_QUERY="property_queries",TABLE_OCCUPANCY="occupancy",TABLE_WISHLIST="wish_list",TABLE_RECENTS="recent_views",
            TABLE_FURNISHINGS="furnishings",TABLE_AMENITIES="amenities",TABLE_LOCATION="location",TABLE_PHOTOS="photos",TABLE_PREFERNCES="preferences";

    private static final String COL_NAME="name",COL_PHOTO="photo",
            COL_USERNAME="username",COL_PASSWORD="password",COL_ID="user_id",COL_PHNNO="phone";

    private static final String COL_PROP_ID="property_id",COL_PURPOSE="property_purpose",COL_TYPE="property_type",COL_OWNER_ID="owner_id",
            COL_DATE_POSTED="date_posted",COL_AREA="area",COL_DESCRIPTION="description",COL_RENT_OR_PRICE="rent_or_price",COL_CONSTR_STATUS="constr_status",
            COL_VIEWS="number_of_views",COL_RATINGS="ratings",COL_REPORTS="number_of_reports";

    private static final String COL_CON_USER_ID="contacted_user_id";

    private static final String COL_PROP_OR_PREF_TYPE="property_or_preference",COL_TYPE_ID="type_id";

    private static final String COL_PROP_TYPE="property_type",COL_OCCUPANCY="property_occupancy",COL_BEDROOMS="number_of_rooms";

    private static final String COL_BALCONIES="number_of_balconies",COL_BATHS_ATTACHED="baths_attached",COL_BATHS_UNATTACHED="baths_unattached",
            COL_DEPOSIT="deposit",COL_MAINTENANCE="maintenance",COL_FLOORING="flooring",COL_FACING="facing",COL_VEGGIES="veggies_compulsory",
            COL_PETS="pets_allowed";

    private static final String COL_SQFT_RATE="rate_per_sqft";

    private static final String COL_QUESTION="question",COL_ANSWER="answer",COL_QUES_NO="question_number",COL_ASKER_ID="asker_id";
    private static final String COL_OCCUPANCY_TYPE="occupancy_type";

    private static final String COL_AC="ac",COL_TV="tv",COL_WASH_MACH="washing_machine",COL_FRIDGE="refrigerator",COL_GEYSER="geyser",
            COL_WARDROBE="wardrobes",COL_LIGHTS="lights",COL_FANS="fans",COL_KITCHEN="modular_kitchen",COL_SOFA="sofa",COL_BEDS="beds";

    private static final String COL_PARKING="parking",COL_WIFI="wifi",COL_POWER_BACKUP="power_backup",COL_WASTE_DISPOSAL="waste_disposal",
            COL_LIFT="lift",COL_POOL="pool",COL_GYM="gym",COL_GAME_ARENA="games_arena";
    
    private static final String COL_ADDR="detailed_address",COL_CITY="city",COL_MAP_LAT="location_latitude",COL_MAP_LONG="location_longitude";
    private static final String COL_LINK="photo_link";
    
    private static final String COL_BUDGET_MIN="min_budget",COL_BUDGET_MAX="max_budget",COL_AREA_MIN="min_area",COL_AREA_MAX="max_area", COL_PREF_ID="preference_id",
            COL_NEARBY_LOCS="prefer_nearby_locations",COL_NOTIFY_SIMILAR_CONTENT="notify_similar_content",COL_NOTIFY_PROP_MATCH="notify_new_matching_properties";
    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        mContentResolver=context.getContentResolver();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_USER+"("+COL_ID+" TEXT PRIMARY KEY, "+COL_NAME+" TEXT, "+COL_PHOTO+" TEXT, "
                +COL_USERNAME+" TEXT, "+COL_PASSWORD+" TEXT, "+COL_PHNNO+" NUMBER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PROPERTY+"("+COL_PROP_ID+" TEXT PRIMARY KEY, "+COL_PURPOSE+" INTEGER, "+COL_TYPE+" INTEGER, "
                +COL_OWNER_ID+" TEXT, "+COL_DATE_POSTED+" TEXT, "+COL_AREA+" NUMBER, "+COL_DESCRIPTION+" TEXT, "+COL_RENT_OR_PRICE+" NUMBER, "+COL_CONSTR_STATUS+" INTEGER, "
                +COL_VIEWS+" INTEGER, "+COL_RATINGS+" NUMBER, "+COL_REPORTS+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_CONTACTED_PEOPLE_DETAILS+"("+COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"),"
                +COL_OWNER_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_OWNER_ID+"),"+COL_CON_USER_ID+" TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_RESIDENTIAL+"("+COL_PROP_OR_PREF_TYPE+" INTEGER, "+COL_TYPE_ID+" TEXT, "+COL_PROP_TYPE+" INTEGER, "
                +COL_BEDROOMS+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_RENTAL_RESIDENTIAL+"("+COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"),"
                +COL_BALCONIES+" INTEGER, "+COL_OCCUPANCY+" INTEGER, "+COL_BATHS_ATTACHED+" INTEGER, "+COL_BATHS_UNATTACHED+" INTEGER, "+COL_DEPOSIT+" NUMBER, "+COL_MAINTENANCE+" NUMBER, "
                +COL_FLOORING+" TEXT, "+COL_FACING+" TEXT, "+COL_VEGGIES+" INTEGER, "+COL_PETS+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_COMM_AGRI_LAND+"("+COL_PROP_OR_PREF_TYPE+" INTEGER, "+COL_TYPE_ID+" TEXT, "+COL_PROP_TYPE+" INTEGER, "
                +COL_SQFT_RATE+" NUMBER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PROP_QUERY+"("+COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"),"+COL_QUESTION+" TEXT, "
                +COL_ANSWER+" TEXT, "+COL_QUES_NO+" INTEGER PRIMARY KEY, "+COL_ASKER_ID+" TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_OCCUPANCY+"("+COL_ID+" TEXT REFERENCES "+TABLE_USER+"("+COL_ID+"),"
                +COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"), "+COL_OCCUPANCY_TYPE+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_WISHLIST+"("+COL_ID+" TEXT REFERENCES "+TABLE_USER+"("+COL_ID+"),"
                +COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_RECENTS+"("+COL_ID+" TEXT REFERENCES "+TABLE_USER+"("+COL_ID+"),"
                +COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"))");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_FURNISHINGS+"("+COL_PROP_OR_PREF_TYPE+" INTEGER, "+COL_TYPE_ID+" TEXT, "+COL_AC+" INTEGER, "+COL_TV+" INTEGER, "+COL_WASH_MACH+" INTEGER, "+COL_FRIDGE+" INTEGER, "
                +COL_GEYSER+" INTEGER, "+COL_WARDROBE+" INTEGER, "+COL_LIGHTS+" INTEGER, "+COL_FANS+" INTEGER, "+COL_KITCHEN+" INTEGER, "+COL_SOFA+" INTEGER, "
                +COL_BEDS+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_AMENITIES+"("+COL_PROP_OR_PREF_TYPE+" INTEGER, "+COL_TYPE_ID+" TEXT, "+COL_PARKING+" INTEGER, "
                +COL_WIFI+" INTEGER, "+COL_POWER_BACKUP+" INTEGER, "+COL_WASTE_DISPOSAL+" INTEGER, "
                +COL_LIFT+" INTEGER, "+COL_POOL+" INTEGER, "+COL_GYM+" INTEGER, "+COL_GAME_ARENA+" INTEGER)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_LOCATION+"("+COL_PROP_OR_PREF_TYPE+" INTEGER, "+COL_TYPE_ID+" TEXT, "+COL_ADDR+" TEXT, "+COL_CITY+" TEXT, "+COL_MAP_LAT+" DOUBLE, "+COL_MAP_LONG+" DOUBLE)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PHOTOS+"("+COL_PROP_ID+" TEXT REFERENCES "+TABLE_PROPERTY+"("+COL_PROP_ID+"), "+COL_LINK+" TEXT)");

        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_PREFERNCES+"("+COL_PURPOSE+" INTEGER, "+COL_ID+" TEXT REFERENCES "+TABLE_USER+"("+COL_ID+"), "
                +COL_PREF_ID+" TEXT PRIMARY KEY, "+COL_PROP_TYPE+" INTEGER, "+COL_BUDGET_MIN+" INTEGER, "+COL_BUDGET_MAX+" INTEGER, "+COL_AREA_MIN+" INTEGER, "+COL_AREA_MAX+" INTEGER, "
                +COL_NEARBY_LOCS+" INTEGER, "+COL_NOTIFY_SIMILAR_CONTENT+" INTEGER, "+COL_NOTIFY_PROP_MATCH+" INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public void addUser(User user,int insert)
    {
        ContentValues values=new ContentValues();
        values.put(COL_NAME,user.getName());
        values.put(COL_ID,user.getId());
        values.put(COL_PASSWORD,user.getPassword());
        values.put(COL_PHNNO,user.getPhno());
        values.put(COL_USERNAME,user.getUsername());
        values.put(COL_PHOTO,user.getPhoto());

        if(insert==0)
        {
            mContentResolver.update(MyContentProvider.USER_URI, values, COL_ID + " = ?", new String[]{MainActivity.mCurrentUser.getId()});
        }
        else
        {
            Uri uri = mContentResolver.insert(MyContentProvider.USER_URI, values);
        }
        displayContentProvider();
    }
    public boolean checkPassword(String username,String password)
    {
        Cursor cursor=mContentResolver.query(MyContentProvider.USER_URI,null,null,null,null);
        if (cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do{
            if (cursor.getString(cursor.getColumnIndex(COL_USERNAME)).equals(username) && cursor.getString(cursor.getColumnIndex(COL_PASSWORD)).equals(password))
                return true;
            }while (cursor.moveToNext());
        }

        return false;
    }
    public boolean checkUsername(String username)
    {
        Cursor cursor=mContentResolver.query(MyContentProvider.USER_URI,null,null,null,null);
        if (cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do{
                if (username.equals(cursor.getString(cursor.getColumnIndex(COL_USERNAME))))
                    return true;
            }while (cursor.moveToNext());
        }
        return false;
    }
    public void displayContentProvider()
    {
        Cursor cursor=mContentResolver.query(MyContentProvider.USER_URI,null,null,null,null);
        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();
            do {
                Log.d("thisOne", cursor.getString(cursor.getColumnIndex(COL_USERNAME)) + "\t" + cursor.getString(cursor.getColumnIndex(COL_PASSWORD)));
            }while (cursor.moveToNext());
        }
    }
    public User getUser(String id)
    {
        Cursor cursor=mContentResolver.query(MyContentProvider.USER_URI,null,COL_ID+" = ?",new String[]{id},null);
        if(cursor!=null)
        {
            cursor.moveToFirst();
            return new User(cursor.getString(1),cursor.getString(0),cursor.getString(3),cursor.getString(4),cursor.getString(2),cursor.getLong(5));
        }
        return null;
    }
}
