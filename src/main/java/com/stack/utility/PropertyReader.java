package com.stack.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader 
{
	private static final String CONFIG_PROPERTIES = "/config.properties";

    public static Properties getConfigProperties() 
    {
        InputStream input = null;
        final Properties properties = new Properties();
        try
        {
            input = PropertyReader.class.getResourceAsStream(CONFIG_PROPERTIES);
            properties.load(input);

        } 
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (input != null) 
            {
                try
                {
                    input.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }

}
