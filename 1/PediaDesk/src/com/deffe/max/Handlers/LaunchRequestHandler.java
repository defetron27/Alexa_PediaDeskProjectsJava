package com.deffe.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler
{
    @Override
    public boolean canHandle(HandlerInput handlerInput)
    {
        return handlerInput.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput)
    {
        String speechText = "Welcome to the Alexa MovieDesk. " +
                "It's a pleasure to talk to you. " +
                "If you want exact movie or cast & crews details please follow instructions below. " +
                "If you want movie details ask \"Alexa, ask movie desk to say 'some movie name' movie details.\" " +
                "Or If you want movie cast & crew details ask \"Alexa, ask movie desk to say 'some movie name' cast & crew details.\". " +
                "I can give the details of that movie or cast & crew details. " +
                "Ok, Now you start to say the movie titles. Also, another thing please, give exact movie title for exact details. " +
                "If you want more instructions or help, ask \" Alexa ask movie desk to say help.\"";

        return handlerInput.getResponseBuilder()
                .withSpeech(speechText)
                .build();
    }
}
