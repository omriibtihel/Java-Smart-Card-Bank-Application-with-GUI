package clientpackage;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.border.Border;
import java.io.IOException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

import com.sun.javacard.apduio.CadTransportException;
import com.sun.javacard.apduio.Apdu;

public class ClientInterface extends JFrame implements ActionListener {
	boolean carteIn = false;
	boolean creditOn = false;
	boolean debitOn = false;
	boolean debitDone = false;
	boolean pinIn = false;
	JPanel panePrincipale = new JPanel();
	JPanel panelEcran = new JPanel(null);
	JPanel paneLecteur = new JPanel();
	JPanel paneMoney = new JPanel();
    JLabel leteurLabel = new JLabel();
    JLabel moneyLabel = new JLabel();
    JLabel pinLabel = new JLabel("Put your card");
    JLabel balanceLabel = new JLabel();
	
	String s="";String shownS="";
	JButton ba1 = new JButton("Credit");
    JButton ba2 = new JButton("Debit");
    JButton ba3 = new JButton(">>>>>");
    JButton ba4 = new JButton(">>>>>");
    JButton ba5 = new JButton("Balance");
    JButton ba6 = new JButton("<<<<<");
    JButton ba7 = new JButton("<<<<<");
    JButton ba8 = new JButton("<<<<<");
    JButton joker1 = new JButton();
    JButton joker2 = new JButton();
    JButton joker5 = new JButton();
    JButton joker6 = new JButton();
    JButton eyeButton = new JButton();
    JButton hiddenButton = new JButton();
    
    JTextField tPin = new JTextField("");
    
	JButton b0 = new JButton();
    JButton b1 = new JButton();
    JButton b2 = new JButton();
    JButton b3 = new JButton();
    JButton b4 = new JButton();
    JButton b5 = new JButton();
    JButton b6 = new JButton();
    JButton b7 = new JButton();
    JButton b8 = new JButton();
    JButton b9 = new JButton();
    
    JButton cancelBtn = new JButton("CANCEL");
    JButton clearBtn = new JButton("CLEAR");
    JButton okBtn = new JButton("ENTER");
    JButton retourBtn = new JButton("RETURN"); 
    
    ImageIcon iconError = new ImageIcon("img/error.png");
    ImageIcon iconPadlock = new ImageIcon("img/padlock.png");
        
    Font msgLabel = new Font("Tahoma", Font.PLAIN, 22);
    
    public static final byte INS_INTERROGER_COMPTE = 0x01;
	public static final byte INS_INCREMENTER_COMPTE = 0x02;
	public static final byte INS_DECREMENTER_COMPTE = 0x03;
	public static final byte INS_INITIALISER_COMPTE = 0x04;
	public static final byte INS_TEST_CODE_PIN = 0x00;
	
	private ClientFunction client;
    
    public static void main(String[] args) {
    	try {
			ClientInterface window = new ClientInterface();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public ClientInterface() {
    	client = new ClientFunction();
		client.Connect();
		try {
			client.select();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CadTransportException e) {
			e.printStackTrace();
		}
		initiate();
    }
    
    public void initiate() {
    	setTitle("GUICHET AUTOMATIQUE DE BILLETS");
		setResizable(false);
		setBounds(0, 0, 465, 540);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Color bgColor = new Color(50, 50, 50);
        panePrincipale.setLayout(null);
                  
        
        //**********************Panel Ecran ***********************
        panelEcran.setBounds(115,40, 220, 220);  
        panelEcran.setBackground(Color.black);
        pinLabel.setFont(msgLabel);
        pinLabel.setBounds(30, 20, 180, 30);
        panelEcran.add(pinLabel);
        

        
        tPin.setFont(msgLabel);
        tPin.setBounds(15,60,160,30);
        tPin.setVisible(false);
        panelEcran.add(tPin);
        
        tPin.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            	e.consume();
            }
            public void keyPressed(KeyEvent e) {
            	e.consume();
            }
            public void keyReleased(KeyEvent e) {              
            	e.consume();
            }
        });
        
        eyeButton.setBounds(180,60, 30, 28);
        ImageIcon iconhid = new ImageIcon("img/hidden.png");
        eyeButton.setIcon(iconhid);
        panelEcran.add(eyeButton);
        eyeButton.setVisible(false);
        
        hiddenButton.setBounds(180,60, 30, 28);
        ImageIcon iconeye = new ImageIcon("img/eye.png");
        hiddenButton.setIcon(iconeye);
        panelEcran.add(hiddenButton);
        hiddenButton.setVisible(false);
        
        retourBtn.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        retourBtn.setBounds(70, 100, 80,40);
        panelEcran.add(retourBtn);
        retourBtn.setVisible(false);
        
        
       
        ba1.setBounds(10, 40, 90, 40); 
        ba2.setBounds(10,100, 90, 40);
        ba3.setBounds(10,160, 90, 40);
        ba4.setBounds(10,220, 90, 40);
        ba5.setBounds(350,40, 90, 40);
        ba6.setBounds(350,100, 90, 40);
        ba7.setBounds(350,160, 90, 40);
        ba8.setBounds(350,220, 90, 40);
        
        ba1.setEnabled(false);
        ba2.setEnabled(false);
        ba3.setEnabled(false);
        ba4.setEnabled(false);
        ba5.setEnabled(false);
        ba6.setEnabled(false);
        ba7.setEnabled(false);
        ba8.setEnabled(false);
        
        //*********************** logo lecteur *************************
        
        ImageIcon iconLecteur = new ImageIcon("img/atm_carte.png");

        leteurLabel.setIcon(iconLecteur);
        paneLecteur.add(leteurLabel);
        paneLecteur.setBounds(5, 300, 100, 150);
        paneLecteur.setBackground(bgColor);
        
        leteurLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(!carteIn) {
            		try {
                        File file = new File("img/true.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                        Clip clip = AudioSystem.getClip();
                        clip.open(audioInputStream);
                        clip.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
            		pinLabel.setText("Enter your PIN : ");
                	paneLecteur.setVisible(false);
                	tPin.setVisible(true);
                	eyeButton.setVisible(true);
                	carteIn = true;
                	
            	}else {
            		
            		pinLabel.setText("CARD EJECTED");
    				pinLabel.setVisible(true);
                	paneLecteur.setVisible(false);
                	carteIn = false;
            	}
            	
            }
        });
        
        // ***************************** money logo ************************
        
        ImageIcon iconMoney = new ImageIcon("img/atm_money.png");
        moneyLabel.setIcon(iconMoney);
        paneMoney.add(moneyLabel);
        paneMoney.setBounds(5, 300, 100, 150);
        paneMoney.setBackground(bgColor);
        paneMoney.setVisible(false);
        
        moneyLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	paneMoney.setVisible(false);
            	if(debitOn) {
            		pinLabel.setVisible(false);
            	}       	
            	System.out.println("money got");
            }
        });
        //********************* clavier ******************
        b0.setBounds(190,325, 65, 65);
        ImageIcon icon0 = new ImageIcon("img/n0.png");
        b0.setIcon(icon0);
        
        b1.setBounds(115, 200, 65, 65);
        ImageIcon icon1 = new ImageIcon("img/n1.png");
        b1.setIcon(icon1);
        
        b2.setBounds(190, 200, 65, 65);
        ImageIcon icon2 = new ImageIcon("img/n2.png");
        b2.setIcon(icon2);
        
        b3.setBounds(265,200, 65, 65);
        ImageIcon icon3 = new ImageIcon("img/n3.png");
        b3.setIcon(icon3);
        
        b4.setBounds(115, 275, 65, 65);
        ImageIcon icon4 = new ImageIcon("img/n4.png");
        b4.setIcon(icon4);
        
        b5.setBounds(190, 275, 65, 65);
        ImageIcon icon5 = new ImageIcon("img/n5.png");
        b5.setIcon(icon5);
        
        b6.setBounds(265, 275, 65, 65);
        ImageIcon icon6 = new ImageIcon("img/n6.png");
        b6.setIcon(icon6);
        
        b7.setBounds(115, 350, 65, 65);
        ImageIcon icon7 = new ImageIcon("img/n7.png");
        b7.setIcon(icon7);
        
        b8.setBounds(190, 350, 65, 65);
        ImageIcon icon8 = new ImageIcon("img/n8.png");
        b8.setIcon(icon8);
        
        b9.setBounds(265, 350, 65, 65);
        ImageIcon icon9 = new ImageIcon("img/n9.png");
        b9.setIcon(icon9);
        
         
        
        cancelBtn.setBounds(350,275, 90, 50);
        cancelBtn.setBackground(new Color(255, 0, 0));
        clearBtn.setBounds(350,330, 90, 50);
        clearBtn.setBackground(new Color(255, 255, 0));
        okBtn.setBounds(350,385, 90, 50);
        okBtn.setBackground(new Color(0, 255, 0));
        

        Color cancelBtnBorderColor = new Color(180, 0, 0);
        int borderThickness = 3; 
        cancelBtn.setBorder(BorderFactory.createLineBorder(cancelBtnBorderColor, borderThickness));
        
        Color clearBtnBorderColor = new Color(180, 180, 0);
        clearBtn.setBorder(BorderFactory.createLineBorder(clearBtnBorderColor, borderThickness));

        Color okBtnBorderColor = new Color(0, 180, 0);
        okBtn.setBorder(BorderFactory.createLineBorder(okBtnBorderColor, borderThickness));
        
        
        //ajout du zone de sortie
        panePrincipale.add(panelEcran);
        panePrincipale.add(ba3);
        panePrincipale.add(ba1);
        panePrincipale.add(ba2);
        panePrincipale.add(ba4);
        panePrincipale.add(ba6);
        panePrincipale.add(ba5);
        panePrincipale.add(ba7);
        panePrincipale.add(ba8);
        panePrincipale.add(paneLecteur);
        panePrincipale.add(paneMoney);
        
        JPanel paneClavier = new JPanel();
        paneClavier.add(b1);
        paneClavier.add(b2);
        paneClavier.add(b3);
        paneClavier.add(b4);
        paneClavier.add(b5);
        paneClavier.add(b6);
        paneClavier.add(b7);
        paneClavier.add(b8);
        paneClavier.add(b9);
        paneClavier.add(b0);
        paneClavier.setBounds(100, 270, 250, 230);
        
        panePrincipale.add(cancelBtn);      
        panePrincipale.add(clearBtn);        
        panePrincipale.add(okBtn);        
        
        Border bordure = BorderFactory.createLineBorder(Color.BLACK);
        //paneClavier.setBorder(bordure);
        
        panePrincipale.add(paneClavier); // ajout du clavier
        paneClavier.setBackground(bgColor);
        panePrincipale.setBackground(bgColor);
        add(panePrincipale);
      
        
		setVisible(true);
		
		// ******************************* les evenements **************************
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		clearBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		okBtn.addActionListener(this);
		eyeButton.addActionListener(this);
		hiddenButton.addActionListener(this);
		ba5.addActionListener(this);
		ba6.addActionListener(this);
		ba1.addActionListener(this);
		ba2.addActionListener(this);
		ba3.addActionListener(this);
		ba4.addActionListener(this);
		ba7.addActionListener(this);
		ba8.addActionListener(this);
		retourBtn.addActionListener(this);
		joker1.addActionListener(this);
		joker2.addActionListener(this);
		joker5.addActionListener(this);
		joker6.addActionListener(this);
    }
    
    public BigInteger balance_action() {
        Apdu apdu = null;
        try {
            apdu = client.Msg(INS_INTERROGER_COMPTE, (byte) 0x00, null, (byte) 0x7f);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (CadTransportException e1) {
            e1.printStackTrace();
        }
        if (apdu.getStatus() != 0x9000) {
            System.out.println("Erreur : status word different de 0x9000");
        } else {
            BigInteger balance = new BigInteger(apdu.dataOut);
            
            // Show a message in red if balance is less than 0
            if (balance.compareTo(BigInteger.ZERO) < 0) {
            	try {
                    File file = new File("img/erreur.wav");
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                    ee.printStackTrace();
                }
                showMessage(this, " YOU ARE IN RED.",iconError);
            }
            
            return balance;
        }
        return null;
    }

    
    public void crediter_action(String a) {
    	short value = Short.parseShort(a);
    	System.out.println(value);
    	byte[] montant = {(byte) (value >> 8), (byte) value};
		Apdu apdu = null;
		try {
			apdu = client.Msg(INS_INCREMENTER_COMPTE, (byte) 0x02, montant, (byte) 0x7f);
			System.out.println(apdu);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CadTransportException e1) {
			e1.printStackTrace();
		}
		if (apdu.getStatus() != 0x9000) {
			if (apdu.getStatus() == 0x2710) {
				try {
                    File file = new File("img/erreur.wav");
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                    ee.printStackTrace();
                }
				showMessage(this, "Max Balance", iconError);
			}
			pinLabel.setText("Operation Failed !");
			pinLabel.setVisible(true);
		} else {
			pinLabel.setText("Operation Done !");
			pinLabel.setVisible(true);
		}
	}
	
    public void debiter_action(String a) {
        short value = Short.parseShort(a);
        System.out.println(value);
        byte[] montant = {(byte) (value >> 8), (byte) value};
        Apdu apdu = null;
        try {
            apdu = client.Msg(INS_DECREMENTER_COMPTE, (byte) 0x02, montant, (byte) 0x7f);
            System.out.println(apdu);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CadTransportException e) {
            e.printStackTrace();
        }

        if (apdu.getStatus() != 0x9000) {
            if (apdu.getStatus() == 0x2710) {
            	try {
                    File file = new File("img/erreur.wav");
                    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                    Clip clip = AudioSystem.getClip();
                    clip.open(audioInputStream);
                    clip.start();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                    ee.printStackTrace();
                }
                showMessage(this, "Insufficient Balance", iconError);
                
                // Check if the balance is less than 0
                if (value < 0) {
                	
                    showMessage(this, "Vous êtes dans le rouge", iconError);
                }
            }
            pinLabel.setText("Operation Failed !");
            pinLabel.setVisible(true);
        } else {
            pinLabel.setText("Operation Done !");
            pinLabel.setVisible(true);
            debitDone = true;
        }
    }

	

	public void actionPerformed(ActionEvent e) {
		
		JButton var =null;
		Object src = e.getSource();
		if(src instanceof JButton)
			var = (JButton) src;
		
		if(carteIn) {
			if(shownS.length() <= 3 ) {
				if(var == b0)
				{  try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "0";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}
				}
				else if(var == b1)
					
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "1";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b2)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }
						s += "2";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b3)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "3";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b4)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "4";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b5)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "5";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b6)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "6";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b7)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "7";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b8)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "8";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
				else if(var == b9)
				{try {
		            File file = new File("img/clic.wav");
		            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
		            Clip clip = AudioSystem.getClip();
		            clip.open(audioInputStream);
		            clip.start();
				 } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                     ee.printStackTrace();
                 }

						s += "9";
						shownS += "•";
						if(eyeButton.isVisible()) {
							tPin.setText(shownS);
						}else {
							tPin.setText(s);
						}	
				}
			}
		}
		
		
		if(var == clearBtn && carteIn) {
			s="";
			shownS="";
			tPin.setText("");
			hiddenButton.setVisible(false);
			eyeButton.setVisible(true);
		}
		if(var == eyeButton && s.length() != 0) {
			eyeButton.setVisible(false);
			hiddenButton.setVisible(true);
			tPin.setText(s);
		}
		if(var == hiddenButton && s.length() != 0) {
			hiddenButton.setVisible(false);
			eyeButton.setVisible(true);
			tPin.setText(shownS);
		}
		if(var == cancelBtn) {
			if(carteIn) {
				paneLecteur.setVisible(true);
				pinLabel.setText("Take your card");
				pinLabel.setVisible(true);
				tPin.setVisible(false);
				hiddenButton.setVisible(false);
				eyeButton.setVisible(false);
				
				ba1.setEnabled(false);
				ba2.setEnabled(false);
				ba5.setEnabled(false);
			} else {
		        File fichier = new File("C:\\Users\\ibtih\\OneDrive\\Bureau\\ATM-MasterCard\\bin\\balance.txt");

		        FileWriter redacteurFichier = null;
				try {
					redacteurFichier = new FileWriter(fichier, false);
				} catch (IOException e2) {
					e2.printStackTrace();
				}

		        BufferedWriter redacteurBuffer = new BufferedWriter(redacteurFichier);

		        try {
					redacteurBuffer.write(balance_action().toString());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

		        try {
					redacteurBuffer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		        
		        client.Deselect();
				System.exit(1);
		    }
	}
		if(var == okBtn) {
			if(carteIn) {
				String code= s;
				if(code.length()!=4) {
					showMessage(this, "pin (4 chiffres) !", iconError);
				}
				else {
					int a=0;
					try {
					a= Integer.parseInt(code);
					}catch(NumberFormatException Nfe) {}
					int a1=a/1000;
					int a2=(a/100)%10;
					int a3=(a/10)%10;
					int a4=a%10;
					byte[] pin_ok= {(byte) a1, (byte) a2, (byte) a3, (byte) a4};
					Apdu apdu = null;
					try {
						apdu = client.Msg(INS_TEST_CODE_PIN, (byte) 0x04, pin_ok, (byte) 0x7f);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (CadTransportException e1) {
						e1.printStackTrace();
					}
					System.out.println(apdu);
					if (apdu.getStatus() == 0x6300) {
						try {
	                        File file = new File("img/erreur.wav");
	                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
	                        Clip clip = AudioSystem.getClip();
	                        clip.open(audioInputStream);
	                        clip.start();
	                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
	                        ee.printStackTrace();
	                    }
							showMessage(this, "pin incorrect!", iconError);
					} else if(apdu.getStatus()== 0x6321) {
						try {
	                        File file = new File("img/alert.wav");
	                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
	                        Clip clip = AudioSystem.getClip();
	                        clip.open(audioInputStream);
	                        clip.start();
	                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
	                        ee.printStackTrace();
	                    }
						showMessage(this, "card blocked!", iconPadlock);
					}
					else
						{
						ba1.setEnabled(true);
						ba2.setEnabled(true);
						//ba3.setEnabled(true);
						//ba4.setEnabled(true);
						ba5.setEnabled(true);
						//ba6.setEnabled(true);
						//ba7.setEnabled(true);
						//ba8.setEnabled(true);
						pinLabel.setVisible(false);
						hiddenButton.setVisible(false);
						eyeButton.setVisible(false);
						tPin.setVisible(false);
						clearBtn.setEnabled(false);
						okBtn.setEnabled(false);
						pinIn = true;
						}
				}
			}	
		}
		if(var == ba5) {
			 try {
                 File file = new File("img/tchek.wav");
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                 Clip clip = AudioSystem.getClip();
                 clip.open(audioInputStream);
                 clip.start();
             } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                 ee.printStackTrace();
             }
			cancelBtn.setEnabled(false);
			BigInteger one = balance_action();
			
			pinLabel.setText("Your balance is : ");
			pinLabel.setVisible(true);
			tPin.setVisible(true);
			tPin.setText(one+" TND");
			tPin.setHorizontalAlignment(SwingConstants.CENTER);
			tPin.setBounds(30,60,160,30);
			tPin.setEditable(false);
				
			ba1.setEnabled(false);
			ba2.setEnabled(false);
			ba3.setEnabled(false);
			ba4.setEnabled(false);
			ba5.setEnabled(false);
			ba6.setEnabled(false);
			ba7.setEnabled(false);
			ba8.setEnabled(false);
		    retourBtn.setVisible(true);        
				System.out.println(one+"tnd");
		}
		
		
		if(var == ba1) {
			 try {
                 File file = new File("img/tchek.wav");
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                 Clip clip = AudioSystem.getClip();
                 clip.open(audioInputStream);
                 clip.start();
             } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                 ee.printStackTrace();
             }
			cancelBtn.setEnabled(false);
			creditOn = true;
			ba3.setEnabled(true);
			ba4.setEnabled(true);
			ba7.setEnabled(true);
			ba8.setEnabled(true);
			ba1.setVisible(false);
			ba2.setVisible(false);
			ba5.setVisible(false);
			ba6.setVisible(false);
			
			joker1.setBounds(10, 40, 90, 40);
			joker2.setBounds(10,100, 90, 40);
			joker5.setBounds(350,40, 90, 40);
			joker6.setBounds(350,100, 90, 40);

			joker1.setVisible(true);
			joker2.setVisible(true);
			joker5.setVisible(true);
			joker6.setVisible(true);
			
			retourBtn.setVisible(true);
			
			panePrincipale.add(joker1);
			panePrincipale.add(joker2);
			panePrincipale.add(joker5);
			panePrincipale.add(joker6);
			joker1.setText("10 TND");
			joker2.setText("20 TND");
			ba3.setText("50 TND");
			ba4.setText("80 TND");
			joker5.setText("100 TND");
			joker6.setText("200 TND");
			ba7.setText("300 TND");
			ba8.setText("500 TND");			
		}
		
		if(var == ba2) {
			 try {
                 File file = new File("img/tchek.wav");
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                 Clip clip = AudioSystem.getClip();
                 clip.open(audioInputStream);
                 clip.start();
             } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                 ee.printStackTrace();
             }
			cancelBtn.setEnabled(false);
			debitOn = true;
			ba3.setEnabled(true);
			ba4.setEnabled(true);
			ba7.setEnabled(true);
			ba8.setEnabled(true);
			ba1.setVisible(false);
			ba2.setVisible(false);
			ba5.setVisible(false);
			ba6.setVisible(false);
			
			joker1.setBounds(10, 40, 90, 40);
			joker2.setBounds(10,100, 90, 40);
			joker5.setBounds(350,40, 90, 40);
			joker6.setBounds(350,100, 90, 40);

			joker1.setVisible(true);
			joker2.setVisible(true);
			joker5.setVisible(true);
			joker6.setVisible(true);
			
			retourBtn.setVisible(true);
			
			panePrincipale.add(joker1);
			panePrincipale.add(joker2);
			panePrincipale.add(joker5);
			panePrincipale.add(joker6);
			joker1.setText("10 TND");
			joker2.setText("20 TND");
			ba3.setText("50 TND");
			ba4.setText("80 TND");
			joker5.setText("100 TND");
			joker6.setText("200 TND");
			ba7.setText("300 TND");
			ba8.setText("500 TND");			
		}
		
		
		if(var == retourBtn) {
			creditOn = false;
			debitOn = false;
			joker1.setVisible(false);
			joker2.setVisible(false);
			joker5.setVisible(false);
			joker6.setVisible(false);
			
			ba3.setText(">>>>>");
			ba4.setText(">>>>>");
			ba7.setText("<<<<<");
			ba8.setText("<<<<<");
			
			ba1.setVisible(true);
			ba2.setVisible(true);
			ba3.setVisible(true);
			ba4.setVisible(true);
			ba5.setVisible(true);
			ba6.setVisible(true);
			ba7.setVisible(true);
			ba8.setVisible(true);
			
			cancelBtn.setEnabled(true);
			
			ba1.setEnabled(true);
			ba2.setEnabled(true);
			ba3.setEnabled(false);
			ba4.setEnabled(false);
			ba5.setEnabled(true);
			ba6.setEnabled(false);
			ba7.setEnabled(false);
			ba8.setEnabled(false);
			
			retourBtn.setVisible(false);
			pinLabel.setVisible(false);
			tPin.setVisible(false);
		}
		 // ************************ evenements credit ******************************
		if(var == joker1) {
			if(creditOn) {
				crediter_action("10");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("10");
				retourBtn.setVisible(true);
				if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
				
			}
			
		}
		if(var == joker2) {
			if(creditOn) {
				crediter_action("20");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("20");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba3) {
			if(creditOn) {
				crediter_action("50");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("50");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba4) {
			if(creditOn) {
				crediter_action("80");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("80");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == joker5) {
			if(creditOn) {
				crediter_action("100");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("100");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == joker6) {
			if(creditOn) {
				crediter_action("200");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("200");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba7) {
			if(creditOn) {
				crediter_action("300");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("300");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
		if(var == ba8) {
			if(creditOn) {
				crediter_action("500");
	        	retourBtn.setVisible(true);
			}
			else {
				debiter_action("500");
	        	retourBtn.setVisible(true);
	        	if(debitDone) {
					pinLabel.setText("Take your money");
					pinLabel.setVisible(true);       	
		        	paneMoney.setVisible(true);
		        	debitDone = false;
				}
			}
		}
	}  
	
	
	private static void showMessage(JFrame parent, String message, ImageIcon icon) {
		Font msgLabel = new Font("Tahoma", Font.PLAIN, 18);
		int borderThickness = 3; 
		
        final JDialog errorDialog = new JDialog(parent, "Message", true);
        errorDialog.setLayout(null);

        JLabel MessageLabel = new JLabel(message);
        MessageLabel.setFont(msgLabel);
        MessageLabel.setBounds(100,10, 340, 30);
        errorDialog.add(MessageLabel);

        JButton okButton = new JButton("OK");
        okButton.setPreferredSize(new Dimension(80, 40));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                errorDialog.dispose(); 
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.setBounds(130, 130, 80, 50);
        errorDialog.add(buttonPanel);
        
        JLabel image = new JLabel();
        image.setIcon(icon);
        image.setBounds(140, 50, 70, 70);
        errorDialog.add(image);
        
        errorDialog.setSize(360, 240);
        errorDialog.setLocationRelativeTo(parent);

        errorDialog.setVisible(true);
    }

}


