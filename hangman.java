//Allowed errors = 6

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
//For Box Layout
import javax.swing.*;

public class hangman extends Applet implements ActionListener{
		TextField name;
		Button submit;
		Canvas stage;
		Panel pane;
		TextField guess;
		Label state, title, l1, l2, error;
		int errors=0;
		int allowed=6;
		Font bold = new Font("Arial", Font.BOLD, 30);
		Font normal = new Font("Arial", Font.PLAIN, 15);
		boolean correct=false;

		String word = "HANGMAN";
		String filler="";
		String newFiller="";
		String lives="";

		public void init(){
			setLayout(new BorderLayout());
			setBackground(Color.white);
			setForeground(Color.black);

			//create Objects
			stage = new Canvas();
			pane = new Panel();
			guess = new TextField(1);

			title = new Label("Hangman", Label.CENTER);
			l1=new Label("Current Status : ", Label.CENTER);
			l2= new Label("Please enter your guess : ", Label.CENTER);
			

			for (int i=0; i<word.length(); i++)
				filler+="_";
			state = new Label(filler, Label.CENTER);

			for (int i=0; i<allowed; i++)
				lives+="\u2764";
			error = new Label(lives,Label.RIGHT);
			

			state.setFont(bold);
			submit = new Button("Submit");
			submit.addActionListener(this);
			
			add(title, BorderLayout.PAGE_START);
			add(stage, BorderLayout.LINE_START);
			add(pane, BorderLayout.LINE_END);
			pane.setPreferredSize(new Dimension(300, 200));
			pane.setLayout(new BoxLayout(pane, BoxLayout.PAGE_AXIS));

		}

		public void paint(Graphics g){

			pane.add(error);
			pane.add(l1);
			pane.add(state);
			pane.add(l2);
			pane.add(guess);
			pane.add(submit);
			this.setSize(new Dimension(600,400));
			
			switch(6-allowed){
				//Right Foot
				case 6:	g.drawLine(175,230,220,270);
				//Left Foot
				case 5: g.drawLine(175,230, 130,270);
				//Right Arm
				case 4:	g.drawLine(175,180,210,165);
				//Left Arm
				case 3: g.drawLine(140,165,175,180);
				//Body
				case 2: g.drawLine(175,150,175,230);
				//Face
				case 1: g.drawOval(150,100, 50,50);

				default: //Woodwork
						g.fillRect(60,50,7,300);
						g.fillRect(67,50,125,7);
						g.fillRect(30,350,67,7);

						//Diagonal
						g.drawLine(67,87,97,57);
						g.drawLine(67,88,98,57);
						g.drawLine(67,89,99,57);
						g.drawLine(67,90,100,57);

						//Rope
						g.drawLine(175,57,175,100);
						}
	
		}


		public void actionPerformed(ActionEvent e){
			
			String guessedLetter = guess.getText();

			//Clear the text field
			guess.setText("");

			//Set defaults
			filler = state.getText();
			filler.replaceAll("\\s","");
			newFiller="";
			lives="";
			correct=false;
			
			if(guessedLetter.length()>1)
				showStatus("Please enter a single character only.");
			else{
				for(int i=0; i<word.length(); i++){
					showStatus("Checking");
					if(Character.toString(guessedLetter.charAt(0)).equalsIgnoreCase(Character.toString(word.charAt(i)))){
						showStatus("Check");
						newFiller+=guessedLetter.toUpperCase();
						correct=true;
					}
					else{
						newFiller+=Character.toString(filler.charAt(i));
					}
				}
				if(!correct)
					allowed--;
				for (int i=0; i<allowed; i++)
					lives+="\u2764";
				error.setText(lives);
				state.setText(newFiller);
				repaint();
			}
		}
}