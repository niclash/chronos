/*
 * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.ui.common;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.qi4j.chronos.ui.util.UiUtil;

public class ChronosPageableTable<T> extends AbstractPanel
{
    private final static int DEFAULT_ITEM_PER_PAGE = 50;

    private JButton backButton;
    private JButton nextButton;

    private JButton lastButton;
    private JButton firstButton;

    private JLabel resultInfoLabel;

    private ChronosTable table;

    private int itemPerPage;

    private int totalPages;
    private int currPage = 1;

    private String[] colNames;
    private int[] colWidths;

    private ChronosDataProvider<T> dataProvider;

    private int totalItems;

    private List<T> dataList;

    public ChronosPageableTable( ChronosDataProvider<T> dataProvider, String[] colNames, int[] colWidths )
    {
        this( dataProvider, colNames, colWidths, DEFAULT_ITEM_PER_PAGE );
    }

    public ChronosPageableTable( ChronosDataProvider<T> dataProvider, String[] colNames, int[] colWidths, int itemPerpage )
    {
        this.dataProvider = dataProvider;
        this.colNames = colNames;
        this.colWidths = colWidths;
        this.totalItems = dataProvider.getSize();
        this.itemPerPage = itemPerpage;

        init();

        initData();
    }

    private void initData()
    {
        if( totalItems != 0 )
        {
            //calculate total pages
            totalPages = totalItems / itemPerPage;

            //plus one if it has remainder.
            if( ( totalItems % itemPerPage ) != 0 )
            {
                totalPages++;
            }
        }

        if( totalItems != 0 )
        {
            updateNavigatorButtons();
        }
    }

    protected String getLayoutColSpec()
    {
        return "1dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "1dlu:grow, 3dlu, p";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( UiUtil.createScrollPanel( table ), cc.xy( 1, 1, "fill,fill" ) );
        builder.add( new NavigatorPanel(), cc.xy( 1, 3, "fill,fill" ) );
    }

    private class NavigatorPanel extends AbstractPanel
    {
        public NavigatorPanel()
        {
            init();
        }

        protected String getLayoutColSpec()
        {
            return "1dlu:grow, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 1dlu:grow";
        }

        protected String getLayoutRowSpec()
        {
            return "p";
        }

        protected void initLayout( PanelBuilder builder, CellConstraints cc )
        {
            builder.add( firstButton, cc.xy( 2, 1 ) );
            builder.add( backButton, cc.xy( 4, 1 ) );
            builder.add( resultInfoLabel, cc.xy( 6, 1 ) );
            builder.add( nextButton, cc.xy( 8, 1 ) );
            builder.add( lastButton, cc.xy( 10, 1 ) );
        }

        protected void initComponents()
        {
            //nothing
        }
    }

    protected void initComponents()
    {
        backButton = new JButton( "<" );
        nextButton = new JButton( ">" );
        lastButton = new JButton( ">>" );
        firstButton = new JButton( "<<" );

        resultInfoLabel = new JLabel();

        table = UiUtil.createTable( new ChronosTableModel( colNames ), colWidths );

        initListeners();
    }

    private void initListeners()
    {
        backButton.addActionListener( newIncrementActionListener( -1 ) );
        nextButton.addActionListener( newIncrementActionListener( 1 ) );

        lastButton.addActionListener( newNavigationActionListener( -1 ) );
        firstButton.addActionListener( newNavigationActionListener( 1 ) );

        table.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                //only interested left double click
                if( e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 )
                {
                    handleTableDoubleClick();
                }
            }
        } );
    }

    private void handleTableDoubleClick()
    {
        int row = table.getSelectedRow();

        if( row != -1 )
        {
            rowOnDoubleClick( dataList.get( row ) );
        }
    }

    protected void rowOnDoubleClick( T t )
    {
        //override this if you need this event
    }

    private ActionListener newNavigationActionListener( final int pageNum )
    {
        return new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                currPage = pageNum == -1 ? totalPages : pageNum;

                updateNavigatorButtons();
            }
        };
    }

    private ActionListener newIncrementActionListener( final int increment )
    {
        return new ActionListener()
        {
            public void actionPerformed( ActionEvent e )
            {
                currPage = currPage + increment;

                updateNavigatorButtons();
            }
        };
    }

    private void updateNavigatorButtons()
    {
        lastButton.setEnabled( false );
        firstButton.setEnabled( false );
        nextButton.setEnabled( false );
        backButton.setEnabled( false );

        if( currPage != 1 )
        {
            backButton.setEnabled( true );
            firstButton.setEnabled( true );
        }

        if( currPage != totalPages )
        {
            nextButton.setEnabled( true );
            lastButton.setEnabled( true );
        }

        int from;
        int to;

        if( currPage == 1 )
        {
            from = 0;

            if( currPage == totalPages )
            {
                to = totalItems;
            }
            else
            {
                to = itemPerPage;
            }
        }
        else
        {
            from = ( currPage - 1 ) * itemPerPage;

            if( currPage == totalPages )
            {
                to = totalItems;
            }
            else
            {
                to = currPage * itemPerPage;
            }
        }

        refreshTable( from, to );
    }

    private void refreshTable( int from, int to )
    {
        //update table model
        table.setModel( new ChronosTableModel( colNames ) );
        UiUtil.initTableWidth( table, colWidths );

        setResultInfo( "Showing: " + ( from + 1 ) + " - " + to + " of " + totalItems );

        if( dataProvider.getSize() != 0 )
        {
            int count = itemPerPage;

            if( from + count > totalItems )
            {
                count = totalItems - from;
            }

            List<T> list = dataProvider.getData( from, count );

            //keep it for later reference
            dataList = list;

            for( T t : list )
            {
                Object[] objs = dataProvider.populateData( t );

                table.insertToLastRow( objs );
            }
        }
    }

    private void setResultInfo( String resultInfo )
    {
        resultInfoLabel.setText( "<html><i>" + resultInfo + "</i></html>" );
    }
}
