package com.test.restClient;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
 
//import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class SpringRestClient {
	
	//public static final String REST_SERVICE_URI = "<a class="vglnk" href="http://localhost:8080/SecureRESTApiWithBasicAuthentication" rel="nofollow"><span>http</span><span>://</span><span>localhost</span><span>:</span><span>8080</span><span>/</span><span>SecureRESTApiWithBasicAuthentication</span></a>";
	
	public static final String REST_SERVICE_URI = "http://localhost:8080/SpringSecurityWithRest";
		  
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send user-credentials.
     */
    private static HttpHeaders getHeaders(){
        String plainCredentials="Chandan:Chandan";
        //String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
         
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + plainCredentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
     
    /*
     * Send a GET request to get list of all users.
     */
    @SuppressWarnings("unchecked")
    private static void listAllUsers(){
        System.out.println("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/login", HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>)response.getBody();
         
        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("User : id="+map.get("id")+", Name="+map.get("name")+", Age="+map.get("age")+", Salary="+map.get("salary"));;
            }
        }else{
            System.out.println("No user exist----------");
        }
    }
	      
	public static void main(String args[]){
	         
	        listAllUsers();
	 
	       /* getUser();
	 
	        createUser();
	        listAllUsers();
	 
	        updateUser();
	        listAllUsers();
	 
	        deleteUser();
	        listAllUsers();
	 
	        deleteAllUsers();
	        listAllUsers();*/
	    }
}


