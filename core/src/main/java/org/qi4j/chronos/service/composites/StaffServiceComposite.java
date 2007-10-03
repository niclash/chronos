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
package org.qi4j.chronos.service.composites;

import org.qi4j.api.Composite;
import org.qi4j.api.annotation.ImplementedBy;
import org.qi4j.chronos.service.StaffService;
import org.qi4j.chronos.service.mocks.MockStaffMiscServiceMixin;
import org.qi4j.chronos.service.mocks.MockStaffServiceMixin;

@ImplementedBy( { MockStaffServiceMixin.class, MockStaffMiscServiceMixin.class } )
public interface StaffServiceComposite extends StaffService, Composite
{

}
