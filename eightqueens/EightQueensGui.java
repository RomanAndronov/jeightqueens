package eightqueens;

/*
   By Roman Andronov
 */

import java.awt.Insets;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.RootPaneContainer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

class EightQueensGui
{
	EightQueensGui( EightQueensPanel pnl8qs )
	{
		pnl8Qs = pnl8qs;
	}

	void
	init( RootPaneContainer rpc )
	{
		GridBagConstraints	gbc = new GridBagConstraints();
		Insets			dfltInsts = gbc.insets;

		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.insets = INSETS;

		rpc.getContentPane().setLayout( new GridBagLayout() );

		pnl8Qs.setLayout( new GridBagLayout() );
		pnl8Qs.setBorder( BorderFactory.createLineBorder( CLRGRAY ) );
		pnl8Qs.setBackground( BOARD_CLR );
		rpc.getContentPane().add( pnl8Qs, gbc );

		pnl8Qs.pnlMain = new JPanel();
		pnl8Qs.pnlMain.setBorder( BorderFactory.createLineBorder( CLRGRAY ) );
		pnl8Qs.pnlMain.setLayout( new GridBagLayout() );
		pnl8Qs.pnlMain.setBackground( BOARD_CLR );
		pnl8Qs.add( pnl8Qs.pnlMain, gbc );

		pnl8Qs.pnlBoard = new JPanel();
		pnl8Qs.pnlBoard.setLayout( new GridBagLayout() );
		gbc.insets = dfltInsts;
		pnl8Qs.pnlMain.add( pnl8Qs.pnlBoard, gbc );
		gbc.insets = INSETS;
		mkBoardPnl();

		gbc.gridy = 1;
		gbc.weighty = 0.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnl8Qs.pnlCtrls = new JPanel();
		pnl8Qs.pnlCtrls.setBorder( BorderFactory.createLineBorder( CLRGRAY ) );
		pnl8Qs.pnlCtrls.setLayout( new GridBagLayout() );
		pnl8Qs.add( pnl8Qs.pnlCtrls, gbc );
		mkCtrlsPnl();
	}

	private void
	mkBoardPnl()
	{
		SquareView		sv = null;
		GridBagConstraints	gbc = new GridBagConstraints();

		pnl8Qs.pnlBoard.setBackground( BOARD_CLR );

		pnl8Qs.board =
			new Square[ EightQueensPanel.BOARD_SIZE ][ EightQueensPanel.BOARD_SIZE ];

		gbc.weightx = gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;

		for ( int r = 0; r < EightQueensPanel.BOARD_SIZE; r++ )
		{
			for ( int c = 0; c < EightQueensPanel.BOARD_SIZE; c++ )
			{
				sv = new SquareView( pnl8Qs );
				pnl8Qs.board[ r ][ c ] = new Square( r, c, sv );
				sv.setSquare( pnl8Qs.board[ r ][ c ] );
				gbc.gridx = c;
				gbc.gridy = r;
				pnl8Qs.pnlBoard.add( sv, gbc );
			}
		}
	}

	private void
	mkCtrlsPnl()
	{
		GridBagConstraints	gbc = new GridBagConstraints();

		gbc.gridx = gbc.gridy = 0;
		gbc.insets = INSETS;

		pnl8Qs.pnlCtrls.setBackground( BOARD_CLR );
		
		pnl8Qs.jbNewGame = new JButton( "New Game" );
		pnl8Qs.jbNewGame.setMnemonic( KeyEvent.VK_N );
		pnl8Qs.jbNewGame.addActionListener( pnl8Qs );
		pnl8Qs.pnlCtrls.add( pnl8Qs.jbNewGame, gbc );

		gbc.gridx = 1;
		pnl8Qs.lblQsPlaced = new JLabel( "Queens Placed: " );
		pnl8Qs.pnlCtrls.add( pnl8Qs.lblQsPlaced, gbc );

		gbc.gridx = 2;
		gbc.weightx = 1.0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		pnl8Qs.lblQsCount = new JLabel( "0" );
		pnl8Qs.pnlCtrls.add( pnl8Qs.lblQsCount, gbc );
	}


	EightQueensPanel			pnl8Qs;

	static final Insets			INSETS = new Insets( 5, 5, 5, 5 );

	static final Color			CLRGRAY = Color.GRAY;
	static final Color			CLRLGRAY = Color.LIGHT_GRAY;
	static final Color			BOARD_CLR = new Color( 197, 213, 203 );
}
