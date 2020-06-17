

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.Border;

public class DumbPhone extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 300;
	private static final int HEIGHT = 500;
	private static final String CALL_BUTTON_TEXT = "Call";
	private static final String TEXT_BUTTON_TEXT = "Text";
	private static final String DELETE_BUTTON_TEXT = "Delete";
	private static final String CANCEL_BUTTON_TEXT = "Cancel";
	private static final String SEND_BUTTON_TEXT = "Send";
	private static final String END_BUTTON_TEXT = "End";
	private static final String CALLING_DISPLAY_TEXT = "Calling...";
	private static final String TEXT_DISPLAY_TEXT = "Enter text...";
	private static final String ENTER_NUMBER_TEXT = "Enter a number...";
	private JTextArea display;
	private JButton topMiddleButton;
	private JButton topLeftButton;
	private JButton topRightButton;
	private JButton[] numberButtons;
	private JButton starButton;
	private JButton poundButton;
	
	public DumbPhone() {
		setTitle("Dumb Phone");
	    setSize(WIDTH, HEIGHT);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    createContents();
	    setVisible(true);
	}

	
	private void createContents() {

		display = new JTextArea();
		display.setPreferredSize(new Dimension(280,80));
		display.setLineWrap(true);
		display.setEnabled(false);
		display.setFont(new Font("Helvetica", Font.PLAIN, 32));
		display.setEditable(false);

		JPanel b = new JPanel(new GridLayout(5,3));

        //create JButtons
		topMiddleButton = new JButton(CALL_BUTTON_TEXT);
		topLeftButton = new JButton(DELETE_BUTTON_TEXT);
		topLeftButton.setEnabled(false);
		topRightButton = new JButton(TEXT_BUTTON_TEXT);
		numberButtons = new JButton[10];
		numberButtons[1] = new JButton("<html><center>1</center></html>");
		numberButtons[2] = new JButton("<html><center>2<br>ABC</center></html>");
		numberButtons[3] = new JButton("<html><center>3<br>DEF</center></html>");
		numberButtons[4] = new JButton("<html><center>4<br>GHI</center></html>");
		numberButtons[5] = new JButton("<html><center>5<br>JKL</center></html>");
		numberButtons[6] = new JButton("<html><center>6<br>MNO</center></html>");
		numberButtons[7] = new JButton("<html><center>7<br>PQRS</center></html>");
		numberButtons[8] = new JButton("<html><center>8<br>TUV</center></html>");
		numberButtons[9] = new JButton("<html><center>9<br>WXYZ</center></html>");
		numberButtons[0] = new JButton("<html><center>0<br>space</center></html>");

		starButton = new JButton("*");
		poundButton = new JButton("#");
        
        
        //add JButtons to buttons JPanel
       b.add(topLeftButton);
       b.add(topMiddleButton);
       b.add(topRightButton);
       b.add(numberButtons[1]);
       b.add(numberButtons[2]);
       b.add(numberButtons[3]);
       b.add(numberButtons[4]);
       b.add(numberButtons[5]);
       b.add(numberButtons[6]);
       b.add(numberButtons[7]);
       b.add(numberButtons[8]);
       b.add(numberButtons[9]);
       b.add(starButton);
       b.add(numberButtons[0]);
       b.add(poundButton);

        
        //add Listener instance (inner class) to buttons
		Listener listener = new Listener();
       topLeftButton.addActionListener(listener);
       topMiddleButton.addActionListener(listener);
       topRightButton.addActionListener(listener);
       for (int i =0; i<10; i++) {
		   numberButtons[i].addActionListener(listener);
	   }
       starButton.addActionListener(listener);
       poundButton.addActionListener(listener);

        //add display and buttons to JFrame
		add(display, BorderLayout.NORTH);
		add(b);

	}
	private boolean isDisplayEmpty(){
		return display.getText().equals("");
	}

	
	private class Listener implements ActionListener {
		private boolean isNumberMode = true;
		private String lastPressed = "";
		private int lastCharacterIndex = 0;
		private Date lastPressTime = new Date();

		private void pressed(String str, int num){
			Date currentTime = new Date();
			if(display.getText().equals(TEXT_DISPLAY_TEXT)){
				display.setText("");
			}
			if(lastPressed.equals(numberButtons[num].getText()) && currentTime.getTime() -lastPressTime.getTime() <1000 ){
				lastCharacterIndex++;
				display.setText(display.getText().substring(0,display.getText().length()-1) + str.charAt(lastCharacterIndex % str.length()));
			}else {
				lastCharacterIndex =0;

				display.setText(display.getText() + str.charAt(0));
			}
			lastPressed = numberButtons[num].getText();
			lastPressTime = new Date();
		}

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == numberButtons[0]){

				if(isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "0");
				}else{
					display.setText(display.getText()+" ");
					topRightButton.setEnabled(true);

				}
			}
			if(e.getSource() == numberButtons[1]) {

				if (isNumberMode) {
					numberButtons[1].setEnabled(true);
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "1");
				}
			}
			if(e.getSource() == numberButtons[2]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "2");
				}else{
					pressed("ABC",2);



				}
			}
			if(e.getSource() == numberButtons[3]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "3");
				}else{
					pressed("DEF",3);

				}
			}
			if(e.getSource() == numberButtons[4]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "4");
				}else{
					pressed("GHI",4);

				}
			}
			if(e.getSource() == numberButtons[5]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "5");
				}else{
					pressed("JKL", 5);

				}
			}
			if(e.getSource() == numberButtons[6]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "6");
				}else{
					pressed("MNO",6);

				}
			}
			if(e.getSource() == numberButtons[7]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "7");
				}else{
					String pqrs = "PQRS";
					pressed("PQRS",7);

				}
			}
			if(e.getSource() == numberButtons[8]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "8");
				}else{
					pressed("TUV",8);

				}
			}
			if(e.getSource() == numberButtons[9]) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT) ||display.getText().equals(ENTER_NUMBER_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "9");
				}else{
					pressed("WXYZ",9);

				}
			}


			if(e.getSource() == starButton) {
				if (isNumberMode) {if(display.getText().equals(CALLING_DISPLAY_TEXT)){
					display.setText("");
				}
					display.setText(display.getText() + "*");
					topLeftButton.setEnabled(true);
				}
			}
			if(e.getSource() == poundButton) {
				if (isNumberMode) {
					if(display.getText().equals(CALLING_DISPLAY_TEXT)){
						display.setText("");
					}
					display.setText(display.getText() + "#");
					topLeftButton.setEnabled(true);
				}
			}
			if(e.getSource() == topLeftButton){
				topLeftButton.setEnabled(true);
				display.setText(display.getText().substring(0,display.getText().length()-1));
				lastPressed = topLeftButton.getText();
			}
			if(e.getSource() == topMiddleButton){
				if(topMiddleButton.getText().equals(CALL_BUTTON_TEXT)) {
					if (isDisplayEmpty() && !display.getText().equals(ENTER_NUMBER_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)) {
						display.setText(ENTER_NUMBER_TEXT);
						topLeftButton.setEnabled(false);
					} else {
						display.setText(CALLING_DISPLAY_TEXT);
						topMiddleButton.setText(END_BUTTON_TEXT);
						topLeftButton.setText("");
						topLeftButton.setEnabled(false);
						topRightButton.setText("");
						topRightButton.setEnabled(false);
					}
				}else if(topMiddleButton.getText().equals(END_BUTTON_TEXT)){
					topMiddleButton.setText(CALL_BUTTON_TEXT);
					topLeftButton.setText(DELETE_BUTTON_TEXT);
					topRightButton.setText(TEXT_BUTTON_TEXT);
					topRightButton.setEnabled(true);
					display.setText("");
				}
				if(topMiddleButton.getText().equals(CANCEL_BUTTON_TEXT)){
					topMiddleButton.setText(CALL_BUTTON_TEXT);
					topRightButton.setText(TEXT_BUTTON_TEXT);
					starButton.setEnabled(true);
					poundButton.setEnabled(true);
					numberButtons[1].setEnabled(true);
					display.setText("");
					isNumberMode = true;
					topRightButton.setEnabled(true);
				}
				topLeftButton.setEnabled(true);
			}
			if(e.getSource() == topRightButton) {
				if (isDisplayEmpty() && isNumberMode && topRightButton.getText().equals(TEXT_BUTTON_TEXT) || display.getText().equals(ENTER_NUMBER_TEXT)) {
					display.setText(ENTER_NUMBER_TEXT);
					numberButtons[1].setEnabled(true);
					poundButton.setEnabled(true);
					starButton.setEnabled(true);
				} else if (!isDisplayEmpty() && !topMiddleButton.getText().equals(CANCEL_BUTTON_TEXT)) {
					display.setText(TEXT_DISPLAY_TEXT);
					topMiddleButton.setText(CANCEL_BUTTON_TEXT);
					isNumberMode = false;
					numberButtons[1].setEnabled(false);
					poundButton.setEnabled(false);
					starButton.setEnabled(false);
					topRightButton.setText(SEND_BUTTON_TEXT);
					topRightButton.setEnabled(false);
				} else if (topRightButton.getText().equals(SEND_BUTTON_TEXT) && !display.getText().equals(TEXT_DISPLAY_TEXT) && !display.getText().equals("")&& !isNumberMode) {
					display.setText("");
					topMiddleButton.setText(CALL_BUTTON_TEXT);
					topRightButton.setText(TEXT_BUTTON_TEXT);
					numberButtons[1].setEnabled(true);
					isNumberMode = true;

				} else if (topRightButton.getText().equals(SEND_BUTTON_TEXT) && display.getText().equals(ENTER_NUMBER_TEXT) && !display.getText().equals("") && !isNumberMode) {
					topRightButton.setText(TEXT_BUTTON_TEXT);
				}


			}
			if(isDisplayEmpty() || display.getText().equals(ENTER_NUMBER_TEXT) ||display.getText().equals(CALLING_DISPLAY_TEXT)||display.getText().equals(TEXT_DISPLAY_TEXT)){
				topLeftButton.setEnabled(false);
			}else{
				topLeftButton.setEnabled(true);
			}
			if(!isDisplayEmpty()&& !isNumberMode && topRightButton.getText().equals(SEND_BUTTON_TEXT)&& !display.getText().equals(TEXT_DISPLAY_TEXT)){
				topRightButton.setEnabled(true);
			}
	    }
	}
	
	public static void main(String[] args) {
		new DumbPhone();
	}


}