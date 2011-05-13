package com.elevenfolders.arduino.buildlight;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.*;

/**
 * @author Franklin Dattein
 * @since 1.0
 */
public class HudsonReader implements ContinuousIntegrationFeedReader {

//  private static final String YELLOW = "yellow";
  private static final String BLUE = "blue";
  private static final String API_XML = "/api/xml";
  private List<String> urls;

  Map<String, Status> colorMapping = new HashMap<String, Status>();

  public HudsonReader(String[] urls) {
    appendAPISuffix(urls);

    //TODO: Make it customizable
    colorMapping.put("yellow", Status.FAILED);
    colorMapping.put("red", Status.FAILED);
    colorMapping.put("blue", Status.SUCCESS);
    colorMapping.put("yellow_anime", Status.BUILDING_FROM_FAILURE);
    colorMapping.put("orange_anime", Status.BUILDING_FROM_FAILURE);
    colorMapping.put("blue_anime", Status.BUILDING_FROM_SUCCESS);
    colorMapping.put("disabled", Status.DISABLED);
  }

  private void appendAPISuffix(String[] urls) {
    for (int i = 0; i < urls.length; i++) {
      if (urls[i] != null && !urls[i].endsWith(API_XML)) {
        urls[i] += API_XML;
      }
    }
    this.urls = Arrays.asList(urls);
  }

  private Job getJobFromUrl(String url) {
    try {
      HttpGet httpget = new HttpGet(url);
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
      String xml = str.toString();
      String color = fromXmlToColor(xml);
      Status status = fromColorToStatus(color);
      Job job = new Job(status);
      job.setColor(color);
      job.setUrl(url);

      return  job;

    } catch (Exception ex) {
      System.err.println("Could not get Url: "+ex);
      return null;
    }
  }

  private Jobs getJobsFromUrls() {
    Jobs jobs = new Jobs();
    for (String url : this.urls) {
      jobs.add(getJobFromUrl(url));
    }
    return jobs;
  }

  public Status getStatus() {
    Jobs jobs = getJobsFromUrls();
    return jobs.getPrevalentStatus();
  }

  private Status fromColorToStatus(String color) {
    return colorMapping.get(color);
  }

  private String fromXmlToColor(String xml) {
    String result = BLUE;
    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true); // never forget this!
    DocumentBuilder builder = null;
    try {
      builder = domFactory.newDocumentBuilder();

      Document document = builder.parse(new InputSource(new StringReader(xml)));

      XPathFactory factory = XPathFactory.newInstance();
      XPath xpath = factory.newXPath();
      XPathExpression expr = xpath.compile("//color");

      Object n = expr.evaluate(document, XPathConstants.NODESET);
      NodeList nodes = (NodeList) n;
      if (nodes.getLength() > 0) {
        result = (String) nodes.item(0).getTextContent();
      }

    } catch (Exception e) {
      System.err.println("fromXmlToColor:"+e);
    }
    return result;
  }

}
