/*
 * (c) 2015 The Dragonet Team
 * All rights reserved.
 */
package org.dragonet.rhino;

import java.util.*;
import java.io.*;
import com.google.common.io.Files;
import java.nio.charset.Charset;
import org.mozilla.javascript.*;

/**
 *
 * @author TheMCPEGamer__
 */
public class Script
{
    public String name = "script.js";
    
    public String fullFilePath = "filepath";
    
    public String fileContents = "System.out.println(\"File is empty!\")";
    
    public File file;
    
    public Script(File scriptFile)
    {       
        this.name = scriptFile.getName();
        
        this.fullFilePath = scriptFile.getAbsolutePath();
        
        this.fileContents = getScriptContents(scriptFile);
        
        this.file = scriptFile;
    }
    
    public File getFile()
    {
        return this.file;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public String getPath()
    {
        return this.fullFilePath;
    }
    
    private String getScriptContents(File f)
    {
        List<String> scriptContentList = new ArrayList<>();
        
        String scriptContents = new String();
        
        try {            
            scriptContentList = Files.readLines(f, Charset.defaultCharset());          
        }
        
        catch(IOException IOe) {
            System.out.println(IOe.getMessage());
        }
            
        for(String str : scriptContentList) {
            scriptContents += " " + str;
        }
        
            return scriptContents;
    }
    
    public void runFunction(String func) {  
        BufferedReader script = null;
        
        try {
            script = new BufferedReader(new FileReader(this.getFile()));
        }
        
        catch(IOException IOe) {
            System.out.println(Arrays.toString(IOe.getStackTrace()));
        }
        
        Context context = Context.enter();
        
	try {
            ScriptableObject scope = context.initStandardObjects();
            
            try {
                context.evaluateReader(scope, script, "script", 1, null);
            }
            
            catch(IOException IOe) {
                System.out.println(Arrays.toString(IOe.getStackTrace()));
            }
            
            
            Function fct = (Function)scope.get(func, scope);
            Object result = fct.call(context, scope, scope, new Object[] {2, 3});
            
            /*if(result != null) {
                System.out.println(Context.jsToJava(result, String.class));
            }*/
	} 
	
        finally {
            Context.exit();
	}
    }
}