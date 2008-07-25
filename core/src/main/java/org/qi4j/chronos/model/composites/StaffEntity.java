/*
 * Copyright (c) 2007 Sianny Halim. All Rights Reserved.
 * Copyright (c) 2007 Lan Boon Ping. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.qi4j.chronos.model.composites;

import org.qi4j.chronos.model.Staff;
import org.qi4j.entity.EntityComposite;

/**
 * Staff works for the company and hence contains more information on top of the basic user information provided in
 * {@link org.qi4j.chronos.model.User} like:
 * <p/>
 * <li>
 * <ol>Start and end employment date
 * <ol>Salary
 * <ol>Basic price rate
 * <ol>Projects that this staff is working on
 * </li>
 */
public interface StaffEntity extends Staff, EntityComposite
{
}