package classes;
import java.util.HashSet;
import java.util.Set;

public class StudentKey {

	static Set<String> stdKey=new HashSet<String>();

	public static Set<String> getStdId() {
		return stdKey;
	}

	public static void setStdId(String id) {
		stdKey.add(id);
	}
	
}
