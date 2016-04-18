package GUI;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


/**
 * http://home.agh.edu.pl/~pernach/zajecia1.pdf
 * @author Mateusz Kaflowski
 *
 */
public class Main {
	
	public static void main(String[] args) {
		System.out.println("Created GUI on EDT? "+
                SwingUtilities.isEventDispatchThread());
        MyFrame f = new MyFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
	}

}
