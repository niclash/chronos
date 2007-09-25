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
package org.qi4j.chronos.ui.pricerate;

import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.model.composites.RelationshipComposite;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;
import org.qi4j.chronos.ui.common.SimpleLink;

public abstract class PriceRateOptionPanel extends Panel
{
    private SimpleDropDownChoice<PriceRateDelegator> priceRateChoice;
    private SimpleLink newPriceRateLink;

    private List<PriceRateDelegator> priceRateList;

    //TODO bp. remove static
    private static List<PriceRateComposite> newPriceRateList;

    public PriceRateOptionPanel( String id )
    {
        super( id );

        newPriceRateList = new ArrayList<PriceRateComposite>();

        initComponents();
    }

    private void initComponents()
    {
        initPriceRateList();

        priceRateChoice = new SimpleDropDownChoice<PriceRateDelegator>( "priceRateChoice", priceRateList, true );
        newPriceRateLink = new SimpleLink( "newLink", "Create New" )
        {
            public void linkClicked()
            {
//                RelationshipAddPage addPage = new RelationshipAddPage( (BasePage) this.getPage() )
//                {
//                    public ProjectOwnerEntityComposite getProjectOwner()
//                    {
//                        return RelationshipOptionPanel.this.getProjectOwner();
//                    }
//
//                    public void newRelationship( RelationshipComposite relationship )
//                    {
//                        RelationshipOptionPanel.this.addedNewRelationship( relationship );
//                    }
//                };
//
//                setResponsePage( addPage );
            }
        };

        if( priceRateList.size() == 0 )
        {
            priceRateChoice.setVisible( false );
        }

        add( priceRateChoice );
        add( newPriceRateLink );
    }

    private void addNewPriceRate( PriceRateComposite priceRateComposite )
    {
        newPriceRateList.add( priceRateComposite );
        priceRateList.add( new PriceRateDelegator( priceRateComposite ) );

        priceRateChoice.setVisible( true );
    }

    private void initPriceRateList()
    {
//        Set<String> relationshipSet = new HashSet();
//
//        RelationshipService service = ChronosWebApp.getServices().getRelationshipService();
//
//        ProjectOwnerEntityComposite projectOwner = getProjectOwner();
//
//        List<RelationshipComposite> list = service.findAll( projectOwner );
//
//        for( RelationshipComposite relationship : list )
//        {
//            relationshipSet.add( relationship.getRelationship() );
//        }
//
//        relationshipList = new ArrayList<String>();
//
//        relationshipList.addAll( relationshipSet );
    }

    public boolean checkIfNotValidated()
    {
        return priceRateList.size() == 0 ? true : false;
    }

    public void setSelectedPriceRate( PriceRateComposite priceRate )
    {
        priceRateChoice.setChoice( new PriceRateDelegator( priceRate ) );
    }

    public RelationshipComposite getSelectedRelationship()
    {
        //TODO
//        String choice = relationshipChoice.getChoiceAsString();
//
//        for( RelationshipComposite relationship : newRelationshipList )
//        {
//            if( choice.equals( relationship.getRelationship() ) )
//            {
//                return relationship;
//            }
//        }
//
//        return ChronosWebApp.getServices().getRelationshipService().get( getProjectOwner(), choice );
        return null;
    }

    public abstract ProjectEntityComposite getProject();
}

