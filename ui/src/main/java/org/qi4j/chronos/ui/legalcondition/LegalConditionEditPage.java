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
import org.qi4j.chronos.model.LegalCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LegalConditionEditPage extends LegalConditionAddEditPage
{
    private final static Logger LOGGER = LoggerFactory.getLogger( LegalConditionEditPage.class );

    private LegalCondition legalCondition;

    public LegalConditionEditPage( Page goBackPage, LegalCondition legalCondition )
    {
        super( goBackPage );

        this.legalCondition = legalCondition;
    }

    public void onSubmitting()
    {
        legalCondition.setLegalConditionName( nameField.getText() );
        legalCondition.setLegalConditionDesc( descField.getText() );

        try
        {
            //TODO bp. FIXME

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
}
