/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.domain.model;

import org.qi4j.chronos.domain.model.associations.HasComments;
import org.qi4j.chronos.domain.model.associations.HasCreatedDate;
import org.qi4j.chronos.domain.model.associations.HasProjectAssignee;
import org.qi4j.chronos.domain.model.common.period.Period;
import org.qi4j.entity.Identity;
import org.qi4j.library.general.model.Description;

public interface WorkEntry extends Title, Description, Period, HasComments, HasCreatedDate, HasProjectAssignee, Identity
{
    public final static int TITLE_LEN = 120;

    public final static int DESCRIPTION_LEN = 1000;
}
