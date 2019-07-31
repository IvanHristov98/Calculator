package com.calculator.cli.tests.pageObjects;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

public class CliPage {
	public static String PATH_TO_JAR = "." + File.separator + "target" + File.separator + "lib" + File.separator + "calculator-cli-1.0-SNAPSHOT.jar";
	
	public CliPage () {
	}
	
	public String getCliOutput(String...commandArguments) throws IOException {
		Process process = this.getProcess(commandArguments);
		String processErrorOutput = this.getDataFromProcessStream(process.getInputStream());
		// destroying the process would result in emptying the buffered reader
		destroyProcess(process);
		
		return processErrorOutput;
	}
	
	public String getCliError(String... commandArguments) throws IOException {
		Process process = this.getProcess(commandArguments);
		String processErrorOutput = this.getDataFromProcessStream(process.getErrorStream());
		// destroying the process would result in emptying the buffered reader
		destroyProcess(process);
		
		return processErrorOutput;
	}
	
	private Process getProcess(String... commandArguments) throws IOException {
		String[] defaultJarCallingPrefixArguments = new String[] { "java", "-jar", PATH_TO_JAR };
		commandArguments = mergeStringArrays(defaultJarCallingPrefixArguments, commandArguments);

		ProcessBuilder processBuilder = new ProcessBuilder(commandArguments);
		return  processBuilder.start();
	}
	
	private String getDataFromProcessStream(InputStream inputStream) throws IOException {
		InputStreamReader streamReader = new InputStreamReader(inputStream);
		BufferedReader bufferedReader = new BufferedReader(streamReader);
		
		return bufferedReader.readLine();
	}

	private String[] mergeStringArrays(String[] firstArray, String[] secondArray) {
		String[] resultArray = (String[]) Array.newInstance(String.class, firstArray.length + secondArray.length);

		for (int i = 0; i < firstArray.length; ++i) {
			resultArray[i] = firstArray[i];
		}

		for (int i = firstArray.length; i < firstArray.length + secondArray.length; ++i) {
			resultArray[i] = secondArray[i - firstArray.length];
		}

		return resultArray;
	}
	
	private void destroyProcess(Process process) {
		process.destroy();
	}
}
