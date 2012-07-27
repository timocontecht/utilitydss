package org.visico.utilitydss.server.processsim;


import java.io.File;
import java.util.Calendar;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import desmoj.core.report.Message;
import desmoj.core.report.MessageReceiver;
import desmoj.core.report.Reporter;


public class Receiver implements MessageReceiver
{

	public Receiver() 
	{
		project_start = Calendar.getInstance();
		buildBaseXML();
		
	}
	
	private void buildBaseXML() 
	{
		// fstream = new FileWriter("out.txt");
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try 
		{
			docBuilder = docFactory.newDocumentBuilder();
		
		
			// root elements
			doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("project");
			doc.appendChild(rootElement);
			
			//
			 
			// tasks elements
			tasks = doc.createElement("tasks");
			rootElement.appendChild(tasks);
				
			sectiontasks = new TreeMap<String,Element>();
			
			Element taskdisplay = doc.createElement("taskdisplaycolumns");
			rootElement.appendChild(taskdisplay);
			
			Element displayName = doc.createElement("displaycolumn");
			Attr id = doc.createAttribute("property-id");
			id.setValue("tpd3");
			displayName.setAttributeNode(id);
			Attr order = doc.createAttribute("order");
			order.setValue("0");
			displayName.setAttributeNode(order);
			Attr width = doc.createAttribute("width");
			width.setValue("242");
			displayName.setAttributeNode(width);
			Attr visible = doc.createAttribute("visible");
			visible.setValue("true");
			displayName.setAttributeNode(visible);
			taskdisplay.appendChild(displayName);
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void receive(Message m) 
	{
		
		
		if (m instanceof ActivityMessage)
		{
			ActivityMessage am = (ActivityMessage)m;
			
			Element secTask = sectiontasks.get(am.getSection().getName());
			
			Element work = doc.createElement("task");
			
			Attr id = doc.createAttribute("id");
			id.setValue(Integer.toString(id_counter));
			work.setAttributeNode(id);
			
			Attr name = doc.createAttribute("name");
			name.setValue(am.work());
			work.setAttributeNode(name);
			
			Calendar start = am.start();
			Attr start_att = doc.createAttribute("start");
			start_att.setValue((start.get(Calendar.MONTH) + 1) + "-"
			        + start.get(Calendar.DATE) + "-" + start.get(Calendar.YEAR));
			work.setAttributeNode(start_att);
			
			Attr duration = doc.createAttribute("duration");
			duration.setValue(Double.toString(am.duration()));
			work.setAttributeNode(duration);
			
			secTask.appendChild(work);
			
			id_counter++;
			
		}
		
	}

	@Override
	public void receive(Reporter arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void writeXML()
	{
		try 
		{
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			
			transformer = transformerFactory.newTransformer();
			
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("schedule.xml"));
			transformer.transform(source, result);
		}
		catch (TransformerConfigurationException e) 
		{
				// TODO Auto-generated catch block
				e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createSectionElement(Section sec)
	{
		Element section = doc.createElement("task");
		
		Attr id = doc.createAttribute("id");
		id.setValue(Integer.toString(id_counter+1));
		section.setAttributeNode(id);
		
		Attr name = doc.createAttribute("name");
		name.setValue(sec.getName());
		section.setAttributeNode(name);
		
		tasks.appendChild(section);
		sectiontasks.put(sec.getName(), section);
		id_counter++;
	}
	
	//private DocumentBuilderFactory docFactory;
	private Element tasks;
	private TreeMap<String,Element> sectiontasks;
	private Document doc;
	
	static private int id_counter = 0;
	
	Calendar project_start; 
}
