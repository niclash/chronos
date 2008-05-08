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
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.service.RelationshipService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

//TODO bp. code can be simplified when Relationship is serializable.
public abstract class RelationshipOptionPanel extends Panel
{
    private SimpleDropDownChoice<String> relationshipChoice;
    private SubmitLink newRelationshipLink;

    private List<String> relationshipList;

    private List<Relationship> addedRelationshipList;

    public RelationshipOptionPanel( String id )
    {
        super( id );

        addedRelationshipList = new ArrayList<Relationship>();

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
                    public Customer getCustomer()
                    {
                        return RelationshipOptionPanel.this.getCustomer();
                    }

                    public void newRelationship( Relationship relationship )
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

    public void bindModel( IModel iModel )
    {
        relationshipChoice.setModel( iModel );
    }

    private void addNewRelationship( Relationship relationshipComposite )
    {
        addedRelationshipList.add( relationshipComposite );
        relationshipList.add( relationshipComposite.relationship().get() );

        //set newly added relationship as default value
        relationshipChoice.setChoice( relationshipComposite.relationship().get() );

        relationshipChoice.setVisible( true );
    }

    private void initRelationshipList()
    {
        Set<String> relationshipSet = new HashSet();

//        RelationshipService service = ChronosWebApp.getServices().getRelationshipService();

        Customer customer = getCustomer();

//        customer.contactPersons().iterator().next().relationship().get()

//        List<Relationship> list = service.findAll( customer );
        for( ContactPerson contactPerson : customer.contactPersons() )
        {
            if( null != contactPerson.relationship().get() )
            {
                relationshipSet.add( contactPerson.relationship().get().relationship().get() );
            }
        }
/*
        for( Relationship relationship : list )
        {
            relationshipSet.add( relationship.relationship().get() );
        }
*/

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

    public void setSelectedRelationship( Relationship relationship )
    {
        relationshipChoice.setChoice( relationship.relationship().get() );
    }

    public Relationship getSelectedRelationship()
    {
        String choice = relationshipChoice.getChoiceAsString();

        for( Relationship relationship : addedRelationshipList )
        {
            if( choice.equals( relationship.relationship().get() ) )
            {
                return relationship;
            }
        }

//        return ChronosWebApp.getServices().getRelationshipService().get( getCustomer(), choice );
        for( ContactPerson contactPerson : getCustomer().contactPersons() )
        {
            if( choice.equals( contactPerson.relationship().get().relationship().get() ) )
            {
                return contactPerson.relationship().get();
            }
        }

        return null;
    }

    public abstract Customer getCustomer();
}
