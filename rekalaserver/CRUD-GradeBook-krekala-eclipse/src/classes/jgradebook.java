package classes;

public class jgradebook {

	private String gradebook;
	
	
	public String getGradebook() {
		return gradebook;
	}

	public void setGradebook(String gradebook) {
		this.gradebook = gradebook;
	}
	public void callstudent(String marks)
	{
		studentTemp st=new studentTemp();
		st.setMarks(marks);
	}
}
