package kr.ac.kopo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class BookServlet
 */
//필요한 서블릿 URL만을 넣음으로써 다른 이상한 URL만 넣는다 보통 중괄호{}와 콤마 , 로 구분한다.
@WebServlet({"/book/list","/book/add","/book/update","/book/delete"})
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public BookServlet() {
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println(uri);
	
		if(uri.endsWith("/list")) {
			ArrayList<Book> list = new ArrayList<Book>();
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				
				Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","madang","madang"); 
				
				Statement stmt = conn.createStatement();
				
				ResultSet rs = stmt.executeQuery("SELECT * FROM book ORDER BY bookid asc");
			
			while(rs.next()){
				//book 객체의 공간을 만들어 주고 
				Book item = new Book();
				item.setBookid(rs.getInt(1));
				item.setBookname(rs.getString(2));
				item.setPublisher(rs.getString(3));
				item.setPrice(rs.getInt(4));
				//list에 담는다.
				list.add(item);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			//항상 close가 된 상태에서 넘겨줘야한다.
			}catch(Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("/WEB-INF/book/list.jsp").forward(request, response);
			
		}else if(uri.endsWith("/add")) {
			request.getRequestDispatcher("/WEB-INF/book/add.jsp").forward(request, response);
		}else if(uri.endsWith("/update")) {
			request.getRequestDispatcher("/WEB-INF/book/update.jsp").forward(request, response);
		}else if(uri.endsWith("/delete")) {
			request.getRequestDispatcher("/WEB-INF/book/delete.jsp").forward(request, response);
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		if(uri.endsWith("/add")) {
			request.getRequestDispatcher("/WEB-INF/book/add_apply.jsp").forward(request, response);
		}else if(uri.endsWith("/update")) {
			request.getRequestDispatcher("/WEB-INF/book/update_apply.jsp").forward(request, response);
		}
	}
}
