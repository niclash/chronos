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

import com.intellij.openapi.vfs.VirtualFileAdapter;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileManager;

public class VirtualFileActivityTracker extends AbstractAcitivityTracker
{
    private VirtualFileAdapter virtualFileAdapter;

    public VirtualFileActivityTracker( ActivityManager activityManager )
    {
        super( activityManager );
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

    //TODO VirutalFileAdapter is an application level listener, need filter here.
    private class MyVirtualFileAdapter extends VirtualFileAdapter
    {
        public void contentsChanged( VirtualFileEvent event )
        {
            newActivity( new Activity( "[Edited] File =" + event.getFileName() ) );
        }

        public void fileCreated( VirtualFileEvent event )
        {
            newActivity( new Activity( "[Created] File =" + event.getFileName() ) );
        }

        public void fileDeleted( VirtualFileEvent event )
        {
            newActivity( new Activity( "[Deleted] File =" + event.getFileName() ) );
        }
    }
}
