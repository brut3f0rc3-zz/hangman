//Allowed errors = 6

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
//For Box Layout
import javax.swing.*;

public class hangman extends Applet implements ActionListener{
		boolean letterUsed[] = new boolean[26];
		TextField name;
		Button submit;
		Canvas stage;
		Panel pane;
		TextField guess;
		Label state, title, l1, l2, error, result;
		int errors=0;
		int allowed=6;
		Font bold = new Font("Arial", Font.BOLD, 30);
		Font normal = new Font("Arial", Font.PLAIN, 15);
		boolean correct=false;

		String word = "WIKIPEDIA";
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
				filler+="_ ";
			state = new Label(filler.trim(), Label.CENTER);

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
			
			guess.requestFocus();

			if(allowed==0){
				pane.remove(guess);
				pane.remove(submit);
				pane.remove(l2);
				result = new Label("Sorry! You have lost. The word was : ");
				pane.add(result);
			}

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
						//Rope
						g.drawLine(175,57,175,100);

				default: //Woodwork
						g.fillRect(60,50,7,300);
						g.fillRect(67,50,125,7);
						g.fillRect(30,350,67,7);

						//Diagonal
						g.drawLine(67,87,97,57);
						g.drawLine(67,88,98,57);
						g.drawLine(67,89,99,57);
						g.drawLine(67,90,100,57);
						
						}
	
		}


		public boolean validateInput(String args){

			if(args.length()>1){
				showStatus("Please enter a single character only.");
				return false;
			}
			else if(args.equals("")){
				showStatus("Please provide an input.");
				return false;
			}
			else if(!Character.isLetter(args.charAt(0))){
				showStatus("Please enter only letters.");
				return false;
			}
			else if(letterUsed[(int)args.toLowerCase().charAt(0)-97]){
				showStatus(Character.toString(args.toUpperCase().charAt(0))+" has already been used.");
				return false;
			}
			else{
				letterUsed[(int)args.toLowerCase().charAt(0)-97]=true;
				return true;
			}

		}


		public void actionPerformed(ActionEvent e){
			
			String guessedLetter = guess.getText();
			System.out.println(guessedLetter);

			//Clear the text field and set focus
			guess.setText("");
			guess.requestFocus();

			//Set defaults
			filler = state.getText();
			System.out.println("Current status : "+filler);
			showStatus(filler);
			filler.replaceAll("\\s+","");
			System.out.println("Current status after space removal : "+filler);
			System.out.println("String length : "+filler.length());
			newFiller="";
			lives="";
			correct=false;
			
			if(validateInput(guessedLetter)){
				System.out.println((int)guessedLetter.toLowerCase().charAt(0));
				for(int i=0; i<word.length(); i++){

					if(Character.toString(guessedLetter.charAt(0)).equalsIgnoreCase(Character.toString(word.charAt(i)))){
			
						newFiller+=guessedLetter.toUpperCase()+" ";
						correct=true;
					}
					else{
						newFiller+=Character.toString(filler.charAt(i*2))+" ";
					}
				}


				if(!correct)
					allowed--;
				for (int i=0; i<allowed; i++)
					lives+="\u2764";
				System.out.println("Allowed : "+allowed);
				error.setText(lives);
				state.setText(newFiller.trim());
				showStatus(newFiller);
				System.out.println("Current status : "+newFiller);
				repaint();
			}
		}
}