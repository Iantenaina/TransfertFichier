package serveur;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import fichier.Fichier;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Serveur{

    static ArrayList<Fichier> fichier = new ArrayList<>();
    
    public static void main (String[] args) throws IOException {

        int fileId = 0;

          JFrame frame = new JFrame();
	        frame.setSize( 400, 400);
	        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
         JPanel panel = new JPanel();
            panel.setBackground(Color.PINK);
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            
        JScrollPane scrollPane = new JScrollPane(panel);
        
        JLabel label = new JLabel("Cliquez sur le fichier si vou voulez l enregistrer");
        
        ServerSocket serverSocket = new ServerSocket( 5555);
           
	        frame.add(label);
	        frame.add(scrollPane);
	        frame.setVisible(true);
	        
	        
        while (true) 
        {
        	try{
                  Socket socket = serverSocket.accept();
                  DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                  int fichierN = dataInputStream.readInt();
                 if (fichierN > 0) 
                 {
                    byte[] NomFichier = new byte[fichierN];
                    dataInputStream.readFully(NomFichier,0, NomFichier.length);
                    String nom = new String(NomFichier);
                    int fichierL = dataInputStream.readInt();

                if (fichierL >0)
                {
                        byte[] fileContentBytes = new byte[fichierL];
                        dataInputStream.readFully(fileContentBytes, 0, fichierL);
                        
                        JPanel panel0 = new JPanel();
                            panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
                        JLabel labeel = new JLabel("Vous avez recu le fichier=======>  " + nom +
                        		"                                                          ");
                        JLabel labeell = new JLabel("Vous avez recu l image======>  " + nom+
                        		"                                                           ");
                        
                        if(Extension(nom).equalsIgnoreCase("JPG")) 
                        {
                        	panel0.setName(String.valueOf(fileId));
                        	panel0.addMouseListener(getMyMouseListener());
                        	panel0.add(labeell);
                            panel.add(panel0); 
                            frame.validate();
                            
                            
                        }
                        else
                        {
                        	panel0.setName(String.valueOf(fileId));
                        	panel0.addMouseListener(getMyMouseListener());
                        	panel0.add(labeel) ;
                            panel.add(panel0);
                            frame.validate();
                        }
                        fichier.add(new Fichier(fileId, nom,fileContentBytes, Extension(nom)));
                         fileId ++;
                }
                 }
        	}
        	catch (IOException error)
        	{
                error.printStackTrace();
            }
        }
        
    }

    
    public  static MouseListener getMyMouseListener() 
    {
    	return new MouseListener() 
    	{
            public void mouseClicked(MouseEvent e)
            {
            	JPanel panel = (JPanel) e.getSource();
            	int fileId= Integer.parseInt(panel.getName());
            	for(Fichier f: fichier)
            	{
                    if (f.getId() == fileId) 
                    {
                        JFrame frame = enregister(f.getNom(), f.getDonnee(), f.getExtension());
                        frame.setVisible(true);
                    }
                }
            } 


            public void mousePressed(MouseEvent e){}
            public void mouseReleased(MouseEvent e){}
            public void mouseEntered(MouseEvent e){}
            public void mouseExited(MouseEvent e){}
        }; 

    }

    public static JFrame enregister(String nom, byte[] fichier, String extension) {

        JFrame frame = new JFrame();
         frame.setSize(400, 400);
         
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        
        JLabel label = new JLabel();
        
        JLabel labell = new JLabel("Enregistrer le fichier" + nom );
        
        JButton buttonY = new JButton("Oui");
        
        JLabel lab = new JLabel();
        
        JPanel panelB = new JPanel();
	        panelB.add(buttonY);

        if(extension.equalsIgnoreCase("PNG"))
        {
        	lab.setText("<html>" + "</html>");
        }
        else
        {
        	lab.setIcon(new ImageIcon(fichier));
        }
        

        buttonY.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) 
            {
                File fileToDownload = new File(nom);

                try
                {
                     FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload);
                     fileOutputStream.write(fichier);
                     fileOutputStream.close();
                     frame.dispose();
                } 
                catch(IOException error)
                {
                    error.printStackTrace();
                }
            }
        });
        
        
        panel.add(label);
        panel.add(labell);
        panel.add(lab);
        panel.add(panelB);
        frame.add(panel);

        return frame;

    }

    public static String Extension(String fichier)
    {
        int compte= fichier.lastIndexOf('.');
        if (compte > 0) 
        {
            return  fichier.substring(compte + 1);
        } 
        else 
        {
            return "No extension found.";
        }
    }
}