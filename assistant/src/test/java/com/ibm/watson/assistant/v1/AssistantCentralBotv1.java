
package com.ibm.watson.assistant.v1;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v1.model.*;
import java.util.Scanner;

/**
 * Example of how to call the Assisant Central Bot messages. Example Inputs:
 * what's my credit/I forgot my password/how much will a loan cost me
 * 
 * @version v1-experimental
 */
public class AssistantCentralBotv1 {

  public static void main(String[] args) throws Exception {
    String apiKey = "S6KL23uVaNoURlRfLaVGEk46ghCcDILUPvU3ETzZy5Gd";
    String workspaceId = "8ec2b504-4276-4779-b145-294a89389d15"; // router workspace

    IamAuthenticator authenticator = new IamAuthenticator(apiKey);
    Assistant service = new Assistant("2020-04-01", authenticator);
    service.setServiceUrl("https://api.eu-gb.assistant.watson.cloud.ibm.com");

    MessageInput input = new MessageInput();
    Scanner myObj = new Scanner(System.in); // Create a Scanner object
    System.out.println("Input your question:");
    input.setText(myObj.nextLine());
    myObj.close();
    MessageOptions options = new MessageOptions.Builder(workspaceId).input(input).build();

    // sync
    MessageResponse response = service.message(options).execute().getResult();
    // get child workspace id
    String child_workspace_id = (String) response.getContext().get("workspace_id");
    System.out.println("DEBUG: Child workspace id is: " + child_workspace_id);
    System.out.println("DEBUG: Child skill name is: " + (String) response.getContext().get("skill_name"));

    // get the final result
    MessageOptions child_options = new MessageOptions.Builder(child_workspace_id).input(input).build();
    MessageResponse child_response = service.message(child_options).execute().getResult();
    System.out.println("Response message: " + child_response.getOutput().getGeneric().get(0).text());
  }
}
