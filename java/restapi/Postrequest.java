package restapi;

public class Postrequest {
	
	public static String creatorder(int id) {
		return "{\r\n" + 
				" \"toolId\": "+id+",\r\n" + 
				" \"customerName\": \"John Doe\"\r\n" + 
				"}";
	}


}
