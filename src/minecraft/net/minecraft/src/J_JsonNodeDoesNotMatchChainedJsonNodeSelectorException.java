// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   SourceFile

package net.minecraft.src;

import java.util.LinkedList;
import java.util.List;

// Referenced classes of package net.minecraft.src:
//            J_JsonNodeDoesNotMatchJsonNodeSelectorException, J_JsonNodeSelector, J_Functor

public final class J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException extends J_JsonNodeDoesNotMatchJsonNodeSelectorException
{

    static J_JsonNodeDoesNotMatchJsonNodeSelectorException func_27322_a(J_Functor j_functor)
    {
        return new J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(j_functor, new LinkedList());
    }

    static J_JsonNodeDoesNotMatchJsonNodeSelectorException func_27323_a(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_jsonnodedoesnotmatchchainedjsonnodeselectorexception, J_JsonNodeSelector j_jsonnodeselector)
    {
        LinkedList linkedlist = new LinkedList(j_jsonnodedoesnotmatchchainedjsonnodeselectorexception.failPath);
        linkedlist.add(j_jsonnodeselector);
        return new J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(j_jsonnodedoesnotmatchchainedjsonnodeselectorexception.failedNode, linkedlist);
    }

    static J_JsonNodeDoesNotMatchJsonNodeSelectorException func_27321_b(J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException j_jsonnodedoesnotmatchchainedjsonnodeselectorexception, J_JsonNodeSelector j_jsonnodeselector)
    {
        LinkedList linkedlist = new LinkedList();
        linkedlist.add(j_jsonnodeselector);
        return new J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(j_jsonnodedoesnotmatchchainedjsonnodeselectorexception.failedNode, linkedlist);
    }

    private J_JsonNodeDoesNotMatchChainedJsonNodeSelectorException(J_Functor j_functor, List list)
    {
        super((new StringBuilder()).append("Failed to match any JSON node at [").append(getShortFormFailPath(list)).append("]").toString());
        failedNode = j_functor;
        failPath = list;
    }

    static String getShortFormFailPath(List list)
    {
        StringBuilder stringbuilder = new StringBuilder();
        for(int i = list.size() - 1; i >= 0; i--)
        {
            stringbuilder.append(((J_JsonNodeSelector)list.get(i)).shortForm());
            if(i != 0)
            {
                stringbuilder.append(".");
            }
        }

        return stringbuilder.toString();
    }

    public String toString()
    {
        return (new StringBuilder()).append("JsonNodeDoesNotMatchJsonNodeSelectorException{failedNode=").append(failedNode).append(", failPath=").append(failPath).append('}').toString();
    }

    final J_Functor failedNode;
    final List failPath;
}
