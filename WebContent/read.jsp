<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.*" %>
    <%@ page import="com.java.pack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>Online News Recommendation System</title>
	<link rel="shortcut icon" href="../favicon.ico"> 
		<link rel="stylesheet" type="text/css" href="css/default.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<script src="js/modernizr.custom.js"></script>
		<style>
				
.spacing-table {
         margin-left: 28cm;
    left: 200%;
    font-family: 'Helvetica', 'Arial', sans-serif;
    font-size: 13px;
    border-collapse: separate;
    border-spacing: 0 10px;
width: 21%;}
.spacing-table th {
    text-align: left;
    padding: 5px 15px;
}
.spacing-table td {
    border-width: 1px 0;
    border-color: silver;
    border-style: solid;
    background-color: white;
    color: blueviolet;
    padding: 5px 5px 15px;
        text-decoration: underline;
    
    
}
.spacing-table td:first-child {
    border-left-width: 3px;
    border-radius: 5px 0 0 5px;
}
.spacing-table td:last-child {
    border-right-width: 3px;
    border-radius: 0 5px 5px 0;
}
			   .newcolumn{
			   margin-top:1cm;
			   			   margin-bottom:1cm;
			   
			    border: 2px solid silver;
    background: white;
    color: black;
left: 20%;
    float: left;
    width: 65%;
    padding: 2em 2em 2em 2em;
    min-height: 300px;
    position: relative;
    font-family: 'Helvetica', 'Arial', sans-serif;
    font-size: 14px;
    line-height: 2;
	.sb-search{
	width: 100%;
	}
	.sb-search.sb-search-open,.no-js .sb-search{
		width:block;	
	}
		</style>
</head>
<body>

<div class="container">     
        <div class="newcolumn">
          <div id="sb-search" class="sb-search">


          </div>
            <%!int i; %>
				
				
			<% 
			 HttpSession sess = request.getSession(); 
			 String[] nameList = (String[])sess.getAttribute("read");
			i = Integer.parseInt(request.getParameter("user"));
			i++;
			if(nameList[i]!=null)
					out.println(nameList[i]); 
					
				 %>
				</td></tr>	
				</table>    
				
        </div>
       
      </div>
    
    </div>

 <script src="js/classie.js"></script>
    <script src="js/uisearch.js"></script>
    <script>
      new UISearch( document.getElementById( 'sb-search' ) );
    </script>
    
       
</body>
</html>