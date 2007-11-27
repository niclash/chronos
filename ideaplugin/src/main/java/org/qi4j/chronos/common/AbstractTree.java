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
package org.qi4j.chronos.common;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public abstract class AbstractTree extends JTree
{
    public AbstractTree()
    {
        inits();
    }

    public AbstractTree( TreeNode treeNode )
    {
        super( treeNode );

        inits();
    }

    private void inits()
    {
        this.setOpaque( true );
        this.setBackground( Color.white );

        this.setRootVisible( false );
        this.setShowsRootHandles( true );

        initListenerOrAction();
    }

    private void initListenerOrAction()
    {
        //add tooltips support
        ToolTipManager.sharedInstance().registerComponent( this );

        this.addMouseListener( new MouseAdapter()
        {
            public void mousePressed( final MouseEvent e )
            {
                handleMousePressed( e );
            }
        } );

        this.getInputMap().put( KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ), "Enter" );
        this.getActionMap().put( "Enter", new AbstractAction()
        {
            public void actionPerformed( ActionEvent e )
            {
                onEnter();
            }
        } );
    }

    private void handleMousePressed( MouseEvent mouseEvent )
    {
        if( mouseEvent.getClickCount() == 2 )
        {
            handleDoubleClick( mouseEvent );
        }
        else if( mouseEvent.getClickCount() == 1 )
        {
            handleSingleClick( mouseEvent );
        }
    }

    private void handleDoubleClick( MouseEvent mouseEvent )
    {
        final TreePath treePath = getPathForLocation( mouseEvent.getX(), mouseEvent.getY() );

        TreePath path = getSelectionPath();

        if( path != null && path.equals( treePath ) )
        {
            onDoubleClick( path, path.getLastPathComponent(), mouseEvent );
        }
    }

    private void handleSingleClick( final MouseEvent mouseEvent )
    {
        final TreePath treePath = getPathForLocation( mouseEvent.getX(), mouseEvent.getY() );

        if( mouseEvent.getButton() == MouseEvent.BUTTON1 )
        {
            handleLeftSingleClick( mouseEvent );
        }
        else
        {
            if( !isPathSelected( treePath ) )
            {
                setSelectionPath( treePath );
            }
        }
    }

    private void handleLeftSingleClick( final MouseEvent mouseEvent )
    {
        final TreePath treePath = getPathForLocation( mouseEvent.getX(), mouseEvent.getY() );

        SwingUtilities.invokeLater( new Runnable()
        {
            public void run()
            {
                TreePath path = getSelectionPath();

                if( path != null && path.equals( treePath ) )
                {
                    onClick( path, path.getLastPathComponent(), mouseEvent );
                }
            }
        } );
    }

    protected void onEnter()
    {
        //override this if needed
    }

    protected void onDoubleClick( TreePath path, Object pathComponent, MouseEvent e )
    {
        onEnter();
    }

    protected void onClick( TreePath path, Object pathComponent, MouseEvent e )
    {
        //override this if needed
    }
}

