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

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import java.util.List;
import org.qi4j.chronos.common.AbstractPanel;
import org.qi4j.chronos.common.ChronosTable;
import org.qi4j.chronos.common.ChronosTableModel;
import org.qi4j.chronos.util.DateUtil;
import org.qi4j.chronos.util.UiUtil;
import org.qi4j.chronos.model.Comment;

public class CommentListPanel extends AbstractPanel
{
    private final static String[] COL_NAMES = { "Created Date", "Comment", "Created by" };
    private final static int[] COL_WIDTHS = { 150, 300, 100 };

    private ChronosTable commentTable;

    public CommentListPanel()
    {
        init();
    }

    public void initData( List<Comment> comments )
    {
        for( Comment comment : comments )
        {
            insertComment( comment );
        }
    }

    private void insertComment( Comment comment )
    {
        commentTable.insertToLastRow(
            DateUtil.formatDateTime( comment.createdDate().get() ),
            comment.text().get(),
            comment.user().get().fullName().get()
        );
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
        builder.add( UiUtil.createScrollPanel( commentTable ), cc.xyw( 1, 3, 3, "fill, fill" ) );
    }

    protected void initComponents()
    {
        commentTable = UiUtil.createTable( new ChronosTableModel( COL_NAMES ), COL_WIDTHS );

    }
}
