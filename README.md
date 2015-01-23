#Five-in-a-row
A HTML5 online Game which use WebSocket protocol. The front page use the Bootstrap framework, while the server side use tomcat WebSocket API. When both sides prepared, the game start and playing chess.

-----
Deploy
-------
####About Client
Change the WebSocket connection address(in client/js/init.js),default `ws://zyl-me.xicp.net:1234/WebSocket/five`

####About Server
Put the `WebRoot/*` into `$CATALINA_HOME/webapps/WebSocket`
