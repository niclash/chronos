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

import org.apache.wicket.Page;
import org.qi4j.chronos.model.associations.HasLegalConditions;
import org.qi4j.chronos.model.composites.LegalConditionComposite;
import org.qi4j.chronos.service.LegalConditionService;
import org.qi4j.chronos.ui.ChronosWebApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LegalConditionEditPage extends LegalConditionAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( LegalConditionEditPage.class );

    public LegalConditionEditPage( Page goBackPage )
    {
        super( goBackPage );

        hideSelectionLegalConditionLink();

        initData();
    }

    private void initData()
    {
        assignLegalConditionToFieldValue( getLegalCondition() );
    }

    private LegalConditionService getLegalConditionService()
    {
        return ChronosWebApp.getServices().getLegalConditionService();
    }

    public void onSubmitting()
    {
        try
        {
            LegalConditionComposite toBeUpdated = getLegalCondition();
            LegalConditionComposite old = getLegalCondition();

            assignFieldValueToLegalCondition( toBeUpdated );

            getLegalConditionService().updateLegalCondition( getHasLegalConditions(), old, toBeUpdated );

            logInfoMsg( "Legal conditation is updated successfully!" );

            divertToGoBackPage();
        }
        catch( Exception err )
        {
            logErrorMsg( err.getMessage() );
            LOGGER.error( err.getMessage(), err );
        }
    }

    public String getSubmitButtonValue()
    {
        return "Save";
    }

    public String getTitleLabel()
    {
        return "Edit Legal Condition";
    }

    public abstract LegalConditionComposite getLegalCondition();

    public abstract HasLegalConditions getHasLegalConditions();
}
