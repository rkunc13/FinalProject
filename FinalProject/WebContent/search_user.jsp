<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import ="java.util.*, Database.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	UserData userManager = (UserData) getServletContext().getAttribute("user manager");
	DBConnection connection = (DBConnection) getServletContext().getAttribute("connection");
	FriendData friendsManager = (FriendData) getServletContext().getAttribute("friends manager");
	String info = (String) session.getAttribute("info");
	int userId = (Integer) session.getAttribute("user id");
	ArrayList<Integer> userInfo = userManager.findUsers(userId, info); 
	int otherUserId = 0;
	String otherUserEmail = "";
	int friendStatus = 0;
%>

<html>
<head>
	<link rel="stylesheet" href="style.css">
	<link rel="stylesheet" href="normalize.css">
	<link rel="shortcut icon" type="image/ico" href="images/favicon.ico" />
	<link href="//fonts.googleapis.com/css?family=Raleway:400,300,600" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>User Search</title>
</head>
<body>

<h1 class="auth-center">Search Result for <%=info%>:</h1>
	<% if(userInfo.size() > 0){ %>
	<div class="card">
	<%for (int i = 0 ; i < userInfo.size() ; i++) { %>
		<% 
		otherUserId = userInfo.get(i);
		otherUserEmail = userManager.getEmailByID(otherUserId);
		friendStatus = friendsManager.checkFriendStatus(userId, otherUserId); 
		%>
		<ul>
		<li>
		<a href="friend_homepage.jsp?friendEmail=<%=otherUserEmail%>"><%=userManager.getNameByID(otherUserId)%>: <%=otherUserEmail%></a>
		</li>
		</ul>
		<%
		if (friendStatus == 1) {%>  
			<p>Friends</p>
		<%}%>
		<%
		if (friendStatus == 2) {%>  
			<p>Friend Request Sent</p>
		<%}%>
		<%
		if (friendStatus == 3) {%>  
			<form action="FriendServlet" method="post">
			<input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
			<input type="hidden" value="Accept From Search Page" name="decision" />
			<input type="submit" value="Accept Request" />
			</form>
			<form action="FriendServlet" method="post">
			<input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
			<input type="hidden" value="Reject From Search Page" name="decision" />
			<input type="submit" value="Reject Request" />
			</form>
		<%}%>
		<%
		if (friendStatus == 4) {%>  
			<form action="FriendRequestServlet" method="post">
			<p><input type="hidden" value=<%=otherUserEmail%> name="friendEmail" />
			<input type="hidden" value="Request from Search Page" name="requestSent" />
		    <input type="submit" value="Send Friend Request" /></p>
		    </form>
		<%}%>
		<br/>
	<%}%>
	</div>
	<% } %>

<p id="search_user_back_to_profile"><a href="homepage.jsp"><button type="button">Back to My Profile</button></a></p>


</body>
</html>