package com.elevenfolders.arduino.buildlight;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Franklin Dattein
 * @since 1.0
 */
public class HudsonReader implements CIReader {

    private static final String YELLOW = "yellow";
    private static final String BLUE = "blue";
    private static final String API_XML = "/api/xml";
    private String json;
    private String xml;
    private String url;

    Map<String, Status> colorMapping = new HashMap<String, Status>();

    public HudsonReader(String url) {
        if(url != null && !url.endsWith(API_XML)) {
            url += API_XML;
        }
        this.url = url;

        colorMapping.put("yellow", Status.FAILED);
        colorMapping.put("red", Status.FAILED);
        colorMapping.put("blue", Status.SUCCESS);
        colorMapping.put("orange_anime", Status.BUILDING_FROM_FAILURE);
        colorMapping.put("blue_anime", Status.BUILDING_FROM_SUCCESS);
        colorMapping.put("disabled", Status.DISABLED);
    }

    public void updateFromServer() {
//        this.json = getFromUrl();
        this.xml = getFromUrl();
    }

    private String getFromUrl() {
        try {
            HttpGet httpget = new HttpGet(this.url);
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(httpget);
            InputStream in = response.getEntity().getContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line, html = null;
            while ((line = reader.readLine()) != null) {
                str.append(line);
            }
            in.close();
            return str.toString();
        } catch (Exception ex) {
            System.err.println(ex);
            return null;
        }
    }

    public Status getStatus() {
        if (this.xml == null) {
            throw new NullPointerException("Xml needs to be initialized. Call updateFromServer first");
        }
//        List<Job> jobs = fromJsonToJobs();

        String color = fromXmlToColor();

        return fromColorToStatus(color);
//        if (containsFailed(jobs)) {
//            return Status.FAILED;
//        } else if (containsBuilding(jobs)) {
//            return Status.BUILDING;
//        } else {
//            return Status.SUCCESS;
//        }
    }

    private Status fromColorToStatus(String color) {


        return colorMapping.get(color);
    }

    private String fromXmlToColor() {
//        List<Job> jobs = new ArrayList<Job>();
        String result = "blue";
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = null;
        try {
            builder = domFactory.newDocumentBuilder();

            Document document = builder.parse(new InputSource(new StringReader(this.xml)));

            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            XPathExpression expr = xpath.compile("//freeStyleProject/color");

            Object n = expr.evaluate(document, XPathConstants.NODESET);
            NodeList nodes = (NodeList) n;
            if (nodes.getLength() > 0) {
                result = (String) nodes.item(0).getTextContent();
            }

        } catch (Exception e) {
            System.err.println(e);
        }
        return result;
    }


    private List<Job> fromJsonToJobs() {
        Gson gson = new Gson(); // Or use new GsonBuilder().create();

        Type lt = new TypeToken() {
        }.getType();
        String result = gson.fromJson(this.json, lt);

        List<Job> jobs;

        Type listType = new TypeToken<List<Job>>() {
        }.getType();
        jobs = gson.fromJson(this.json, listType);
        return jobs;
    }

    private boolean containsBuilding(List<Job> jobs) {
        return containsColor(jobs, BLUE);

    }

    private boolean containsFailed(List<Job> jobs) {
        return containsColor(jobs, YELLOW);
    }

    private boolean containsColor(List<Job> jobs, String color) {
        for (Job job : jobs) {
            if (job.getColor() != null && job.getColor().toLowerCase().startsWith(color.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getJson() {
        return this.json;
    }
}
