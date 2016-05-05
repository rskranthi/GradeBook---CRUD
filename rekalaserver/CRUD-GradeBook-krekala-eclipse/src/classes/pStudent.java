package classes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class pStudent {
	public String StudentID(Document doc)
	{
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("student");
		Element eElement = (Element) nList.item(0);
		String s=eElement.getElementsByTagName("studentid").item(0).getTextContent();
		String result=s;
		return result;
	}
	public String getGradeItems()
	{
		StudentKey id=new StudentKey();
		StringBuilder sb1=new StringBuilder();
		sb1.append("<Students>");
		Set<String> s=id.getStdId();
		Iterator it = s.iterator();
		while(it.hasNext())
		{
			String str=it.next().toString();
			sb1.append("<student>");
			sb1.append("<studentid>"+str+"</studentid>");
			sb1.append("</student>");
		}
		sb1.append("</Students>");
		return sb1.toString();
	}
}
