/* Name:Andres Albornoz
Course: CNT 4714 – Fall 2024 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 1, 2024
*/
import java.io.*;
import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.MysqlDataSource;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/Authenticate")
public class AuthenticationServlet extends HttpServlet {
	private Connection connection;
	private PreparedStatement statement;
	private ResultSet rs;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String q= "Select * from usercredentials where login_username= ? and login_password= ?";
        boolean userCredentials=false;

        try {
        	getDBConnection();
        	statement=connection.prepareStatement(q);
        	
        	statement.setString(1, username);
        	statement.setString(2, password);
        	
        	rs=statement.executeQuery();
        	
        	if(rs.next()) {
        		userCredentials=true;
        	}else {
        		userCredentials=false;
        		response.sendRedirect("errorpage.html");
        	}
        }catch(SQLException sqlException) {
        	JOptionPane.showMessageDialog(null, "Error: SQL Exception", "MAJOR ERROR- ERROR ", JOptionPane.ERROR_MESSAGE);
        	response.sendRedirect("errorpage.html");
        }
        
        if(userCredentials) {
        	switch (username) {
	        	case "root":
		        	response.sendRedirect("rootHome.jsp");
		            break;
	            case "client":
		            response.sendRedirect("clientHome.jsp");
		            break;
	            case "theaccountant":
		            response.sendRedirect("accountantHome.jsp");
		            break;
	            default:
	            	response.sendRedirect("errorpage.html");
	                break;
        	}
        }
        destroy();
    }
    
    private void getDBConnection() {
    	// Load properties file for database connection
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        try {
            filein = new FileInputStream("C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project4/WEB-INF/lib/systemapp.properties");
            properties.load(filein);
            dataSource = new MysqlDataSource();
            dataSource.setURL(properties.getProperty("MYSQL_DB_DRIVER_URL"));
            dataSource.setUser(properties.getProperty("MYSQL_DB_DRIVER_USERNAME"));
            dataSource.setPassword(properties.getProperty("MYSQL_DB_DRIVER_PASSWORD"));
            connection = dataSource.getConnection();
//		    statement = connection.createStatement();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error: SQL Exception", "MAJOR ERROR- ERROR ", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    public void destroy()
    {
       // attempt to close statements and database connection
       try 
       {
          statement.close();
          connection.close();
       } // end try
       // handle database exceptions by returning error to client
       catch( SQLException sqlException ) 
       {
          sqlException.printStackTrace();
       } // end catch
    } // end method destroy
}

