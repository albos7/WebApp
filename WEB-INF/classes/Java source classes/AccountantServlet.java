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

@WebServlet("/Accountant")
public class AccountantServlet extends HttpServlet {

	private Connection connection;
	private CallableStatement statement;
	private boolean mysqlReturnValue;
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message="";
        String command="";
       
        // Retrieve the selected report option from the form
        String reportOption = request.getParameter("reportOption");
       try {
    	   getDBConnection();
    	   
    	   switch(reportOption) {
		       case "1": command = "{CALL Get_The_Sum_Of_All_Parts_Weights()}"; break;
		       case "2": command = "{CALL Get_The_Maximum_Status_Of_All_Suppliers()}"; break;
		       case "3": command = "{CALL Get_The_Total_Number_Of_Shipments()}"; break;
		       case "4": command = "{CALL Get_The_Name_Of_The_Job_With_The_Most_Workers()}"; break;
		       case "5": command = "{CALL List_The_Name_And_Status_Of_All_Suppliers()}"; break;
		       default: command= "{CALL ERROR()}"; break;
    	   }
    	   statement= connection.prepareCall(command);
    	   mysqlReturnValue= statement.execute();
    	   if(mysqlReturnValue) {
    		   ResultSet rs=statement.getResultSet();
    		   
    		   message= ResultSetToHTMLFormatterClass.getHTMLRows(rs);
    	   }else {
    		   message= "Error executing RPC!";
    	   }
    			   
       }catch(SQLException e) {
    	   message="<tr bgcolor=#ff0000><td><font color= #ffffff><b>Error: Executing the SQL statement:</b><br>"+e.getMessage()+"</tr></td></font>";
       }
       // Set message as a request attribute and forward to JSP
       HttpSession session = request.getSession();
       session.setAttribute("message",  message);
       RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/accountantHome.jsp");
       dispatcher.forward(request, response);
       destroy();
    }

   
    private void getDBConnection() {
    	// Load properties file for database connection
        Properties properties = new Properties();
        FileInputStream filein = null;
        MysqlDataSource dataSource = null;

        try {
            filein = new FileInputStream("C:/Program Files/Apache Software Foundation/Tomcat 10.1/webapps/Project4/WEB-INF/lib/accountant.properties");
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
          connection.close();
       } // end try
       // handle database exceptions by returning error to client
       catch( SQLException sqlException ) 
       {
          sqlException.printStackTrace();
       } // end catch
    } // end method destroy
}