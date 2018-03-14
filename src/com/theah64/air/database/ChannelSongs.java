package com.theah64.air.database;

import com.theah64.air.models.ChannelSong;
import com.theah64.air.models.Song;
import com.theah64.webengine.database.BaseTable;

import java.util.List;

/**
 * Created by theapache64 on 14/3/18.
 */
public class ChannelSongs extends BaseTable<ChannelSong> {

    private static final ChannelSongs instance = new ChannelSongs();

    private ChannelSongs() {
        super("channel_songs");
    }

    public static ChannelSongs getInstance() {
        return instance;
    }

}
