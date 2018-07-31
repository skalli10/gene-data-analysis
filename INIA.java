import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.Arrays.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.TitledBorder.*;

import java.awt.*;
import java.awt.event.*;

import java.io.*;
import java.lang.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import oracle.jdbc.driver.*;
import java.sql.*;
import javax.swing.*;
import java.util.StringTokenizer;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.activation.*;
import javax.mail.internet.*;
import javax.mail.MessagingException;

import java.security.*;
import javax.crypto.*;
import javax.crypto.Cipher;


import javax.swing.event.InternalFrameAdapter.*;
import javax.swing.event.InternalFrameListener.*;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameAdapter;


class INIA {

private static String query = null;
private static String result = null;
static final String PROPERTY_FILE = "config.ini";
static Connection conn;
static Properties configParam = new Properties();
static String sql = "";
static JPanel pane = new JPanel();

// PROCEDURE: main

public static void main(String args[] ) {


   try {

         readConfiguration(PROPERTY_FILE);
         connectToDB();


          Dimension d = new Dimension(700,700);
          Color labelColor = new Color(102,102,153);



          pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
          pane.setMaximumSize(new Dimension(d));
          pane.setPreferredSize(new Dimension(d));

          JPanel entryScreen = new JPanel();
          entryScreen.setLayout(new BoxLayout(entryScreen, BoxLayout.X_AXIS));

          // Pane for labels
          JPanel labelPane = new JPanel();
          labelPane.setLayout(new BoxLayout(labelPane, BoxLayout.Y_AXIS));

          // Pane for drop downs
          JPanel listPane = new JPanel();
          listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));

          final JLabel [] labelArray =
                       {new JLabel("Serial") ,
                        new JLabel("State") ,
                        new JLabel("City") ,
                        new JLabel("Country") ,
                        };

          for (int k = 0; k < labelArray.length ; k++)
              { labelPane.add(labelArray[k]);
                labelArray[k].setMaximumSize(new Dimension(200,22));
                labelArray[k].setPreferredSize(new Dimension(200,22));
                labelArray[k].setForeground(labelColor);
              } //for

          // Combo Boxes
          final JComboBox [] comboBoxArray =
                {new JComboBox(),
                 new JComboBox(),
                 new JComboBox(),
                 new JComboBox()};

          for (int k = 0; k < comboBoxArray.length ; k++)
              { listPane.add(comboBoxArray[k]);
                comboBoxArray[k].setEditable(true);
                comboBoxArray[k].setMaximumSize(new Dimension(350,22));
                comboBoxArray[k].setPreferredSize(new Dimension(350,22));
              } //for

          // Instruction Panel
          JPanel pInstr = new JPanel();

          String instr = "* The values that you enter are case sensitive." ;

          JLabel instructions = new JLabel(instr);
          instructions.setForeground(Color.blue);
          pInstr.add(instructions);
          pInstr.setMaximumSize(new Dimension(300,40));
          pInstr.setPreferredSize(new Dimension(300,40));

          // Button Panel
          JPanel pButton = new JPanel();
          pButton.setLayout(new BoxLayout(pButton, BoxLayout.X_AXIS));

          final JButton buttonSubmit = new JButton("Query");
          final JButton buttonDisplay = new JButton("Save");
          pButton.add(buttonSubmit);
          pButton.add(buttonDisplay);

          JButton [] buttonArray =
                        { buttonSubmit, buttonDisplay};
          for (int k = 0; k < buttonArray.length ; k++)
                { buttonArray[k].setForeground(labelColor);

                } //for

          entryScreen.add(labelPane);
          entryScreen.add(listPane);

          TitledBorder userTitle = BorderFactory.createTitledBorder(
                      BorderFactory.createLineBorder(Color.gray), " Select appropriate values from drop down lists. ",2,2);
          Border edge = BorderFactory.createEmptyBorder(10,10,10,10);
          userTitle.setTitleColor(labelColor);

          entryScreen.setBorder(BorderFactory.createCompoundBorder(userTitle,edge));
          entryScreen.setMaximumSize(new Dimension(600,500));
          entryScreen.setPreferredSize(new Dimension(600,500));

          pane.add(Box.createRigidArea(new Dimension(50,50)));
          pane.add(entryScreen);
          pane.add(Box.createRigidArea(new Dimension(50,10)));
          pane.add(pInstr);
          pane.add(pButton);

          JFrame frame = new JFrame();
          frame.setContentPane(pane);
          frame.pack();
          frame.setVisible(true);
          frame.setTitle("INIA");
          frame.setExtendedState(Frame.MAXIMIZED_BOTH);
          frame.addWindowListener(new WindowAdapter()
                 { public void windowClosing(WindowEvent e)
                   {   //conn.close();
                       System.exit(0);}
            });

         // buttonDisplay.addActionListener
          buttonDisplay.addActionListener(new ActionListener()
            {
             public void actionPerformed(ActionEvent e1)
                {
					 sql = "select * from a ";
                    boolean firstTag = true;

                    for (int k = 0; k < comboBoxArray.length ; k++)
                        { System.out.println(comboBoxArray.length);
							System.out.println(k);
                            String str = (String)comboBoxArray[k].getSelectedItem();
                            System.out.println("input: " + str);
                            if (! nullString(str) && ! str.trim().equals(""))
                             {  System.out.println("labelArray[k].getText():" + labelArray[k].getText());
                                 if (! firstTag) {sql = sql + " and ";}System.out.println("a");
                                 sql = "where " + sql +
                                         labelArray[k].getText() +
                                         "='" +
                                         str.trim() +
                                         "'";System.out.println("bbb");
                                 firstTag = false;
                              }

                          } //for



                    int count = 0;


                    JOptionPane option1 = new JOptionPane();
                    int n = option1.showConfirmDialog(
                       null,
                       "Number of chip runs:  " + count + "\n" +
                       "Display results ?",
                       "",
                       JOptionPane.YES_NO_OPTION,
                       JOptionPane.PLAIN_MESSAGE
                       );

                    if (n==0)
                      { createEditorPane() ; }
                } // actionPerformed
            }); // addActionListener



     // buttonSubmit.addActionListener

     buttonSubmit.addActionListener(new ActionListener()
        {
         public void actionPerformed(ActionEvent e1)
            { //createEditorPane() ;
              createResultsWindow();
            System.out.println("Test");
             } // actionPerformed
            }); // addActionListener

     }//try

     catch(Exception e){
       System.out.println(e);
       return;
     } // catch

  } // main


// **********************************************************************

    private static void createResultsWindow() {

try{
        JEditorPane resultsWindow = new JEditorPane();
        resultsWindow.setEditable(false);
        java.net.URL helpURL = INIA.class.getResource(
                                        "config.ini");
        if (helpURL != null) {
            try {
                resultsWindow.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: INIA.html");
        }

         JScrollPane editorScrollPane = new JScrollPane(resultsWindow);
         editorScrollPane.setVerticalScrollBarPolicy(
                           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         editorScrollPane.setPreferredSize(new Dimension(600, 600));
         editorScrollPane.setMinimumSize(new Dimension(600, 600));

         final JFrame frame = new JFrame();
         frame.setContentPane(editorScrollPane);
         frame.pack();
         frame.setVisible(true);
         frame.setTitle("Results");
         frame.setExtendedState(Frame.MAXIMIZED_BOTH);
         frame.addWindowListener(new WindowAdapter()
                { public void windowClosing(WindowEvent e)
                  {frame.dispose();}
           });

        System.out.println("sql" + sql);

		 Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
		                                        ResultSet.CONCUR_UPDATABLE);
		 ResultSet cursor1 = stmt1.executeQuery(sql);
}
catch ( SQLException ex ) {System.out.println ("exception" + ex.getMessage());
	                               //resultsWindow.setCursor(new Cursor(0));
	                                }

        //return resultsWindow;
    }

// **********************************************************************

    private static void createEditorPane() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        java.net.URL helpURL = INIA.class.getResource(
                                        "config.ini");
        if (helpURL != null) {
            try {
                editorPane.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: INIA.html");
        }

         JScrollPane editorScrollPane = new JScrollPane(editorPane);
         editorScrollPane.setVerticalScrollBarPolicy(
                           JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
         editorScrollPane.setPreferredSize(new Dimension(600, 600));
         editorScrollPane.setMinimumSize(new Dimension(600, 600));

         final JFrame frame = new JFrame();
         frame.setContentPane(editorScrollPane);
         frame.pack();
         frame.setVisible(true);
         frame.setTitle("Results");
         frame.setExtendedState(Frame.MAXIMIZED_BOTH);
         frame.addWindowListener(new WindowAdapter()
                { public void windowClosing(WindowEvent e)
                  {frame.dispose();}
           });

        //return editorPane;
    }



//*******************************************************************************
private static void readConfiguration (String propFile)
   {
      try
      {


         configParam.load(new FileInputStream(propFile));

      }
      catch(Exception ex)
	  		  		   	 	{
	  		  		   	 	   System.err.println("Exception in readConfiguration: " + ex.getMessage());
	  		  		   	 	   System.exit(1);
		         }
   }



//*******************************************************************************
  private static void connectToDB()
  {   String Url;

     try
	 	{
			System.out.println("key=[" + configParam.getProperty("KEY")+"]");
            System.out.println("pw encrypted=[" + configParam.getProperty("DBPASSWORD")+"]");

			String tempKey = configParam.getProperty("KEY") ;
			String tempPWD = configParam.getProperty("DBPASSWORD") ;



			Key key = PasswordUtil.decodeKey(tempKey) ;
			String pwd = PasswordUtil.decrypt(tempPWD,key);
            System.out.println("pw decrypted=[" + pwd +"]");

	 		   DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
	 		   System.out.println("Driver Registered");

	 		   Url = "jdbc:oracle:oci8:" + configParam.getProperty("DBUSER") + "/"
	 		                             + pwd + "@"
	 		                             + configParam.getProperty("INSTANCE") ;

	 		   conn = DriverManager.getConnection(Url);
	 		   System.out.println("Connected to " + Url);

	 	    } catch(Exception ex)
		   	 	{
		   	 	   System.err.println("Exception in connectToDB: " + ex.getMessage());
		   	 	   System.exit(1);
		         }

  }

//*******************************************************************************
private static boolean nullString(String str)
{
  if (str == null) {
    return true;
  } else {
    return false;
  }
}


//********************************************************************************
private void openWinAssocAttr()
{ int where = 0;
	try {

        //IssueAssoc winAssocAttr = new IssueAssoc();
        final Component windowGlassPane = window.getRootPane().getGlassPane();

        //final InternalWindow winAssocAttr = new InternalWindow("Associate Attributes","DISPOSE");
        final JInternalFrame winAssocAttr = new JInternalFrame("Associate Attributes", false,true, true, false);
        // title, resizability, closability, maximizability, and iconifiability.
        winAssocAttr.setVisible(true);
		winAssocAttr.moveToFront();
	    winAssocAttr.setIconifiable(false);
		winAssocAttr.setSelected(true);

	    Component gp = winAssocAttr.getRootPane().getGlassPane();

        JPanel p = createWinAssocAttr();
        winAssocAttr.getContentPane().add(p);
	 	window.setGlassPane(gp);
	 	window.getContentPane().add(winAssocAttr);
	    pane.setVisible(false);
	    window.getContentPane().getComponent(0).setVisible(false);

        winAssocAttr.addInternalFrameListener(new InternalFrameAdapter()
		        {public void internalFrameClosing(InternalFrameEvent e)
				   {  pane.setVisible(true);
				      winAssocAttr.dispose();
	                  window.getContentPane().getComponent(0).setVisible(true);
	                  window.setGlassPane(windowGlassPane);
	               }
			});

        winAssocAttr.addInternalFrameListener(new InternalFrameAdapter()
		        {public void internalFrameActivated(InternalFrameEvent e)
				   {
	               }
			});

		 }
		 	        catch (Exception exc)
		 	             { ;}
}

} //class