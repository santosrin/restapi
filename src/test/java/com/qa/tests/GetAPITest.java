package com.qa.tests;

import com.qa.util.TestUtil;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import qa.base.TestBase;
import qa.client.RestClient;

import java.io.IOException;
import java.util.HashMap;

public class GetAPITest extends TestBase{

    TestBase testBase;
    String serviceURL;
    String apiURL;
    String url;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;

    public GetAPITest() throws IOException {
    }

    @BeforeMethod
    public void setUp() throws IOException {
        testBase = new TestBase();
        serviceURL = prop.getProperty("URL");
        apiURL = prop.getProperty("serviceURL");
        url = serviceURL+apiURL;

    }

    @Test
    public void getAPITest() throws IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(url);

        //Get statuscode
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code =>> "+statusCode);

        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200);

        //Json string
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API "+ responseJson);

        //per page
        String perPageValue = TestUtil.getValueByJPath(responseJson,"per_page");
        System.out.println("perPageValue -> "+ perPageValue);
        Assert.assertEquals(perPageValue,"6");

        //total
        String totalValue = TestUtil.getValueByJPath(responseJson,"total");
        System.out.println("totalValue -> "+ totalValue);
        Assert.assertEquals(totalValue,"12");

//        //get the value from JSON array
//        String lastName = TestUtil.getValueByJPath(responseJson,"/data[0]/");
//        System.out.println(lastName);

        //All Headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String,String> allHeaders = new HashMap();

        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }

        System.out.println("Headers Array--->" + allHeaders);

    }

    @Test
    public void getAPITestWithHeaders() throws IOException {
        restClient = new RestClient();

        HashMap<String ,String> hashMap = new HashMap<String, String>();
        hashMap.put("Content-Type","application/json");


        closeableHttpResponse = restClient.get(url,hashMap);

        //Get statuscode
        int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
        System.out.println("Status Code =>> "+statusCode);

        Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200);

        //Json string
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(),"UTF-8");
        JSONObject responseJson = new JSONObject(responseString);
        System.out.println("Response JSON from API "+ responseJson);

        //per page
        String perPageValue = TestUtil.getValueByJPath(responseJson,"per_page");
        System.out.println("perPageValue -> "+ perPageValue);
        Assert.assertEquals(perPageValue,"6");

        //total
        String totalValue = TestUtil.getValueByJPath(responseJson,"total");
        System.out.println("totalValue -> "+ totalValue);
        Assert.assertEquals(totalValue,"12");

//        //get the value from JSON array
//        String lastName = TestUtil.getValueByJPath(responseJson,"/data[0]/");
//        System.out.println(lastName);

        //All Headers
        Header[] headersArray = closeableHttpResponse.getAllHeaders();
        HashMap<String,String> allHeaders = new HashMap();

        for(Header header : headersArray) {
            allHeaders.put(header.getName(), header.getValue());
        }

        System.out.println("Headers Array--->" + allHeaders);

    }

}
