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
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.qi4j.chronos.model.composites.PriceRateComposite;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;
import org.qi4j.chronos.ui.base.BasePage;
import org.qi4j.chronos.ui.common.SimpleDropDownChoice;

public abstract class PriceRateOptionPanel extends Panel
{
    private SimpleDropDownChoice<PriceRateDelegator> priceRateChoice;

    private SubmitLink newPriceRateLink;
    private SubmitLink customizePriceRateLink;

    private List<PriceRateDelegator> priceRateList;

    private static List<PriceRateComposite> addedPriceRateList;

    public PriceRateOptionPanel( String id )
    {
        super( id );

        initComponents();
    }

    private void initComponents()
    {
        initPriceRateList();

        priceRateChoice = new SimpleDropDownChoice<PriceRateDelegator>( "priceRateChoice", priceRateList, true );
        newPriceRateLink = new SubmitLink( "newPriceRateLink" )
        {
            public void onSubmit()
            {
                handleNewPriceRate();
            }
        };

        customizePriceRateLink = new SubmitLink( "customizePriceRateLink" )
        {
            public void onSubmit()
            {
                handleCustomizePriceRate();
            }
        };

        if( priceRateList.size() == 0 )
        {
            setControlVisible( false );
        }

        add( priceRateChoice );
        add( newPriceRateLink );
        add( customizePriceRateLink );
    }

    private void handleNewPriceRate()
    {
        PriceRateAddPage addPage = new PriceRateAddPage( (BasePage) this.getPage() )
        {
            public void addPriceRate( PriceRateComposite priceRate )
            {
                PriceRateOptionPanel.this.addPriceRate( priceRate );
            }
        };

        setResponsePage( addPage );
    }

    private void addPriceRate( PriceRateComposite priceRate )
    {
        addedPriceRateList.add( priceRate );

        PriceRateDelegator delegator = new PriceRateDelegator( priceRate );

        priceRateList.add( delegator );

        //set newly added priceRate as default value
        priceRateChoice.setChoice( delegator );

        setControlVisible( true );
    }

    private void handleCustomizePriceRate()
    {
        PriceRateEditPage editPage = new PriceRateEditPage( (BasePage) this.getPage() )
        {
            public PriceRateComposite getPriceRate()
            {
                return PriceRateOptionPanel.this.getSelectedPriceRate();
            }

            public void updatePriceRate( PriceRateComposite priceRate )
            {
                PriceRateOptionPanel.this.updatePriceRate( priceRate );
            }
        };

        setResponsePage( editPage );
    }

    private void updatePriceRate( PriceRateComposite priceRate )
    {
        initOrResetPriceRateDelegatorList();

        setSelectedPriceRate( priceRate );
    }

    public PriceRateComposite getSelectedPriceRate()
    {
        PriceRateDelegator priceRateDelegator = priceRateChoice.getChoice();

        int index = priceRateList.indexOf( priceRateDelegator );

        return addedPriceRateList.get( index );
    }

    private void setControlVisible( boolean isVisible )
    {
        priceRateChoice.setVisible( isVisible );
        customizePriceRateLink.setVisible( isVisible );
    }

    private void initPriceRateList()
    {
        if( addedPriceRateList == null )
        {
            addedPriceRateList = new ArrayList<PriceRateComposite>();
        }

        addedPriceRateList.clear();

        List<PriceRateComposite> priceRates = getAvailablePriceRates();

        //make a copy of availblePriceRate
        for( PriceRateComposite priceRate : priceRates )
        {
            addedPriceRateList.add( priceRate );
        }

        initOrResetPriceRateDelegatorList();
    }

    private void initOrResetPriceRateDelegatorList()
    {
        if( priceRateList == null )
        {
            priceRateList = new ArrayList<PriceRateDelegator>();
        }

        priceRateList.clear();

        for( PriceRateComposite priceRate : addedPriceRateList )
        {
            priceRateList.add( new PriceRateDelegator( priceRate ) );
        }
    }

    public boolean checkIfNotValidated()
    {
        return priceRateList.size() == 0 ? true : false;
    }

    public void setSelectedPriceRate( PriceRateComposite priceRate )
    {
        priceRateChoice.setChoice( new PriceRateDelegator( priceRate ) );
    }

    public abstract List<PriceRateComposite> getAvailablePriceRates();

    public abstract ProjectEntityComposite getProject();
}

