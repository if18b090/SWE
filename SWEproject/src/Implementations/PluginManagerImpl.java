package Implementations;

import Interfaces.Plugin;
import Interfaces.PluginManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

public class PluginManagerImpl implements PluginManager {
    List<Plugin> myPlugins = new LinkedList<Plugin>();
    String PluginFile = "./plugins.cfg";



    @Override
    public Iterable<Plugin> getPlugins() {
        if(myPlugins.isEmpty()){
            myPlugins.add(new PluginImpl());
        }
        return myPlugins;
    }

    @Override
    public void add(Plugin plugin) {
        if(myPlugins.isEmpty())
        myPlugins.add(plugin);
    }

    @Override
    public void add(String plugin) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        String PluginName = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(PluginFile));
            while (true){
                PluginName = bufferedReader.readLine();
                if(PluginName == null) break;
                Class<?> cls = Class.forName("Implementations." + PluginName);
                myPlugins.add((Plugin) cls.getConstructor().newInstance());
            }
            bufferedReader.close();
        }catch (FileNotFoundException e){
            System.out.println("Config Datei wurde nicht gefunden!");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        myPlugins.clear();
    }


}
