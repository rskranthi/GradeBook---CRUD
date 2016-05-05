package classes;

public class Gradebook {

	String gradebook;
	public String getGradebook() {
		StringBuilder sb1=new StringBuilder();
		sb1.append("<Gradebooks>");
		sb1.append("<Gradebook>");
		sb1.append("</Gradebook>");
		sb1.append("</Gradebooks>");
		return sb1.toString();
	}

	public void setGradebook(String gradebook) {
		this.gradebook = gradebook;
	}
	
}
