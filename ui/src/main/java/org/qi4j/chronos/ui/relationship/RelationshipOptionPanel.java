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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.CustomerEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

//TODO bp. code can be simplified when Relationship is serializable.
public abstract class RelationshipOptionPanel extends Panel
{
    private SimpleDropDownChoice<String> relationshipChoice;
    private SubmitLink newRelationshipLink;

    private List<String> relationshipList;

    //TODO bp. remove static
    private static List<RelationshipComposite> addedRelationshipList;

    public RelationshipOptionPanel( String id )
    {
        super( id );

        addedRelationshipList = new ArrayList<RelationshipComposite>();

        initComponents();
    }

    private void initComponents()
    {
        initRelationshipList();

        relationshipChoice = new SimpleDropDownChoice<String>( "relationshipChoice", relationshipList, true );
        newRelationshipLink = new SubmitLink( "newLink" )
        {
            public void onSubmit()
            {
                RelationshipAddPage addPage = new RelationshipAddPage( (BasePage) this.getPage() )
                {
                    public CustomerEntityComposite getCustomer()
                    {
                        return RelationshipOptionPanel.this.getCustomer();
                    }

                    public void newRelationship( RelationshipComposite relationship )
                    {
                        RelationshipOptionPanel.this.addNewRelationship( relationship );
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

    private void addNewRelationship( RelationshipComposite relationshipComposite )
    {
        addedRelationshipList.add( relationshipComposite );
        relationshipList.add( relationshipComposite.getRelationship() );

        //set newly added relationship as default value
        relationshipChoice.setChoice( relationshipComposite.getRelationship() );

        relationshipChoice.setVisible( true );
    }

    private void initRelationshipList()
    {
        Set<String> relationshipSet = new HashSet();

        RelationshipService service = ChronosWebApp.getServices().getRelationshipService();

        CustomerEntityComposite customer = getCustomer();

        List<RelationshipComposite> list = service.findAll( customer );

        for( RelationshipComposite relationship : list )
        {
            relationshipSet.add( relationship.getRelationship() );
        }

        relationshipList = new ArrayList<String>();

        relationshipList.addAll( relationshipSet );
    }

    public boolean checkIfNotValidated()
    {
        if( relationshipList.size() == 0 )
        {
            error( "Relationship must not be empty! Please create one." );
            return true;
        }

        return false;
    }

    public void setSelectedRelationship( RelationshipComposite relationship )
    {
        relationshipChoice.setChoice( relationship.getRelationship() );
    }

    public RelationshipComposite getSelectedRelationship()
    {
        String choice = relationshipChoice.getChoiceAsString();

        for( RelationshipComposite relationship : addedRelationshipList )
        {
            if( choice.equals( relationship.getRelationship() ) )
            {
                return relationship;
            }
        }

        return ChronosWebApp.getServices().getRelationshipService().get( getCustomer(), choice );
    }

    public abstract CustomerEntityComposite getCustomer();
}
