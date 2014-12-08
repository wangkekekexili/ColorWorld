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
//import java.Runtime;

public class CalcIsam
{   
    private static LinkedList<String> Path            = new LinkedList<String>();
    private static LinkedList<Integer> PrimaryColor   = new LinkedList<Integer>();
    private static LinkedList<Integer> SecondaryColor = new LinkedList<Integer>();
    private static LinkedList<Integer> DetailColor    = new LinkedList<Integer>();
    private static LinkedList<Integer>    PCCount     = new LinkedList<Integer>();
    private static LinkedList<Integer>    SCCount     = new LinkedList<Integer>();
    private static LinkedList<Integer>    DCCount     = new LinkedList<Integer>();


    public static void readFile() {
        int      i = 1;    
        String  thisLine = null;
        try{
           BufferedReader br = new BufferedReader(new FileReader(new File("/Users/ex172000/src/ColorRecognition/result.txt")));
            for (int p = 1; p < 10000; p+=1) {  
                if ((thisLine = br.readLine()) != null) {
                    if (thisLine.length() > 0) {
                        char[] thisLineC = thisLine.toCharArray();
                        if ((thisLineC[0] == 'd')) {
                            Path.add(thisLine);
                            for (int j = 0; j < 6; j+=1) {
                                thisLine = br.readLine();
                                if (thisLine.length() > 0) {
                                    if (j%6 == 0) {
                                        PrimaryColor.add(Integer.parseInt(thisLine));
                                    }
                                    if (j%6 == 1) {
                                        SecondaryColor.add(Integer.parseInt(thisLine));
                                    }
                                    if (j%6 == 2) {
                                        DetailColor.add(Integer.parseInt(thisLine));
                                    }
                                    if (j%6 == 3) {
                                        PCCount.add(Integer.parseInt(thisLine));
                                    }
                                    if (j%6 == 4) {
                                        SCCount.add(Integer.parseInt(thisLine));
                                    }
                                    if (j%6 == 5) {
                                        DCCount.add(Integer.parseInt(thisLine));
                                    }
                                } else if (j == 0) {
                                    PrimaryColor.add(10);
                                    PCCount.add(10);
                                    SecondaryColor.add(10);
                                    SCCount.add(10);
                                    DetailColor.add(10);
                                    DCCount.add(10);
                                    break;
                                } else if (j == 2) {
                                    SecondaryColor.add(10);
                                    SCCount.add(10);
                                    DetailColor.add(10);
                                    DCCount.add(10);
                                    break;
                                } else if (j == 4) {
                                    DetailColor.add(10);
                                    DCCount.add(10);
                                    break;
                                }
                            }
                        }
                    }
                }
            }      
        }catch(Exception e){
           e.printStackTrace();
        }          
    }

    public static void buildISAM(int totalsize) {
        
        for (int i  = 0; i < totalsize; i++) {
            int pc  = PrimaryColor.removeFirst();
            int sc  = SecondaryColor.removeFirst();
            int dc  = DetailColor.removeFirst();
            int pcc = PCCount.removeFirst();
            int scc = SCCount.removeFirst();
            int dcc = DCCount.removeFirst();
            String patth = Path.removeFirst();
            int info = pc + sc * 100 + dc * 10000 + pcc * 1000000 + scc * 10000000 + dcc * 100000000;
            String infoString = Integer.toString(info) + '$' + patth;
            if ((pc < 10) && (sc < 10) && (dc < 10)) {
                writeHelper((pc * 100 + sc * 10 + dc),infoString);
            }
        }
    }

    public static void writeHelper(int index, String infoString) {
        String baseString   = "/Users/ex172000/src/ColorRecognition/index/";
        String suffixString = ".txt";
        String indexsString = baseString + Integer.toString(index)+suffixString;

        //System.out.println(indexsString);
        //System.out.println(infoString);
        //PrintWriter writer;
        //Writer output;
        try {
            //output = new BufferedWriter(new FileWriter(indexsString, true));
            //output.append  (infoString);
            //output.close();
            PrintWriter writer;
            writer  = new PrintWriter(new FileWriter(indexsString, true));
            writer.println(infoString);
            writer.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        /*
        System.out.println(Path.peekLast());
        System.out.println(Path.size());
        System.out.println(PrimaryColor.size());
        System.out.println(SecondaryColor.size());
        System.out.println(DetailColor.size());
        System.out.println(DCCount.size());
        System.out.println(PCCount.size());
        System.out.println(SCCount.size());*/
        readFile();
        //Runtime.getRuntime().exec("cd   /Users/ex172000/src/ColorRecognition/index");
        //Runtime.getRuntime().exec("bash /Users/ex172000/src/ColorRecognition/index/rmfile.sh");
        //Runtime.getRuntime().exec("bash /Users/ex172000/src/ColorRecognition/index/createfile.sh");
        System.out.println("Index Building...");
        try{

            Thread.currentThread().sleep(10000);

        }
            catch(Exception e){ 
        }
        buildISAM(Path.size());
    }
    
}
