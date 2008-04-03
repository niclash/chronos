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
package org.qi4j.chronos.ui.legalcondition;

import org.apache.wicket.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.common.NewLinkPanel;
import org.qi4j.chronos.ui.common.tab.NewLinkTab;
import org.qi4j.entity.association.SetAssociation;

public abstract class LegalConditionTab extends NewLinkTab
{
    public LegalConditionTab( String title )
    {
        super( title );
    }

    public NewLinkPanel getNewLinkPanel( String id )
    {
        return new LegalConditionNewLinkPanel( id );
    }

    private class LegalConditionNewLinkPanel extends NewLinkPanel
    {
        public LegalConditionNewLinkPanel( String id )
        {
            super( id );
        }

        protected void authorizingLink( Link link )
        {
            MetaDataRoleAuthorizationStrategy.authorize( link, RENDER, SystemRole.ACCOUNT_ADMIN );
        }

        public Panel getContent( String id )
        {
            return new LegalConditionTable( id )
            {
                public ProjectEntityComposite getProject()
                {
                    return LegalConditionTab.this.getProject();
                }
            };
        }

        public void newLinkOnClick()
        {
            LegalConditionAddPage addPage = new LegalConditionAddPage( this.getPage() )
            {
                public void addLegalCondition( LegalConditionComposite legalCondition )
                {
                    ProjectEntityComposite project = getProject();
                    SetAssociation<LegalConditionComposite> legalConditions = project.legalConditions();
                    legalConditions.add( legalCondition );

                    getServices().getProjectService().update( project );
                }
            };

            setResponsePage( addPage );
        }

        public String getNewLinkText()
        {
            return "New Legal Condition";
        }
    }

    public abstract ProjectEntityComposite getProject();
}
