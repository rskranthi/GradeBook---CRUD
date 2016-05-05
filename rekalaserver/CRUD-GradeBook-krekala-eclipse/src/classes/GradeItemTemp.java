package classes;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GradeItemTemp {

	public String gradeItem(Document doc)
	{
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("assessment");
		Element eElement = (Element) nList.item(0);
		String s=eElement.getElementsByTagName("gradingItem").item(0).getTextContent();
		String s1=eElement.getElementsByTagName("percentage").item(0).getTextContent();
		String result=s+","+s1;
		return result;
	}

}
