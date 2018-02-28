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
		<script>
		
		function process(row)
		{
			
			var v = row.rowIndex;
			var jspcall="output.jsp?user="; 
		    window.open(jspcall.concat(v),'_blank');
									
		}
		
		</script>
			<style>
			
	.sb-search{
	width: 100%;
	}
	.sb-search.sb-search-open,.no-js .sb-search{
		width:block;	
	}
	
.spacing-table {
       font-family: 'Helvetica', 'Arial', sans-serif;
    font-size: 13px;
    border-collapse: separate;
    border-spacing: 0 10px;
    width: 75%;
    margin-left: 15%;
}
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
		</style>
</head>
<body>
<h1 style = "text-align: center;">News Recommendation System</h1>


<div class="container">     
        <div class="column">
          <div id="sb-search" class="sb-search">
          
          
          
          <form action="second.jsp" method="post">
               <input type="hidden" name="userClick">
            
</form>
          
          
          
          
<form action="RankServlet" method="post">
              <input class="sb-search-input" placeholder="Enter your search term..." type="text" name="user">
              <input class="sb-search-submit" type="submit" value="Submit">
              <span class="sb-icon-search"></span>
    </form>

          </div>
          
          <br><br><br><br><br>
            <%!int i; %>
<table class="spacing-table" >
				<% String[] nameList=(String[])request.getAttribute("Fileoutput");

				HttpSession sess = request.getSession(); 
				sess.setAttribute("output", nameList);
				if(nameList != null)
				for(i=0 ;i <nameList.length-1;i++) {%>
				
					<tr onclick="process(this)"><td> 
					
					<br>
				
					<% if(nameList[i]!=null)
					out.println(nameList[i].substring(0, 120)+ "......"); 
					
				} %>
				
			
				</td></tr>
				</table>   
        </div>
        
      </div>
    

 <script src="js/classie.js"></script>
    <script src="js/uisearch.js"></script>
    <script>
      new UISearch( document.getElementById( 'sb-search' ) );
    </script>
    
       
</body>
</html>