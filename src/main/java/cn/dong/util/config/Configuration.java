package cn.dong.util.config;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.stream.XMLStreamException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.ElementModifier;
import org.dom4j.io.SAXModifier;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;

public class Configuration {

	private static ArrayList<Object> LIST = new ArrayList<Object>();
	private static String FILE = null;

	static {
		FILE = Configuration.class.getClassLoader().getResource("").getPath()
				+ "/Configuration.xml";
	}

	public static void setFile(String filePath) {
		FILE = filePath;
	}

	public static Object getConfig(Class<?> c) {
		Object obj = null;
		for (Iterator<Object> i = LIST.iterator(); i.hasNext();) {
			obj = i.next();

			if (obj.getClass().getName().equals(c.getName()))
				return obj;
		}
		synchronized (Configuration.class) {
			try {
				String xml = loadXml(c);
				if (xml != null && !xml.isEmpty()) {
					obj = deserialize(xml);
					LIST.add(obj);
					return obj;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XMLStreamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	private static Object deserialize(String xml) {
		XStream xst = new XStream();
		return xst.fromXML(xml);
	}

	private static String loadXml(Class<?> c) throws FileNotFoundException,
			XMLStreamException {
		FileInputStream in = new FileInputStream(FILE);
		// java.util.Stack stack = new java.util.Stack();
		SAXReader reader = new SAXReader(DocumentFactory.getInstance());
		try {
			Document doc = reader.read(in);
			Node n = doc.selectSingleNode("Configuration/" + c.getName());
			if (n != null)
				return n.asXML();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void setConfig(Object obj) {
		String xml = serialize(obj);
		for (Iterator<Object> i = LIST.iterator(); i.hasNext();) {
			Object tmp = i.next();

			if (tmp.getClass().getName().equals(obj.getClass().getName()))
				tmp = obj;
		}
		try {
			saveXml(xml, obj.getClass());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String serialize(Object obj) {
		XStream xst = new XStream();
		return xst.toXML(obj);
	}

	private synchronized static void saveXml(String xml, Class<?> c)
			throws UnsupportedEncodingException, FileNotFoundException {
		SAXModifier modifier = new SAXModifier(true);
		modifier.addModifier("/Configuration/" + c.getName(), new XmlModifier(xml));
		modifier.setDocumentFactory(DocumentFactory.getInstance());

		File file = new File(FILE);
		File fileBak = new File(FILE + ".bak");
		fileBak.deleteOnExit();
		file.renameTo(fileBak);
		FileOutputStream out = new FileOutputStream(file);
		XMLWriter wirter = new XMLWriter(out);
		modifier.setXMLWriter(wirter);
		try {
			modifier.modify(fileBak);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	static class XmlModifier implements ElementModifier {

		String xml;

		public XmlModifier(String xml) {
			this.xml = xml;
		}

		@Override
		public Element modifyElement(Element element) throws Exception {
			// TODO Auto-generated method stub
			SAXReader saxReader = new SAXReader();
			Document document;
			try {
				document = saxReader.read(new ByteArrayInputStream(xml
						.getBytes("UTF-8")));
				return document.getRootElement();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			return element;
		}
	}
}
