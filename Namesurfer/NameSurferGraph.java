/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import java.awt.color.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	private static final int spaceBetweenLineAndLabel=2;
	ArrayList <NameSurferEntry> storedEntries;
	private int rank;
	private int nextrank;
	private GLabel nameLabel;
	private String name;
	private NameSurferEntry entry;
	private int colorCounter;
	private GLine line;
	private int startingX;
	private int endingX;
	private int outOfListY;
	private int startingInTheListY;
	private int endingInTheListY;
	
	
	//Creates a new NameSurferGraph object that displays the data.
	public NameSurferGraph() {
		addComponentListener(this);
		storedEntries = new ArrayList<NameSurferEntry>();
	}
	
	//Clears the list of name surfer entries stored inside this class.
	public void clear() {
		storedEntries.clear();
	}
	
	//	 Method: addEntry(entry) */
	//Adds a new NameSurferEntry to the list of entries on the display.
	//Note that this method does not actually draw the graph, but
	//simply stores the entry; the graph is drawn by calling update.
	public void addEntry(NameSurferEntry entry) {
		storedEntries.add(entry);
	}
	
	
//	Updates the display image by deleting all the graphical objects
//	from the canvas and then reassembling the display according to
//	the list of entries. Your application must call update after
//	calling either clear or addEntry; update is also called whenever
//	the size of the canvas changes.
	public void update() {
		removeAll();
		drawBackground();
		if(storedEntries.size()>0 || storedEntries.size()==0) {
			drawGraphs();;
		}
	}
	
	//this method draws background lines for better apparency of the graph
	//pre-condition:there are only JComponents on the window
	//post-condition: the background lines are placed as required
	private void drawBackground() {
		drawHorizontalLines();
		drawVerticalLines();
		drawLabels();
	}
	
	//this is the method for drawing horizontal lines of the background
	//pre-condition:there are only JComponents on the window.
	//post-condition: there are horizontal lines drawn on the background
	private void drawHorizontalLines() {
		for(int i=0; i<NDECADES; i++) {
			GLine line = new GLine(0+i*(getWidth()/11), 0, 0+i*(getWidth()/11), getHeight());
			add(line);
		}
	}
	
	//this method draws 2 vertical lines on the background
	//pre-condition: there are horizontal lines drawn on the background
	//post-condition: there are horizontal and vertical lines on the background
	private void drawVerticalLines() {
		GLine verticalLine1 = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine verticalLine2 = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(),
				getHeight() - GRAPH_MARGIN_SIZE);
		add(verticalLine1);
		add(verticalLine2);
	}
	
	//this method draws decades' labels on the background
	//pre-condition: there are horizontal and vertical lines on the background
	//post-condition: drawing background is finished and it looks as required
	private void drawLabels() {
		for(int i=0; i<NDECADES; i++) {
			int decade = START_DECADE + i*10;
			GLabel label = new GLabel(String.valueOf(decade));
			add(label, 2+ i*(getWidth()/11), getHeight() - GRAPH_MARGIN_SIZE + label.getWidth()/2);
		}
	}
	
	//this method draws graphs of the statitics of particular name popularity
	//pre-condition: there is background lines and JComponents on the window, the user has entered the name
	//post-condition: the program has drawn the graph 
	private void drawGraphs() {
		//colorCounter provides counting of the number of graphs that has been drawn
		colorCounter = 1;
		for(int i=0; i<storedEntries.size(); i++) {
			entry = storedEntries.get(i);
			for( int j=0; j<NDECADES; j++) {
				name = entry.getName();
				rank = entry.getRank(j);
				if(j<10) nextrank = entry.getRank(j+1);
				startingX = j*(getWidth()/NDECADES);
				endingX = (j+1)*(getWidth()/NDECADES);
				outOfListY = getHeight() - GRAPH_MARGIN_SIZE;
				startingInTheListY = GRAPH_MARGIN_SIZE + rank*(getHeight()-2*GRAPH_MARGIN_SIZE)/MAX_RANK;
				endingInTheListY =  GRAPH_MARGIN_SIZE + nextrank*(getHeight()-2*GRAPH_MARGIN_SIZE)/MAX_RANK;
				if(rank==0 && j<11) {
					belowMaxRanksCase(j);
				}else {
						casualCase(j);
				}
			}
			colorCounter++;
		}
	}
	
	//this method provides drawing of the graph when rank does not equal 0 
	//(when the name is in the list of 1000 popular names)
	//pre-condition:the program has stated that in the particular decade the name is in the list of 1000 popular names
	//post-condition:the program draws a line connecting the horizontal lines of this particular decade and next decade
	private void casualCase(int j) {
		name = name+ " " + String.valueOf(rank);
		nameLabel = new GLabel(name);
		add(nameLabel, spaceBetweenLineAndLabel+ startingX, startingInTheListY);
		setColor(nameLabel);
		if(nextrank==0) {
				//if the name is out of the list of 1000 most popular names in the next decade
				line = new GLine(startingX, startingInTheListY, endingX,  outOfListY);
			}else {
				//if the name is in the list of 1000 most popular names in the next decade too
				line = new GLine(startingX, startingInTheListY, endingX,  endingInTheListY);
			}
		add(line);
		setColor(line);
		}
	
	//this method provides drawing of the graph when rank equals 0 (when the name is outside of the list of 1000 most popular names)
	//pre-condition:the program has stated that in the particular decade the name is not in the list of 1000 popular names
	//post-condition:the program draws a line connecting the horizontal lines of this particular decade and next decade
	private void belowMaxRanksCase(int j) {
		name = name+"*";
		nameLabel = new GLabel(name);
		add(nameLabel, spaceBetweenLineAndLabel + startingX, outOfListY - nameLabel.getHeight());
		setColor(nameLabel);
		if(nextrank==0) {
			//if the name is not in the list of 1000 most popular names in the next decade too
			line = new GLine(startingX, outOfListY, endingX, outOfListY);
		}else {
			//if the name is in the list of 1000 most popular names in the next decade
			line = new GLine(startingX,  outOfListY , endingX, endingInTheListY);
		}
		add(line);
		setColor(line);
	}
	
	//this method defines what should be the color of the graph
	//pre-condition:the user has entered the name 
	//post-condition:this method returnes the colors in particular order according to which graph the user is 
	//trying to draw(the amount of graphs are counted by colorCounter). there are only 4 option of colors. 
	private void setColor(GObject object) {
		if(colorCounter%4==1) object.setColor(Color.black);
		if(colorCounter%4==2) object.setColor(Color.red);
		if(colorCounter%4==3) object.setColor(Color.blue);
		if(colorCounter%4==0) object.setColor(Color.yellow);
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
