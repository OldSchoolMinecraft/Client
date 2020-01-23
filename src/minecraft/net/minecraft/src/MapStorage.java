// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 
// Source File Name:   SourceFile

package net.minecraft.src;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

// Referenced classes of package net.minecraft.src:
//            MapDataBase, ISaveHandler, CompressedStreamTools, NBTTagCompound, 
//            NBTBase, NBTTagShort

public class MapStorage
{

    public MapStorage(ISaveHandler isavehandler)
    {
        loadedDataMap = new HashMap();
        loadedDataList = new ArrayList();
        idCounts = new HashMap();
        saveHandler = isavehandler;
        loadIdCounts();
    }

    public MapDataBase loadData(Class class1, String s)
    {
        MapDataBase mapdatabase = (MapDataBase)loadedDataMap.get(s);
        if(mapdatabase != null)
        {
            return mapdatabase;
        }
        if(saveHandler != null)
        {
            try
            {
                File file = saveHandler.getMapFile(s);
                if(file != null && file.exists())
                {
                    try
                    {
                        mapdatabase = (MapDataBase)class1.getConstructor(new Class[] {
                            java.lang.String.class
                        }).newInstance(new Object[] {
                            s
                        });
                    }
                    catch(Exception exception1)
                    {
                        throw new RuntimeException((new StringBuilder()).append("Failed to instantiate ").append(class1.toString()).toString(), exception1);
                    }
                    FileInputStream fileinputstream = new FileInputStream(file);
                    NBTTagCompound nbttagcompound = CompressedStreamTools.loadGzippedCompoundFromOutputStream(fileinputstream);
                    fileinputstream.close();
                    mapdatabase.readFromNBT(nbttagcompound.getCompoundTag("data"));
                }
            }
            catch(Exception exception)
            {
                exception.printStackTrace();
            }
        }
        if(mapdatabase != null)
        {
            loadedDataMap.put(s, mapdatabase);
            loadedDataList.add(mapdatabase);
        }
        return mapdatabase;
    }

    public void setData(String s, MapDataBase mapdatabase)
    {
        if(mapdatabase == null)
        {
            throw new RuntimeException("Can't set null data");
        }
        if(loadedDataMap.containsKey(s))
        {
            loadedDataList.remove(loadedDataMap.remove(s));
        }
        loadedDataMap.put(s, mapdatabase);
        loadedDataList.add(mapdatabase);
    }

    public void saveAllData()
    {
        for(int i = 0; i < loadedDataList.size(); i++)
        {
            MapDataBase mapdatabase = (MapDataBase)loadedDataList.get(i);
            if(mapdatabase.isDirty())
            {
                saveData(mapdatabase);
                mapdatabase.setDirty(false);
            }
        }

    }

    private void saveData(MapDataBase mapdatabase)
    {
        if(saveHandler == null)
        {
            return;
        }
        try
        {
            File file = saveHandler.getMapFile(mapdatabase.mapName);
            if(file != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                mapdatabase.writeToNBT(nbttagcompound);
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setCompoundTag("data", nbttagcompound);
                FileOutputStream fileoutputstream = new FileOutputStream(file);
                CompressedStreamTools.writeGzippedCompoundToOutputStream(nbttagcompound1, fileoutputstream);
                fileoutputstream.close();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void loadIdCounts()
    {
        try
        {
            idCounts.clear();
            if(saveHandler == null)
            {
                return;
            }
            File file = saveHandler.getMapFile("idcounts");
            if(file != null && file.exists())
            {
                DataInputStream datainputstream = new DataInputStream(new FileInputStream(file));
                NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
                datainputstream.close();
                Iterator iterator = nbttagcompound.getTags().iterator();
                do
                {
                    if(!iterator.hasNext())
                    {
                        break;
                    }
                    NBTBase nbtbase = (NBTBase)iterator.next();
                    if(nbtbase instanceof NBTTagShort)
                    {
                        NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
                        String s = nbttagshort.getKey();
                        short word0 = nbttagshort.shortValue;
                        idCounts.put(s, Short.valueOf(word0));
                    }
                } while(true);
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public int getUniqueDataId(String s)
    {
        Short short1 = (Short)idCounts.get(s);
        if(short1 == null)
        {
            short1 = Short.valueOf((short)0);
        } else
        {
            Short short2 = short1;
            Short short3 = short1 = Short.valueOf((short)(short1.shortValue() + 1));
            Short _tmp = short2;
        }
        idCounts.put(s, short1);
        if(saveHandler == null)
        {
            return short1.shortValue();
        }
        try
        {
            File file = saveHandler.getMapFile("idcounts");
            if(file != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                String s1;
                short word0;
                for(Iterator iterator = idCounts.keySet().iterator(); iterator.hasNext(); nbttagcompound.setShort(s1, word0))
                {
                    s1 = (String)iterator.next();
                    word0 = ((Short)idCounts.get(s1)).shortValue();
                }

                DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file));
                CompressedStreamTools.write(nbttagcompound, dataoutputstream);
                dataoutputstream.close();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
        return short1.shortValue();
    }

    private ISaveHandler saveHandler;
    private Map loadedDataMap;
    private List loadedDataList;
    private Map idCounts;
}