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
package org.qi4j.chronos.model.validations;

import org.qi4j.chronos.model.PriceRateSchedule;
import org.qi4j.chronos.model.Project;
import org.qi4j.chronos.model.ProjectStatusEnum;
import org.qi4j.chronos.model.TimeRange;
import org.qi4j.chronos.util.ValidatorUtil;
import org.qi4j.entity.association.Association;
import org.qi4j.library.framework.validation.AbstractValidatableConcern;
import org.qi4j.library.framework.validation.Validator;

public class ProjectValidatableConcern extends AbstractValidatableConcern
{
    @This private Project project;

    protected void isValid( Validator validator )
    {
        ValidatorUtil.isEmptyOrInvalidLength( project.name().get(), "Name", Project.NAME_LEN, validator );
        ValidatorUtil.isEmptyOrInvalidLength( project.reference().get(), "Reference", Project.REFERENCE_LEN, validator );

        Association<PriceRateSchedule> projectPriceRateSchedule = project.priceRateSchedule();
        if( projectPriceRateSchedule.get() == null )
        {
            validator.error( true, "Please create a new price Rate Schedule." );
        }


        TimeRange projectEstimateTime = project.estimateTime().get();
        ValidatorUtil.isAfter( projectEstimateTime.startTime().get(), projectEstimateTime.endTime().get(),
                               "Start Date(Est.)", "End Date(Est.)", validator );

        ProjectStatusEnum projectStatus = project.projectStatus().get();
        if( projectStatus == ProjectStatusEnum.CLOSED )
        {
            TimeRange projectActualTime = project.actualTime().get();
            ValidatorUtil.isAfter( projectActualTime.startTime().get(), projectActualTime.endTime().get(),
                                   "Start Date(Act.)", "End Date(Act.)", validator );
        }
    }

    protected String getResourceBundle()
    {
        return null; //no i18n
    }
}