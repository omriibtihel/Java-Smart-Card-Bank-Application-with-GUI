# Java-Smart-Card-Bank-Application-with-GUI
This project combines a Java-based client application with a graphical user interface (GUI) and a JavaCard applet for managing bank account operations. The client application communicates with the JavaCard using the APDU protocol, facilitating operations such as connecting to the JavaCard, verifying PINs, querying balances, and performing credit and debit transactions. The GUI features a virtual keypad for input and action buttons for common operations, enhancing user interaction.

Key Components:

Client Application:
Provides a user-friendly interface for interacting with the JavaCard.
Implements GUI elements such as a virtual keypad and action buttons.
Utilizes the ClientFunction class to manage communication with the JavaCard.

JavaCard Applet (BankAdmin):
Manages bank account operations on the JavaCard.
Implements PIN verification, credit, debit, and balance inquiry functionalities.
Processes incoming APDU commands and routes them to appropriate methods.

Integration and Usage:
Setup:
Configure connection details and file paths in the client application and JavaCard applet.
Ensure access to a JavaCard simulator or a physical JavaCard device.

Compile and Run:
Compile the project using a Java IDE or build tool.
Run the client application to interact with the JavaCard through the GUI.

Operations:
Connect to the JavaCard, select the applet, and perform bank operations using APDU commands.
Use the GUI to input values, execute transactions, and view account balances.

Example Usage:
Connect to the JavaCard and select the applet.
Send APDU commands to perform credit, debit, or balance inquiry operations.
Process incoming commands on the JavaCard applet, verifying PINs and updating account balances accordingly.

This project provides a comprehensive solution for managing bank accounts securely using JavaCard technology. The combination of a user-friendly GUI and robust backend functionality ensures a seamless banking experience for users interacting with JavaCard-based systems.
