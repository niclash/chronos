package org.qi4j.chronos.model.mixins;

import org.qi4j.chronos.model.ProjectAssignee;
import org.qi4j.chronos.model.associations.HasLeadProjectAssignee;

public class HasLeadProjectAssigneeMixin implements HasLeadProjectAssignee
{
    private ProjectAssignee leadProjectAssignee;

    public ProjectAssignee getLeadProjectAssignee()
    {
        return leadProjectAssignee;
    }

    public void setLeadProjectAssignee( ProjectAssignee lead )
    {
        this.leadProjectAssignee = lead;
    }
}
