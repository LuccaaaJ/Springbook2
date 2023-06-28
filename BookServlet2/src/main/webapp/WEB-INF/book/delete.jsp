<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	String bookid = request.getParameter("bookid");
	String bookname = request.getParameter("bookname");
	String publisher = request.getParameter("publisher");
	String price = request.getParameter("price");

	Class.forName("oracle.jdbc.OracleDriver");
	Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","madang","madang"); 
	
	PreparedStatement pstmt = conn.prepareStatement("DELETE FROM book WHERE bookid=?");
	
	pstmt.setString(1, bookid);
	
	int result = pstmt.executeUpdate();
	pstmt.close();
	conn.close();
 %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<div>
			<h3><%=result %>건이 삭제 되었습니다</h3>
		</div>
		<div>
			<a href="list">목록</a>
		</div>
		
	</div>

</body>
</html>