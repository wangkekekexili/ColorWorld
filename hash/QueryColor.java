import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.io.PrintWriter;
import java.io.Writer;

public class QueryColor 
{
	public static boolean readHelper(int index) {
        String baseString   = "/Users/ex172000/src/ColorRecognition/index/";
        String suffixString = ".txt";
        String indexsString = baseString + Integer.toString(index)+suffixString;
        //PrintWriter writer;
        //Writer output;
        boolean output = false;
        String  thisLine = null;
        try {
            //output = new BufferedWriter(new FileWriter(indexsString, true));
            //output.append  (infoString);
            //output.close();
            //writer  = new PrintWriter(new FileWriter(indexsString, true));
            //writer.println(infoString);
            //writer.close();
            BufferedReader br = new BufferedReader(new FileReader(new File(indexsString)));
            do {
        		thisLine = br.readLine();
        		
        		if (thisLine != null) {
        			System.out.println(thisLine);
        			output = !output;
        		}
            } while (thisLine != null);

        } catch(Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public static int colorHelper(String Color1) {
    	int ccolor1 = 0;
    	//System.out.println(Color1.length());
    	//String cccolor = Color1;
    	//System.out.println(Color1.equals("Green"));

    	//System.out.println((Color1));
   	    if      (Color1.equals("Red"    )) { ccolor1 = 0; } 
       	else if (Color1.equals("Orange" )) { ccolor1 = 1; } 
       	else if (Color1.equals("Yellow" )) { ccolor1 = 2; } 
       	else if (Color1.equals("Green"  )) { ccolor1 = 3; } 
       	else if (Color1.equals("Aqua"   )) { ccolor1 = 4; } 
        else if (Color1.equals("Blue"   )) { ccolor1 = 5; } 
       	else if (Color1.equals("Purple" )) { ccolor1 = 6; } 
       	else if (Color1.equals("White"  )) { ccolor1 = 7; } 
       	else if (Color1.equals("Black"  )) { ccolor1 = 8; } 
       	else if (Color1.equals("Grey"   )) { ccolor1 = 9; } 
        return ccolor1;
    }

    public static int levelHelper(char Level1) {
   	    int llevel1 = 0;
   	    if (Level1 == 'P') {
       		llevel1 = 0;
       		//lan = "a lot of";
       	} 
       	if (Level1 == 'S') {
       		llevel1 = 1;
       		//lan = "some";
       	} 
       	if (Level1 == 'D') {
       		llevel1 = 2;
       		//lan = "a bit of";
       	}
       	return llevel1;
    }

    public static String languageHelper(char Level1) {
    	String lan = null;
    	if (Level1 == 'P') {
       		//llevel1 = 0;
       		lan = "a lot of";
       	} 
       	if (Level1 == 'S') {
       		//llevel1 = 1;
       		lan = "some";
       	} 
       	if (Level1 == 'D') {
       		//llevel1 = 2;
       		lan = "a bit of";
       	}
       	return lan; 
    }

    public static int indexHelper(int c1, int l1, int c2, int l2, int c3, int l3) {
    	int index;
    	index = c1 * (int)Math.pow(10, (2-l1)) + c2 * (int)Math.pow(10, 2 - l2) + c3 * (int)Math.pow(10, 2 - l3);
    	//System.out.println(index);
    	return index;
    }

	public static void main(String[] args)
    {	
    	boolean tmp    = false;
    	boolean global = false;
        if (args.length == 1) {
        	int ccolor1 = 0;
        	String Color1 = args[0];
        	Color1 = Color1.replaceAll("(\\r|\\n)", "");
        	ccolor1 = colorHelper(Color1);
        	int c1 = ccolor1+1;
        	//System.out.println(args[0]);
        	System.out.println("You are requesting photos with at least some color of " + Color1+"!");
        	System.out.println("Primary: ");
        	for (int i = ((c1-1)*100); i < c1*100; i++) {
        		tmp = readHelper(i);
        		global = tmp || global;
        	}
        	System.out.println("Secondary: ");
        	for (int i = 0; i < 1000; i+=100) {
        		for (int j = ((c1-1)*10); j < c1*10; j++) {
        			//System.out.println(i+j);
        			tmp = readHelper(i+j);
        			global = tmp || global;
        		}
        	}
        	System.out.println("Detail: ");
        	for (int i = 0; i < 1000; i+=10)  {
        		tmp = readHelper(i+c1-1);
        		global = tmp || global;
        	}
        	if (global == false) {
        		System.out.println("Sorry, we have no photo meets your demand");
        	}
        	//readHelper(indexHelper(ccolor1, 1, 0, 0, 0, 0));
        	//readHelper(indexHelper(ccolor1, 2, 0, 0, 0, 0));
        }

        if (args.length == 2) {
        	int ccolor1 = 0;
            int llevel1 = 0;
            String Color1 = args[0];
            String Level  = args[1];
            char[] Levelc = Level.toCharArray();
            char Level1 = Levelc[0];
            String lan = null;
        	ccolor1 = colorHelper(Color1);
        	llevel1 = levelHelper(Level1);
        	lan =  languageHelper(Level1);
        	int c1 = ccolor1+1;
        	System.out.println("You are requesting photos with " + lan + " color " + Color1 + "!");
        	if (llevel1 == 0) {
        		for (int i = ((c1-1)*100); i < c1*100; i++) {
        			tmp = readHelper(i);
        			global = tmp || global;
        		}
        	}
            if (llevel1 == 1) {
            	for (int i = 0; i < 1000; i+=100) {
            		for (int j = ((c1-1)*10); j < c1*10; j++) {
            			//System.out.println(i+j);
            			tmp = readHelper(i+j);
            			global = tmp || global;
            		}
            	}
            }
        	if (llevel1 == 2) {
        		for (int i = 0; i < 1000; i+=10)  {
        			tmp = readHelper(i+c1-1);
        			global = tmp || global;
        		}
        	}
        	if (global == false) {
        		System.out.println("Sorry, we have no photo meets your demand");
        	}
        }
        if (args.length == 3) {
        	int ccolor1 = 0;
        	int ccolor2 = 0;
        	int ccolor3 = 0;
            //int llevel1 = 0;
            String Color1 = args[0];
            String Color2 = args[1];
            String Color3 = args[2];
            //System.out.println(Color1);
        	//System.out.println(Color2);
        	//System.out.println(Color3);
            //String Level  = args[1];
            //char[] Levelc = Level.toCharArray();
            //char Level1 = Levelc[0];
            //String lan = null;
        	ccolor1 = colorHelper(Color1);
        	//ccolor2 = colorHelper("Yellow");
        	ccolor2 = colorHelper(Color2);
        	ccolor3 = colorHelper(Color3);
        	//System.out.println(ccolor1);
        	//System.out.println(colorHelper("Yellow"));
        	//System.out.println(ccolor3);
        	//llevel1 = levelHelper(Level1);
        	//lan =  languageHelper(Level1);
        	int c1 = ccolor1+1;
        	int c2 = ccolor2+1;
        	int c3 = ccolor3+1;

        	System.out.println("You are requesting photos with colors of "+Color1+", "+ Color2+" and "+Color3+"!");
        	tmp = readHelper(indexHelper(ccolor1,0,ccolor2,1,ccolor3,2));
        	global = tmp || global;
        	tmp = readHelper(indexHelper(ccolor1,0,ccolor2,2,ccolor3,1));
        	global = tmp || global;
        	tmp = readHelper(indexHelper(ccolor1,1,ccolor2,0,ccolor3,2));
        	global = tmp || global;
        	tmp = readHelper(indexHelper(ccolor1,1,ccolor2,2,ccolor3,0));
        	global = tmp || global;
        	tmp = readHelper(indexHelper(ccolor1,2,ccolor2,0,ccolor3,1));
        	global = tmp || global;
        	tmp = readHelper(indexHelper(ccolor1,2,ccolor2,1,ccolor3,0));
        	global = tmp || global;
        	if (global == false) {
        		System.out.println("Sorry, we have no photo meets your demand");
        	}
        }
   	}
}