package Shader.tool;

import Shader.tool.Save;




import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.lucene.queryparser.classic.ParseException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.NoWorkTreeException;

import processing.app.Editor;




public class Wizard  {
	
    String searchin = "shader";
	JTextArea output;
	JList list;
	JTable table;
	String newline = "\n";
	String descrip = "Welcome to Shader Tool"; 
	ListSelectionModel listSelectionModel;
	//String[] listadata = {"hola1", "hola2", "hola3","hola4"};
	String[] listadata = null;
	//String[] prueba = {"0", "1", "2", "3"};
	String[] prueba = null;
	int cuentat = 0;
	String shaderse;
	String shadersename;
	JButton save;
	JButton search;
	JButton git;
	JButton upload;
	JButton update;
	Image rpta=null;
	
	final JLabel picLabel = new JLabel();
    final static String BUTTONPANEL = "Shader List";
    final static String TEXTPANEL = "Options";
    final static int extraWindowWidth = 100;
    final JPanel card1 = new JPanel();
    final JPanel card2 = new JPanel();
    String labelText;
    final JTextArea textarea = new JTextArea();
    JTextField searchtext;
    private Editor editor = null;
    static String OS = System.getProperty("os.name").toLowerCase();
    Path pathos;
    String[] searchid2 = null;
    String[] searchnames2 = null;
    String[] searchfolder2 = null;
    
    public Wizard(Editor editor) {
    	this.editor = editor;
    	
    	    	
    	// TODO Auto-generated constructor stub
	}


	public void addComponentToPane(Container pane) throws TransportException, GitAPIException, IOException, ParseException {
        JTabbedPane tabbedPane = new JTabbedPane();
        //OS Check
        
        //Prints OS
        //System.out.println(OS);
        
		if (isWindows()) {
			System.out.println("Windows");
			pathos= Paths.get(System.getProperty("user.home"),"Documents/Processing/tools/ShaderTool/tool/Shaderepo");
			 
		} else if (isMac()) {
			System.out.println("Mac iOS");
			pathos= Paths.get(System.getProperty("user.home"),"Documents/Processing/tools/ShaderTool/tool/Shaderepo");
		} else if (isUnix()) {
			System.out.println("Linux");
			pathos= Paths.get(System.getProperty("user.home"),"Documents/sketchbook/tools/ShaderTool/tool/Shaderepo");
		} else if (isSolaris()) {
			System.out.println("Solaris");
		} else {
			System.out.println("Your OS is not supported!!");
		}
		
		//Shaderepo data (Yes = Pull) (No = Clone) 
		
		if (Files.exists(pathos)) {
			System.out.println("Folder");
			
			
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Would do you like to update the repo data??", 
                    "Choose", 
                    JOptionPane.YES_NO_OPTION); 
			if (selectedOption == JOptionPane.YES_OPTION) {

				
				try {
					Pull pull = new Pull(pathos, editor);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransportException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoWorkTreeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
           	
				
				
				String searchin1 = "shader";
				Search search2 = new Search(searchid2, searchnames2, editor, pathos, searchfolder2, searchin1);
				listadata = search2.searchnames;
		        //prueba =  search2.searchid;
		        prueba =  search2.searchfolder;
				
			} else {
				//Pull pull = new Pull(null, pathos); (Falla Encabezado revisar como aconsejo grupo jgit Eclipse)
				
				
				
				
				String searchin1 = "shader";
				Search search2 = new Search(searchid2, searchnames2, editor, pathos, searchfolder2, searchin1);
				listadata = search2.searchnames;
		        //prueba =  search2.searchid;
		        prueba =  search2.searchfolder;
			}
			
			
		}else {
			
			//System.out.println("No Folder");
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Shader Tool needs to download all the repo data in order to continue", 
                    "Choose", 
                    JOptionPane.YES_NO_OPTION); 
			if (selectedOption == JOptionPane.YES_OPTION) {
            
				Clone clone = new Clone(null, pathos); //Clone Git
				Index index = new Index(pathos, null); //Index Lucene
				Search search1 = new Search(searchid2, searchnames2, editor, pathos, searchfolder2, searchin);
				
				//System.out.println(search.searchnames[2]);
		        //System.out.println(search.searchfolder[3]);
		        //System.out.println(search.searchid[4]);
		        
		        listadata = search1.searchnames;
		        prueba =  search1.searchfolder;
				
			}else {
				
			
			}
		}
        
        //Initial values
        
 
        
        String[] listData = listadata;
        list = new JList(listData);
 
        listSelectionModel = list.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new SharedListSelectionHandler());
        JScrollPane listPane = new JScrollPane(list);
 
        
 
        //Build output area.
        output = new JTextArea(1, 10);
        output.setEditable(false);
        JScrollPane outputPane = new JScrollPane(output,
                         ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                         ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
 
        //Do the layout.
        //JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JPanel splitPane = new JPanel ();
       // add(splitPane, BorderLayout.CENTER);
 
        JPanel topHalf = new JPanel();
        topHalf.setLayout(new BoxLayout(topHalf, BoxLayout.LINE_AXIS));
        JPanel listContainer = new JPanel(new GridLayout(1,1));
        listContainer.setBorder(BorderFactory.createTitledBorder(
                                                "Shader List"));
        listContainer.add(listPane);
         
        topHalf.setBorder(BorderFactory.createEmptyBorder(5,5,0,5));
        topHalf.add(listContainer);
        //topHalf.add(tableContainer);
 
        //topHalf.setMinimumSize(new Dimension(100, 50));
        topHalf.setPreferredSize(new Dimension(450, 150));
        splitPane.add(topHalf);
 
        JPanel bottomHalf = new JPanel(new BorderLayout());
        //JPanel buttons = new JPanel();
        
               
        //Cambio
        bottomHalf.add(outputPane, BorderLayout.CENTER);
        	//XXX: next line needed if bottomHalf is a scroll pane:
        	//bottomHalf.setMinimumSize(new Dimension(400, 50));
        bottomHalf.setPreferredSize(new Dimension(400, 135));
        //bottomHalf.add(save);
        //bottomHalf.add(search);
        splitPane.add(bottomHalf);
        
             
        //textarea.setBorder(new EmptyBorder(5, 5, 10, 5));
        textarea.setBackground(null);
        textarea.setEditable(false);
        //textarea.setHighlighter(null);
        textarea.setFont(new Font("Dialog", Font.PLAIN, 12));

        
        
        //Card layout.
       final JPanel card1 = new JPanel() {
            
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        };
        
        
        card1.add(splitPane);
        card1.add(picLabel);
        //textarea.append(descrip);
        card1.add(textarea);
        //card1.add(new JButton("Button 1"));
        save = new JButton("Load");
        card1.add(save); 
        save.setEnabled(false);
        upload = new JButton("Upload");
        
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           	
            	Save save = new Save(shaderse,editor,shadersename); 
            	System.out.println("Loading .....");
            	
            } 
           
        });
        
        searchtext = new JTextField("Search ie: blur, texture", 50);
        //card1.add(new JTextField("Search for a Shader", 50));
        card1.add(searchtext); 
        search = new JButton("Search");
        update = new JButton("Update");
        card1.add(upload);
        card1.add(update);
        
        //Upload
        
       upload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           	
            	try {
					Upload Upload = new Upload(editor, pathos);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransportException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
            	
            	
            } 
           
        });
        
        
       
       //UPDATE PULL
       
      update.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
          	
           	try {
					Pull pull = new Pull(pathos, editor);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransportException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GitAPIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoWorkTreeException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
           	
           	
           } 
          
       });
       
                
        //card1.add(search);    
        
        searchtext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           	String searchin = searchtext.getText(); 
           	Search search;
			try {
				search = new Search(searchid2, searchnames2, editor, pathos, searchfolder2, searchin);
				search.changeSearch(searchin);
						
				//String[] listadata1 = new String[search.searchnames];
		
		        prueba =  search.searchfolder;
		        listadata = search.searchnames;
		        list.setListData(listadata);
				
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
           
           // String[] inid = search.searchid;
           // String[] inname = search.searchnames;
                     
           // System.out.println(inname[0]); 
           // System.out.println(inid[0]); 
           // list.setListData(inname);
           // prueba = inid;
            } 
           
        });
        
        
        //card1.add(new JButton("Save"));
        
 
        //Menu 2 
        
        //card2.add(new JTextField("Search for a Shader", 50));
        //git = new JButton("GitPrueba");
        //card2.add(git);    
        //card2.add(new JButton(""));
        
        
      //git.addActionListener(new ActionListener() {
        //    public void actionPerformed(ActionEvent e) {
           	
            	
          //  	System.out.println("Prueba Git .....");
            	
            //} 
           
        //});
 
        tabbedPane.addTab(BUTTONPANEL, card1);
        tabbedPane.addTab(TEXTPANEL, card2);
 
        pane.add(tabbedPane, BorderLayout.CENTER);
    }//end component

	
    
    class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
 
            int firstIndex = e.getFirstIndex();
            int lastIndex = e.getLastIndex();
            //boolean isAdjusting = e.getValueIsAdjusting();
            //output.append("Event for indexes "
            //              + firstIndex + " - " + lastIndex
            //             + "; isAdjusting is " + isAdjusting
            //              + "; selected indexes:");
            
            //output.append("selected indexes:");
            output.append(descrip);
            
            
            if (lsm.isSelectionEmpty()) {
                output.append("");
            } else {
                // Find out which indexes are selected.
                int minIndex = lsm.getMinSelectionIndex();
                int maxIndex = lsm.getMaxSelectionIndex();
                for (int i = minIndex; i <= maxIndex; i++) {
                    if (lsm.isSelectedIndex(i)) {
                        output.append(" " + i);
                        
                                
                        shaderse = prueba[i];
                        shadersename = listadata[i];
                        
                        
                    }
                }
            }
            output.append(newline);
            output.setCaretPosition(output.getDocument().getLength());
            
            rpta = null;
            picLabel.setIcon(null);
            inforeq();        
            save.setEnabled(true);  
            ImageIcon one;      
           
            
            one = new ImageIcon(rpta);
            picLabel.setIcon(one);
            
            
           
          picLabel.setIcon( new ImageIcon(rpta));
          labelText = descrip;
           // labelText = "Hola";
          
           //card1.add(picLabel);
            
        }
    }//SharedList Listener

    


    
	 public void inforeq() {
		 
		 //Values IMG, Description, etc (TAGS)
		 try {
		        
			 
			 
		        //IMG JPG
		        rpta=null;
		       
		        
		        try 
		        {
		        	//BufferedImage image = ImageIO.read(new File(System.getProperty("user.home"),"Documents/Processing/tools/ShaderTool/tool/img.jpg")); 
		        	
		        	//BufferedImage image = ImageIO.read(new File(shaderse +"/"+"shadersename"+".img")); 
		        	String a = shaderse +"/"+shadersename+".jpg";
		        	BufferedImage image = ImageIO.read(new File(a));
		        	//System.out.println(a);
		        	
		        	
		        	BufferedImage resizedImage=resize(image,250,200);
		        	rpta= resizedImage; 
		        } 
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
		       
		   	    
		     
		        
		        //Description
		       	      		  
		        descrip=null;
		           
		        //String dir = new File(System.getProperty("user.home"),"Documents/Processing/tools/ShaderTool/tool/img.jpg").toString();        
		        String a = shaderse +"/"+shadersename+".txt";
		        
		        //File dir2 = new File(System.getProperty("user.home"),"Documents/Processing/tools/ShaderTool/tool/Prueba.txt");        
		        
		        File dir2 = new File(a);        
		        
		        
		        try {
		            
		        	BufferedReader br = new BufferedReader(new FileReader(dir2));
		        	StringBuilder sb = new StringBuilder();
	     	        String line = br.readLine();

               while (line != null) {
		                sb.append(line);
		                sb.append(System.lineSeparator());
		                line = br.readLine();
		            }
		            String everything = sb.toString();
		            //System.out.println(everything);
		            descrip = everything;
		        }
		        
		        catch (IOException e) 
		        {
		            e.printStackTrace();
		        }
		        
		     		  
		 
	        } catch(Exception e) {
	        System.out.println("Unexpected error: "+e.getMessage());
	    }
		 
		 
	 }//end inforeq
	 
	 public Editor editor() {
			return editor;
		}
	
	 
	 
	 
	 
	public static BufferedImage resize(BufferedImage image, int width, int height) {
		    BufferedImage bi = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
		    Graphics2D g2d = (Graphics2D) bi.createGraphics();
		    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
		    g2d.drawImage(image, 0, 0, width, height, null);
		    g2d.dispose();
		    return bi;
	}//Resize image
	 
	
	
	public static boolean isWindows() {
		 
		return (OS.indexOf("win") >= 0);
 
	}//end win
 
	public static boolean isMac() {
 
		return (OS.indexOf("mac") >= 0);
 
	}//end Mac
 
	public static boolean isUnix() {
 
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
 
	}//end Linux
 
	public static boolean isSolaris() {
 
		return (OS.indexOf("sunos") >= 0);
 
	}//end Solaris
}//end wizard