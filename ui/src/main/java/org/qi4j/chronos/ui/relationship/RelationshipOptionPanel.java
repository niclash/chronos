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
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.qi4j.chronos.model.ContactPerson;
import org.qi4j.chronos.model.Customer;
import org.qi4j.chronos.model.Relationship;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.wicket.base.BasePage;
import org.qi4j.entity.Identity;
import org.qi4j.entity.UnitOfWork;

//TODO bp. code can be simplified when Relationship is serializable.
public abstract class RelationshipOptionPanel extends Panel
{
    private SimpleDropDownChoice<Relationship> relationshipChoice;
    private SubmitLink newRelationshipLink;
    private final List<Relationship> relationshipList = new ArrayList<Relationship>();
    private final List<Relationship> addedRelationshipList = new ArrayList<Relationship>();
    private final IChoiceRenderer relationshipChoiceRenderer = new RelationshipChoiceRenderer();

    public RelationshipOptionPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        initRelationshipList();

        relationshipChoice =
            new SimpleDropDownChoice<Relationship>( "relationshipChoice", relationshipList, relationshipChoiceRenderer );
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

                    public UnitOfWork getSharedUnitOfWork()
                    {
                        return RelationshipOptionPanel.this.getSharedUnitOfWork();
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

    private void addNewRelationship( Relationship relationship )
    {
        addedRelationshipList.add( relationship );
        relationshipList.add( relationship );

        //set newly added relationship as default value
        relationshipChoice.getModel().setObject( relationship );
        relationshipChoice.setVisible( true );
    }

    public List<Relationship> getRelationshipList()
    {
        return relationshipList;
    }
    
    private void initRelationshipList()
    {
        Set<Relationship> relationshipSet = new HashSet<Relationship>();
        Customer customer = RelationshipOptionPanel.this.getCustomer();
        for( ContactPerson contactPerson : customer.contactPersons() )
        {
            if( null != contactPerson.relationship().get() )
            {
                relationshipSet.add( contactPerson.relationship().get() );
            }
        }
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
        relationshipChoice.setChoice( relationship );
    }

    public Relationship getSelectedRelationship()
    {
        return relationshipChoice.getChoice();
    }

    private class RelationshipChoiceRenderer implements IChoiceRenderer
    {
        private static final long serialVersionUID = 1L;

        public Object getDisplayValue( Object object )
        {
            Relationship relationship = (Relationship) object;
            return relationship.relationship().get();
        }

        public String getIdValue( Object object, int index )
        {
            Identity identity = (Identity) object;
            return identity.identity().get();
        }
    }

    public abstract UnitOfWork getSharedUnitOfWork();

    public abstract Customer getCustomer();
}
