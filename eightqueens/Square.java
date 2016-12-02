package eightqueens;

/*
  By Roman Andronov
 */

class Square
{
	Square( int row, int col, SquareView sv )
	{
		myRow = row;
		myColumn = col;
		myState = STATE_FREE;
		mySquareView = sv;
		numOfAttacks = 0;
	}

	int
	getRow()
	{
		return myRow;
	}

	int
	getColumn()
	{
		return myColumn;
	}

	SquareView
	getView()
	{
		return mySquareView;
	}

	int
	getState()
	{
		if ( numOfAttacks > 0 )
		{
			return Square.STATE_UNDER_ATTACK;
		}

		return myState;
	}

	void
	setState( int s )
	{
		myState = s;
		mySquareView.repaint();
	}

	int
	getNumberOfAttacks()
	{
		return numOfAttacks;
	}

	void
	addAttack()
	{
		numOfAttacks++;
		mySquareView.repaint();
	}

	void
	rmAttack()
	{
		if ( numOfAttacks == 0 )
		{
			return;
		}

		numOfAttacks--;
		if ( numOfAttacks == 0 )
		{
			myState = STATE_FREE;
			mySquareView.repaint();
		}
	}

	void
	clear()
	{
		myState = STATE_FREE;
		numOfAttacks = 0;
		mySquareView.setSquareColor( SquareView.SQUARE_CLR );
	}

	static final int		STATE_FREE = 0;
	static final int		STATE_UNDER_ATTACK = 1;
	static final int		STATE_HAS_QUEEN = 2;

	private final int		myRow;
	private final int		myColumn;
	private final SquareView	mySquareView;
	private int			myState; // Varies
	private int			numOfAttacks; // ditto
}
