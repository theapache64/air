<%@ page import="com.theah64.air.database.Channels" %>
<%@ page import="com.theah64.air.database.Songs" %>
<%@ page import="com.theah64.air.models.Channel" %>
<%@ page import="com.theah64.air.models.Song" %>
<%@ page import="com.theah64.webengine.utils.WebEngineConfig" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 13/3/18
  Time: 10:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%

    final String channelId = request.getParameter("id");
    final Channel channel = Channels.getInstance().get(Channels.COLUMN_ID, channelId);

    if (channel == null) {
        throw new IllegalArgumentException("Invalid channel id : " + channelId);
    }
%>
<html>
<head>
    <title>Channel <%=channel.getId()%>
    </title>

    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/aplayer@1.8.0/dist/APlayer.min.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/npm/aplayer@1.8.0/dist/APlayer.min.js"></script>
    <script>
        $(document).ready(function () {

            const ap = new APlayer({
                container: document.getElementById('aplayer'),
                audio: [

                    <%
                         final List<Song> songs = Songs.getInstance().getAll(channelId);
                         for(Song song : songs){
                             %>
                    {


                        name: '<%=song.getName()%>',
                        artist: 'Channel <%=channel.getId()%>',
                        url: '<%=song.getPath()%>',
                        cover: 'https://image.freepik.com/free-vector/jazz-music-poster_23-2147509220.jpg'
                    }
                    ,
                    <%
                 }
            %>


                ]
            });

            //Socket config
            var socketUrl = "<%=WebEngineConfig.isDebugMode()
            ? "ws://localhost:8080/"
            : "ws://theapache64.com/"%>";

            socketUrl += "air/v1/air_socket/LISTENER/<%=channelId%>";

            console.log("Socket URL : " + socketUrl);

            //Building socket
            var webSocket = new WebSocket(socketUrl);

            webSocket.onopen = function (evnt) {
                console.log("Socket opened");
            };

            webSocket.onmessage = function (evnt) {

                console.log("Socket got new message");
                console.log(evnt);

                var data = JSON.parse(evnt.data);

                ap.addAudio([
                    {
                        name: data.name,
                        artist: 'Channel <%=channelId%>',
                        url: data.path,
                        cover: 'https://f4.bcbits.com/img/a1299877209_10.jpg'
                    }
                ]);

                ap.switchAudio(ap.options.audio.length-1);
                ap.seek(0);
                ap.play();
            };

            webSocket.onclose = function (evnt) {
                console.log("Socket closed!");
            };

            webSocket.onerror = function (evnt) {
                console.log("Socket error occurred");
            };


        });

    </script>
</head>
<body>


<div id="aplayer"></div>

</body>
</html>
