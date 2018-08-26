<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
      <script src="<c:url value='https://cdnjs.cloudflare.com/ajax/libs/jquery/1.8.1/jquery.min.js'/>"></script>
<script type="text/javascript">
		  $(document).ready(function(){
			  console.log("thoughtSpot JSP")
			  resetAPI();
		  });
		  function resetAPI() {       
              $.ajax('https://thoughtspot.AL.com/callosum/v1/session/login', {
                  data: {
                      username: "tsadmin",
                      password: "admin",
                      rememberme: true
                },
                xhrFields: {
                    withCredentials: true
                    },
                type: 'POST',
                timeout: 3000, // connection attempt timesout in 3 seconds
                success: function (data,textstatus,request) {
	           		   var jsonData = JSON.stringify(data);
	           		   var pinboardId = "c3e42e87-f7f0-418c-9856-8db76de6119f";
	                   var myURL = "https://thoughtspot.AL.com/callosum/v1/tspublic/v1/pinboarddata?id=" + pinboardId;
		                   $.ajax(myURL, {
		                          data: {
		                                rememberme: true
		                                },
		                          type: 'POST',
		                          xhrFields: {
		                                     withCredentials: true
		                                     },
		                    timeout: 3000, // connection attempt timesout in 3 seconds
			                    success: function (data1) {
			                    	var jsonData = JSON.stringify(data1);
			               			//alert("jsonData::"+jsonData);
			               			//console.log(data1);
			                    	//console.log(jsonData);
			                    	var path = $("#path").val();
			                    	dataJson = {};
			                    	dataJson['inputJson'] = JSON.stringify(data1);
			                    	console.log(dataJson['inputJson']);
			                    	 $.ajax({
			                            	type: 'POST',
			                            	data:dataJson,
			                            	url: path+"/salescenter/saveThoughtSpotDataToCache",
			                            	success: function (data2) {
			 			                       console.log("Data ::"+data2);
			 			                     /*  $.ajax({
					                            	type: 'POST',
					                            	data:dataJson,
					                            	url: path+"/salescenter/getThoughtSpotDataFromCache",
					                            	success: function (data3) {
					 			                       alert("Data ::"+data3);
					 			                       console.log("data ="+data3);
					 			                      }
					                         	});  */
			 			                      }
			                         	}); 
		                        },
			                    error: function (data) {
			                    	var jsonData = JSON.stringify(data);
			               			alert("Error="+jsonData);
		                        }   
		                  });
                },
               error: function (data) {
             	  var jsonData = JSON.stringify(data);
                   alert("ErrorJsondata="+jsonData);
               }  
               });
            }
  </script>
</head> 
<body id="body">
      <form name="thoughtSpotForm" id="thoughtSpotForm">
         <br/>
         <br/>
         <br/>
         <div style="padding-left: 330px">
          <input type="hidden" id="path" name="path" value="<%=request.getContextPath()%>"/>
            
            <span id="error2"> Loading...............</span>
         </div>
      </form>
</body>
</html>