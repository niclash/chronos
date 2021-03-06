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
package org.qi4j.chronos.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.qi4j.chronos.common.ChronosTable;
import org.qi4j.chronos.common.ChronosTableModel;

public final class UiUtil
{
    private final static Color TITLE_BG_COLOR2 = new Color( 214, 223, 247 );

    public static JScrollPane createScrollPanel( JComponent component )
    {
        return new JScrollPane( component, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
    }

    public static Object getUserObject( TreePath path )
    {
        Object value = path.getLastPathComponent();

        if( value instanceof DefaultMutableTreeNode )
        {
            return ( (DefaultMutableTreeNode) value ).getUserObject();
        }

        return value;
    }

    public static void addSelectAllTextOnFocus( final JTextComponent component )
    {
        component.addFocusListener( new FocusAdapter()
        {
            public void focusGained( FocusEvent e )
            {
                component.selectAll();
            }
        } );
    }

    public static void expandAll( JTree tree )
    {
        for( int i = 0; i < tree.getRowCount(); i++ )
        {
            tree.expandRow( i );
        }
    }

    public static void showErrorMsgDialog( String title, String msg )
    {
        JOptionPane.showMessageDialog( null, msg, title, JOptionPane.ERROR_MESSAGE );
    }

    public static void showMsgDialog( String title, String msg )
    {
        JOptionPane.showMessageDialog( null, msg, title, JOptionPane.INFORMATION_MESSAGE );
    }

    public static boolean showConfirmationDialog( String title, String question )
    {
        int result = JOptionPane.showOptionDialog( null, new JLabel( "<html><body>" + question.replaceAll( "\n", "<br>" ) + "</body></html>" ), title,
                                                   JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                   null, null, null );

        return result == JOptionPane.YES_OPTION;
    }

    public static void initTableWidth( JTable table, int[] colWiths )
    {
        if( table.getColumnModel().getColumnCount() != colWiths.length )
        {
            throw new IllegalArgumentException( "Number of Column widths doesn't matched!" );
        }

        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );

        for( int i = 0; i < colWiths.length; i++ )
        {
            TableColumn col = table.getColumnModel().getColumn( i );
            col.setPreferredWidth( colWiths[ i ] );
        }
    }

    public static ChronosTable createTable( ChronosTableModel tableModel, int[] columnWidths )
    {
        ChronosTable table = new ChronosTable( tableModel )
        {
            public Component prepareRenderer( TableCellRenderer renderer, int rowIndex, int vColIndex )
            {
                try
                {
                    Component component = super.prepareRenderer( renderer, rowIndex, vColIndex );

                    if( !isCellSelected( rowIndex, vColIndex ) )
                    {
                        if( rowIndex % 2 == 0 )
                        {
                            component.setBackground( getBackground() );
                        }
                        else
                        {
                            component.setBackground( TITLE_BG_COLOR2 );
                        }
                    }

                    return component;
                }
                catch( Exception e )
                {
                    //unlikely happen
                    return null;
                }
            }
        };

        table.setGridColor( table.getBackground() );

        initTableWidth( table, columnWidths );

        return table;
    }
}
