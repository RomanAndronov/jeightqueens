package eightqueens;

/*
   By Roman Andronov
 */

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

/*
   This will execute 8queens as a stand-alone
   Java program.

   To execute it as a Java applet consult the
   EightQueensApplet class in this package
*/

public
class EightQueensFrame
	extends JFrame
{
	public
	EightQueensFrame()
	{
		super();
		setTitle( "Eight Queens" );
		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
	}

	public static void
	main( String[] args )
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			public void
			run()
			{
				EightQueensFrame		eqsfrm = new EightQueensFrame();

				eqsfrm.eqspnl = new EightQueensPanel( eqsfrm );
				eqsfrm.pack();
				eqsfrm.setLocationRelativeTo( null );
				eqsfrm.setVisible( true );
			}
		});

	}

	private EightQueensPanel		eqspnl = null;
}
