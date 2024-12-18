/* Name:Andres Albornoz
Course: CNT 4714 – Fall 2024 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 1, 2024
*/
import java.sql.*;

public class ResultSetToHTMLFormatterClass {

    /**
     * The getHtmlRows() method takes the inbound ResultSet object (a table returned from an SQL database query)
     * and reformats it into an HTML encoded table suitable for return and display in any web browser.
     * 
     * IMPORTANT NOTE:
     * This is a static synchronized method! This is because we only want to allow at most one thread
     * to be executing this code at one time to eliminate the possibility of interleaved return results.
     */
    public static synchronized String getHTMLRows(ResultSet results) throws SQLException {
        // Create a StringBuilder object to hold the HTML version of the ResultSet
        StringBuilder htmlRows = new StringBuilder();
        
        // Get metadata from the ResultSet to retrieve column names and column count
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        // Build the table header row
        htmlRows.append("<table border='1' style='border-collapse: collapse; width: 100%;'>");
        htmlRows.append("<tr style='background-color: #f2f2f2;'>");
        for (int i = 1; i <= columnCount; i++) {
            htmlRows.append("<th style='padding: 8px; text-align: left;'>")
                    .append(metaData.getColumnName(i))
                    .append("</th>");
        }
        htmlRows.append("</tr>");

        // Build the table rows
        int rowCount = 0; // Counter for zebra row striping
        while (results.next()) {
            // Alternate row colors for zebra striping
            String rowStyle = (rowCount % 2 == 0) ? "background-color: #ffffff;" : "background-color: #f9f9f9;";
            htmlRows.append("<tr style='").append(rowStyle).append("'>");

            // Add table data cells
            for (int i = 1; i <= columnCount; i++) {
                htmlRows.append("<td style='padding: 8px;'>")
                        .append(results.getString(i) == null ? "" : results.getString(i))
                        .append("</td>");
            }
            htmlRows.append("</tr>");
            rowCount++;
        }

        // Return the final HTML string
        return htmlRows.toString();
    }
}
