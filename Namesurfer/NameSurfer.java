/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	private static final int NAME_FIELD_LENGTH = 10;
	private JTextField text;
	private JButton drawGraph;
	private JButton clear;
	private NameSurferGraph graph; 
	private NameSurferDataBase dataBase;
	
///* Method: init() */
// This method has the responsibility for reading in the data base
// and initializing the interactors at the bottom of the window.
	public void init() {
		//initialization of JButton Graph
		drawGraph = new JButton("Graph");
		drawGraph.addActionListener(this);
		drawGraph.setActionCommand("Graph");
		
		//initialization of JButton clear
		clear = new JButton("clear");
		clear.addActionListener(this);
		clear.setActionCommand("clear");
		
		//initialization of JLabel name
		JLabel name = new JLabel("Name");
		
		//initialization of JTextField 
		text = new JTextField(NAME_FIELD_LENGTH);
		text.addActionListener(this);
		text.setActionCommand("Graph");
		
		//initialization of new nameSurferGraph
		graph = new NameSurferGraph(); 
		
		//adding each component
		add(graph);
		add(name, SOUTH);
		add(text, SOUTH);
		add(drawGraph, SOUTH);
		add(clear, SOUTH);
		try {
			dataBase = new NameSurferDataBase("names-data.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
/* Method: actionPerformed(e) */
//This class is responsible for detecting when the buttons are
//clicked, so you will have to define a method to respond to
//button actions.
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Graph")) { 
			String enteredName = text.getText();
			NameSurferEntry getData = dataBase.findEntry(enteredName);
			if(getData!= null ) {
				graph.addEntry(getData);
				graph.update();
			}else {
					System.out.println("name not found");
			}
		}
		if(e.getActionCommand().equals("clear")) {
			graph.clear();
			graph.update();
		}
		text.setText("");
	}
	
	
}
