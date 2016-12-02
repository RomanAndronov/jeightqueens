package eightqueens;

/*
  By Roman Andronov
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

class SquareView
	extends JLabel
{
	SquareView( EightQueensPanel eqspnl )
	{
		pnl8Qs = eqspnl;

		addMouseListener( pnl8Qs.SV_MH );
		addMouseMotionListener( pnl8Qs.SV_MH );

		setMaximumSize( SQUARE_VIEW_DIM );
		setMinimumSize( SQUARE_VIEW_DIM );
		setPreferredSize( SQUARE_VIEW_DIM );

		setBorder( RAISED_BRDR );
		setOpaque( true );
		setBackground( SQUARE_CLR );
	}

	void
	setSquare( Square sq )
	{
		mySquare = sq;
	}

	Square
	getSquare()
	{
		return mySquare;
	}

	void
	mousePressed( MouseEvent me )
	{
		int		state = mySquare.getState();

		if ( pnl8Qs.gameOver == true ||
			state == Square.STATE_UNDER_ATTACK )
		{
			return;
		}

		pnl8Qs.pressedSquare = mySquare;
		setBorder( LOWERED_BRDR );
	}

	void
	mouseReleased( MouseEvent me )
	{
		Square		prsdSqr = pnl8Qs.pressedSquare;

		/*
		  The pressed square is visually restored
		  regardless of its current state
		 */
		pnl8Qs.pressedSquare = null;
		setBorder( RAISED_BRDR );

		/*
		  Make sure that the mouse event occurred
		  over the originating square

		  Point in the originating square's coordinates, the
		  one that originated the mouse press event
		 */
		Point		ocvp = me.getPoint();

		/*
		  Point in the Board's coordinates
		 */
		Point		brdp = SwingUtilities.convertPoint( this, ocvp, pnl8Qs.pnlBoard );

		/*
		  Over what component was the mouse released?
		 */
		Component	comp = SwingUtilities.getDeepestComponentAt( pnl8Qs.pnlBoard, brdp.x, brdp.y );
		if ( comp == null )
		{
			return;
		}

		if ( !( comp instanceof SquareView ) )
		{
			return;
		}

		/*
		  Destination square
		 */
		SquareView	destsv = ( SquareView )comp;

		/*
		  No op if releasing the mouse over the square view
		  that did not originate the mouse press event
		 */
		Square		destSqr = destsv.getSquare();
		if ( destSqr != prsdSqr )
		{
			return;
		}

		int		state = prsdSqr.getState();
		int		cmd =
			state == Square.STATE_FREE ?
				EightQueensPanel.CMD_ADD_QUEEN :
				EightQueensPanel.CMD_RM_QUEEN;

		pnl8Qs.handleMove( cmd, prsdSqr );
	}

	void
	mouseEntered( MouseEvent me )
	{
		int		state = mySquare.getState();

		if ( pnl8Qs.gameOver == true ||
			state == Square.STATE_HAS_QUEEN  ||
			state == Square.STATE_UNDER_ATTACK )
		{
			return;
		}

		pnl8Qs.handleMove( EightQueensPanel.CMD_SHOW_HILITE, mySquare );
	}

	void
	mouseExited( MouseEvent me )
	{
		int		state = mySquare.getState();

		if ( pnl8Qs.gameOver == true ||
			state == Square.STATE_HAS_QUEEN  ||
			state == Square.STATE_UNDER_ATTACK )
		{
			return;
		}

		pnl8Qs.handleMove( EightQueensPanel.CMD_RM_HILITE, mySquare );
	}

	void
	setSquareColor( Color cc )
	{
		setBackground( cc );
	}

	public void
	paintComponent( Graphics g )
	{
		super.paintComponent( g );

		int		state = mySquare.getState();

		if ( state == Square.STATE_FREE )
		{
			return;
		}

		setBackground( HI_LITE_CLR );

		if ( state == Square.STATE_HAS_QUEEN )
		{
			drawO( g );
		}
		else if ( state == Square.STATE_UNDER_ATTACK )
		{
			drawX( g );
		}
	}

	void
	drawX( Graphics g )
	{
		int		cvw = getWidth();
		int		cvh = getHeight();
		Insets		cvi = getInsets();

		int		x1 = cvi.left + IN_FACTOR_X * HL_OFFSET;
		int		y1 = cvi.top + IN_FACTOR_X * HL_OFFSET;
		int		x2 = x1 + cvw - cvi.left - cvi.right - 2 * IN_FACTOR_X * HL_OFFSET - 1;
		int		y2 = y1 + cvh - cvi.top - cvi.bottom - 2 * IN_FACTOR_X * HL_OFFSET - 1;
		int		t = y1;

		Graphics2D  g2d = ( Graphics2D )g.create();
		g2d.setColor( X_CLR );
		g2d.setStroke( BSC_STRK );
		g2d.drawLine( x1, y1, x2, y2 );

		y1 = y2;
		y2 = t;
		g2d.drawLine( x1, y1, x2, y2 );
		g2d.dispose();
	}

	public void
	drawO( Graphics g )
	{
		int		cvw = getWidth();
		int		cvh = getHeight();
		Insets		cvi = getInsets();

		int		x = cvi.left + IN_FACTOR_O * HL_OFFSET;
		int		y = cvi.top + IN_FACTOR_O * HL_OFFSET;
		int		w = cvw - cvi.left - cvi.right - 2 * IN_FACTOR_O * HL_OFFSET - 1;
		int		h = cvh - cvi.top - cvi.bottom - 2 * IN_FACTOR_O * HL_OFFSET - 1;

		Graphics2D	g2d = ( Graphics2D )g.create();

		g2d.setColor( O_CLR );
		g2d.fillOval( x, y, w, h );
		g2d.dispose();
	}

	static final int			IN_FACTOR_X = 5;
	static final int			IN_FACTOR_O = 3;
	static final int			HL_OFFSET = 4;
	static final float			XLINE_WIDTH = 2.0f;
	static final int			SQUARE_VIEW_SIZE = 50;
	static final Dimension			SQUARE_VIEW_DIM =
		new Dimension( SQUARE_VIEW_SIZE, SQUARE_VIEW_SIZE );
	static final BevelBorder		RAISED_BRDR = new BevelBorder( BevelBorder.RAISED );
	static final BevelBorder		LOWERED_BRDR = new BevelBorder( BevelBorder.LOWERED );
	static final Color			HI_LITE_CLR = new Color( 197, 213, 203 );
	static final Color			SQUARE_CLR = new Color( 227, 224, 207 );
	static final Color			X_CLR = new Color( 114, 120, 116 );
	static final Color			O_CLR = new Color( 114, 120, 116 );
	static final BasicStroke		BSC_STRK = new BasicStroke( XLINE_WIDTH );
	
	private final EightQueensPanel		pnl8Qs;
	private Square				mySquare;
}
