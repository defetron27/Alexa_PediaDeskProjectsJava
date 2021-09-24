package main.java.com.def.max;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class SpeechRequestStreamHandler extends SpeechletRequestStreamHandler
{
    private static final Set<String> supportedApplicationIds;

    static
    {
        supportedApplicationIds = new HashSet<>();

        supportedApplicationIds.add("amzn1.ask.skill.dd0c3fc4-9025-43c6-a681-ee3c7ce81182");
    }

    public SpeechRequestStreamHandler()
    {
        super(new GetMeaningSpeechLet(), supportedApplicationIds);
    }
}
