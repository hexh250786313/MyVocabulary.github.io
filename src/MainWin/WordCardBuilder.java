package MainWin;

import java.awt.BorderLayout;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class WordCardBuilder {

	private static Dimension dismension = new Dimension(150, 20);
	private final String DEFAULT_XML_URL = "./file/MyVocabulary.xml";
	private int count;//增加窗体的长度的计数
	private JFrame frame;
	private JPanel panel;
	private JPanel centrePanel;
	private JTextField wordText;
	private JButton addTrans;
	private JButton addPhrase;
	private JButton addExp;
	private JButton newButton;
	
	//存放翻译、词组、例句文本框的三个对象组
	private ArrayList<JTextField> transTextList;
	private ArrayList<JTextField> phraseTextList;
	private ArrayList<JTextField> expTextList;
	
	void go() {
		
		transTextList = new ArrayList<JTextField>();
		phraseTextList = new ArrayList<JTextField>();
		expTextList = new ArrayList<JTextField>();
		
		count = 0;
		frame = new JFrame("Builder");
		panel = new JPanel();
		centrePanel = new JPanel();
		wordText = new JTextField();
		addTrans = new JButton("Add Translation");
		addPhrase = new JButton("Add Phrase");
		addExp = new JButton("Add Example");
		newButton = new JButton("NEW");
		
		centrePanel.setLayout(new BorderLayout());
		//panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		addTrans.setPreferredSize(dismension);
		addPhrase.setPreferredSize(dismension);
		addExp.setPreferredSize(dismension);
		wordText.setPreferredSize(dismension);
		
		panel.add(new JLabel("——WORD——"));
		panel.add(wordText);
		panel.add(new JLabel("                                     "));
		panel.add(new JLabel("——TRANSLATION——"));
		panel.add(setTextField(1));
		panel.add(addTrans);
		panel.add(new JLabel("                                     "));
		panel.add(new JLabel("——PHRASE——"));
		panel.add(setTextField(2));
		panel.add(addPhrase);
		panel.add(new JLabel("                                     "));
		panel.add(new JLabel("——EXAMPLE——"));
		panel.add(setTextField(3));
		panel.add(addExp);
		
		addTrans.addActionListener(new addTransListener(panel));
		addPhrase.addActionListener(new addPhraseListener(panel));
		addExp.addActionListener(new addExpListener(panel));
		newButton.addActionListener(new saveCardListener(panel));
		
		centrePanel.add(panel, BorderLayout.CENTER);
		centrePanel.add(newButton, BorderLayout.SOUTH);
		frame.add(centrePanel);
		
		//匿名类，窗体大小变化的监听器，拉伸时自动调节到相应大小
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				int width = e.getComponent().getWidth();
				//int height = e.getComponent().getHeight();
				if (width > 200) {
					width = 200;
					frame.setSize(width, 420);
				}
			}
		});
		
		//frame.pack();
		frame.setResizable(false);
		frame.setSize(new Dimension(200, 420));
		//frame.setMinimumSize(new Dimension(200, 430));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	
	}
	
	//addTrans按钮的监听器，新增一个文本框
	public class addTransListener implements ActionListener{
		
		JPanel panel;
		
		public addTransListener(JPanel panel) {
			this.panel = panel;
		}
		
		public void actionPerformed(ActionEvent ev){
			
			int flag = transTextList.size();
			
			if(transTextList.get(flag - 1).getText().equals("")) {
				JOptionPane.showMessageDialog(null, "输入框为空", "WARNING", JOptionPane.WARNING_MESSAGE);
			}else {
				count = count + 25;
				
				panel.removeAll();
				panel.add(new JLabel("——WORD——"));
				panel.add(wordText);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——TRANSLATION——"));
				//这个循环的目的是把原来有的文本框都重新调出来
				for(int i = 0; i < transTextList.size(); i++) {
					panel.add(transTextList.get(i));
				}
				panel.add(setTextField(1));
				transTextList.get(flag).requestFocus();
				panel.add(addTrans);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——PHRASE——"));
				for(int i = 0; i < phraseTextList.size(); i++) {
					panel.add(phraseTextList.get(i));
				}
				//panel.add(getTextField(2));
				panel.add(addPhrase);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——EXAMPLE——"));
				for(int i = 0; i < expTextList.size(); i++) {
						panel.add(expTextList.get(i));
				}
				//panel.add(getTextField(3));
				panel.add(addExp);

				panel.repaint();
				frame.setSize(200, 420 + count);
			}
		}
	}
	
	//addPhrase按钮的监听器，增加一个文本框
	public class addPhraseListener implements ActionListener{
		
		JPanel panel;
		
		public addPhraseListener(JPanel panel) {
			this.panel = panel;
		}
		
		public void actionPerformed(ActionEvent ev){
			
			int flag = phraseTextList.size();
			
			if(phraseTextList.get(flag - 1).getText().equals("")) {
				JOptionPane.showMessageDialog(null, "输入框为空", "WARNING", JOptionPane.WARNING_MESSAGE);
			}else {
				count = count + 25;
				
				panel.removeAll();
				panel.add(new JLabel("——WORD——"));
				panel.add(wordText);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——TRANSLATION——"));
				for(int i = 0; i < transTextList.size(); i++) {
					panel.add(transTextList.get(i));
				}
				//panel.add(setTextField(1));
				panel.add(addTrans);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——PHRASE——"));
				for(int i = 0; i < phraseTextList.size(); i++) {
					panel.add(phraseTextList.get(i));
				}
				panel.add(setTextField(2));
				phraseTextList.get(flag).requestFocus();
				panel.add(addPhrase);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——EXAMPLE——"));
				for(int i = 0; i < expTextList.size(); i++) {
						panel.add(expTextList.get(i));
				}
				//panel.add(getTextField(3));
				panel.add(addExp);
				
				panel.repaint();
				frame.setSize(200, 420 + count);
			}
		}
	}
	
	//addExp按钮的监听器，增加一个文本框
	public class addExpListener implements ActionListener{
		
		JPanel panel;
		
		public addExpListener(JPanel panel) {
			this.panel = panel;
		}
		
		public void actionPerformed(ActionEvent ev){
			
			int flag = expTextList.size();
			
			if(expTextList.get(flag - 1).getText().equals("")) {
				JOptionPane.showMessageDialog(null, "输入框为空", "WARNING", JOptionPane.WARNING_MESSAGE);
			}else {
				count = count + 25;
				
				panel.removeAll();
				panel.add(new JLabel("——WORD——"));
				panel.add(wordText);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——TRANSLATION——"));
				for(int i = 0; i < transTextList.size(); i++) {
					panel.add(transTextList.get(i));
				}
				//panel.add(setTextField(1));
				panel.add(addTrans);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——PHRASE——"));
				for(int i = 0; i < phraseTextList.size(); i++) {
					panel.add(phraseTextList.get(i));
				}
				//panel.add(setTextField(2));
				panel.add(addPhrase);
				panel.add(new JLabel("                                     "));
				
				panel.add(new JLabel("——EXAMPLE——"));
				for(int i = 0; i < expTextList.size(); i++) {
						panel.add(expTextList.get(i));
				}
				panel.add(setTextField(3));
				expTextList.get(flag).requestFocus();
				panel.add(addExp);
				
				panel.repaint();
				frame.setSize(200, 420 + count);
			}
		}
	}
	
	//NEW按钮的监听器，把当前编辑器的信息写入到XML中
	public class saveCardListener implements ActionListener{
		
		JPanel panel;
		
		public saveCardListener(JPanel panel) {
			this.panel = panel;
		}
		
		public void actionPerformed(ActionEvent ev) {

			if(wordText.getText().length() < 1) {
				JOptionPane.showMessageDialog(null, "请输入单词信息", "WARNING", JOptionPane.WARNING_MESSAGE);
			}else {
				int ifAdd = JOptionPane.showConfirmDialog(null, "确认添加？", "注意",JOptionPane.YES_NO_OPTION); 
	    		if(ifAdd == JOptionPane.YES_OPTION) {
	    			cutTheEmptyText();
	    			saveCard();
	    			JOptionPane.showMessageDialog(null, "——SUCESS——!", "WARNING", JOptionPane.WARNING_MESSAGE);
	    			clearText();
	    		}
			}	
		}
	}
	
	//根据参数新建三种不同的文本框，并添加到相应的对象组中
	private JTextField setTextField(int i) {
		JTextField textField = new JTextField();
		textField.setPreferredSize(dismension);
		if(i == 1) {
			transTextList.add(textField);
		}else if(i == 2) {
			phraseTextList.add(textField);
		}else if(i == 3) {
			expTextList.add(textField);
		}
		return textField;
	}

	//把空的文本框去掉
	private void cutTheEmptyText() {
		for(int i = 0; i < transTextList.size(); i++) {
			if(transTextList.get(i).getText().length() < 1) transTextList.remove(i);
		}
		
		for(int i = 0; i < phraseTextList.size(); i++) {
			if(phraseTextList.get(i).getText().length() < 1) phraseTextList.remove(i);
		}
		
		for(int i = 0; i < expTextList.size(); i++) {
			if(expTextList.get(i).getText().length() < 1) expTextList.remove(i);
		}
	}
	
	//写入操作
	private void saveCard() {

		SAXReader reader = new SAXReader();
		
		try {
			Document document = reader.read(new File(DEFAULT_XML_URL));
	    	Element root = document.getRootElement();
	          
	    	Element vcblr = root.addElement("vcblr");
	    	Element word = vcblr.addElement("word");
	    	word.setText(wordText.getText());
	    	
	    	for(int i = 0; i < transTextList.size(); i++) {
	    		Element translation = vcblr.addElement("translation");
	    		translation.addAttribute("id", "1");
	    		translation.setText(transTextList.get(i).getText());
			}
	    	
	    	for(int i = 0; i < phraseTextList.size(); i++) {
	    		Element phrase = vcblr.addElement("phrase");
	    		phrase.addAttribute("id", "2");
	    		phrase.setText(phraseTextList.get(i).getText());
			}
	    	
	    	for(int i = 0; i < expTextList.size(); i++) {
	    		Element expSts = vcblr.addElement("expSts");
	    		expSts.addAttribute("id", "3");
	    		expSts.setText(expTextList.get(i).getText());
			}
	    	  
	          
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			XMLWriter xmlWriter = new XMLWriter(
					new FileOutputStream(DEFAULT_XML_URL),format);
			xmlWriter.write(document);
			xmlWriter.close();
			
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//重新设置界面和清除有关对象的引用，新建新的引用
	private void clearText() {
		
		panel.removeAll();
		transTextList.clear();
		phraseTextList.clear();
		expTextList.clear();
		wordText.setText("");
		count = 0;
		
		panel.add(new JLabel("——WORD——"));
		panel.add(wordText);
		
		panel.add(new JLabel("                                     "));
		panel.add(new JLabel("——TRANSLATION——"));
		panel.add(setTextField(1));
		panel.add(addTrans);
		panel.add(new JLabel("                                     "));
		panel.add(new JLabel("——PHRASE——"));
		panel.add(setTextField(2));
		panel.add(addPhrase);
		panel.add(new JLabel("                                     "));
		panel.add(new JLabel("——EXAMPLE——"));
		panel.add(setTextField(3));
		panel.add(addExp);

		centrePanel.repaint();
		frame.pack();
	}	

}
