package serverpackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.OwnerPIN;
import javacard.framework.Util;

public class BankAdmin extends Applet {

	/* Constants */
	public static final byte CLA_MONAPPLET = (byte) 0xB0;
	public static final byte INS_TEST_CODE_PIN = 0x00;
	public static final byte INS_INTERROGER_COMPTE = 0x01;
	public static final byte INS_INCREMENTER_COMPTE = 0x02;
	public static final byte INS_DECREMENTER_COMPTE = 0x03;
	public static final byte INS_INITIALISER_COMPTE = 0x04;

	public final static short MAX_BALANCE = 0x2710;//maximum de la balance (10 000 TND)

	public final static byte MAX_MONTANT_TRANSACTION = (byte)500;// maximum montant qu'on peuttransiter 

	public final static byte MAX_ERROR_PIN = (byte) 0x03;// maximum de code pin erroner

	public final static byte MAX_PIN_LENGTH = (byte) 0x04;// longeur maximale du code pin

	
	private byte[] INIT_PIN = { (byte) 0, (byte) 0,(byte) 0,(byte) 0 };
	
    private static final String balancePath = "C:\\Users\\ibtih\\OneDrive\\Bureau\\ATM-MasterCard\\img\\balance.txt";

	
	
	final static short SW_VERIFICATION_FAILED = 0x6300;

	final static short SW_EXCEED_TRY_LIMIT = 0x6321;
	// signal the the PIN validation is required
	// for a credit or a debit transaction

	// signal that the balance exceed the maximum
	final static short SW_EXCEED_MAXIMUM_BALANCE = 0x2710;//32767
	// signal the the balance becomes negative
	final static short SW_NEGATIVE_BALANCE = 0x2710;


	/* variables */
	OwnerPIN pin;
	static short balance = readBalance(balancePath);

	private BankAdmin(byte[] bArray,int i,int j) {
		pin = new OwnerPIN(MAX_ERROR_PIN, MAX_PIN_LENGTH);

		// Initialization parametre pin
		pin.update(INIT_PIN,(short) 0, (byte) 0x04);
	    
	}

	public static void install(byte bArray[], short bOffset, byte bLength) throws ISOException {
		new BankAdmin(bArray,bOffset,bLength).register();
	}
	
	public boolean select() {

		// pas de selection si le pin est blocker
		if (pin.getTriesRemaining() == 0)
			return false;

		return true;
	}
	
	public void deselect() {
	    pin.reset();
	}

	public void process(APDU apdu) {
		
				byte[] buffer = apdu.getBuffer();

				// exception qui teste sur la commande de selection
				if (apdu.isISOInterindustryCLA()) {
					if (buffer[ISO7816.OFFSET_INS] == (byte) (0xA4)) {
						return;
					} else {
						ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
					}
				}

				// Vérifier si réinitialisation a une CLA correcte qui spécifie la
				// structure de commandement
				if (this.selectingApplet())
					return;
				if (buffer[ISO7816.OFFSET_CLA] != CLA_MONAPPLET) {
					ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);
				}

				switch (buffer[ISO7816.OFFSET_INS]) {
				case INS_TEST_CODE_PIN:
					verify(apdu);
					break;
				case INS_INCREMENTER_COMPTE:
					credit(apdu);
					break;
				case INS_DECREMENTER_COMPTE:
					debit(apdu);
					break;
				case INS_INTERROGER_COMPTE:
					getBalance(apdu);
					break;

				default:
					ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
				}

			}
	

	
	private void credit(APDU apdu) {
	    byte[] buffer = apdu.getBuffer();

	    // Lc byte denotes the number of bytes in the
	    short numBytes = (short) (buffer[ISO7816.OFFSET_LC] & 0xFF);

	    short bytesRead = apdu.setIncomingAndReceive();

	    // it is an error if the number of data bytes
	    // read does not match the number in Lc byte
	    if (numBytes != bytesRead) {
	        ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
	    }


	    short creditAmount = 0;
	    for (short i = 0; i < bytesRead; i++) {
	    	creditAmount = (short) ((creditAmount << 8) | (buffer[ISO7816.OFFSET_CDATA + i] & 0xFF));
	    }

	    // check the new balance
	    if ((short) (balance + creditAmount) > MAX_BALANCE) {
	        ISOException.throwIt(SW_EXCEED_MAXIMUM_BALANCE);
	    }

	    // credit the amount
	    balance = (short) (balance + creditAmount);
	}

	
	private void debit(APDU apdu) {


		byte[] buffer = apdu.getBuffer();

		short numBytes = (short) (buffer[ISO7816.OFFSET_LC] & 0xFF);

		short bytesRead = apdu.setIncomingAndReceive();

		if (numBytes != bytesRead) {
	        ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);
	    }

		// get debit amount
		short debitAmount = 0;
	    for (short i = 0; i < bytesRead; i++) {
	    	debitAmount = (short) ((debitAmount << 8) | (buffer[ISO7816.OFFSET_CDATA + i] & 0xFF));
	    }

		// check the new balance
		if ((short) (balance - debitAmount) < (short) -500)
			ISOException.throwIt(SW_NEGATIVE_BALANCE);

		balance = (short) (balance - debitAmount);		
	}
	
	
	private void getBalance(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		
		short le = apdu.setOutgoing();

		if (le < 2)
			ISOException.throwIt(ISO7816.SW_WRONG_LENGTH);

		apdu.setOutgoingLength((byte) 2);
		
		buffer[0] = (byte) (balance >> 8);
		buffer[1] = (byte) (balance & 0xFF);
		
		//Util.setShort(buffer, (short)0, balance);
		
		apdu.sendBytes((short) 0, (short) 2);	
	}
	
	private void verify(APDU apdu) {
		byte[] buffer = apdu.getBuffer();
		// retrieve the PIN data for validation.
		byte byteRead = (byte) (apdu.setIncomingAndReceive());
		if(pin.getTriesRemaining() <= (byte) 1) {
			if (pin.check(buffer, ISO7816.OFFSET_CDATA, byteRead) == false) {
				ISOException.throwIt(SW_EXCEED_TRY_LIMIT);
			}			
		}			
		if (pin.check(buffer, ISO7816.OFFSET_CDATA, byteRead) == false)
			ISOException.throwIt(SW_VERIFICATION_FAILED);	
	}
	
	    public static short  readBalance(String balancePath) {

		        try {
		            File file = new File(balancePath);

		            FileReader readFile = new FileReader(file);

		            BufferedReader readBuffer = new BufferedReader(readFile);

		            String line = readBuffer.readLine();

		            if (line != null) {
		                short nombre = Short.parseShort(line);
		                return nombre;
		            } else {
		                System.out.println("Le fichier est vide.");
		            }

		            // Fermez le BufferedReader
		            readBuffer.close();

		        } catch (IOException e) {
		            e.printStackTrace();
		        } catch (NumberFormatException e) {
		            System.err.println("Le fichier ne contient pas un nombre valide.");
		      }
				return -1;
	    }
}






