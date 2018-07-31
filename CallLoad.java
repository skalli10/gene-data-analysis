import java.io.*;
import java.lang.*;

class CallLoad {

  // **************************** PROCEDURE: main ********************************

  public static void main (String args[]) {
    try
    {
        // Check if all parameters have been provided
        if (args.length != 3) {
            System.out.println("\nusage:  parameters??");
            System.exit(0);
            }

// CallLoad(experimentID pathToCELFiles DataFile)
// CallLoad  $i $folder $file
// CallLoad 1 TabakoffData/Affy_AC7_TGvsWT_C57_012_U74Av2 Sanjiv_10_9-24-03_MGU74Av2

System.out.println(args[0] + " # " + args[1] + " # " + args[2]);
System.out.println(args[0] + " # " + args[1]+"/" + " # " + args[2]+".CEL.Rdata" + " # "   + args[2]+".CEL.xml" );
System.out.println("\n");


LoadExperiment.load(
args[0] ,
args[1]+"/"+args[2]+".CEL.Rdata"  ,
args[1]+"/" ,
args[1]+"/"+args[2]+".CEL.xml");

// LoadExperiment.load(experimentID, binaryDataFile, pathToCELFiles, metaDataFile)


    } // try
    catch(Exception e) {
          e.printStackTrace();
    } // catch

  } //main

} //class