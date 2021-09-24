package main.java.com.def.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class YesIntentHandler implements RequestHandler
{
    @Override
    public boolean canHandle(HandlerInput handlerInput)
    {
        return handlerInput.matches(intentName("AMAZON.YesIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput)
    {
        String speechText = "Ok, i stopped the all conversations. If you like to speak to me again. Simply you can ask or say \"Alexa, open pedia desk and definition for any word\".";

        String cardTitle = "Finish";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(cardTitle,speechText)
                .build();
    }
}
