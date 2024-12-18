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

@WebServlet("/Root")
public class RootServlet extends HttpServlet {
	private Connection connection;
	String message="";
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sqlCommand = request.getParameter("sqlCommand");


	        // Connect to the database and execute the SQL command
	        try  {
	        	getDBConnection();
	        	Statement statement = connection.createStatement();
	            // Sanitize the SQL command for basic validation
	            String lowerSql = sqlCommand.trim().toLowerCase();
	            int updateReturnValues=0;
	            	 
	            try {
	            		if (lowerSql.contains("shipments")&&(lowerSql.startsWith("update") || lowerSql.startsWith("insert") || lowerSql.startsWith("replace"))) {
	            			updateReturnValues=simpleBusinessLogic(connection, statement, sqlCommand);
		            			
		            		if (updateReturnValues > 0) {
		            			message+="<p>Business logic applied: " + updateReturnValues + " supplier(s) updated.</p>";
		            		} else {
		            			message+="<p>Business logic checked: No suppliers required updates.</p>";
		            		}
		                }else {
		                	ResultSet rs = statement.executeQuery(sqlCommand);
			            	message= ResultSetToHTMLFormatterClass.getHTMLRows(rs);
		                }
	            }catch(SQLException e) {
	            		message="<tr bgcolor=#ff0000><td><font color= #ffffff><b>Error: Executing the SQL statement:</b><br>"+e.getMessage()+"</tr></td></font>";
	            }
	            statement.close();
	        }catch(SQLException e) {
        		message="<tr bgcolor=#ff0000><td><font color= #ffffff><b>Error: Executing the SQL statement:</b><br>"+e.getMessage()+"</tr></td></font>";
	        }
        	
        	// Set message as a request attribute and forward to JSP
        	 HttpSession session = request.getSession();
             session.setAttribute("message",  message);
             RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
             dispatcher.forward(request, response);
             destroy();
        

        
    }
    
    private void getDBConnection() {
    	// Load properties file for database connection
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        try {
            filein = new FileInputStream("C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project4/WEB-INF/lib/root.properties");
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
    private int simpleBusinessLogic(Connection conn, Statement statement, String sqlCommand) throws SQLException {
        // Execute the user's SQL command first
        int rowsAffected = statement.executeUpdate(sqlCommand);

        // Business logic: Increment supplier status if a shipment with quantity >= 100 is involved
        String businessLogic = """
                UPDATE suppliers
                SET status = status + 5
                WHERE snum IN (
                    SELECT snum
                    FROM shipments
                    WHERE quantity >= 100
                )
                """;
        int suppliersUpdated = statement.executeUpdate(businessLogic);
        return suppliersUpdated;
    }
    public void destroy()
    {
       // attempt to close statements and database connection
       try 
       {
          connection.close();
       } // end try
       // handle database exceptions by returning error to client
       catch( SQLException sqlException ) 
       {
          sqlException.printStackTrace();
       } // end catch
    } // end method destroy
}