package classes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class StudentMap {

	static HashMap<String,ArrayList<AssessmentMap>> studentHashMap=new HashMap<String,ArrayList<AssessmentMap>>();
	public void createentry(String itemname)
	{
		StudentKey student=new StudentKey();
		Set<String> students=StudentKey.getStdId();
		for(String s:students)
		{
			System.out.println("Student Id : "+s);
			AssessmentMap work=new AssessmentMap();
			work.setGradingitem(itemname);
			if(studentHashMap.containsKey(s))
			{
				studentHashMap.get(s).add(work);
			}
			else
			{
				ArrayList<AssessmentMap> temp=new ArrayList<AssessmentMap>();
				temp.add(work);
				studentHashMap.put(s,temp);
			}
		}
	}
	public static HashMap<String, ArrayList<AssessmentMap>> getStudentIdwork() {
		return studentHashMap;
	}
	public static void setStudentIdwork(HashMap<String, ArrayList<AssessmentMap>> studentIdwork) {
		StudentMap.studentHashMap = studentIdwork;
	}
}
