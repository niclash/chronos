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
package org.qi4j.chronos.comment;

import com.intellij.openapi.project.Project;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import org.qi4j.chronos.common.AddEditDialog;
import org.qi4j.chronos.common.text.JMaxLengthTextArea;
import org.qi4j.chronos.common.text.ReadOnlyTextField;
import org.qi4j.chronos.model.Comment;
import org.qi4j.chronos.model.User;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;

public abstract class CommentAddEditDialog extends AddEditDialog
{
    private ReadOnlyTextField createdDateField;
    private ReadOnlyTextField userField;

    private JMaxLengthTextArea commentTextArea;

    public CommentAddEditDialog( Project project )
    {
        super( project );
    }

    protected void initComponents()
    {
        createdDateField = new ReadOnlyTextField( "--" );

        userField = new ReadOnlyTextField( getCreatedBy().fullName().get() );

        commentTextArea = new JMaxLengthTextArea( Comment.COMMENT_LEN );
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
        builder.addLabel( "Added By", cc.xy( 1, 1 ) );
        builder.add( userField, cc.xy( 3, 1 ) );

        builder.addLabel( "Created Date", cc.xy( 5, 1 ) );
        builder.add( createdDateField, cc.xy( 7, 1 ) );

        builder.addLabel( "Comment", cc.xy( 1, 3, "right,top" ) );
        builder.add( UiUtil.createScrollPanel( commentTextArea ), cc.xyw( 3, 3, 5, "fill, fill" ) );
    }

    public void assignFieldValueToComment( Comment comment )
    {
        comment.text().set( commentTextArea.getText() );
        comment.user().set( getCreatedBy() );
    }

    protected void assignCommentToFieldValue( Comment comment )
    {
        createdDateField.setText( DateUtil.formatDateTime( comment.createdDate().get() ) );
        userField.setText( comment.user().get().fullName().get() );
        commentTextArea.setText( comment.text().get() );
    }

    public abstract User getCreatedBy();
}
