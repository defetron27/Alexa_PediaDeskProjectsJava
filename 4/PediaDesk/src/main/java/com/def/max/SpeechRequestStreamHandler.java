package main.java.com.def.max;

import com.amazon.ask.Skill;
import com.amazon.ask.builder.StandardSkillBuilder;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.util.IOUtils;
import main.java.com.def.max.Handlers.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SpeechRequestStreamHandler implements RequestStreamHandler
{
    private Skill skill;
    private JacksonSerializer jacksonSerializer;

    public SpeechRequestStreamHandler()
    {
        skill = new StandardSkillBuilder()
                .addRequestHandler(new LaunchRequestHandler())
                .addRequestHandler(new HelpIntentHandler())
                .addRequestHandler(new GetMeaningNameIntentHandler())
                .addRequestHandler(new SessionEndedRequestHandler())
                .addRequestHandler(new CancelAndStopIntentHandler())
                .addRequestHandler(new YesIntentHandler())
                .addRequestHandler(new NoIntentHandler())
                .addRequestHandler(new FallbackIntentHandler())
                .build();

        jacksonSerializer = new JacksonSerializer();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException
    {
        String request = IOUtils.toString(inputStream);
        RequestEnvelope requestEnvelope = jacksonSerializer.deserialize(request, RequestEnvelope.class);
        ResponseEnvelope responseEnvelope = skill.invoke(requestEnvelope);
        byte[] response = jacksonSerializer.serialize(responseEnvelope).getBytes(StandardCharsets.UTF_8);

        outputStream.write(response);
    }
}
