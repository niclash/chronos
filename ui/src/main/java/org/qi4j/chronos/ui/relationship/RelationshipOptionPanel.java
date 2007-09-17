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
package org.qi4j.chronos.ui.relationship;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.ProjectOwnerEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.SimpleLink;

//TODO bp. code can be simplified when Relationship is serializable.
public abstract class RelationshipOptionPanel extends Panel
{
    private SimpleDropDownChoice<String> relationshipChoice;
    private SimpleLink newRelationshipLink;

    private List<String> relationshipList;

    //TODO bp. remove static
    private static List<RelationshipComposite> newRelationshipList;

    public RelationshipOptionPanel( String id )
    {
        super( id );

        newRelationshipList = new ArrayList<RelationshipComposite>();

        initComponents();
    }

    private void initComponents()
    {
        initRelationshipList();

        relationshipChoice = new SimpleDropDownChoice<String>( "relationshipChoice", relationshipList, true );
        newRelationshipLink = new SimpleLink( "newLink", "Create New" )
        {
            public void linkClicked()
            {
                RelationshipAddPage addPage = new RelationshipAddPage( (BasePage) this.getPage() )
                {
                    public void newRelationship( RelationshipComposite relationship )
                    {
                        RelationshipOptionPanel.this.addedNewRelationship( relationship );
                    }
                };

                setResponsePage( addPage );
            }
        };

        if( relationshipList.size() == 0 )
        {
            relationshipChoice.setVisible( false );
        }

        add( relationshipChoice );
        add( newRelationshipLink );
    }

    private void addedNewRelationship( RelationshipComposite relationshipComposite )
    {
        newRelationshipList.add( relationshipComposite );
        relationshipList.add( relationshipComposite.getRelationship() );

        relationshipChoice.setVisible( true );
    }

    private List<String> initRelationshipList()
    {
        relationshipList = new ArrayList<String>();

        RelationshipService service = ChronosWebApp.getServices().getRelationshipService();

        ProjectOwnerEntityComposite projectOwner = getProjectOwner();

        List<RelationshipComposite> list = service.findAll( projectOwner );

        for( RelationshipComposite relationship : list )
        {
            relationshipList.add( relationship.getRelationship() );
        }

        return relationshipList;
    }

    public boolean checkIfNotValidated()
    {
        return relationshipList.size() == 0 ? true : false;
    }

    public void setSelectedRelationship( RelationshipComposite relationship )
    {
        relationshipChoice.setChoice( relationship.getRelationship() );
    }

    public RelationshipComposite getSelectedRelationship()
    {
        String choice = relationshipChoice.getChoiceAsString();

        for( RelationshipComposite relationship : newRelationshipList )
        {
            if( choice.equals( relationship.getRelationship() ) )
            {
                return relationship;
            }
        }

        return ChronosWebApp.getServices().getRelationshipService().get( getProjectOwner(), choice );
    }

    public abstract ProjectOwnerEntityComposite getProjectOwner();
}
