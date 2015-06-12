package com.example.slr_e_ticketer.slr_e_ticketer;

import android.provider.BaseColumns;

/**
 * Created by Shalika on 4/28/2015.
 */
public class TableData {
    public TableData(){

    }
    public static abstract class TableInfo implements BaseColumns {
        public static final String USER_NAME = "user_name";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name ";
        public static final String NIC = "nic";
        public static final String PASSWORD = "password";
        public static final String DATABASE_NAME = "user_info_1.db";
        public static final String TABLE_NAME = "reg_info";
    }
}
