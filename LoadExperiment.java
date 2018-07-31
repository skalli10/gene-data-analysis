///////////////////////////////////////////////////////////////////////////////////
// Class:                                                                        //
//       LoadExperiment                                                          //
// Input parameters:                                                             //
//       Experiment ID                                                           //
//       Binary Data File Name                                                   //
//       Path To Original CEL Files                                              //
//       Meta Data File Name                                                     //
// Returns:                                                                      //
//       void                                                                    //
// Descripion:                                                                   //
//       Reads binary data from a file and convert to XML-safe data              //
//       Reads MAGE-ML data from an xml file                                     //
//       Stores both in Neocore                                                  //
///////////////////////////////////////////////////////////////////////////////////

// MODIFICATION LOG
//---------------------------------------------------------------------------------
//     Name               Date       Version              Description
//---------------------------------------------------------------------------------
// Sajni Chowrira      04/15/2004                         Original
// Sajni Chowrira      04/21/2004                         Modified for 14 May demo
//---------------------------------------------------------------------------------

import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.lang.*;
import com.neocore.httpclient.*; // /stroma/NeoCore/API/Java/lib/neoHTTPConnection.jar

class LoadExperiment {

private static String server = "localhost";    // !!! CHANGE THIS TO THE NAME OR IP OF YOUR INSTANCE !!!
private static String user = "Administrator";
private static String password = "neocore";

// ******************** PROCEDURE: main **************************

  public static void load (String experimentID,
                           String binaryDataFile,
                           String pathToCELFiles,
                           String metaDataFile)
  {
    // Check if all parameters have been provided
    if ( (experimentID.trim().equals("")) ||
         (binaryDataFile.trim().equals("")) ||
         (pathToCELFiles.trim().equals("")) ||
         (metaDataFile.trim().equals("")) )
         {
           System.out.println("\nusage:  java loadExperiment ExperimentID BinaryDataFileName PathToOriginalCELFiles MetaDataFileName");
           System.exit(0);
         }

    System.out.println("\nProcessing experiment " + experimentID);

    String binaryData = encodeBinaryData(binaryDataFile,pathToCELFiles);
    String metaData = buildMetaData(metaDataFile);
    String experiment = buildExperiment(experimentID, binaryData, metaData);
    storeExperiment(experiment);
    System.out.println("\tExperiment '" + experimentID + "' has been processed.");

  } // main

// ******************** PROCEDURE: encodeBinaryData **************************
//
// Provides encoding of input string to base64-encoded characters
// that is XML-safe data. Calls the 'encode' function in class 'Base64'.
// Returns the zipped and encoded binary data enclosed in appropriate tags.
//
// ***************************************************************************

private static String encodeBinaryData(String fileName, String pathToCELFiles) {

    String xmlString = null;

    try {
           // Open the input file that contains the binary data
           File binaryFile = new File(fileName);
           FileInputStream binaryFileStream = new FileInputStream(binaryFile);

           // Read in the binary data
           byte[] raw = new byte[(int) binaryFile.length()];
           binaryFileStream.read(raw, 0, raw.length);
           binaryFileStream.close();

           // Encode the compressed data to base64 for XML-safe transport
           char[] encoded = Base64.encode(raw); // comment line if using zip utility
           String encodedString = new String(encoded);

           // Wrap encoded data in XML tags as needed
           xmlString =  "<Binary_data>"
                + "<Path_to_CEL_files>"
                + pathToCELFiles
                + "</Path_to_CEL_files>"
                + "<data>"
                + encodedString
                + "</data>"
                + "</Binary_data>";

        }
        catch (Exception e) {
               System.out.println("Exception in module encodeBinaryData: " + "\r\n");
               System.out.println(e);
               e.printStackTrace();
               System.exit(0);}

        return xmlString;

} // encodeBinaryData


// ******************** PROCEDURE: buildMetaData ************
//
// Returns the meta data enclosed in appropriate tags.
//
// **********************************************************

 private static String buildMetaData(String metaDataFile)   {

     String xmlString = null;

     try {
          File file = new File(metaDataFile);
          char[] xmlCharArray = readChars(file);
          xmlString = new String(xmlCharArray);
          xmlString = "<Meta_data>" + xmlString + "</Meta_data>" ;
         } // try
     catch (Exception e) {
            System.out.println("Exception in module buildMetaData: " + "\r\n");
            System.out.println(e);
            e.printStackTrace();
            System.exit(0);}

     return xmlString;

    } // buildMetaData


// ******************** PROCEDURE: buildExperiment ***********************
//
// Builds the entire 'Experiment' document and
// returns the same enclosed in appropriate tags.
//
// ***********************************************************************


 private static String buildExperiment(String experimentID,
                                       String binaryData,
                                       String metaData)   {
     String xmlString = null;

     try {
          xmlString = "<Phenotype_analysis>" +
                      "<Experiment>" +
                      "<Experiment_id>" + experimentID + "</Experiment_id>" +
                      binaryData +
                      metaData +
                      "</Experiment>" +
                      "</Phenotype_analysis>";
         }
     catch (Exception e) {
            System.out.println("Exception in module buildExperiment: " + "\r\n");
            System.out.println(e);
            e.printStackTrace();
            System.exit(0);}

     return xmlString;

    } // buildExperiment


// ******************** PROCEDURE: storeExperiment ***********************
//
// Stores the document provided as input in Neocore
//
// ***********************************************************************

 private static void storeExperiment(String xmlString) {

         try {
               SessionManagedNeoConnection neosession=null;
               String sid = null;

               // Get connection
               try{
                   neosession = new  SessionManagedNeoConnection(server, 7700);
                   // neosession will manage session id for us
                   System.out.println("Connection successful.");
               }
               catch(Exception e){
                 System.out.println("Exception in storeExperiment-1: " + "\r\n");
                 System.out.println(e);
                 e.printStackTrace();
                 System.exit(0);
               }

               // Login
               try{
                    sid = neosession.login(user, password);
                    System.out.println("Logon successful.");
               }
               catch(Exception e){
                 System.out.println("Exception in module storeExperiment-2: " + "\r\n");
                 System.out.println(e);
                 e.printStackTrace();
                 System.exit(0);
               }

               // Store document
               try{
                  String s = null;
                  System.out.println("Loading in process....");
                  s = neosession.storeXML(xmlString, null, null);
                  System.out.println("\t" + s);
                  System.out.println("Loading complete.");
               }
               catch(Exception e){
                     System.out.println("Exception in module storeExperiment-3: " + "\r\n");
                     System.out.println(e);
                     e.printStackTrace();
                     System.exit(0);
               }

               // Logout
               try{
                  neosession.logout();
                  System.out.println("Logout successful.");
               }
               catch(Exception e){
                  System.out.println("Exception in module storeExperiment-4: " + "\r\n");
                  System.out.println(e);
                  e.printStackTrace();
                  System.exit(0);
               }
          } // try
          catch (Exception e) {
                 System.out.println("Exception in module storeExperiment: " + "\r\n");
                 System.out.println(e);
                 e.printStackTrace();
                 System.exit(0);}

    } // storeExperiment


// ******************** PROCEDURE: readChars **************************
//
// Copied procdeure from Class: Base64
//
// ********************************************************************

    private static char[] readChars(File file)
    {
        CharArrayWriter caw = new CharArrayWriter();
        try
        {
            Reader fr = new FileReader(file);
            Reader in = new BufferedReader(fr);
            int count = 0;
            char[] buf = new char[16384];
            while ((count=in.read(buf)) != -1) {
                if (count > 0) caw.write(buf, 0, count);
            }
            in.close();
        }
        catch (Exception e) {
               System.out.println("Exception in module readChars: " + "\r\n");
               System.out.println(e);
               e.printStackTrace();
               System.exit(0);}

        return caw.toCharArray();
}

} //class