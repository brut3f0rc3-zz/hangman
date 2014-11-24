/*
	Simulation of Hangman using Swing class.
	JAVA Lab Group Project
	Members : Nisha Sinha, Md.Sajid, Ratan Jain, Palash Chatterjee
*/

	import java.awt.*;
	import java.awt.event.*;
	import javax.swing.*;

//Class to handle Hangman drawing
	class stage extends JPanel{

		static int allowed;

		public stage(){
			allowed=6;

		}

		public void setAllowed(int x){
			allowed=x;
			//System.out.println("Allowed : "+allowed);
			//new Throwable().getStackTrace();
			this.repaint();
		}

		@Override public void paintComponent(Graphics g) {
			super.paintComponent(g);
			//System.out.println("hanged : "+allowed);

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
		@Override public Dimension getPreferredSize(){
			return new Dimension(300,300);
		}
	}


//Driver class
	public class hangman extends JApplet implements ActionListener{

		stage stagePanel = new stage();
		JPanel controlPanel = new JPanel();
		Box verticalBox = Box.createVerticalBox();
		JPanel banner = new JPanel();
		JLabel name = new JLabel("Hang-Man", SwingConstants.CENTER);;
		JTextField guess = new JTextField(1);

		JLabel guessLabel = new JLabel("Please enter your guess...");	
		JLabel current;
		JLabel currentLabel = new JLabel("Current State");;
		JLabel lives;
		JLabel result = new JLabel("");
		JButton submit = new JButton("Submit");
		JButton resetGame = new JButton(new AbstractAction("New Game"){
			@Override
			public void actionPerformed(ActionEvent ae){
				reset();
			}
		});


		String currentStatus=null;
		String lifeStatus=null;
		String selectedWord;
		String guessedLetter, newFiller;
		String userName;
		int allowed;
		boolean correct;
		boolean letterUsed[] = new boolean[26];

		public void init(){	

			System.out.println(" _   _    __    _  _  ___  ____  ____  ");
			System.out.println("( )_( )  /__\\  ( \\( )/ __)( ___)(  _ \\ ");
			System.out.println(" ) _ (  /(__)\\  )  (( (_-. )__)  )(_) )");
			System.out.println("(_) (_)(__)(__)(_)\\_)\\___/(____)(____/ ");
			System.out.println("\n");
			System.out.println("++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("................L O A D I N G.............");

				selectedWord = words.wordreturn();
				allowed = 6;
				lifeStatus="";
				for (int i=0; i<allowed; i++)
					lifeStatus+="\u2764";
				currentStatus="";
				for (int i=0; i<selectedWord.length(); i++)
					currentStatus+="_ ";
				lives = new JLabel(lifeStatus, SwingConstants.RIGHT);
				lives.setFont(new Font("Serif", Font.PLAIN, 20));
				current	= new JLabel(currentStatus, SwingConstants.CENTER);
				current.setFont(new Font("Serif", Font.PLAIN, 30));
				submit.addActionListener(this);

				guess.addKeyListener(new KeyAdapter(){
					public void keyPressed(KeyEvent e){
						int key = e.getKeyCode();
						if(key==KeyEvent.VK_ENTER){
							acceptInput();
						}
					}
				});

			}

			public void reset(){
				selectedWord = words.wordreturn();

				for(int i=0; i<26; i++)
					letterUsed[i]=false;

				allowed = 6;

				lifeStatus="";
				for (int i=0; i<allowed; i++)
					lifeStatus+="\u2764";

				lives.setText(lifeStatus);

				currentStatus="";
				for (int i=0; i<selectedWord.length(); i++)
					currentStatus+="_ ";

				current.setText(currentStatus);

				stagePanel.setAllowed(allowed);
				this.repaint();
				guessLabel.setText("Please enter your guess...");
				guess.setEnabled(true);
				submit.setEnabled(true);
				result.setVisible(false);
				showStatus("The game has been restarted.");
				System.out.println("The game has been restarted.");

			}
			public void paint(Graphics g){
				super.paint(g);
				stagePanel.repaint();

		//Banner
				banner.setBackground(Color.CYAN);
				banner.add(name);

		//Control Panel
				controlPanel.setBackground(Color.WHITE);
				controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.PAGE_AXIS));
				verticalBox.add(lives);
				verticalBox.add(currentLabel);
				verticalBox.add(current);
				verticalBox.add(guessLabel);
				guess.setMaximumSize(new Dimension(getSize().width,50));
				guess.setAlignmentX(SwingConstants.CENTER);
				verticalBox.add(guess);
				verticalBox.add(submit);
				verticalBox.add(resetGame);
				verticalBox.add(result);
				controlPanel.add(verticalBox);
				controlPanel.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));


				if(allowed==0){

					guessLabel.setText(" ");
					guess.setEnabled(false);
					submit.setEnabled(false);
					result.setText("The word was : "+selectedWord.toUpperCase()+"  !!!");
					System.out.println("The word was : "+selectedWord.toUpperCase()+"  !!!");
					result.setVisible(true);
				}

		//Applet basic settings and positioning
				this.setLayout(new BorderLayout());
				this.add(stagePanel, BorderLayout.WEST);	
				this.add(controlPanel, BorderLayout.CENTER);
				this.add(banner, BorderLayout.NORTH);

			//Clear the text field and set focus
				guess.setText("");
				guess.requestFocus();

			}

			public void acceptInput(){
				guessedLetter = guess.getText();

		//Clear the text field and set focus
				guess.setText("");
				guess.requestFocus();

		//Reset strings
				lifeStatus="";

				showStatus(currentStatus);
				currentStatus.replaceAll("\\s","");
			//showStatus(currentStatus);

				if(validateInput(guessedLetter)){


					newFiller="";
					correct=false;

					System.out.println("You guessed : "+guessedLetter.toUpperCase().charAt(0));
					for(int i=0; i<selectedWord.length(); i++){

						if(Character.toString(guessedLetter.charAt(0)).equalsIgnoreCase(Character.toString(selectedWord.charAt(i)))){

							showStatus(guessedLetter.toUpperCase()+" is correct.");
							newFiller+=guessedLetter.toUpperCase()+" ";
							correct=true;
						}
						else{
							newFiller+=Character.toString(currentStatus.charAt(i*2))+" ";
						}
					}


					if(!correct)
					{
						allowed--;
						showStatus(guessedLetter.toUpperCase()+" is incorrect.");
					}
					if(allowed==0)
						lifeStatus="\u2639";
					else{
						for (int i=0; i<allowed; i++)
							lifeStatus+="\u2764";
					}	
					lives.setText(lifeStatus);
					currentStatus = newFiller.trim();
					current.setText(currentStatus);
					System.out.println("Current status : "+newFiller);
					stagePanel.setAllowed(allowed);

					if(selectedWord.equalsIgnoreCase(current.getText().replaceAll("\\s","")))
					{	
						result.setText("Congratulations!");
						System.out.println("Congratulations!");
						result.setVisible(true);
						guess.setEnabled(false);
						submit.setEnabled(false);
					}
					
			//stagePanel.repaint();
					this.repaint();
				}
			}

			public void actionPerformed(ActionEvent ae){

				acceptInput();

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
					return true;
				}
				else{
					letterUsed[(int)args.toLowerCase().charAt(0)-97]=true;
					return true;
				}

			}
		}

//Class to generate words at random for the words input
		class words
		{
			public static String wordreturn()
			{
				String wordarray[]={"Scrabble","crossword","maintain","second","adjacent","Diagonal","premium","Distribution","Quantity",
				"Hangman","computer","appropriate","television","prompt","immediately","arrangement","possible","calculate",
				"comproportionation","exuberant","flambouyant","LEXICOLOGY","ambidexterous","philanthropist","flabbergast","quiver"
				,"tubectomy","impotent","important","buoyancy","bazinga","Parliament","zealous","yesteryear","xenophobia","workaholic",
				"Swagger","oblivious","valedictorian","Undocumented","nincompoop","Stallion","zeitgeist","braille","binary",
				"claustrophobic","encyclopedia","exaggeration","flamingos","freaks","google","graphical","horoscope","hysterical",
				"judiciary","justification","Pandora","krushkal","kardashian","lullaby","lollipop","mythology","mystique",
				"nonchalant","questionaire","Procrastination","Karaoke","Cryptography","Sublime", "Organic","Countermeasure","Ethical","University","Project","Hangman"};
				int upper=wordarray.length-1;
				int lower=0;
				int r = (int) (Math.random() * (upper - lower)) + lower;
				try{
					return wordarray[r];
				}
				catch(Exception e){
					r = (int) (Math.random() * (upper - lower)) + lower;
					return wordarray[r];
				}
			}

		}