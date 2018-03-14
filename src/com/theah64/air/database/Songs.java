package com.theah64.air.database;

import com.theah64.air.models.Song;
import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.querybuilders.QueryBuilderException;
import com.theah64.webengine.database.querybuilders.SelectQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by theapache64 on 14/3/18.
 */
public class Songs extends BaseTable<Song> {

    private static final Songs instance = new Songs();
    public static final String COLUMN_PATH = "path";

    private Songs() {
        super("songs");
    }

    public static Songs getInstance() {
        return instance;
    }

    public List<Song> getAll(final String channelId) throws QueryBuilderException, SQLException {

        return new SelectQueryBuilder.Builder<Song>(getTableName(), new SelectQueryBuilder.Callback<Song>() {
            @Override
            public Song getNode(ResultSet rs) throws SQLException {
                return new Song(
                        rs.getString(COLUMN_ID),
                        rs.getString(COLUMN_PATH)
                );
            }
        })
                .query("SELECT s.id, s.path FROM channel_songs cs INNER JOIN channels c ON c.id = cs.channel_id INNER JOIN songs s ON s.id = cs.song_id WHERE cs.channel_id = ? GROUP BY s.id;")
                .where("cs.channel_id", channelId)
                .build()
                .getAll();

    }

    @Override
    public List<Song> getAll() throws QueryBuilderException, SQLException {
        return new SelectQueryBuilder.Builder<Song>(getTableName(), rs -> new Song(
                rs.getString(COLUMN_ID),
                rs.getString(COLUMN_PATH)
        )).select(new String[]{COLUMN_ID, COLUMN_PATH})
                .build()
                .getAll();
    }

    @Override
    public Song get(String column, String value) throws QueryBuilderException, SQLException {
        return new SelectQueryBuilder.Builder<Song>(getTableName(), new SelectQueryBuilder.Callback<Song>() {
            @Override
            public Song getNode(ResultSet rs) throws SQLException {
                return new Song(
                        rs.getString(COLUMN_ID),
                        rs.getString(COLUMN_PATH)
                );
            }
        })
                .select(new String[]{COLUMN_ID, COLUMN_PATH})
                .where(column, value)
                .build()
                .get();
    }
}
