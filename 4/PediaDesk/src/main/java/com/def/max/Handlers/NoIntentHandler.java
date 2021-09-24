package main.java.com.def.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NoIntentHandler implements RequestHandler
{
    @Override
    public boolean canHandle(HandlerInput handlerInput)
    {
        return handlerInput.matches(intentName("AMAZON.NoIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput)
    {
        String speechText = "Ok, don't worry. We can continue the conversation.";

        String rePrompt = "Please, tell me some name to get the definition.";

        String cardTitle = "No";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(cardTitle,speechText)
                .withReprompt(rePrompt)
                .build();
    }
}
