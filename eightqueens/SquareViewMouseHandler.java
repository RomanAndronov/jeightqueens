package eightqueens;

/*
  By Roman Andronov
 */

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputAdapter;

class SquareViewMouseHandler
	extends MouseInputAdapter
{
	public void
	mousePressed( MouseEvent me )
	{
		SquareView	sv = ( SquareView )me.getComponent();
		sv.mousePressed( me );
	}

	public void
	mouseReleased( MouseEvent me )
	{
		SquareView	sv = ( SquareView )me.getComponent();
		sv.mouseReleased( me );
	}

	public void
	mouseEntered( MouseEvent me )
	{
		SquareView	sv = ( SquareView )me.getComponent();
		sv.mouseEntered( me );
	}

	public void
	mouseExited( MouseEvent me )
	{
		SquareView	sv = ( SquareView )me.getComponent();
		sv.mouseExited( me );
	}
}
