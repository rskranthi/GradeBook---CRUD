package classes;
import java.util.HashMap;

public class GradingItems {

	static HashMap<String,String> gradingItem=new HashMap<String,String>();

	public static HashMap<String, String> getGradingItem() {
		return gradingItem;
	}

	public static void setGradingItem(String Item,String percent) {
		gradingItem.put(Item,percent);
	}
}
