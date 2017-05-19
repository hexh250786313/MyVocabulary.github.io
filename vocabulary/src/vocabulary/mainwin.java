package vocabulary;
import java.util.*;

public class mainwin {
	public static void main(String[] args) {
		MaxNumber maxnumber = new MaxNumber();
		VocabularyMgr vocabularymgr = new VocabularyMgr();
		vocabularymessage vocabularymessage = new vocabularymessage();
		int function;
		boolean flag = true;
		boolean turn = true;
		Scanner reader = new Scanner(System.in);
		
		System.out.println("**Welcome to MyVocabulary**");
		while(turn){
			flag = true;
			System.out.println();
			System.out.println("*You can ch0v0se the number*");
			System.out.println("+---------------------+");
			System.out.println("| number | function   |");
			System.out.println("+---------------------+");
			System.out.println("|      1 | skim       |");
			System.out.println("|      2 | input      |");
			System.out.println("|      3 | update     |");
			System.out.println("|      4 | exam       |");
			System.out.println("+---------------------+");
			System.out.print("myvocabulary>");
			
			try{
				function = reader.nextInt();
				switch(function){
					case 1:{
						reader.nextLine();
						System.out.println();
						System.out.println("+-------------------------+");
						System.out.println("| number | vocabulary     |");
						System.out.println("+-------------------------+");
						int i = -1;
						while(i < maxnumber.getMaxNumber()-1){
							i++;
							System.out.print("|"+String.format("%7d",vocabularymgr.getVocabulary()[i].getNumber())+" | ");
							System.out.println(String.format("%-15s",vocabularymgr.getVocabulary()[i].getVocabulary())+"|");
						}
						System.out.println("+-------------------------+");
						System.out.println("There are "+maxnumber.getMaxNumber()+" vocabularies");
						System.out.print("enter>");
						reader.nextLine();
					}break;
					case 2:{
						reader.nextLine();
						System.out.println();
						while(flag){
							vocabularymessage.setNumber(maxnumber.getMaxNumber()+1);
							System.out.print("vocabulary>");
							try{
								String vocabulary = reader.nextLine();
								if(vocabulary.equals("stop")) flag = false;
								else{
									vocabularymessage.setVocabulary(vocabulary);
									vocabularymgr.addVocabulary(vocabularymessage);
									System.out.println("**success**");
								}
							}catch(Exception e){
								System.out.println(e.getMessage());
							}
						}
					}break;
					case 3:{
						System.out.println("*input the number*");
						for(int i = 0;i < 1;){
							System.out.print("number>");
							try{
								int number = reader.nextInt();
								reader.nextLine();
								i++;
								System.out.print("vocabulary>");
								vocabularymgr.updateVocabulary(reader.nextLine(),number);
								System.out.println("**success**");
							}catch(Exception e){
								if(reader.nextLine().equals("stop")){
									break;
								}
								else{
									System.out.println("ERROR:You have an error input.");
									i = 0;
								}
							}
						}
					}break;
					case 4:{
						System.out.println("There are "+maxnumber.getMaxNumber()+" vocabularies");
						System.out.println("How many vocabularies will be picked up?");
						for(int j = 0;j < 1;){
							System.out.print("number>");
							try{
								int number = reader.nextInt();
								if(number <= maxnumber.getMaxNumber()){
									j++;
									reader.nextLine();
									vocabularymgr.randVocabulary();
									System.out.println();
									System.out.println("+-------------------------+");
									System.out.println("| number | vocabulary     |");
									System.out.println("+-------------------------+");
									int i = -1;
									while(i < number-1){
										i++;
										System.out.print("|"+String.format("%7d",i+1)+" | ");
										System.out.println(String.format("%-15s",vocabularymgr.randVocabulary()[i].getVocabulary())+"|");
									}
									System.out.println("+-------------------------+");
									System.out.print("enter>");
									reader.nextLine();
								}else{
									System.out.println("ERROR:The number should be less than or equal to "+maxnumber.getMaxNumber()+".");
									j = 0;
								}
							}catch(Exception e){
								if(reader.nextLine().equals("stop")){
									break;
								}
								else{
									System.out.println("ERROR:You have an error input.");
									j = 0;
								}
							}
						}
					}break;
				}
			}catch(Exception e){
				if(reader.nextLine().equals("stop")){
					System.out.println("Bye");
					break;
					}
				else{
					System.out.println("ERROR:You have an error input.");
				}
			}
		}
	}
}
