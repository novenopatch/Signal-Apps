package jin.jerrykel.dev.signal.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import jin.jerrykel.dev.signal.model.InfomationAppUser;

/**
 * Created by JerrykelDEV on 28/01/2021 03:44
 */
public class DatabaseManager extends OrmLiteSqliteOpenHelper {
    private static final  String DATABASE_NAME = "AppInf.db";
    private static final  int DATABASE_VERSION = 1;
    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, InfomationAppUser.class);
        }catch (Exception e){

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            InfomationAppUser infomationAppUser = new InfomationAppUser(false,new Date());
            TableUtils.dropTable(connectionSource, InfomationAppUser.class,true);
            onCreate(database,connectionSource);
            insertInformation(infomationAppUser);
        }catch (Exception e){

        }
    }
    private   Dao getDao(){
        Dao<InfomationAppUser,Integer> dao = null;
        try {
             dao= getDao(InfomationAppUser.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dao;
    }
    public  void insertInformation(InfomationAppUser infomationAppUser){

        try {
            Dao<InfomationAppUser,Integer> dao =getDao();
            dao.create(infomationAppUser);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void updateInformation(InfomationAppUser infomationAppUser){
        try {

            getDao().update(infomationAppUser);



        } catch (SQLException e) {
            //Log.e("DATABASE","insert error");
        }
    }
    public InfomationAppUser getInformation(){

        try {
            ArrayList<InfomationAppUser> infomationAppUsers = (ArrayList<InfomationAppUser>) getDao().queryForAll();
          return infomationAppUsers.get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }


    }
}
