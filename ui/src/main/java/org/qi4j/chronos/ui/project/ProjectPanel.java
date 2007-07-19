/*
 * Copyright (c) 2007, Sianny Halim. All Rights Reserved.
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
package org.qi4j.chronos.ui.project;

import org.qi4j.ui.annotation.ModelledBy;
import org.qi4j.ui.annotation.Label;
import org.qi4j.ui.annotation.TextField;
import org.qi4j.ui.annotation.TextArea;
import org.qi4j.ui.annotation.CheckBox;
import org.qi4j.ui.annotation.Button;
import org.qi4j.ui.component.Position;
import org.qi4j.ui.component.ButtonType;
import org.qi4j.chronos.model.composites.ProjectEntityComposite;

@ModelledBy( { ProjectEntityComposite.class } )
public interface ProjectPanel
{
    @Label( value = "Project name:" )
    @TextField( width = 120 ) void name();

    @Label( value = "Project description: " )
    @TextArea( name = "projectDescription", rows = 3, cols = 12 ) void description();

    @Label( value = "Chargeable project", position = Position.right )
    @CheckBox void chargeable();

    @Button( value = "Save", type = ButtonType.submit ) void saveProject();
}