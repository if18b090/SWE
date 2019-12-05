package Implementations;

import Interfaces.Plugin;
import Interfaces.PluginManager;
import Interfaces.Request;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PluginManagerImpl implements PluginManager {
    List<Plugin> myPlugins = new LinkedList<Plugin>();
    //String PluginFile = "./plugins.cfg";

    public PluginManagerImpl() {
        myPlugins.add(new TempPlugin());
        myPlugins.add(new NaviPlugin());
        myPlugins.add(new ToLowerPlugin());
        myPlugins.add(new StaticFilePlugin());
        myPlugins.add(new PluginImpl());
    }


    @Override
    public Iterable<Plugin> getPlugins() {
        if (myPlugins.isEmpty()) {
            myPlugins.add(new PluginImpl());
        }
        return myPlugins;
    }

    @Override
    public void add(Plugin plugin) {
        myPlugins.add(plugin);
    }

    @Override
    public void add(String plugin) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        try {
            Class<?> cls = Class.forName(plugin);
            if (PluginImpl.class.isAssignableFrom(cls)) {
                myPlugins.add((Plugin) cls.getConstructor().newInstance());
            }
        } catch (ClassNotFoundException  e) {
            System.out.println("Class not found!");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }catch (InstantiationException e){
            e.printStackTrace();
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }



    @Override
    public void clear() {
        myPlugins.clear();
    }

    public Plugin getBestPlugin(Request req){
        Plugin res = null;
        float max = 0;
        for (Plugin pl : myPlugins) {
            float num = pl.canHandle(req);
            if(num > max){
                max = num;
                res = pl;
            }
        }
        return res;
    }


}
