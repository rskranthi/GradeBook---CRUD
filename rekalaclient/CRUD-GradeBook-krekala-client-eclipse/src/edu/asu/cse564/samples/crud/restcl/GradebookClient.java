package edu.asu.cse564.samples.crud.restcl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import edu.asu.cse564.samples.crud.jaxb.model.Assessment;
import edu.asu.cse564.samples.crud.jaxb.model.Gradebook;
import edu.asu.cse564.samples.crud.jaxb.model.Student;
import edu.asu.cse564.samples.crud.jaxb.utils.Converter;

import java.util.HashMap;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


public class GradebookClient {

    
    private static final Logger LOG = LoggerFactory.getLogger(GradebookClient.class);
    Converter xmlconverter=new Converter();
    private WebResource webResource;
    Gson jsonConverter = new Gson();
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/CRUD-GradeBook-krekala-eclipse/gradebooks/gradebook";
    public GradebookClient() {        
        LOG.info("Creating a Gradebook_CRUD REST client");

        ClientConfig config = new DefaultClientConfig();
        client = Client.create(config);
        webResource = client.resource(BASE_URI);//.path("gradebook/");
        LOG.debug("webResource = {}", webResource.getURI());
    }

    public ClientResponse createGradeBook() throws UniformInterfaceException {
        LOG.info("Initiating a Create request");
        //LOG.debug("XML String = {}", (String) requestEntity);
        Gradebook gb=new Gradebook();
        String Xmlconv=xmlconverter.convertFromObjectToXml(gb,Gradebook.class);
        System.out.println(Xmlconv);
        webResource = client.resource(BASE_URI);//.path("gradebook/");
       
        return webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, Xmlconv);
    }
    public ClientResponse createStudent(String id){

    	Student stud = new Student();
        stud.setStudentid(id);
        //String res=jsonConverter.toJson(stud);
        //System.out.println(jsonConverter.toJson(stud));
        //String Xmlconv=xmlconverter.convertFromObjectToXml(stud);
        //System.out.println(Xmlconv);
        String res=xmlconverter.convertFromObjectToXml(stud,Student.class);
        System.out.println(res);
    	webResource = client.resource(BASE_URI).path("/createStudent/");
    	return webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, res);
   
    }
    public ClientResponse createGradingItem(String name,String percentage) throws UniformInterfaceException {
        Assessment assess=new Assessment();
        assess.setGradingItem(name);
        assess.setPercentage(percentage);
        //System.out.println(jsonConverter.toJson(assess));
        String Xmlconv=xmlconverter.convertFromObjectToXml(assess,Assessment.class);
        System.out.println(Xmlconv); 
    	webResource = client.resource(BASE_URI).path("/assesments/");
    	return webResource.type(MediaType.APPLICATION_XML).post(ClientResponse.class, Xmlconv);
    }
    public ClientResponse deletegrade(String gradeitem) throws UniformInterfaceException {
        LOG.info("Initiating a Delete request");
        LOG.debug("name = {}", gradeitem);
        MultivaluedMap queryParams = new MultivaluedMapImpl();
    	queryParams.add("name", gradeitem);
    	webResource = client.resource(BASE_URI).path("/deletegrade/");
        return webResource.queryParams(queryParams).delete(ClientResponse.class);
    }

    public ClientResponse updateAssessment(String studentId, String GradeItem,String marks ,String feedBack) throws JsonProcessingException
    {	
    	Student student = new Student();
    	student.setStudentid(studentId);
    	Assessment assessment = new Assessment();
    	assessment.setFeedBack(feedBack);
    	assessment.setGradingItem(GradeItem);
    	assessment.setMarks(marks);
    	student.setAssessment(assessment);
    	String Xmlconv=xmlconverter.convertFromObjectToXml(student,Student.class);
        System.out.println("xml data : \n"+Xmlconv ); 
    	webResource = client.resource(BASE_URI).path("/updategradebook/");
    	return webResource.type(MediaType.APPLICATION_XML).put(ClientResponse.class,Xmlconv);
    	
    }

    public ClientResponse readAssessment(String id,String name){
    	System.out.println("Id : "+id+"name : "+name);
    	
    	webResource = client.resource(BASE_URI).path("/getGrade/");
    	MultivaluedMap queryParams = new MultivaluedMapImpl();
    	queryParams.add("id", id);
    	queryParams.add("name", name);
    	ClientResponse response = webResource.queryParams(queryParams)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        return response;    	
    }
    
    public void close() {
        LOG.info("Closing the REST Client");
        
        client.destroy();
    }
    
}
