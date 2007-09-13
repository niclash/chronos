///*
// * Copyright (c) 2007, Lan Boon Ping. All Rights Reserved.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.qi4j.chronos.model.modifiers;
//
//import org.qi4j.api.annotation.Modifies;
//import org.qi4j.api.annotation.ThisAs;
//import org.qi4j.chronos.model.composites.ProjectEntityComposite;
//import org.qi4j.library.general.model.Validatable;
//import org.qi4j.library.general.model.ValidationException;
//
//public class ProjectCreationValidationModifier implements Validatable
//{
//    @Modifies private Validatable next;
//    @ThisAs private ProjectEntityComposite project;
//
//    public void validate() throws ValidationException
//    {
//        String projectName = project.getName();
//
//        if( projectName == null )
//        {
//            throw new ValidationException( "Project name must not be null!" );
//        }
//
//        String reference = project.getReference();
//
//        if( reference == null )
//        {
//            throw new ValidationException( "Project reference must not be null!" );
//        }
//
//        String identity  = project.getIdentity();
//
//        if( identity == null )
//        {
//            throw new ValidationException( "Project identity must not be null!");
//        }
//    }
//}
