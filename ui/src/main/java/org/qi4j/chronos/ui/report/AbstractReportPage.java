/*
 * Copyright (c) 2008, Muhd Kamil Mohd Baki. All Rights Reserved.
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
package org.qi4j.chronos.ui.report;

import org.apache.wicket.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.joda.time.Period;
import org.qi4j.chronos.model.SystemRole;
import org.qi4j.chronos.model.WorkEntry;
import org.qi4j.chronos.model.associations.HasWorkEntries;
import org.qi4j.chronos.ui.wicket.base.LeftMenuNavPage;

@AuthorizeInstantiation( SystemRole.ACCOUNT_ADMIN )
public abstract class AbstractReportPage extends LeftMenuNavPage
{
    protected static Period calculatePeriod( HasWorkEntries hasWorkEntries )
    {
        long duration = 0L;
        for( WorkEntry workEntry : hasWorkEntries.workEntries() )
        {
            duration += getTimeInMillis( workEntry );
        }

        return new Period( duration );
    }

    public static Period getPeriod( WorkEntry workEntry )
    {
        return new Period( getTimeInMillis( workEntry ) );
    }

    protected static long getTimeInMillis( WorkEntry workEntry )
    {
        return workEntry.endTime().get().getTime() - workEntry.startTime().get().getTime();
    }
}
