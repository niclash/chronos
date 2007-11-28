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
package org.qi4j.chronos.activity;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileAdapter;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;

public class VirtualFileActivityTracker extends AbstractAcitivityTracker
{
    private VirtualFileAdapter virtualFileAdapter;

    private Project project;

    public VirtualFileActivityTracker( ActivityManager activityManager, Project project )
    {
        super( activityManager );

        this.project = project;
    }

    public void start()
    {
        if( virtualFileAdapter == null )
        {
            virtualFileAdapter = new MyVirtualFileAdapter();
            VirtualFileManager.getInstance().addVirtualFileListener( virtualFileAdapter );
        }
    }

    public void stop()
    {
        if( virtualFileAdapter != null )
        {
            VirtualFileManager.getInstance().removeVirtualFileListener( virtualFileAdapter );

            virtualFileAdapter = null;
        }
    }

    private class MyVirtualFileAdapter extends VirtualFileAdapter
    {
        public void fileCreated( VirtualFileEvent event )
        {
            handleVirtualFileEvent( event, EventType.Added );
        }

        public void fileDeleted( VirtualFileEvent event )
        {
            handleVirtualFileEvent( event, EventType.Deleted );
        }
    }

    private void handleVirtualFileEvent( VirtualFileEvent event, EventType eventType )
    {
        final VirtualFile virtualFile = event.getFile();

        if( !isAcceptedFileExtension( virtualFile ) )
        {
            return;
        }

        Module module = ModuleUtil.findModuleForFile( virtualFile, getProject() );

        //since virtualFileListener is an application level listener, it is not surpsied
        //that the given event is not belong this project.
        if( module != null )
        {
            String fileName = virtualFile.getName();

            PsiFile psiFile = PsiManager.getInstance( getProject() ).findFile( event.getFile() );

            if( psiFile instanceof PsiJavaFile )
            {
                PsiJavaFile javaFile = (PsiJavaFile) psiFile;

                String packageName = javaFile.getPackageName();

                if( packageName != null && packageName.length() != 0 )
                {
                    fileName = packageName + "." + fileName;
                }
            }

            newActivity( new Activity( "[" + eventType.toString() + "]" + fileName ) );
        }
        else
        {
            System.err.println( "Miss Event " + event.getFileName() + " event " + eventType );
        }
    }

    private enum EventType
    {
        Edited, Added, Deleted
    }

    private boolean isAcceptedFileExtension( VirtualFile virtualFile )
    {
        if( virtualFile.getExtension().equals( "iws" ) )
        {
            return false;
        }

        return true;
    }
}
