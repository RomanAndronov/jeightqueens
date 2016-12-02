package eightqueens;

/*
   By Roman Andronov
*/

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

public
class EightQueensApplet
	extends JApplet
{
	public void
	init()
	{
		SwingUtilities.invokeLater( new Runnable()
		{
			public void
			run()
			{
				createAppletGui();
			}
		});
	}

	private void
	createAppletGui()
	{
		if ( eqspnl == null )
		{
			eqspnl = new EightQueensPanel( this );
		}
	}

	private EightQueensPanel		eqspnl = null;
}
