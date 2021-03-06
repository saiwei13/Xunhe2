package saiwei.com.river.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import saiwei.com.river.model.User;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "USER".
*/
public class UserDao extends AbstractDao<User, String> {

    public static final String TABLENAME = "USER";

    /**
     * Properties of entity User.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Userid = new Property(0, String.class, "userid", true, "USERID");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property No = new Property(2, int.class, "no", false, "NO");
        public final static Property LoginName = new Property(3, String.class, "loginName", false, "LOGIN_NAME");
        public final static Property Email = new Property(4, String.class, "email", false, "EMAIL");
        public final static Property Phone = new Property(5, String.class, "phone", false, "PHONE");
        public final static Property Mobile = new Property(6, String.class, "mobile", false, "MOBILE");
        public final static Property LoginDate = new Property(7, long.class, "loginDate", false, "LOGIN_DATE");
    }


    public UserDao(DaoConfig config) {
        super(config);
    }
    
    public UserDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"USER\" (" + //
                "\"USERID\" TEXT PRIMARY KEY NOT NULL ," + // 0: userid
                "\"NAME\" TEXT," + // 1: name
                "\"NO\" INTEGER NOT NULL ," + // 2: no
                "\"LOGIN_NAME\" TEXT," + // 3: loginName
                "\"EMAIL\" TEXT," + // 4: email
                "\"PHONE\" TEXT," + // 5: phone
                "\"MOBILE\" TEXT," + // 6: mobile
                "\"LOGIN_DATE\" INTEGER NOT NULL );"); // 7: loginDate
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"USER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, User entity) {
        stmt.clearBindings();
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(1, userid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getNo());
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(4, loginName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(5, email);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(6, phone);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(7, mobile);
        }
        stmt.bindLong(8, entity.getLoginDate());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, User entity) {
        stmt.clearBindings();
 
        String userid = entity.getUserid();
        if (userid != null) {
            stmt.bindString(1, userid);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getNo());
 
        String loginName = entity.getLoginName();
        if (loginName != null) {
            stmt.bindString(4, loginName);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(5, email);
        }
 
        String phone = entity.getPhone();
        if (phone != null) {
            stmt.bindString(6, phone);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(7, mobile);
        }
        stmt.bindLong(8, entity.getLoginDate());
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public User readEntity(Cursor cursor, int offset) {
        User entity = new User( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // userid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getInt(offset + 2), // no
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // loginName
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // email
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // phone
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // mobile
            cursor.getLong(offset + 7) // loginDate
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, User entity, int offset) {
        entity.setUserid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNo(cursor.getInt(offset + 2));
        entity.setLoginName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setEmail(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPhone(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setMobile(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setLoginDate(cursor.getLong(offset + 7));
     }
    
    @Override
    protected final String updateKeyAfterInsert(User entity, long rowId) {
        return entity.getUserid();
    }
    
    @Override
    public String getKey(User entity) {
        if(entity != null) {
            return entity.getUserid();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(User entity) {
        return entity.getUserid() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
