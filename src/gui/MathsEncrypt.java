package gui;

//B I R  C H
//1 8 17 2 7
//H C R  I B
//7 2 17 8 1

import java.lang.StringBuilder;
public class MathsEncrypt {
	private String message="";
	private String code ="";
	private String alphabet ="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private char alphNum[] = new char[62];
	int a=0; int b=0;
	
	public MathsEncrypt(){
	}
	public String encrypt(String msg, String username){
		
	
		this.message = msg + username;
	

		for(int i=0;i<alphNum.length;i++){
			alphNum[i] = alphabet.charAt(i);
		}
		code="";
		//Reverses the message String
		message = new StringBuilder(message).reverse().toString();
		
		//encodes it once while the message is reversed
			for(int t=0;t<message.length();t++){
				for(int i=0;i<alphNum.length;i++){
					if(alphNum[i] == (message.charAt(t))){
						
						//Sets the code according to the key birch
						if(((t+1)%5)==0){a=7;b=1;} else if(((t+1)%4)==0){a=2;b=8;} 	else if(((t+1)%3)==0){a=17;b=17;}else if(((t+1)%2)==0){a=8;b=2;} else if(((t+1)%1)==0){a=1;b=7;}
						
						code +=(alphNum[(((a*i)+b)%62)]);
							
					}	
				}
				if(' ' == (message.charAt(t))){
					code += " ";
				}
			}
			
			//moves the string in code back into message
			message = code;
			//reverses the message once more to put it back the right way round again
			message = new StringBuilder(message).reverse().toString();
			//makes the code string empty once more
			code = "";
			//encodes it a second time 
			for(int t=0;t<message.length();t++){
				for(int i=0;i<alphNum.length;i++){
					if(alphNum[i] == (message.charAt(t))){
						
						
						//Sets the code according to the key birch
						if(((t+1)%5)==0){a=7;b=1;} else if(((t+1)%4)==0){a=2;b=8;} 	else if(((t+1)%3)==0){a=17;b=17;}else if(((t+1)%2)==0){a=8;b=2;} else if(((t+1)%1)==0){a=1;b=7;}
						
						code +=(alphNum[(((a*i)+b)%62)]);
							
					}	
				}
				if(' ' == (message.charAt(t))){
					code += " ";
				}
			}
			return code;
	}
	
	

	
	
	
	

}
