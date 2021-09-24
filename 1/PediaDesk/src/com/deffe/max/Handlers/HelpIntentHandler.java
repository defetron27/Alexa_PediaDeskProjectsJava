package com.deffe.max.Handlers;

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
        String speechText = "Hey friend, it pleasure to help you. " +
                "If you have any doubts or you don't know how to ask to 'Alexa', don't worry. \n" +
                "I clarify your doubts. " +
                "I give you some examples to how to ask to 'Alexa' . \n" +
                "Ok, now let's start, you say, \n" +
                "\"Alexa, ask movie desk to say 'some movie name' movie details. \" \n" +
                "\"Alexa, ask movie desk to say 'some movie name' cast and crew details. \" \n" +
                "\"Alexa, ask movie desk to say 'some movie name' crew details. \" \n" +
                "\"Alexa, ask movie desk to say 'some movie name' cast details. \" \n" +
                "\"Alexa, ask movie desk to say 'some cast name' details. \" \n" +
                "I believe that the above examples are helpful to you. " +
                "Ok, now start to say movie title. " +
                "Get the movie details with the cast and crew details. " +
                "Be happier.";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .build();
    }
}
