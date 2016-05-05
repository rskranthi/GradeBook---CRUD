package classes;
 
import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBException;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.google.gson.Gson;

import classes.Converter;

@Path("/gradebook")
public class Controller {
	@Context
	private UriInfo context;
	Gson jsonConverter = new Gson();
	GradingItems GI=new GradingItems();
	StudentMap createEntry=new StudentMap();
	@POST
	@Produces("application/xml")
	public Response creategradebook()
	{
		Gradebook gb=new Gradebook();
		String result = gb.getGradebook();
		String newUri = context.getAbsolutePath().toString();
		UriBuilder builder = UriBuilder.fromPath(newUri);
		URI locationURI = builder.build();
		return Response.status(Response.Status.CREATED).location(locationURI).entity(result).build();
	}
	@Path("/assesments")
	@POST
	@Consumes("application/xml")
	public Response creategradeitem(Document doc)
	{
		GradeItemTemp item=new GradeItemTemp();
		String[] res=item.gradeItem(doc).split(",");
		System.out.println(res[0]+","+res[1]);
		GI.setGradingItem(res[0], res[1]);
		createEntry.createentry(res[0]);
		String s="CSE 564";
		URI locationURI = URI.create(context.getAbsolutePath().toString());
		return Response.status(Response.Status.CREATED).location(locationURI).entity(s).build();
	}
	
	@Path("/createStudent")
	@POST
	@Produces("application/xml")
	@Consumes("application/xml")
	public Response createstudent(Document doc)
	{
		pStudent id=new pStudent();
		String res=id.StudentID(doc);
		if(!StudentKey.getStdId().contains(res))
		StudentKey.setStdId(res);
		else
		{
			String s="conflict";
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(409).location(locationURI).entity(s).build();
		}
		String s=id.getGradeItems();
		URI locationURI = URI.create(context.getAbsolutePath().toString());
		return Response.status(Response.Status.CREATED).location(locationURI).entity(s).build();
	}
	
	@Path("/updategradebook")
	@PUT
	@Produces("application/xml")
	@Consumes("application/xml")
	public Response update_gradebook(String content) throws JAXBException
	{
		Student stud=new Student();
		stud = (Student) Converter.convertFromXmlToObject(content, Student.class);
		String StudentId=stud.getStudentid();
		Assessment s=stud.getAssessment();
		String gradeitem=s.getGradingItem();
		String marks=s.getMarks();
		String feedback=s.getFeedBack();
		String result="";
		boolean flag=false;
		HashMap<String, ArrayList<AssessmentMap>> asw=createEntry.getStudentIdwork();
		try
		{
		ArrayList<AssessmentMap> res=asw.get(StudentId);
		if(res == null){
			String res1="not found";
			
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(404).location(locationURI).entity(res1).build();

		}
		for(AssessmentMap aws:res)
		{
			if(aws.getGradingitem().equals(gradeitem))
			{
				flag=true;
				aws.setFeedback(feedback);
				aws.setGrade(marks);
				break;
			}
		}
		if(flag)
		{
		String s1= "success update";
		URI locationURI = URI.create(context.getAbsolutePath().toString());
		return Response.status(200).location(locationURI).entity(s1).build();
		}
		else
		{
			String res1="not found";
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(404).location(locationURI).entity(res1).build();
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			Response.status(500).entity("s").build();
		}
		//return null;
		return null;
	}
	
	@GET
	@Path("/getGrade")
	@Produces("application/json")
	public Response getgrade_feedback(@QueryParam("id") String userid,@QueryParam("name") String name)
	{
		String stu_id=userid;
		String grading_item=name;
		String result="";
		boolean flag=false;
		HashMap<String, ArrayList<AssessmentMap>> asw=StudentMap.getStudentIdwork();
		ArrayList<AssessmentMap> res=asw.get(stu_id);
		if(res==null)
		{
			String res1="not found";
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(404).location(locationURI).entity(res1).build();
		}
		for(AssessmentMap aws:res)
		{
			if(aws.getGradingitem().equals(grading_item))
			{
				flag=true;
				result="Grade:"+aws.getGrade()+","+" FeedBack:"+aws.getFeedback();
				System.out.println("Result : "+result);
				break;
			}
		}
		if(flag)
		{
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(200).location(locationURI).entity(result).build();
		}
		else
		{
			String res1="not found";
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(404).location(locationURI).entity(res1).build();
		}
	}
	
	@DELETE
	@Path("/deletegrade")
	public Response delete_gradebook(@QueryParam("name") String name)
	{
		Set<String> Studentid=StudentKey.getStdId();
		String gradeitem=name;
		boolean flag=false;
		HashMap<String, ArrayList<AssessmentMap>> asw=StudentMap.getStudentIdwork();
		try
		{
		for(String s:Studentid )
		{
			System.out.println("Student"+s);
		ArrayList<AssessmentMap> res=asw.get(s);
		for(AssessmentMap aws:res)
		{
			System.out.println("item"+aws.getGradingitem());
			if(aws.getGradingitem().equals(gradeitem))
			{
				System.out.println("I am here");
				flag=true;
				res.remove(aws);
				break;
			}
		}
		}
		if(flag)
		{
		String res="successful deleted grade item+"+name;
		URI locationURI = URI.create(context.getAbsolutePath().toString());
		return Response.status(200).location(locationURI).entity(res).build();
		}
		else
		{
			String res1="not found";
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(404).location(locationURI).entity(res1).build();
		}
		}
		catch(Exception e)
		{
			String res1="not found exception";
			URI locationURI = URI.create(context.getAbsolutePath().toString());
			return Response.status(404).location(locationURI).entity(res1).build();
		}
		
	}
}
