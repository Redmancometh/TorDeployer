package com.redmancometh.orgbot.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Modifier;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.redmancometh.orgbot.config.tor.AutoDeployNode;
import com.redmancometh.orgbot.config.tor.HostedNode;
import com.redmancometh.orgbot.config.tor.Config;
import com.redmancometh.orgbot.config.tor.TorNode;

import lombok.Getter;

public class ConfigManager
{

    @Getter
    private RuntimeTypeAdapterFactory<TorNode> adapterFactory = RuntimeTypeAdapterFactory.of(TorNode.class).registerSubtype(AutoDeployNode.class).registerSubtype(HostedNode.class);

    private Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).registerTypeAdapterFactory(adapterFactory).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES).registerTypeHierarchyAdapter(String.class, new PathAdapter()).create();

    private Config config;

    public void init()
    {
        initConfig();
    }

    private void initConfig()
    {
        try (FileReader reader = new FileReader("config" + File.separator + "config.json"))
        {
            Config conf = gson.fromJson(reader, Config.class);
            this.config = conf;
        }
        catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    public Config getConfig()
    {
        return config;
    }

    public void setConfig(Config config)
    {
        this.config = config;
    }

    public static class PathAdapter extends TypeAdapter<String>
    {

        @Override
        public String read(JsonReader arg0) throws IOException
        {
            String string = arg0.nextString();
            if (string.contains("http")) return string;
            return string.replace("//", File.separator).replace("\\", File.separator);
        }

        @Override
        public void write(JsonWriter arg0, String arg1) throws IOException
        {

        }

    }

}
