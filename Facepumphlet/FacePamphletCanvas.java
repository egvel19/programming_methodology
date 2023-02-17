/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	private GLabel profileName;
	private double ImageStartingY;
	private GLabel friends;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		GLabel message = new GLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, APPLICATION_WIDTH/2 - message.getHeight()/2, APPLICATION_HEIGHT - BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		addName(profile);
		addImage(profile);
		addStatus(profile);
		addFriendList(profile);
	}
	
	//This method adds profile name as GLabel on canvas 
	private void addName(FacePamphletProfile profile) {
		String name = profile.getName();
		profileName = new GLabel(name);
		profileName.setFont(PROFILE_NAME_FONT);
		profileName.setColor(Color.blue);
		add(profileName, LEFT_MARGIN, TOP_MARGIN);
	}
	
	//this method gets information from FacePamphletProfile profile and adds profile image on canvas. 
	//(If profile does not have profile picture then the method adds borders and Labels instead (as given in the instructions)
	private void addImage(FacePamphletProfile profile) {
		double ImageStartingX = LEFT_MARGIN;
		ImageStartingY = TOP_MARGIN + profileName.getHeight()+ IMAGE_MARGIN ;
		if(profile.getImage()==null) {
			addImageBoxLines(ImageStartingX, ImageStartingY);
			addImageString(ImageStartingX, ImageStartingY);	
		}else {
			GImage profileImage = profile.getImage();
			profileImage.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(profileImage, ImageStartingX, ImageStartingY);
		}
	}
	
	//this method adds image box border lines. 
	//pre-condition: the program has determined that the current profile does not have profile picture and it is going to add image box instead
	//post-condition: the program has added image box on the canvas
	private void addImageBoxLines(double initX, double initY) {
		GLine topLine = new GLine(initX, initY, initX + IMAGE_WIDTH, initY);
		GLine bottomLine = new GLine(initX, initY + IMAGE_HEIGHT, initX+ IMAGE_WIDTH, initY + IMAGE_HEIGHT);
		GLine leftLine = new GLine(initX, initY, initX, initY + IMAGE_HEIGHT);
		GLine rightLine = new GLine(initX + IMAGE_WIDTH, initY, initX + IMAGE_WIDTH, initY + IMAGE_HEIGHT);
		add(topLine);
		add(bottomLine);
		add(leftLine);
		add(rightLine);
	}
	
	
	//this method adds no image string on the canvas
	//pre-condition: the program has determined that the current profile does not have profile picture and it has added image box instead
	//post-condition: the program has added no Image String in the middle of the image box
	private void addImageString(double initX, double initY) {
		GLabel noImageString = new GLabel("No Image");
		noImageString.setFont(PROFILE_IMAGE_FONT);
		double labelWidth = noImageString.getWidth();
		double labelHeight = noImageString.getHeight();
		add(noImageString, initX + IMAGE_WIDTH/2 - labelWidth/2, initY + IMAGE_HEIGHT/2 - labelHeight/2);
	}
	
	
	//this method adds profile status on the canvas if there is any.
	private void addStatus(FacePamphletProfile profile) {
		if(!profile.getStatus().equals("")) {
			displayCurStatus(profile);
		}else {
			noCurStatusString(profile);
		}
	}
	
	//this method is responsible for what happens if there is no current status
	//pre-condition: the program has determined that there is no current status 
	//post-condition: the program has displayed corresponding message on the canvas.
	private void noCurStatusString(FacePamphletProfile profile) {
		GLabel curStatus = new GLabel("No current Status");
		curStatus.setFont(PROFILE_STATUS_FONT);
		add(curStatus, LEFT_MARGIN, ImageStartingY + IMAGE_HEIGHT + STATUS_MARGIN);
	}
	
	//this method adds current status on the canvas as a GLabel
	//pre-condition: the program has determined that the profile has current status
	//post-condition: the method has added current status on the canvas
	private void displayCurStatus(FacePamphletProfile profile) {
		String statusText = profile.getName() + " is " + profile.getStatus();
		GLabel curStatus = new GLabel(statusText);
		curStatus.setFont(PROFILE_STATUS_FONT);
		add(curStatus, LEFT_MARGIN, ImageStartingY + IMAGE_HEIGHT + STATUS_MARGIN);
	}
	
	//this method adds current profile's friend list on the canvas
	private void addFriendList(FacePamphletProfile profile) {
		friendsLabel(profile);
		Iterator <String> friendList = profile.getFriends();
		int spaceBetweenLines = 2;
		double yCoordinate = ImageStartingY + friends.getHeight() + spaceBetweenLines;
		while(friendList.hasNext()) {
			String friend = friendList.next();
			GLabel addNewFriend = new GLabel(friend);
			addNewFriend.setFont(PROFILE_FRIEND_FONT);
			add(addNewFriend, APPLICATION_WIDTH/2, yCoordinate);
			yCoordinate += addNewFriend.getHeight() + spaceBetweenLines;
		}
	}
	
	//this method adds GLabel "friends" on the canvas
	private void friendsLabel(FacePamphletProfile profile) {
		friends = new GLabel("Friends");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friends, APPLICATION_WIDTH/2, ImageStartingY);
	}
}
