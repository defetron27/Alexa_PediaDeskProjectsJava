package main.java.com.def.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class HelpIntentHandler implements RequestHandler
{
    @Override
    public boolean canHandle(HandlerInput handlerInput)
    {
        return handlerInput.matches(intentName("AMAZON.HelpIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput)
    {
        String speechText = "Hey friend. I am glad to help you. If you have any doubts about pedia desk, don't worry. I am here to help to you. Simply you can ask or say \"Alexa, ask pedia desk to say definition for any word\".";

        String rePrompt = "Please, say the valid name to get the definiton for that name.";

        String cardTitle = "Help!";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(cardTitle,speechText)
                .withReprompt(rePrompt)
                .build();
    }
}
