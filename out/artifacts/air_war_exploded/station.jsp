<%@ page import="com.theah64.air.database.Channels" %>
<%@ page import="com.theah64.air.database.Songs" %>
<%@ page import="com.theah64.air.models.Channel" %>
<%@ page import="com.theah64.air.models.Song" %>
<%@ page import="java.util.List" %>
<%@ page import="com.theah64.webengine.utils.WebEngineConfig" %><%--
  Created by IntelliJ IDEA.
  User: theapache64
  Date: 14/3/18
  Time: 10:07 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Station</title>

    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    <script>
        $(document).ready(function () {

            var socketUrl = "<%=WebEngineConfig.isDebugMode()
            ? "ws://localhost:8080/"
            : "ws://theapache64.com/"%>";

            socketUrl += "air/v1/air_socket/STATION/STATION";

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


            };

            webSocket.onclose = function (evnt) {
                console.log("Socket closed!");
            };

            webSocket.onerror = function (evnt) {
                console.log("Socket error occurred");
            };

            $("form#station").on('submit', function (event) {
                event.preventDefault();

                var songId = $("select#songs option:selected").val();
                var channelId = $("select#channels option:selected").val();

                var data = {
                    song_id: songId,
                    channel_id: channelId
                };

                webSocket.send(JSON.stringify(data));
                console.log("Song id "+songId+" and Channel id "+channelId);

            });


        });
    </script>

</head>
<body>

<%
    final List<Song> songs = Songs.getInstance().getAll();
    final List<Channel> channels = Channels.getInstance().getAll();
%>

<div class="container">
    <br>
    <div class="row">
        <form id="station" class="form-inline">
            Play
            <select class="form-control" id="songs">
                <%
                    for (final Song song : songs) {
                %>
                <option value="<%=song.getId()%>"><%=song.getName()%>
                </option>
                <%
                    }
                %>
            </select>

            On

            <select class="form-control" id="channels">
                <%
                    for (final Channel channel : channels) {
                %>
                <option value="<%=channel.getId()%>">Channel <%=channel.getId()%>
                </option>
                <%
                    }
                %>
            </select>

            <button class="btn btn-success"><span class="glyphicon glyphicon-play"></span> Play</button>

        </form>
    </div>
</div>

</body>
</html>
