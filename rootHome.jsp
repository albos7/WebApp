<!-- /* Name:Andres Albornoz
Course: CNT 4714 – Fall 2024 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 1, 2024
*/ -->
<!DOCTYPE html>
<%
    String sqlStatement= (String) session.getAttribute("sqlStatement");
    if(sqlStatement == null) sqlStatement= "select * from suppliers";
    String message= (String) session.getAttribute("message");
    if (message==null) message= "Enter a valid query";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Root User Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
        }
        h1 {
            text-align: center;
        }
        textarea {
            width: 100%;
            height: 150px;
            margin-bottom: 10px;
        }
        button {
            margin-right: 10px;
            padding: 10px 20px;
            font-size: 16px;
        }
        .results {
            margin-top: 20px;
            border: 1px solid #ccc;
            padding: 10px;
            background-color: #f9f9f9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Welcome, Root User</h1>
    <form action="Root" method="post">
        <label for="sqlCommand">Enter SQL Command:</label><br>
        <textarea id="sqlCommand" name="sqlCommand" placeholder="Enter your SQL command here"></textarea><br>
        <input type="submit" value="Execute Command"></input> &nbsp; &nbsp; &nbsp; 
        <input type="reset" value="Reset Form" onclick="javascript:erasetext"></input> &nbsp; &nbsp; &nbsp; 
        <input type="button" value="Clear Results" onclick="javascript:erasedata"></input>
    </form>
    <p class="results">All execution results will appear below this line</p>
    <hr>
    <div class="results">
        <h2>Results:</h2>
        <center>
        <p>
        <table id="data">
            <%=message%>
        </table> 
        </p>
        </center>
    </div>
</div>
</body>
</html>
