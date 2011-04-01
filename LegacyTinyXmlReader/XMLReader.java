import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.swing.*;


public class XMLReader {
	
	public class XmlLog {
		
		private Logger logger = null;
		private FileHandler fh = null;
		
		public XmlLog(String filename) {
			try {
				LogManager lm = LogManager.getLogManager();		
				fh = new FileHandler(filename);
				logger = Logger.getLogger("Logging1");
				lm.addLogger(logger);
				logger.setLevel(Level.INFO);
				fh.setFormatter(new XMLFormatter());
				logger.addHandler(fh);
				
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void write(String logString) {
			logger.log(Level.INFO, logString);
		}
		
		public void close() {
			fh.close();
		}
	}
	
	public class Person {
		private String name;
		private int age;
		private String adress;
		private String city;
		private int postalCode;
		
		public Person() {
			
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getAdress() {
			return adress;
		}

		public void setAdress(String adress) {
			this.adress = adress;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public int getPostalCode() {
			return postalCode;
		}

		public void setPostalCode(int postalCode) {
			this.postalCode = postalCode;
		}
		
		
		@Override
		public String toString() {
			return "Person [adress=" + adress + ", age=" + age + ", city="
					+ city + ", name=" + name + ", postalCode=" + postalCode
					+ "]";
		}
	}

	
	public XMLReader() {
	}
	
	private String readFile(String file) throws Exception {
		String s = "";
		File f = new File(file);
		FileInputStream fis = new FileInputStream(f);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DataInputStream dis = new DataInputStream(bis);
		while (dis.available() != 0)
		{
			s += dis.readLine();
		}
		return s;
	}
	
	
	public Person read(String file) throws Exception {
		String filecontent = this.readFile(file); 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    factory.setIgnoringComments(true);
	    factory.setCoalescing(true); // Convert CDATA to Text nodes
	    factory.setNamespaceAware(false); // No namespaces: this is default
	    factory.setValidating(false); // Don't validate DTD: also default
	    
	    DocumentBuilder parser = factory.newDocumentBuilder();
	    Document document = parser.parse(new InputSource(new StringReader(filecontent)) );
	    
	    
	    NodeList sections = document.getElementsByTagName("person");
	    int numSections = sections.getLength();
	    
	    if (numSections == 1) {
	    	
                Node node = sections.item(0); 
                Person p = new Person();
                p.setName( this.getValue(node, "name") ); 
                p.setAge( Integer.parseInt( this.getValue(node, "age")) ); 
                p.setAdress( this.getValue(node, "adress") ); 
                p.setCity( this.getValue(node, "city") );
                p.setPostalCode( Integer.parseInt(this.getValue(node, "postalCode")) );
	    	return p;
	    } else {
	    	return null;
	    }
		
	}
	private String getValue(Node node, String tag) {
		String value = "";
	
		if (node.getNodeType() == Node.ELEMENT_NODE) { 
	        Element element = (Element)node; 
	        NodeList nodeList = element.getElementsByTagName(tag); 
	        Element item = (Element)nodeList.item(0); 
	        NodeList list = item.getChildNodes(); 
	        value = ((Node)list.item(0)).getNodeValue().trim(); 
	    } 
		return value;
	}
	public static void main(String[] args) throws Exception {
		XMLReader xmlReader = new XMLReader();
		XMLReader.XmlLog xmlLog = xmlReader.new XmlLog("output/xmllog.txt");
		
		try {
			Person p1 = xmlReader.read("data/PersonData.xml");
			xmlLog.write("PersonData.xml parsing OK");
		}
		catch (Exception e) {
			xmlLog.write("PersonData.xml parsing FAILED");
		}
		
		try {
			Person p2 = xmlReader.read("data/PersonDataWithError.xml");
			xmlLog.write("PersonDataWithError.xml parsing OK");
		}
		catch (Exception e) {
			xmlLog.write("PersonDataWithError.xmll parsing FAILED");
		}
		
		
	}
}
