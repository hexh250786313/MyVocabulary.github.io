package MainWin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BoxLayout;
//import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class MyWordViewer {

	private boolean flag;//�����жϵ�ǰ�Ƿ������״̬��trueΪ���ǣ�false˵����ǰ���������״̬
	private final String DEFAULT_XML_URL = "./file/MyVocabulary.xml";
	private JFrame frame;
	private JTable table;
	private JScrollPane jp;
	//��ű�����ݵ�Vector
	private Vector<Vector<String>> data;
	private Vector<String> title;
	//��Ŵ�XML����ȡ��������
	private ArrayList<MyVocabulary> vocabulary;
	private Iterator<MyVocabulary> listIterator;
	private DefaultTableModel model;
	
	public static void main(String[] args) {
		MyWordViewer test = new MyWordViewer();
		test.gui();
	}
	
	//����gui����
	void gui() {
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu optionMenu = new JMenu("Option");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem shuffleMenuItem = new JMenuItem("Shuffle");
		JMenuItem refreshMenuItem = new JMenuItem("Refresh");
		
		flag = true;
		title = new Vector<String>();
		frame = new JFrame("MyVocabulary");
		
		refreshMenuItem.addActionListener(new RefreshListener());
		newMenuItem.addActionListener(new NewMenuListener());
		shuffleMenuItem.addActionListener(new ShuffleListener());
		
		optionMenu.add(shuffleMenuItem);
		fileMenu.add(newMenuItem);
		fileMenu.add(refreshMenuItem);
		menuBar.add(fileMenu);
		menuBar.add(optionMenu);
		
		title.add("#");
		title.add("Word");
		title.add("Translation");
		
		readMyVocabulary();
		setVocabulary();
		
		//���ñ���ʽ
		model = new  DefaultTableModel(data, title);
		table = new JTable(model){  
			private static final long serialVersionUID = 6401295888790008991L;
			public boolean isCellEditable(int row,int col){ 
				return false;
			}
		};
		setTableSize();
		
		MouseListener listener = new ClickMouseEvent(model);
		table.addMouseListener(listener);
        
		jp = new JScrollPane(table);
        
		frame.setJMenuBar(menuBar);
        frame.add(jp);
        frame.setSize(new Dimension(310, 297));
        frame.setResizable(false);
        //frame.setMinimumSize(new Dimension(200, 300));
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.pack();
        frame.setVisible(true);
		
	}
	
	//�˵���New�ļ����������ڵ����µĴ��������½����浥����Ϣ
	public class NewMenuListener implements ActionListener {
		
		public void actionPerformed(ActionEvent ev) { 
			//����WordCardBuilder��
			WordCardBuilder builder = new WordCardBuilder();
			builder.go();
       }
	}
	
	//˫��ĳһ�е��������������ڵ�����Ӧ�е������ĵ�����Ϣ
	public class ClickMouseEvent implements MouseListener {

		DefaultTableModel model;
		
		public ClickMouseEvent (DefaultTableModel model) {
			this.model = model;
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			
			//flag�ж��Ƿ��������״̬������������״̬������������Ϣ���壨���ɶԵ��ʽ���ɾ�����޸Ĳ����ģ�
			if(e.getClickCount() == 2) {
				//��ȡ������ĵ�ǰ��
				int row = ((JTable) e.getSource()).rowAtPoint(e.getPoint());
				//int col = ((JTable) e.getSource()).columnAtPoint(e.getPoint());
				if(flag) 
					wordCard(row);
					//String cellVal = (String)(model.getValueAt(row, col));
				else 
					shuffleWordCard(row);
			}else return;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {	
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

	}
	
	//Refresh�˵��ļ����������¶�ȡXML��д����
	public class RefreshListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ev) { 
			flag = true;
			model.getDataVector().clear();
					
			readMyVocabulary();
			setVocabulary();

			model.setDataVector(data, title);
			
			setTableSize();

       }
	}

	//Shuffle�˵��ļ�������ʹvocabulary����д����
	public class ShuffleListener implements ActionListener{
		
		public void actionPerformed(ActionEvent ev) { 
			
			flag = false;
			//����ķ���
			Collections.shuffle(vocabulary);
			listIterator = vocabulary.iterator();
			
			model.getDataVector().clear();
			setVocabulary();
			model.setDataVector(data, title);

			setTableSize();
       }
	}
	
	//wordCard�еĲ˵���ĿDelete�ļ�������ɾ����ǰ��Ƭ
	public class DeleteWordListener implements ActionListener{
		
		int i;
		JFrame cardFrame;
		
		public DeleteWordListener(int i, JFrame cardFrame) {
			this.i = i; 
			this.cardFrame = cardFrame;
		}
		
		public void actionPerformed(ActionEvent e) {
			
			SAXReader reader = new SAXReader();
    		
    		try {
    			Document document = reader.read(new File(DEFAULT_XML_URL));

				int ifDelete = JOptionPane.showConfirmDialog(null, "�Ƴ���", "ע��",JOptionPane.YES_NO_OPTION); 
	    		if(ifDelete == JOptionPane.YES_OPTION) {
	    			
	    			Element vcblrlist = document.getRootElement();
					Element vcblr = vcblrlist.elements("vcblr").get(i);
					//JOptionPane.showMessageDialog(null, todo.element("item").getText(), "Title", JOptionPane.ERROR_MESSAGE); 
					vcblrlist.remove(vcblr);
	    		}
    			//��д����
	    		OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("utf-8");
				XMLWriter xmlWriter = new XMLWriter(
						new FileOutputStream(DEFAULT_XML_URL),format);
				xmlWriter.write(document);
				xmlWriter.close();
    		}catch (DocumentException | IOException ev) {
	    		ev.printStackTrace();
	    	}
    		//�رյ�ǰ����
			cardFrame.dispose();
			flag = true;
			model.getDataVector().clear();
					
			readMyVocabulary();
			setVocabulary();

			model.setDataVector(data, title);
			
			setTableSize();
		}
	}

	//wordCard�еĲ˵���ĿEdit�ļ��������޸ĵ�ǰ��Ƭ
	public class EditWordListener implements ActionListener{
		
		int i;
		JFrame cardFrame;
		
		public EditWordListener(int i, JFrame cardFrame) {
			this.i = i;
			this.cardFrame = cardFrame;
		}
		
		public void actionPerformed(ActionEvent e) {
			WordInfoEditor editor = new WordInfoEditor(i);
			editor.go();
			cardFrame.dispose();
		}
	}
	
	//��ȡXML
	private void readMyVocabulary() {
		//��XML�ĵ�������ȫ�������浽vocabulary��������
		vocabulary = new ArrayList<MyVocabulary>();
    	SAXReader reader = new SAXReader();
    	
    	try {
    		Document document = reader.read(new File(DEFAULT_XML_URL));
    		Element vcblrlist = document.getRootElement();
    		Iterator<Element> vocabularyIterator = vcblrlist.elementIterator();
    		
    		
    		//�ֱ��ȡÿ��vcblr����item��ֵ����vcblr��Ȼ����뵽toDoList��
    		while (vocabularyIterator.hasNext()) { 
    			Element vcblr = (Element) vocabularyIterator.next();
    			boolean flag = false;
    			//�����濪ʼ��ÿһ��word
    			Iterator<Element> wordIterator = vcblr.elementIterator();
    			//�Ȱѵ��ʴ������
    			MyVocabulary myVocabulary = new MyVocabulary(((Element)wordIterator.next()).getText());
    		    
    			//��flag����������һ�ε�������Ϊ��һ���ǵ���
    			while(wordIterator.hasNext()) {
    				if(flag) {
    					//����XML���ֽڵ����ԶԷ��롢���顢������������ɸѡ��Ȼ��ֱ��ȡ��ŵ�myVocabulary��
    					Element tp = (Element) wordIterator.next();
    					if((tp.attribute(0).getText().equals("1"))) 
    						myVocabulary.addTranslation(tp.getText());
    					else if(tp.attribute(0).getText().equals("2")) 
    						myVocabulary.addPhrase(tp.getText());
    					else if(tp.attribute(0).getText().equals("3")) 
    						myVocabulary.addExpSts(tp.getText());
    				}else
    					flag = true;
    			}
    			vocabulary.add(myVocabulary);
    		}
    	}catch (DocumentException e) {
    		e.printStackTrace();
    	}

		//�������������ֱ��ȡÿһ�����ʵ���Ϣ
		listIterator = vocabulary.iterator();
	}
	
	//��ȡvocabulary���ѵ��ʺͷ����ŵ�data�У����е������У�
	private void setVocabulary() {
		
		data = new Vector<Vector<String>>();
		int count = 1;
		
		while(listIterator.hasNext()) {
			
			MyVocabulary myvocabulary = (MyVocabulary)listIterator.next();
			
			Vector<String> oneData = new Vector<String>();
			String str = "";
			
			//��ȡ���룬��ÿһ�����붼��������
			for(String trans : myvocabulary.getTranslation()) {
				str = str + trans +  "; " ;
			}
			oneData.add(count + "");
			oneData.add(myvocabulary.getWord());
			oneData.add(str);
	
			data.add(oneData);
			count ++;
		}
	}

	//���嵥����Ϣ�Ĵ��壬����ͨ����Ӧ�Ĳ˵�����ɾ�����޸Ĳ���
	private void wordCard(int i) {
		
		//������������ȡ���еĵڼ�����¼��Ȼ���vocabulary�л�ȡ������¼
		MyVocabulary myvocabulary = vocabulary.get(i);
		String word = myvocabulary.getWord();
		
		JMenu menu = new JMenu("Option");
		JMenuItem deleteItem = new JMenuItem("Delete");
		JMenuItem editItem = new JMenuItem("Edit");
		JMenuBar menuBar = new JMenuBar();
		JPanel centrePanel = new JPanel();
		JFrame cardFrame = new JFrame("Information of __" + word + "___");
		JPanel cardPanel = new JPanel();
		JLabel wordLabel = new JLabel(word);
		int m = 1;
		int n = 1;
		
		//ɾ�����޸��¼��ļ�����
		deleteItem.addActionListener(new DeleteWordListener(i, cardFrame));
		editItem.addActionListener(new EditWordListener(i, cardFrame));
		
		JLabel blue = new JLabel("�����塿");
		blue.setForeground(Color.BLUE);
		JLabel green = new JLabel("�����顿");
		green.setForeground(new Color(0, 100, 0));
		JLabel red = new JLabel("�����䡿");
		red.setForeground(new Color(139, 58, 58));
		
		wordLabel.setFont(new Font("",1,17));
		
		centrePanel.setLayout(new BorderLayout());
		
		cardPanel.add(wordLabel);
		cardPanel.add(new JLabel(" "));
		cardPanel.add(blue);
		for(String trans : myvocabulary.getTranslation()) {
			JLabel translation = new JLabel(trans);
			cardPanel.add(translation);
		}
		cardPanel.add(new JLabel(" "));
		cardPanel.add(green);
		for(String phr : myvocabulary.getPhrase()) {
			JLabel phrase = new JLabel(m + ".  " + phr);
			cardPanel.add(phrase);
			m++;
		}
		cardPanel.add(new JLabel(" "));
		cardPanel.add(red);
		for(String sts : myvocabulary.getExpSts()) {
			JLabel expsts = new JLabel(n + ".  " + sts);
			cardPanel.add(expsts);
			n++;
		}
		
		menuBar.add(menu);
		menu.add(deleteItem);
		menu.add(editItem);
		
		centrePanel.add(new JLabel("     "), BorderLayout.WEST);
		centrePanel.add(new JLabel("     "), BorderLayout.EAST);
		centrePanel.add(new JLabel("     "), BorderLayout.NORTH);
		centrePanel.add(new JLabel("     "), BorderLayout.SOUTH);
		
		
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		
		centrePanel.add(cardPanel, BorderLayout.CENTER);
		cardFrame.setJMenuBar(menuBar);
		cardFrame.add(centrePanel);
		cardFrame.setLocationRelativeTo(null);
		cardFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		cardFrame.setResizable(false);
		cardFrame.setMinimumSize(new Dimension(200, 50));
		cardFrame.pack();
		cardFrame.setVisible(true);
	}

	//�����״̬�µľ��嵥����Ϣ���壬������wordCard���ƣ�����û��ɾ�����޸Ĺ���
	private void shuffleWordCard(int i) {
		MyVocabulary myvocabulary = vocabulary.get(i);
		String word = myvocabulary.getWord();
		
		JMenuBar menuBar = new JMenuBar();
		JPanel centrePanel = new JPanel();
		JFrame cardFrame = new JFrame("Information of __" + word + "___");
		JPanel cardPanel = new JPanel();
		JLabel wordLabel = new JLabel(word);
		int m = 1;
		int n = 1;
		
		JLabel blue = new JLabel("�����塿");
		blue.setForeground(Color.BLUE);
		JLabel green = new JLabel("�����顿");
		green.setForeground(new Color(0, 100, 0));
		JLabel red = new JLabel("�����䡿");
		red.setForeground(new Color(139, 58, 58));
		
		wordLabel.setFont(new Font("",1,17));
		
		centrePanel.setLayout(new BorderLayout());
		
		cardPanel.add(wordLabel);
		cardPanel.add(new JLabel(" "));
		cardPanel.add(blue);
		for(String trans : myvocabulary.getTranslation()) {
			JLabel translation = new JLabel(trans);
			cardPanel.add(translation);
		}
		cardPanel.add(new JLabel(" "));
		cardPanel.add(green);
		for(String phr : myvocabulary.getPhrase()) {
			JLabel phrase = new JLabel(m + ".  " + phr);
			cardPanel.add(phrase);
			m++;
		}
		cardPanel.add(new JLabel(" "));
		cardPanel.add(red);
		for(String sts : myvocabulary.getExpSts()) {
			JLabel expsts = new JLabel(n + ".  " + sts);
			cardPanel.add(expsts);
			n++;
		}
		
		centrePanel.add(new JLabel("     "), BorderLayout.WEST);
		centrePanel.add(new JLabel("     "), BorderLayout.EAST);
		centrePanel.add(new JLabel("     "), BorderLayout.NORTH);
		centrePanel.add(new JLabel("     "), BorderLayout.SOUTH);
		
		
		cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
		
		centrePanel.add(cardPanel, BorderLayout.CENTER);
		cardFrame.setJMenuBar(menuBar);
		cardFrame.add(centrePanel);
		cardFrame.setLocationRelativeTo(null);
		cardFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		cardFrame.setResizable(false);
		cardFrame.setMinimumSize(new Dimension(200, 50));
		cardFrame.pack();
		cardFrame.setVisible(true);
	}
	
	//���ñ���С
	private void setTableSize() {
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(80);
		table.getColumnModel().getColumn(2).setPreferredWidth(166); //181, 166
		//table.getColumnModel().getColumn(0).setResizable(false);
	}
}
