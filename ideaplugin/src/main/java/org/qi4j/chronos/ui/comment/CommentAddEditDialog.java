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
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.ui.common.AddEditDialog;
import org.qi4j.chronos.ui.common.text.JMaxLengthTextArea;
import org.qi4j.chronos.ui.util.UiUtil;

public abstract class CommentAddEditDialog extends AddEditDialog
{
    private JTextField createdDateField;
    private JTextField userField;

    private JMaxLengthTextArea commentTextArea;
    private JScrollPane commentScrollPanel;

    public CommentAddEditDialog()
    {
    }

    protected void initComponents()
    {
        createdDateField = new JTextField( "--" );

        userField = new JTextField();

        commentTextArea = new JMaxLengthTextArea( Comment.COMMENT_LEN );
        commentScrollPanel = UiUtil.createScrollPanel( commentTextArea );
    }

    protected String getLayoutColSpec()
    {
        return "right:p, 3dlu, 80dlu:grow, 3dlu, right:p, 3dlu, 80dlu:grow";
    }

    protected String getLayoutRowSpec()
    {
        return "p, 3dlu, 80dlu";
    }

    protected void initLayout( PanelBuilder builder, CellConstraints cc )
    {
        builder.add( new JLabel( "User" ), cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.add( new JLabel( "Created Date" ), cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.add( new JLabel( "Comment" ), cc.xy( 1, 3, "right,top" ) );
        builder.add( commentScrollPanel, cc.xyw( 3, 3, 5, "fill, fill" ) );
    }

    public void handleOkClicked()
    {
        //TODO
    }
}
