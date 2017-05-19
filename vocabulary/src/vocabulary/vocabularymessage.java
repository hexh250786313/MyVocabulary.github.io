package vocabulary;

public class vocabularymessage {
	private long number;
	private String vocabulary;
	//获取编号
	public long getNumber(){
		return number;
	}
	//设置编号
	public void setNumber(long number){
		this.number=number;
	}
	//获取单词
	public String getVocabulary(){
		return vocabulary;
	}
	//设置单词
	public void setVocabulary(String vocabulary){
		this.vocabulary=vocabulary;
	}
}
