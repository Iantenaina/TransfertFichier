package client;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Client{

public static void main (String[] args) {

    final File[] fichierEnvoye = new File[1];

		   JFrame frame = new JFrame () ;
		     frame.setSize (450, 450);
		     frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		     
		   JLabel label = new JLabel("Choisir le fichier avant d envoyer");
		  
		   JButton envoyer = new JButton("Envoyer");
		   JButton choisir = new JButton("Choisir");
		   
		   JPanel panel = new JPanel();
		     panel.setBackground(Color.blue);
		     panel.add(envoyer);
		     panel.add(choisir); 
		     
     
     
		     choisir.addActionListener(new ActionListener()
		     {
		          public void actionPerformed(ActionEvent e)
		          {
		            JFileChooser fileChooser = new JFileChooser();
		            fileChooser.setDialogTitle("Choisir le fichier");
		
		            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		            {
		            	fichierEnvoye[0] = fileChooser.getSelectedFile(); 
		                 label.setText("le fichier que vous avez choisis est:" + fichierEnvoye[0].getName());
		            }
		         }
		    });
 
     
		     envoyer.addActionListener(new ActionListener()
		     {
		        public void actionPerformed(ActionEvent e)
		        {
		            if (fichierEnvoye[0] == null)
		            {
		            	label.setText("Choisir avant d envoyer");
		            } 
		            else
		            {
		                try
		                {
		                    FileInputStream fileInputStream = new FileInputStream(fichierEnvoye[0].getAbsolutePath());
		                    Socket socket = new Socket ("localhost", 5555);
		                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
		                    String nom = fichierEnvoye[0].getName();
		                    
		                    byte[] nomFichier = nom.getBytes();
		                    byte[] donnees = new byte[(int)fichierEnvoye[0].length()];
		                    fileInputStream.read(donnees);
		                    
		                    dataOutputStream.writeInt(nomFichier.length); 
		                    dataOutputStream.write(nomFichier);
		                    dataOutputStream.writeInt(donnees.length);
		                    dataOutputStream.write(donnees);
		                }
		                catch (IOException error)
		                {
		                    error.printStackTrace();
		                }
		            }
		        }
		    });

     frame.add(label);
     frame.add(panel);
     frame.setVisible(true);

    }
    
}
  