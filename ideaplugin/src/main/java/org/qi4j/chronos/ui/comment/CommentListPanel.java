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
package org.qi4j.chronos.ui.comment;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import javax.swing.JButton;
import javax.swing.JTable;
import org.qi4j.chronos.ui.common.AbstractPanel;
import org.qi4j.chronos.ui.util.UiUtil;

public class CommentListPanel extends AbstractPanel
{
    private JButton newCommentButton;

    private JTable commentTable;

    public CommentListPanel()
    {
        init();
    }

    protected String getLayoutColSpec()
    {
        return "1dlu:grow, 3dlu, p";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, 1dlu:grow";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( newCommentButton, cc.xy( 3, 1, "right, center" ) );
        builder.add( UiUtil.createScrollPanel( commentTable ), cc.xyw( 1, 3, 3, "fill, fill" ) );
    }

    protected void initComponents()
    {
        newCommentButton = new JButton( "New Comment" );
        commentTable = new JTable();
    }
}
