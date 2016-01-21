package sample;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Objects;

public class PluginManager {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private JSONObject loadJSON(String tool) {

        URL jsonUrl = getClass().getResource("../plugin/"+tool+"/manifest.json");
        JSONObject json = null;
        ArrayList<Command> nodes = new ArrayList<>();

        try {

            InputStream is = jsonUrl.openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = new JSONObject(jsonText);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    private Command loadTool(String tool) {

        Command command = null;

        JSONObject manifest = loadJSON(tool);

        String className = null;
        String packageName = null;
        String toolName = null;

        try {
            className = (String) manifest.get("class");
            packageName = (String) manifest.get("package");
            toolName = (String) manifest.get("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(className != null && packageName != null){
            URL classUrl = getClass().getResource("../plugin/"+tool+"/" + className + ".class");
            URL[] urls = new URL[]{classUrl};
            ClassLoader cl = new URLClassLoader(urls);

            try {
                Class cls = cl.loadClass(packageName+"."+className);
                command = (Command) cls.newInstance();
                System.out.print("Initialized " + toolName + "!\n");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return command;
    }

    public ArrayList loadPlugins() {

        ArrayList<Command> commands = new ArrayList<>();
        URL directoryUrl = getClass().getResource("../plugin/");
        File file = new File(directoryUrl.getPath());
        String[] names = file.list();

        for(String name : names)
        {
            if (new File(directoryUrl.getPath()).isDirectory() && !Objects.equals(name, ".DS_Store"))
            {
                Command newTool = loadTool(name);
                if(newTool != null)
                    commands.add(newTool);
            }
        }

        return commands;
    }
}
