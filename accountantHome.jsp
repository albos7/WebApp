<!-- /* Name:Andres Albornoz
Course: CNT 4714 – Fall 2024 – Project Four
Assignment title: A Three-Tier Distributed Web-Based Application
Date: December 1, 2024
*/ -->
<!DOCTYPE html>
<%
    String message= (String) session.getAttribute("message");
    if (message==null) message= "Enter a valid query";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Accountant User Home</title>
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
        select, button {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
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
    <h1>Welcome, Accountant User</h1>
    <form action="Accountant" method="post">
        <label for="reportOption">Select a Report:</label><br>
        <select id="reportOption" name="reportOption">
            <option value="1">Report 1: Sum Of All Parts Weights</option>
            <option value="2">Report 2: Maximum Status Of All Suppliers</option>
            <option value="3">Report 3: Total Number Of Shipments</option>
            <option value="4">Report 4: Name Of The Job With The Most Worker</option>
            <option value="5">Report 5: Name And Status Of All Suppliers</option>
        </select><br>
        <input type="submit" value="Run Report"></input>
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
