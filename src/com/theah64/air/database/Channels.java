package com.theah64.air.database;

import com.theah64.air.models.Channel;
import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.querybuilders.QueryBuilderException;
import com.theah64.webengine.database.querybuilders.SelectQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by theapache64 on 14/3/18.
 */
public class Channels extends BaseTable<Channel> {

    private static final Channels instance = new Channels();

    private Channels() {
        super("channels");
    }

    public static Channels getInstance() {
        return instance;
    }

    @Override
    public Channel get(String column, String value) throws QueryBuilderException, SQLException {
        return new SelectQueryBuilder.Builder<Channel>(getTableName(), new SelectQueryBuilder.Callback<Channel>() {
            @Override
            public Channel getNode(ResultSet rs) throws SQLException {
                return new Channel(rs.getString(COLUMN_ID));
            }
        }).select(new String[]{COLUMN_ID})
                .where(column, value)
                .build()
                .get();
    }

    @Override
    public List<Channel> getAll() throws QueryBuilderException, SQLException {
        return new SelectQueryBuilder.Builder<Channel>(getTableName(), new SelectQueryBuilder.Callback<Channel>() {
            @Override
            public Channel getNode(ResultSet rs) throws SQLException {
                return new Channel(
                        rs.getString(COLUMN_ID)
                );
            }
        })
                .select(new String[]{COLUMN_ID})
                .build()
                .getAll();
    }
}
