package com.deffe.max;

import com.amazonaws.util.StringInputStream;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

public class PediaDeskRequestStreamHandlerTest {

    @org.junit.Test
    public void handleRequest()
    {
        PediaDeskRequestStreamHandler handler = new PediaDeskRequestStreamHandler();
        try
        {
            StringInputStream inputStream = new StringInputStream(TestData.getRequestContext());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            handler.handleRequest(inputStream,outputStream,null);

            String respond = outputStream.toString();

            Assert.assertNotNull(respond);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}