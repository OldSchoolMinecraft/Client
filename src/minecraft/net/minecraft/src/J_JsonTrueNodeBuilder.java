// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   SourceFile

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            J_JsonNodeBuilder, J_JsonNodeFactories, J_JsonNode

final class J_JsonTrueNodeBuilder
    implements J_JsonNodeBuilder
{

    J_JsonTrueNodeBuilder()
    {
    }

    public J_JsonNode buildNode()
    {
        return J_JsonNodeFactories.aJsonTrue();
    }
}
