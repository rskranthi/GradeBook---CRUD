package classes;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Assessment {

	private String GradingItem;
	private String Percentage;
	private String marks;
	private String feedBack;
	public String getGradingItem() {
		return GradingItem;
	}
	public void setGradingItem(String gradingItem) {
		this.GradingItem = gradingItem;
	}
	public String getPercentage() {
		return Percentage;
	}
	public void setPercentage(String percentage) {
		this.Percentage = percentage;
	}
	public String getMarks() {
		return marks;
	}
	public void setMarks(String marks) {
		this.marks = marks;
	}
	public String getFeedBack() {
		return feedBack;
	}
	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
}
