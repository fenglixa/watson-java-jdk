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

        MessageContext context = new MessageContext.Builder().build();
        Assistant service = new Assistant("2019-02-28", authenticator);
        service.setServiceUrl("https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/" + instansID);

        // get session ID
        CreateSessionOptions createSessionOptions = new CreateSessionOptions.Builder().assistantId(assistantId).build();
        SessionResponse sessionResponse = service.createSession(createSessionOptions).execute().getResult();
        String sessionId = sessionResponse.getSessionId();

        MessageInput input = new MessageInput.Builder().text(textMessage).build();
        MessageOptions messageOptions = new MessageOptions.Builder().assistantId(assistantId).sessionId(sessionId)
                .input(input).build();
        MessageResponse messageResponse = service.message(messageOptions).execute().getResult();
        context = messageResponse.getContext();

        System.out.println(messageResponse);
        System.out.println(context);
    }
}
