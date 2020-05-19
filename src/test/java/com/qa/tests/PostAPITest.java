package com.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.data.Users;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.base.TestBase;
import qa.client.RestClient;

import javax.swing.text.html.parser.Entity;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class PostAPITest extends TestBase {

    TestBase testBase;
    String serviceURL;
    String apiURL;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    public PostAPITest() throws IOException {
    }

    @BeforeMethod
    public void setUp() throws IOException {
        testBase = new TestBase();
        serviceURL = prop.getProperty("URL");
        apiURL = prop.getProperty("serviceURL");
        url = serviceURL+apiURL;

    }

    @Test
    public void postAPITest() throws IOException {
        restClient = new RestClient();
        HashMap<String ,String> hashMap = new HashMap<String, String>();
        hashMap.put("Content-Type","application/json");

        //Jackson API
        ObjectMapper mapper = new ObjectMapper();
        Users users = new Users("Morpheus","Leader"); // expected users object

        //Object to JSON file conversion
        mapper.writeValue(new File("/Users/santoshsrinivas/Documents/restapi/src/main/java/com/qa/data/users.json"),users);

        //Object to JSON String
        String usersJSONString = mapper.writeValueAsString(users);
        System.out.println("usersJSONString => "+ usersJSONString);

        closeableHttpResponse = restClient.post(url,usersJSONString,hashMap);

        //Status code
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_201);

        //JsonString
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        System.out.println(responseString);

        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("The response from API is: "+ responseJson);

        //Json to java
        Users userObj = mapper.readValue(responseString, Users.class); //Actual users object
        System.out.println(userObj);

        Assert.assertTrue(users.getName().equals(userObj.getName()));
        Assert.assertTrue(users.getJob().equals(userObj.getJob()));

        System.out.println(userObj.getId() +" : "+ userObj.getCreatedAt());
    }

}
