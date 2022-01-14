import javax.swing.JFrame;
import javax.swing.JPanel;

public class Picross extends JFrame{
			
		public static void main(String[] args){	
			Picross window = new Picross();
	        JPanel p = new JPanel();
	        p.add(new Board());
	        window.setTitle("Picross");
	        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        window.setContentPane(p);
	        
	        window.pack();
	        window.setLocationRelativeTo(null);
	        window.setVisible(true);
		}
}