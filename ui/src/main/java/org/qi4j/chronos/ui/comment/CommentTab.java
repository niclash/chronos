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

import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.associations.HasComments;
import org.qi4j.chronos.model.composites.CommentComposite;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;

public abstract class CommentTab extends NewLinkTab
{
    public CommentTab( String title )
    {
        super( title );
    }

    public NewLinkPanel getNewLinkPanel( String id )
    {
        return new CommentNewLinkPanel( id );
    }

    private class CommentNewLinkPanel extends NewLinkPanel
    {
        public CommentNewLinkPanel( String id )
        {
            super( id );
        }

        public Panel getContent( String id )
        {
            return new CommentTable( id )
            {
                public HasComments getHasComments()
                {
                    return CommentTab.this.getHasComments();
                }
            };
        }

        public void newLinkOnClick()
        {
            CommentAddPage addPage = new CommentAddPage( this.getPage() )
            {
                public void addComment( CommentComposite comment )
                {
                    CommentTab.this.addComment( comment );
                }
            };

            setResponsePage( addPage );
        }

        public String getNewLinkText()
        {
            return "New Comment";
        }
    }

    public abstract HasComments getHasComments();

    public abstract void addComment( CommentComposite comment );
}
