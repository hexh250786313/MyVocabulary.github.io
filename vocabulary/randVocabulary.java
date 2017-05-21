package vocabulary;

import java.util.*;

public class randVocabulary {
	public vocabularymessage[] randVocabulary(int maxnumber,int number){  
        int a;
        int b;
        vocabularymessage tem;
        Random rand = new Random();
          
        vocabularymessage arr[] = new VocabularyMgr().randVocabulary();
        vocabularymessage arr02[] = new VocabularyMgr().randVocabulary();
        for(int i=0; i<maxnumber*10000; i++){
            a = rand.nextInt(maxnumber);
            b = rand.nextInt(maxnumber);
            tem = arr[a];
            arr[a] = arr[b];
            arr[b] = tem;
        }
        for(int i=0; i<maxnumber - 1; i++) {
            if (i>0 & (i+1)%5 ==0){
            	 arr02[i] = arr[i];
            }else {
            	arr02[i] = arr[i];
            }
        }
        return arr02;
    }
}
