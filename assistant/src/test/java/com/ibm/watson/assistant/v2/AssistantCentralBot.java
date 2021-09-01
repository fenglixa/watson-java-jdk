package com.ibm.watson.assistant.v2;

import com.ibm.cloud.sdk.core.security.Authenticator;
import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.assistant.v2.model.*;

public class AssistantCentralBot {
    public static void main(String[] args) throws Exception {
        Authenticator authenticator = new IamAuthenticator("S6KL23uVaNoURlRfLaVGEk46ghCcDILUPvU3ETzZy5Gd");
        String instansID = "0f067339-148a-421f-a0f2-af7c679a2b3e";
        String assistantId = "35913f3e-e863-4ef7-ab46-c6d62aab6580";
        String textMessage = "what's my credit";

        Assistant service = new Assistant("2021-06-14", authenticator);
        service.setServiceUrl("https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/" + instansID);

        // get session ID
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder().assistantId(assistantId).build();
        SessionResponse sessionResponse = service.createSession(createSessionOptions).execute().getResult();
        String sessionId = sessionResponse.getSessionId();

        MessageInputOptions inputOptions = new MessageInputOptions.Builder().returnContext(true).build();

        MessageInput input = new MessageInput.Builder().messageType("text").text(textMessage).options(inputOptions).build();
        MessageOptions messageOptions = new MessageOptions.Builder(assistantId, sessionId)
                .input(input).build();
        MessageResponse messageResponse = service.message(messageOptions).execute().getResult();
        MessageContext context = new MessageContext.Builder().build();
        context = messageResponse.getContext();

        // get child assistant id
        String childAssistantId = (String) context.skills().get("main skill").userDefined().get("assistant_id");
        System.out.println("DEBUG: Child assistant id is: " + childAssistantId);

        // get the final result
        CreateSessionOptions childCreateSessionOptions = new CreateSessionOptions.Builder().assistantId(childAssistantId).build();
        SessionResponse childSessionResponse = service.createSession(childCreateSessionOptions).execute().getResult();
        String childSessionId = childSessionResponse.getSessionId();

        MessageOptions childOptions = new MessageOptions.Builder(childAssistantId, childSessionId)
        .input(input).build();
        MessageResponse childResponse = service.message(childOptions).execute().getResult();
        System.out.println("Response message: " + childResponse.getOutput().getGeneric().get(0).text());

        // delete the sessions
        DeleteSessionOptions childDeleteOptions = new DeleteSessionOptions.Builder(childAssistantId, childSessionId).build();
        service.deleteSession(childDeleteOptions).execute();

        DeleteSessionOptions options = new DeleteSessionOptions.Builder(assistantId, sessionId).build();
        service.deleteSession(options).execute();

        // System.out.println(messageResponse);
        // System.out.println(context);
    }
}
