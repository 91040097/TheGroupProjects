package thing;

import java.util.Scanner;

public class StringEncoder {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		
		int option = 0;
		
		do
		{
			System.out.println("1.) encrypt\n" + "2.) decrypt\n" + "3.) quit");
			
			option = keyboard.nextInt();
			
			keyboard.nextLine();//eating return
			
			if(option == 1 || option == 2)
			{
			String msg;
			
			System.out.println("Enter Message: ");
			
			msg = keyboard.nextLine();
			
			switch(option)
			{
			case 1:
				System.out.println("Encrypted Message: " + encrypt(msg));
				
				break;
			case 2:
				System.out.println("Decrypted Message: " + decrypt(msg));
				
				break;
			}
			}
				
		}while(option == 1 || option == 2);
	}
	
	public static String encrypt(String msg)
	{
		String encrypted="";
		
		msg = reverseString(format(msg));
		
		for(int i = 0;i < msg.length();i++)
		{
			char ch = msg.charAt(i);
			
			int c = ch - 'A' + 1;
			
			c++;
			
			ch = (char)(c - 1 + 'A');    
			
			encrypted += ch;
		}
		
		return encrypted;
	}
	
	public static String decrypt(String msg)
	{
		String decrypted = "";
		
		msg = reverseString(msg);
		
		for(int i = 0;i < msg.length();i++)
		{
			char ch = msg.charAt(i);
			
			int c = ch - 'A' + 1;
			
			c--;
			
			ch = (char)(c - 1 + 'A');    
			
			decrypted += ch;
		}
		
		return decrypted;
	}
	
	private static String format(String msg)
	{
		String newMsg = "";
		
		for(int i = 0;i < msg.length();i++)
		{
			if(Character.isLetter(msg.charAt(i)))
				newMsg += msg.substring(i, i + 1).toUpperCase();
		}
		
		return newMsg;
	}
	
	private static String reverseString(String msg)
	{
		String newMsg = "";
		
		for(int i = msg.length() - 1;i >= 0;i--)
		{
			newMsg += msg.charAt(i);
		}
		
		return newMsg;
	}
}
