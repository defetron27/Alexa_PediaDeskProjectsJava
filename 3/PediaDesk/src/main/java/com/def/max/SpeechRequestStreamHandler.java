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

        supportedApplicationIds.add("amzn1.ask.skill.6ea76dfb-7c21-4da2-9708-22478a6822b0");
    }

    public SpeechRequestStreamHandler()
    {
        super(new GetMeaningSpeechLet(), supportedApplicationIds);
    }
}
