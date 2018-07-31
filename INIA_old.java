import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.Arrays.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.border.TitledBorder.*;

import java.awt.*;
import java.awt.event.*;

import com.neocore.httpclient.*;

class INIA {

private static String query = null;
private static String result = null;
private static SessionManagedNeoConnection thisSession = null;

// PROCEDURE: main

public static void main(String args[] ) {


   try {

          thisSession = connectToNeocore();

          Dimension d = new Dimension(700,700);
          Color labelColor = new Color(102,102,153);
          // Create pane with labels for User Login

          JPanel pane = new JPanel();
          pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
          pane.setMaximumSize(new Dimension(d));
          pane.setPreferredSize(new Dimension(d));

          JPanel pMetaData = new JPanel();
          pMetaData.setLayout(new BoxLayout(pMetaData, BoxLayout.X_AXIS));

          // Pane for labels
          JPanel p1 = new JPanel();
          p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));

          // Pane for drop downs
          JPanel p2 = new JPanel();
          p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));

          final String [] tagArray =
                      {new String("Chip_Description"),
                       new String("Date_of_experiment"),
                       new String("Phone"),
                       new String("E-mail"),
                       new String("Species"),
                       new String("Gender"),
                       new String("Age"),
                       new String("Genetic_background"),
                       new String("Organ"),
                       new String("Manufacturer"),
                       new String("Gene_Array_ID"),
                       new String("RNA_extraction_method"),
                       new String("Amont_of_RNA_used_in_microgram"),
                       new String("Total_or_mRNA"),
                       new String("cDNA_or_cRNA_preparation_methods_used"),
                       new String("Phenotype_data"),
                       new String("Electrophysiological_data"),
                       new String("Biochemical_data"),
                       new String("Physiological_data"),
                        };

          final JLabel [] labelArray =
                       {new JLabel("Chip Description") ,
                        new JLabel("Experiment Date") ,
                        new JLabel("Phone") ,
                        new JLabel("Email") ,
                        new JLabel("Species") ,
                        new JLabel("Gender") ,
                        new JLabel("Age") ,
                        new JLabel("Genetic Background") ,
                        new JLabel("Organ") ,
                        new JLabel("Manufacturer") ,
                        new JLabel("Gene Array ID"),
                        new JLabel("RNA Extraction Method"),
                        new JLabel("Amt of RNA used(micrograms)"),
                        new JLabel("Total or mRNA"),
                        new JLabel("cDNA/cRNA Preparation Methods"),
                        new JLabel("Phenotype Data"),
                        new JLabel("Electro-Physiological Data"),
                        new JLabel("Biochemical Data"),
                        new JLabel("Physiological Data")
                        };

          for (int k = 0; k < labelArray.length ; k++)
              { p1.add(labelArray[k]);
                labelArray[k].setMaximumSize(new Dimension(200,22));
                labelArray[k].setPreferredSize(new Dimension(200,22));
                labelArray[k].setForeground(labelColor);
              } //for

          // Combo Boxes
          final JComboBox [] comboBoxArray =
                {new JComboBox(createValues(tagArray[0])),
                 new JComboBox(createValues(tagArray[1])),
                 new JComboBox(createValues(tagArray[2])),
                 new JComboBox(createValues(tagArray[3])),
                 new JComboBox(createValues(tagArray[4])),
                 new JComboBox(createValues(tagArray[5])),
                 new JComboBox(createValues(tagArray[6])),
                 new JComboBox(createValues(tagArray[7])),
                 new JComboBox(createValues(tagArray[8])),
                 new JComboBox(createValues(tagArray[9])),
                 new JComboBox(createValues(tagArray[10])),
                 new JComboBox(createValues(tagArray[11])),
                 new JComboBox(createValues(tagArray[12])),
                 new JComboBox(createValues(tagArray[13])),
                 new JComboBox(createValues(tagArray[14])),
                 new JComboBox(createValues(tagArray[15])),
                 new JComboBox(createValues(tagArray[16])),
                 new JComboBox(createValues(tagArray[17])),
                 new JComboBox(createValues(tagArray[18]))};

          for (int k = 0; k < comboBoxArray.length ; k++)
              { p2.add(comboBoxArray[k]);
                comboBoxArray[k].setEditable(true);
                comboBoxArray[k].setMaximumSize(new Dimension(350,22));
                comboBoxArray[k].setPreferredSize(new Dimension(350,22));
              } //for

          // Instruction Panel
          JPanel pInstr = new JPanel();

          String instr = "* Please note that this form is case sensitive." ;

          JLabel instructions = new JLabel(instr);
          instructions.setForeground(Color.blue);
          pInstr.add(instructions);
          pInstr.setMaximumSize(new Dimension(300,40));
          pInstr.setPreferredSize(new Dimension(300,40));

          // Button Panel
          JPanel pButton = new JPanel();
          pButton.setLayout(new BoxLayout(pButton, BoxLayout.X_AXIS));

          final JButton buttonSubmit = new JButton("Submit");
          final JButton buttonDisplay = new JButton("Display results of last sbmitted query");
          pButton.add(buttonSubmit);
          pButton.add(buttonDisplay);

          JButton [] buttonArray =
                        { buttonSubmit, buttonDisplay};
          for (int k = 0; k < buttonArray.length ; k++)
                { buttonArray[k].setForeground(labelColor);

                } //for

          pMetaData.add(p1);
          pMetaData.add(p2);

          TitledBorder userTitle = BorderFactory.createTitledBorder(
                      BorderFactory.createLineBorder(Color.gray), " Meta Data Fields ",2,2);
          Border edge = BorderFactory.createEmptyBorder(10,10,10,10);
          userTitle.setTitleColor(labelColor);

          pMetaData.setBorder(BorderFactory.createCompoundBorder(userTitle,edge));
          pMetaData.setMaximumSize(new Dimension(600,500));
          pMetaData.setPreferredSize(new Dimension(600,500));

          pane.add(Box.createRigidArea(new Dimension(50,50)));
          pane.add(pMetaData);
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
                   {   logOutOfNeocore(thisSession);
                       File file = new File("result.xml");
                       //file.delete();
                       System.exit(0);}
            });

         // buttonSubmit.addActionListener
          buttonSubmit.addActionListener(new ActionListener()
            {
             public void actionPerformed(ActionEvent e1)
                {
                    query = "/ND/Phenotype_analysis/Experiment/Meta_data[";
                    boolean firstTag = true;

                    for (int k = 0; k < comboBoxArray.length ; k++)
                        {
                            String str = (String)comboBoxArray[k].getSelectedItem();
                            if (! str.trim().equals(""))
                             {
                                 if (! firstTag) {query = query + " and ";}
                                 query = query +
                                         tagArray[k] +
                                         "=" +
                                         "*\"" +
                                         str.trim() +
                                         "\"*";
                                 firstTag = false;
                              }

                          } //for
                    query = query + "]";
                    System.out.println(query);

                    int count = getCount(thisSession, query);
                    queryXML(thisSession, query, "result.xml");

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



     // buttonDisplay.addActionListener

     buttonDisplay.addActionListener(new ActionListener()
        {
         public void actionPerformed(ActionEvent e1)
            { createEditorPane() ;
             } // actionPerformed
            }); // addActionListener

     }//try

     catch(Exception e){
       System.out.println(e);
       return;
     } // catch

  } // main


// **********************************************************************

    private static void createEditorPane() {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        java.net.URL helpURL = INIA.class.getResource(
                                        "result.xml");
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


// **********************************************************************

    private static Object[] createValues(String field) {

    Vector values  = new Vector();
    Object[] obj = null;
    values.add(0,"");
    int i = 1;

     try {
        String fileName = field + ".xml" ;
        String query = "distinct-values(/ND/Phenotype_analysis/Experiment/Meta_data/" +
                            field + ")";
        queryXML(thisSession, query, fileName);

        int index;
        String line = null;
        StringTokenizer currentTokenizer = null;
        String currentToken = null;
        FileReader frInputFile = new FileReader(fileName);
        BufferedReader brInputFile = new BufferedReader (frInputFile);

        while (( line = brInputFile.readLine()) != null)
          {
              currentTokenizer = new StringTokenizer(line, ">");

              while (currentTokenizer.hasMoreTokens())
              {
                currentToken = currentTokenizer.nextToken() ;

                // Search for appropriate tag (field)
                if (currentToken.trim().equals("<"+field))
                {
                     currentToken = currentTokenizer.nextToken() ;
                     currentToken = currentToken.trim() ;
                     index = currentToken.indexOf("</" + field);
                     currentToken = currentToken.substring(0,index);

                     values.add(i,currentToken);
                     i++;

                     break;
                 } // if

              } //while

            } //while

        brInputFile.close();
        frInputFile.close();

        File file = new File(fileName);
        file.delete();

        obj = values.toArray();
        java.util.Arrays.sort(obj);



                  }catch(Exception e){
                    System.out.println( e);

                  }//catch
    return obj;
    }
// ******************** PROCEDURE: connectToNeocore **************************

 private static SessionManagedNeoConnection connectToNeocore() {

   SessionManagedNeoConnection neosession = null;
   try {

        // Get connection
        try{
            String server = "140.226.208.20";    // !!! CHANGE THIS TO THE NAME OR IP OF YOUR INSTANCE !!!
            neosession = new  SessionManagedNeoConnection(server, 7700);    // neosession will manage session id for us
            System.out.println("\nConnection successful.");
        }catch(Exception e){
          System.out.println("Cannot connect to NeoCore server:" + e);
        }

         // Login
         try{
              String sid = neosession.login("Administrator", "neocore");
              System.out.println("Logon successful.");
         }catch(Exception e){
           System.out.println("Cannot login to NeoCore server:" + e + "\r\n");
           System.out.println("Make sure NeoServer is running");
          }

    } // try
    catch (Exception e) { e.printStackTrace(); }

    return neosession;

    } // connectToNeocore


// ******************** PROCEDURE: logOutOfNeocore **************************

 private static void logOutOfNeocore(SessionManagedNeoConnection neosession)
 {
    try
    {
       neosession.logout();
       System.out.println("\nLogout successful.");
    } //try
    catch(Exception e)
    {
       System.out.println("Cannot log out of NeoCore server:" + e + "\r\n");
       e.printStackTrace();
    }
 } // logOutOfNeocore

// **********************************************************************

    private static void queryXML(SessionManagedNeoConnection neosession, String query, String fileName) {


        try {
        String result   = null;


     // Retrieve document into  a string
     try{
        result = neosession.queryXML(query);

     }catch(Exception e){
            System.out.println("NeoCore error: " + e);
     }

     // Store result in a file
     try {
         // Create file writer and buffered writer
         FileWriter fw5 = new FileWriter(fileName);
         BufferedWriter bw5 = new BufferedWriter (fw5);
         bw5.write(result);
         bw5.close();
         fw5.close();
     } catch(Exception e){
             System.out.println("Exception: " + e + " while writing to output file");
     }

     }catch(Exception e){
          System.out.println( e );
          }
    }//queryXML


// ******************** PROCEDURE: getCount **************************

 private static int getCount(SessionManagedNeoConnection neosession, String query)
 {
    int count = 0;
    String result = null;

    try
    {  result = neosession.queryCountXML(query, false);
       String fileName = "count.xml";

       // Store result in a file
       FileWriter fw5 = new FileWriter(fileName);
       BufferedWriter bw5 = new BufferedWriter (fw5);
       bw5.write(result);
       bw5.close();
       fw5.close();

        int index;
        String line = null;
        StringTokenizer currentTokenizer = null;
        String currentToken = null;
        FileReader frInputFile = new FileReader(fileName);
        BufferedReader brInputFile = new BufferedReader (frInputFile);

        while (( line = brInputFile.readLine()) != null)
          {
              currentTokenizer = new StringTokenizer(line, ">");

              while (currentTokenizer.hasMoreTokens())
              {
                currentToken = currentTokenizer.nextToken() ;

                // Search for appropriate tag (field)
                if (currentToken.trim().equals("<Count"))
                {
                     currentToken = currentTokenizer.nextToken() ;
                     currentToken = currentToken.trim() ;
                     index = currentToken.indexOf("</Count");
                     currentToken = currentToken.substring(0,index);

                     Integer num = Integer.valueOf(currentToken);
                     count = num.intValue();

                     break;
                 } // if

              } //while

            } //while

        brInputFile.close();
        frInputFile.close();

        File file = new File(fileName);
        file.delete();


    } //try
    catch(Exception e)
    {
       e.printStackTrace();
    }
    return count;
 } // logOutOfNeocore

} //class