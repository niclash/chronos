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
package org.qi4j.chronos.ui.util;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public final class SwingMiscUtil
{
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

}
