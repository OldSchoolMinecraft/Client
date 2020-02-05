// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   SourceFile

package net.minecraft.src;


// Referenced classes of package net.minecraft.src:
//            NBTTagCompound

public abstract class MapDataBase
{

    public MapDataBase(String s)
    {
        mapName = s;
    }

    public abstract void readFromNBT(NBTTagCompound nbttagcompound);

    public abstract void writeToNBT(NBTTagCompound nbttagcompound);

    public void markDirty()
    {
        setDirty(true);
    }

    public void setDirty(boolean flag)
    {
        dirty = flag;
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public final String mapName;
    private boolean dirty;
}
