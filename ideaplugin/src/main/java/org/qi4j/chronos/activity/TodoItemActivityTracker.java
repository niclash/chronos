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

import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.FileEditorManagerAdapter;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.psi.search.TodoItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoItemActivityTracker extends AbstractAcitivityTracker
{
    private final Map<VirtualFile, TodoItem[]> todoItemMap;
    private final Map<VirtualFile, String> virtualFileTextMap;

    private Project project;

    private FileEditorManagerListener fileEditorManagerListener;

    public TodoItemActivityTracker( ActivityManager manager, Project project )
    {
        super( manager );

        this.project = project;

        this.todoItemMap = Collections.synchronizedMap( new HashMap<VirtualFile, TodoItem[]>() );
        virtualFileTextMap = Collections.synchronizedMap( new HashMap<VirtualFile, String>() );
    }

    public void start()
    {
        if( fileEditorManagerListener == null )
        {
            fileEditorManagerListener = new MyFileEditorManagerAdapter();

            FileEditorManager.getInstance( project ).addFileEditorManagerListener( fileEditorManagerListener );
        }
    }

    public void stop()
    {
        if( fileEditorManagerListener != null )
        {
            FileEditorManager.getInstance( project ).removeFileEditorManagerListener( fileEditorManagerListener );

            fileEditorManagerListener = null;

            todoItemMap.clear();
            virtualFileTextMap.clear();
        }
    }

    private PsiFile getPsiFile( VirtualFile virtualFile )
    {
        return PsiManager.getInstance( project ).findFile( virtualFile );
    }

    private TodoItem[] getTodoItems( PsiFile psiFile )
    {
        PsiSearchHelper psiSearchHelper = PsiManager.getInstance( project ).getSearchHelper();

        return psiSearchHelper.findTodoItems( psiFile );
    }

    private void addUnmatchedTodoItem( TodoItem[] sources, TodoItem[] targets, List<TodoItem> resultList )
    {
        for( TodoItem source : sources )
        {
            boolean isFound = false;

            for( TodoItem target : targets )
            {
                if( target.equals( source ) )
                {
                    isFound = true;
                    break;
                }
            }

            if( !isFound )
            {
                resultList.add( source );
            }
        }
    }

    private class MyFileEditorManagerAdapter extends FileEditorManagerAdapter
    {
        public void fileOpened( FileEditorManager source, VirtualFile file )
        {
            PsiFile psiFile = getPsiFile( file );

            if( psiFile != null )
            {
                TodoItem[] todoItems = getTodoItems( psiFile );

                todoItemMap.put( file, todoItems );
                virtualFileTextMap.put( file, psiFile.getText() );
            }
        }

        public void fileClosed( FileEditorManager source, VirtualFile file )
        {
            PsiFile psiFile = getPsiFile( file );

            if( psiFile != null && todoItemMap.containsKey( file ) )
            {
                TodoItem[] oldTodoItems = todoItemMap.get( file );
                TodoItem[] currTodoItems = getTodoItems( psiFile );

                List<TodoItem> newTodoItemList = new ArrayList<TodoItem>();
                List<TodoItem> deletedTodoItemList = new ArrayList<TodoItem>();

                addUnmatchedTodoItem( oldTodoItems, currTodoItems, deletedTodoItemList );
                addUnmatchedTodoItem( currTodoItems, oldTodoItems, newTodoItemList );

                for( TodoItem todoItem : newTodoItemList )
                {
                    addNewTodoItemActivity( todoItem, "TODO Added", psiFile.getText() );
                }

                for( TodoItem todoItem : deletedTodoItemList )
                {
                    String oldText = virtualFileTextMap.get( file );

                    addNewTodoItemActivity( todoItem, "TODO Deleted", oldText );
                }

                //update totoItemMap
                todoItemMap.remove( file );
                virtualFileTextMap.remove( file );
            }
        }

        private void addNewTodoItemActivity( TodoItem todoItem, String type, String text )
        {
            TextRange textRange = todoItem.getTextRange();
            String todoMsg = text.substring( textRange.getStartOffset() + 4, textRange.getEndOffset() );

            String msg = "[" + type + "]" + todoMsg;

            System.err.println( msg );
            newActivity( new Activity( msg ) );
        }
    }
}