
package com.mageddo.titulosbolsa;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.GsonBuilder;
import com.mageddo.titulosbolsa.api.TitulosService;

/**
 *
 * @author elvis
 */
public class Main {
	
	public static Map<String, Runnable> commands = new HashMap<>();
	public static Map<String, String> requiredOptions = new LinkedHashMap<>();
	
	public static void main(String[] args) {
		for(int i=0; i < args.length; i++){
			requiredOptions.put(args[i].toLowerCase(), null);
		}
		if(requiredOptions.isEmpty()){
			System.err.println("Pass a option");
			System.exit(-1);
		}
		commands.get(requiredOptions.keySet().iterator().next()).run();
	}	
	
	static{
		commands.put("--titulos", new Runnable() {
			@Override
			public void run() {
				try {
					GsonBuilder gson = new GsonBuilder();
					if(requiredOptions.containsKey("--beautifull"))
						gson.setPrettyPrinting();
					
					String json = gson.create().toJson(new TitulosService().getTodosTitulos());
					System.out.println(json);
					System.exit(0);
				} catch (Exception e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
	}
}
