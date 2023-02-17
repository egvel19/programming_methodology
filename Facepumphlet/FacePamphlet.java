/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import acmx.export.java.util.ArrayList;

import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {
	
	private JTextField nameField;
	private JTextField status;
	private JTextField picture;
	private JTextField friend;
	
	private FacePamphletDatabase DataBase = new FacePamphletDatabase();
	private FacePamphletProfile profile;
	private FacePamphletCanvas canvas;
	  
	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		initNorth();
		initWest();
		canvas = new FacePamphletCanvas();
		add(canvas);
    }
	
	//this method is responsible for initializing North side of the window
	private void initNorth() {
		JLabel name = new JLabel("Name");
		nameField = new JTextField(TEXT_FIELD_SIZE);
		
		add(name, NORTH);
		add(nameField, NORTH);
		
		createJButtons();
	}
    
	//this method is responsible for initializing and adding Jbuttons on the North side of the window
	private void createJButtons() {
		JButton add = new JButton("Add");
		JButton delete = new JButton("Delete");
		JButton lookUp = new JButton("LookUp");
		
		add(add, NORTH);
		add(delete, NORTH);
		add(lookUp, NORTH);
		
		addActionListeners();
	}
	
	//this method is responsible for initializing West side of the window
	private void initWest() {
		initStatus();
		initPicture();
		initFriend();
	}
	
	//this method is responsible for initializing JButton and JTextField which are responsible for adding Status
	private void initStatus() {
		status = new JTextField(TEXT_FIELD_SIZE);
		status.setActionCommand("Change Status");
		status.addActionListener(this);
	
		JButton changeStatus = new JButton("Change Status");		
		JLabel emptyField = new JLabel(EMPTY_LABEL_TEXT);
		
		add(status, WEST);
		add(changeStatus, WEST);
		add(emptyField, WEST);
		
		addActionListeners();
	}
	
	//this method is responsible for initializing JButton and JTextField which are responsible for changing Profile Picture
	private void initPicture() {
		picture = new JTextField(TEXT_FIELD_SIZE);
		picture.setActionCommand("Change Picture");
		picture.addActionListener(this);
		
		JButton changePicture = new JButton("Change Picture");
		JLabel emptyField = new JLabel(EMPTY_LABEL_TEXT);
		
		add(picture, WEST);
		add(changePicture, WEST);
		add(emptyField, WEST);
		
		addActionListeners();
	}
	
	//this method is responsible for initializing JButton and JTextField which are responsible for adding Friends
	private void initFriend() {
		friend = new JTextField(TEXT_FIELD_SIZE);
		friend.setActionCommand("Add Friend");
		friend.addActionListener(this);
		
		JButton addFriend = new JButton("Add Friend");
		
		add(friend, WEST);
		add(addFriend, WEST);
		
		addActionListeners();
	}
	 
  
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	String actionCommand = e.getActionCommand();
    	String innerText = getInnerText(actionCommand);
    	if(!innerText.equals("")) {
    		if(e.getActionCommand().equals("Add")) addProfile(innerText);
    		if(e.getActionCommand().equals("Delete")) deleteProfile(innerText);
    		if(e.getActionCommand().equals("LookUp")) lookUpProfile(innerText);
    		if(e.getActionCommand().equals("Change Status")) changeStatus(innerText);
    		if(e.getActionCommand().equals("Change Picture")) changePicture(innerText);
    		if(e.getActionCommand().equals("Add Friend")) addFriend(innerText);
    	}
	}
    
    //if the profile with entered name doesn't exist, then method adds new profile on social network;
    //if there already is a profile with the same name, then the program displays that profile.
    private void addProfile(String innerText) {
    	canvas.removeAll();
    	if(!DataBase.containsProfile(innerText)) {
    		profile = new FacePamphletProfile(innerText);
    		DataBase.addProfile(profile);
    		canvas.displayProfile(profile);
    		canvas.showMessage("New profile created.");
    	}else {
    		profile = DataBase.getProfile(innerText);
    		canvas.displayProfile(profile);
    		canvas.showMessage("A profile with the name " + profile.getName() + " already exists.");
    	}
    }
    
    //if the profile with entered name exist, then the program deletes that profile;
    //in other case, program displays corresponding message
    private void deleteProfile(String profileName) {
    	canvas.removeAll();
    	if(DataBase.containsProfile(profileName)) {
    		DataBase.deleteProfile(profileName);
    		canvas.showMessage("Profile of " + profileName + " deleted.");
    	}else {
    		canvas.showMessage("A profile with name " + profileName + " does not exist");
    	}
    	profile = null;
    }
    
    //if the profile with entered name exist, then the program displays that profile,
    //in other case, program displays corresponding message
    private void lookUpProfile(String profileName) {
    	canvas.removeAll();
    	if(DataBase.containsProfile(profileName)) {
    		profile = DataBase.getProfile(profileName);
    		canvas.displayProfile(profile);
    		canvas.showMessage("Displaying " + profileName);
    	}else {
    		profile = null;
    		canvas.showMessage("A profile with the name " + profileName +" does not exist.");
    	}
    }

    //if there is current profile, then the program changes the current profile's status.
    //In other case, it displays corresponding message
    private void changeStatus(String statusText) {
		canvas.removeAll();
    	if(profile!=null) {
    		profile.setStatus(statusText);
    		canvas.displayProfile(profile);
    		canvas.showMessage("Status updated to " + statusText);
    	}else {
    		canvas.showMessage("Please select a profile to change status.");
    	}
    }
    
    //if there is current profile, then the program changes the current profile's image.
    //In other case, it displays corresponding message
    private void  changePicture(String fileName) {
    	canvas.removeAll();
    	if(profile!=null) {
    		GImage image = null;
    		try {
    			image = new GImage(fileName);
    			profile.setImage(image);
    			canvas.showMessage("Picture updated");
    		}catch (ErrorException ex){
    			canvas.showMessage("Unable to open image file: " + fileName);
    		}
    		canvas.displayProfile(profile);
    	}else {
    		canvas.showMessage("Please select a profile to change picture");
    	}
    }
    
    //if there is current profile, then the program adds new friend to current profile.
    //In other case, it displays corresponding message
    private void  addFriend(String newFriend) {
    	canvas.removeAll();
    	if(profile!=null) {
    		if(DataBase.containsProfile(newFriend)) {
    			tryToAddNewFriend(newFriend);
    		}else {
    			canvas.showMessage(newFriend + " does not exist.");
    		}
    		canvas.displayProfile(profile);
    	}else {
    		canvas.showMessage("Please select a profile to add friend");
    	}
    }
    
    //if current profile already has entered profile as a friend, then the program displays corresponding message.
    //If not, then method adds new friend to the current profile.
    private void tryToAddNewFriend(String newFriend) {
		if(!profileAlreadyHasNewFriend(newFriend)) {
			//the next loop checks if the cur profile is trying to add herself/himself as a friend.
			if(newFriend.equals(profile.getName())) {
				canvas.showMessage("you can't add yourself in your friendList");
			}else {
				profile.addFriend(newFriend);
				DataBase.getProfile(newFriend).addFriend(profile.getName());
				canvas.showMessage(newFriend + " added as a friend");
			}
		}else {
			canvas.showMessage(profile.getName() + " already has " + newFriend + " as a friend");
		}
    }
    
  //this method checks if current profile already has entered profile as a friend. 
    private boolean profileAlreadyHasNewFriend(String newFriend) {
    	Iterator <String> friendList = profile.getFriends();
    	while(friendList.hasNext()) {
    		if(friendList.next().equals(newFriend)) return true;
    	}
    	return false;
    }
    
    //this method finds out e.source and gets entered text as a String from corresponding JTextField.
    private String getInnerText(String e) {
    	String innerText = "";
    	if(e.equals("Add") || e.equals("Delete") || e.equals("LookUp")) innerText = nameField.getText();
    	if(e.equals("Change Status")) innerText = status.getText();
    	if(e.equals("Change Picture")) innerText = picture.getText();
    	if(e.equals("Add Friend")) innerText = friend.getText();
    	return innerText;
    }

    
}
