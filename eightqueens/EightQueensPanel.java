package eightqueens;

/*
  By Roman Andronov
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.JLabel;
import javax.swing.JButton;

class EightQueensPanel
	extends JPanel
	implements ActionListener
{
	EightQueensPanel( RootPaneContainer rpc )
	{
		super();

		SV_MH = new SquareViewMouseHandler();

		init( rpc );
	}

	public void
	actionPerformed( ActionEvent ae )
	{
		Object		o = ae.getSource();

		if ( o instanceof JButton )
		{
			JButton		jb = ( JButton )o;

			if ( jb == jbNewGame )
			{
				newGame();
			}
		}
	}

	void
	handleMove( int cmd, Square srcSqr )
	{
		Square			curSqr = null;
		SquareView		curSqrView = null;
		int			d = 0;
		int			N = 0;
		int			row = -1;
		int			col = -1;
		int			startRow = -1;
		int			startCol = -1;
		int			endRow = -1;
		int			endCol = -1;
		int			colsLeft = -1;
		int			rowsLeft = -1;
		int			srcRow = srcSqr.getRow();
		int			srcCol = srcSqr.getColumn();

		if ( cmd == CMD_ADD_QUEEN )
		{
			srcSqr.setState( Square.STATE_HAS_QUEEN );
			numOfQsPlaced++;
			lblQsCount.setText( "" + numOfQsPlaced );
		}
		else if ( cmd == CMD_RM_QUEEN )
		{
			srcSqr.setState( Square.STATE_FREE );
			numOfQsPlaced--;
			lblQsCount.setText( "" + numOfQsPlaced );
		}

		/*
		  All the squares in this column
		 */
		for ( int r = 0; r < BOARD_SIZE; r++ )
		{
			curSqrView = board[ r ][ srcCol ].getView();
			curSqr = curSqrView.getSquare();
			handleSquare( cmd, curSqr, curSqrView, srcSqr );
		}

		/*
		  All the squares in this row
		 */
		for ( int c = 0; c < BOARD_SIZE; c++ )
		{
			curSqrView = board[ srcRow ][ c ].getView();
			curSqr = curSqrView.getSquare();
			handleSquare( cmd, curSqr, curSqrView, srcSqr );
		}

		/*
		  All the squares on the North-West to South-East diagonal.
		  Since diagonals on a square grid are equally displaced
		  from the current square, one metric, "d", is enough. We
		  start from the North-Western corner by computing the
		  distance from the source row/column to the farthest
		  upper/left edges of the board
		 */
		d = srcRow < srcCol ? srcRow : srcCol; // The smaller of the two
		startRow = srcRow - d;
		startCol = srcCol - d;

		/*
		  South-Eastern corner. The rows/colsLeft variables
		  store the distance from the source row/column to the
		  farthest right/lower edges of the board
		 */
		rowsLeft = ( BOARD_SIZE - 1 ) - srcRow;
		colsLeft = ( BOARD_SIZE - 1 ) - srcCol;
		d = rowsLeft < colsLeft ? rowsLeft : colsLeft;
		endRow = srcRow + d;
		endCol = srcCol + d;

		/*
		  It does not matter which metric we use to
		  compute the number of diagonal steps since
		  the difference, below, will be the same
		 */
		N = endRow - startRow + 1;

		row = startRow;
		col = startCol;
		for ( int i = 0; i < N; i++ )
		{
			curSqrView = board[ row ][ col ].getView();
			curSqr = curSqrView.getSquare();
			handleSquare( cmd, curSqr, curSqrView, srcSqr );
			row++;
			col++;
		}

		/*
		  All the squares on the North-East to South-West diagonal.
		  We start from the North-Eastern corner of the board
		 */
		d = srcRow < colsLeft ? srcRow : colsLeft;
		startRow = srcRow - d;
		startCol = srcCol + d;

		/*
		  South-Western corner of the board
		 */
		d = srcCol < rowsLeft ? srcCol : rowsLeft;
		endRow = srcRow  + d;
		endCol = srcCol - d;

		N = endRow - startRow + 1;

		row = startRow;
		col = startCol;
		for ( int i = 0; i < N; i++ )
		{
			curSqrView = board[ row ][ col ].getView();
			curSqr = curSqrView.getSquare();
			handleSquare( cmd, curSqr, curSqrView, srcSqr );
			row++;
			col--;
		}

		if ( numOfQsPlaced == BOARD_SIZE )
		{
			gameOver = true;
		}
	}

	void
	handleSquare( int cmd, Square curSqr, SquareView curSqrView, Square srcSqr )
	{
		Color		clr = null;
		int		state = curSqr.getState();

		if ( cmd == CMD_ADD_QUEEN || cmd == CMD_RM_QUEEN )
		{
			if ( cmd == CMD_ADD_QUEEN )
			{
				if ( curSqr != srcSqr )
				{
					curSqr.addAttack();
				}
			}
			else // cmd == CMD_RM_QUEEN
			{
				if ( curSqr != srcSqr )
				{
					curSqr.rmAttack();
				}
			}
		}
		else // show or rm HiLites
		{
			if ( state == Square.STATE_FREE )
			{
				if ( cmd == CMD_SHOW_HILITE )
				{
					clr = SquareView.HI_LITE_CLR;
				}
				else // CMD_RM_HILITE
				{
					clr = SquareView.SQUARE_CLR;
				}
				curSqrView.setSquareColor( clr );
			}
		}
	}

	private void
	init( RootPaneContainer rpc )
	{
		gui = new EightQueensGui( this );

		gui.init( rpc );

		newGame();
	}

	private void
	newGame()
	{
		gameOver = false;
		numOfQsPlaced = 0;
		lblQsCount.setText( "0" );

		for ( int r = 0; r < BOARD_SIZE; r++ )
		{
			for ( int c = 0; c < BOARD_SIZE; c++ )
			{
				board[ r ][ c ].clear();
			}
		}

		repaint();
	}

	static final int		BOARD_SIZE = 8;
	static final int		CMD_SHOW_HILITE = 0;
	static final int		CMD_RM_HILITE = 1;
	static final int		CMD_ADD_QUEEN = 2;
	static final int		CMD_RM_QUEEN = 3;

	final SquareViewMouseHandler	SV_MH;

	EightQueensGui			gui = null;

	JPanel				pnlMain = null;
	JPanel				pnlBoard = null;

	JPanel				pnlCtrls = null;
	JButton				jbNewGame = null;
	JLabel				lblQsPlaced = null;
	JLabel				lblQsCount = null;

	Square[][]			board = null;
	Square				pressedSquare = null;
	int				numOfQsPlaced = 0;
	boolean				gameOver = true;
}
