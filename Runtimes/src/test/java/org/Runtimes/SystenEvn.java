package org.Runtimes;

public class SystenEvn {

	public static void main(String[] args) {
		
		String[] cmd = new String[2];
		cmd[0] = "cmd";	
		cmd[1] = "set EVNTEST=C:\\Program Files\\Java\\jdk1.6.0_04\bin";
		
		try{
			Runtime.getRuntime().exec(cmd);
			System.out.println(System.getenv("EVNTEST"));
		}
		catch(Exception e){
			System.out.println("Error");
		}
	}
		
	
}
