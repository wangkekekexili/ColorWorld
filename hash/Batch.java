import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.Charset;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JSlider;
//import javax.swing.JButton;
//import javax.swing.event.ChangeEvent;
//import javax.swing.event.ChangeListener;
//import javax.swing.JFileChooser;
//import javax.swing.ImageIcon;
//import java.awt.event.ActionListener;

public class Batch //implements ChangeListener
{
    public static void readFile() {
        //String txtpath = "/Users/ex172000/src/ColorRecognition/result.txt";
        //InputStream    fis;
        String         line;
        for (int i = 1; i < 785; i+=1)
        {
            if ((i < 48) || (i > 53)) {
                
                String  thisLine = null;
                try{
                   // open input stream test.txt for reading purpose.
                   BufferedReader br = new BufferedReader(new FileReader(new File("/Users/ex172000/src/ColorRecognition/result.txt")));
                   while ((thisLine = br.readLine()) != null) {
                      System.out.println(thisLine);
                   }       
                }catch(Exception e){
                   e.printStackTrace();
                }
            }
        }          
    }
    public static void main(String[] args) throws IOException
    {
        //Program prog = new Program();
        //prog.analyzeImage();
        //int Max = 0;
        
        EnhancedColor red     = new EnhancedColor(255,   0,   0);                       
        EnhancedColor orange  = new EnhancedColor(255, 165,   0);            
        EnhancedColor yellow  = new EnhancedColor(255, 255,   0);                       
        EnhancedColor green   = new EnhancedColor(  0, 128,   0);            
        EnhancedColor aqua    = new EnhancedColor(  0, 255, 255);            
        EnhancedColor blue    = new EnhancedColor(  0,   0, 255);            
        EnhancedColor purple  = new EnhancedColor(128,   0, 128);                       
        EnhancedColor white   = new EnhancedColor(255, 255, 255);            
        EnhancedColor black   = new EnhancedColor(  0,   0,   0);            
        EnhancedColor grey    = new EnhancedColor(128, 128, 128);  


        String basepath = "/Users/ex172000/data/";
        for (int i = 1; i < 785; i+=1)
        {
            if ((i < 48) || (i > 53)) {
                String path = basepath + Integer.toString(i) + ".jpg";
                String outputpath = "data/" + Integer.toString(i) + ".jpg";
                System.out.println("\n"+outputpath);
                ColorRecognition rec = new ColorRecognition(path);
                try {
                    rec.smartAnalyze();
                    if      (!rec.getPrimaryColor().isDistinct(red   ,0)) System.out.println("0");
                    else if (!rec.getPrimaryColor().isDistinct(orange,0)) System.out.println("1");
                    else if (!rec.getPrimaryColor().isDistinct(yellow,0)) System.out.println("2");
                    else if (!rec.getPrimaryColor().isDistinct(green ,0)) System.out.println("3");
                    else if (!rec.getPrimaryColor().isDistinct(aqua  ,0)) System.out.println("4");
                    else if (!rec.getPrimaryColor().isDistinct(blue  ,0)) System.out.println("5");
                    else if (!rec.getPrimaryColor().isDistinct(purple,0)) System.out.println("6");
                    else if (!rec.getPrimaryColor().isDistinct(white ,0)) System.out.println("7");
                    else if (!rec.getPrimaryColor().isDistinct(black ,0)) System.out.println("8");
                    else if (!rec.getPrimaryColor().isDistinct(grey  ,0)) System.out.println("9");
                    else                                                  System.out.println("10");
                    System.out.println((int)rec.getPrimaryColorC()/1000+1);    

                    
                    if      (!rec.getSecondaryColor().isDistinct(red   ,0)) System.out.println("0");
                    else if (!rec.getSecondaryColor().isDistinct(orange,0)) System.out.println("1");
                    else if (!rec.getSecondaryColor().isDistinct(yellow,0)) System.out.println("2");
                    else if (!rec.getSecondaryColor().isDistinct(green ,0)) System.out.println("3");
                    else if (!rec.getSecondaryColor().isDistinct(aqua  ,0)) System.out.println("4");
                    else if (!rec.getSecondaryColor().isDistinct(blue  ,0)) System.out.println("5");
                    else if (!rec.getSecondaryColor().isDistinct(purple,0)) System.out.println("6");
                    else if (!rec.getSecondaryColor().isDistinct(white ,0)) System.out.println("7");
                    else if (!rec.getSecondaryColor().isDistinct(black ,0)) System.out.println("8");
                    else if (!rec.getSecondaryColor().isDistinct(grey  ,0)) System.out.println("9");
                    else                                                    System.out.println("10");
                    System.out.println((int)rec.getSecondaryColorC()/1000+1);
                    
                    if      (!rec.getDetailColor().isDistinct(red   ,0)) System.out.println("0");
                    else if (!rec.getDetailColor().isDistinct(orange,0)) System.out.println("1");
                    else if (!rec.getDetailColor().isDistinct(yellow,0)) System.out.println("2");
                    else if (!rec.getDetailColor().isDistinct(green ,0)) System.out.println("3");
                    else if (!rec.getDetailColor().isDistinct(aqua  ,0)) System.out.println("4");
                    else if (!rec.getDetailColor().isDistinct(blue  ,0)) System.out.println("5");
                    else if (!rec.getDetailColor().isDistinct(purple,0)) System.out.println("6");
                    else if (!rec.getDetailColor().isDistinct(white ,0)) System.out.println("7");
                    else if (!rec.getDetailColor().isDistinct(black ,0)) System.out.println("8");
                    else if (!rec.getDetailColor().isDistinct(grey  ,0)) System.out.println("9");
                    else                                                 System.out.println("10");
                    System.out.println((int)rec.getDetailColorC()/1000+1);
     
                    /*
                    if (rec.getPrimaryColorC() > Max) {
                        Max = rec.getPrimaryColorC();
                    }
                    System.out.println(Max);
                    */
                    
                    //System.out.println(rec.getPrimaryColor().isDistinct(red,0));
                    //System.out.println(rec.getPrimaryColor().isDistinct(red,0));
                    //System.out.println(red.isDistinct(rec.getPrimaryColor(),0));
                    //System.out.println(rec.getPrimaryColorC());
                    //System.out.println(rec.getSecondaryColor());
                    //System.out.println(rec.getSecondaryColorC());
                    //System.out.println(rec.getDetailColor());
                    //System.out.println(rec.getDetailColorC());
                } catch (Exception ex) { }
            }
        }
    }
    

    //JLabel image = new JLabel(), primary = new JLabel("Primary Color"), secondary = new JLabel("Secondary Color"), detail = new JLabel("Detailed Color");
    //JSlider distinctMin = new JSlider(JSlider.HORIZONTAL, 0, 255, 50);
    //JButton button = new JButton("Load Image");
    //String path;
    //ColorRecognition rec;
    /*
    public Program() throws IOException
    {
        super("Color Recognition");
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setSize(400, 400);
        super.setLayout(new FlowLayout());
       
        super.getContentPane().add(primary);
        super.getContentPane().add(secondary);
        super.getContentPane().add(detail);
        
        ActionListener listener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                final JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    path = fc.getSelectedFile().getAbsolutePath();
                    
                    rec = new ColorRecognition(path);
                    try
                    {
                        rec.smartAnalyze();
                        setComponentColorsAndProperties(true);
                    }
                    catch(Exception ex)
                    {
                    }
                }
            }
        };
        button.addActionListener(listener);
        super.getContentPane().add(button);
                
        //distinctMin.setMajorTickSpacing(10);
        //distinctMin.setMinorTickSpacing(1);
        //distinctMin.addChangeListener(this);
        //super.getContentPane().add(distinctMin);
        
        super.getContentPane().add(image); 
        
        super.setVisible(true);
    }*/
    
    /*
    public void stateChanged(ChangeEvent e)
    {
        JSlider source = (JSlider)e.getSource();
        int distinct = (int)source.getValue();
        rec.setDistinctMin(distinct);
        
        try
        {
            analyzeImage();
        }
        catch (IOException e1)
        {
            System.out.println("Failed to analyze image. Error: " + e1.getMessage());
        }
    }
    
    private void setComponentColorsAndProperties(boolean updateAll)
    {
        rec.setDistinctMin(10);
        if (updateAll)
        {
            ImageIcon imageIcon = new ImageIcon(path);
            image.setIcon(imageIcon);
    
            super.setSize(rec.getWidth() + 100, rec.getHeight() + 100);
        }
        
        //super.getContentPane().setBackground(rec.getBackgroundColor());
        super.getContentPane().setBackground(rec.getPrimaryColor());
        primary.setForeground(rec.getPrimaryColor());
        secondary.setForeground(rec.getSecondaryColor());
        detail.setForeground(rec.getDetailColor());
        
        //distinctMin.setBackground(rec.getBackgroundColor());
        //distinctMin.setValue(rec.getDistinctMin());
    }
    */
    /*
    public void analyzeImage() throws IOException
    {
        if (rec == null)
            return;
            
        rec.analyzeImage();
        setComponentColorsAndProperties(false);
    }*/
}
