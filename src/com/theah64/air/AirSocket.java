package com.theah64.air;

import com.theah64.air.database.Songs;
import com.theah64.air.models.Song;
import com.theah64.webengine.database.BaseTable;
import com.theah64.webengine.database.querybuilders.QueryBuilderException;
import com.theah64.webengine.servlets.AdvancedBaseServlet;
import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by theapache64 on 14/3/18.
 */
@ServerEndpoint(AdvancedBaseServlet.VERSION_CODE + "/air_socket/{type}/{channel_id}")
public class AirSocket {

    private static final String TYPE_STATION = "STATION";
    private static final String TYPE_LISTENER = "LISTENER";

    //<channelId, listeners>
    private static final Map<String, List<Session>> listeners = new HashMap<>();
    private static final String KEY_CHANNEL_ID = "channel_id";
    private static final String KEY_SONG_ID = "song_id";

    @OnOpen
    public void onOpen(@PathParam("type") String type, @PathParam("channel_id") String channelId, Session session) {
        System.out.println("Socket opened : " + session.toString());

        if (type.equals(TYPE_LISTENER)) {
            System.out.println("Added new listener session to channel " + channelId);

            listeners.computeIfAbsent(channelId, k -> new ArrayList<>());
            listeners.get(channelId).add(session);
        } else {
            System.out.println("Station logged in");
        }
    }

    @OnMessage
    public void onMessage(@PathParam("type") String type, Session session, String data) throws JSONException {

        System.out.println("Message received :" + session.toString() + ":" + data);

        if (type.equals(TYPE_STATION)) {

            //Station wants play song in some channel
            final JSONObject jo = new JSONObject(data);

            final String channelId = jo.getString(KEY_CHANNEL_ID);
            final String songId = jo.getString(KEY_SONG_ID);
            try {
                final Song song = Songs.getInstance().get(Songs.COLUMN_ID, songId);

                final JSONObject joSong = new JSONObject();
                joSong.put(Songs.COLUMN_NAME,song.getName());
                joSong.put(Songs.COLUMN_PATH, song.getPath());

                final List<Session> channelListeners = listeners.get(channelId);
                if (channelListeners != null) {
                    for (final Session lSession : channelListeners) {
                        try {
                            System.out.println("Command sent to channel " + channelId);
                            lSession.getBasicRemote().sendText(joSong.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            } catch (QueryBuilderException | SQLException e) {
                e.printStackTrace();
            }


        }

    }

    @OnError
    public void onError(Throwable e) {
        System.out.println("Error : " + e.getMessage());
    }


    @OnClose
    public void onClose(@PathParam("type") String type, @PathParam("channel_id") String channelId, Session session) {
        System.out.println("Socket closed " + session);

        if (type.equals(TYPE_LISTENER)) {
            System.out.printf("Removing listener session from channel " + channelId);
            final List<Session> lSessions = listeners.get(channelId);
            if (lSessions != null) {
                lSessions.remove(session);
            }
        } else {
            System.out.println("Station logged out");
        }
    }
}
