package com.deffe.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.deffe.max.Utils.GoogleWebSearch;
import com.deffe.max.Utils.SearchQuery;
import com.deffe.max.Utils.SearchResult;
import com.google.common.base.Joiner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class NameIntentHandler implements RequestHandler
{

    @Override
    public boolean canHandle(HandlerInput handlerInput)
    {
        return handlerInput.matches(intentName("NameIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput)
    {
        Request request = handlerInput.getRequestEnvelope().getRequest();

        IntentRequest intentRequest = (IntentRequest) request;

        Intent intent = intentRequest.getIntent();

        Map<String, Slot> slotMap = intent.getSlots();

        Slot movieSlot = slotMap.get("name");

        if (movieSlot != null)
        {
            String meaningName = movieSlot.getValue();

            if (meaningName != null)
            {
                try
                {
                    Document document = Jsoup.connect("https://en.wikipedia.org/wiki/" + meaningName).get();

                    Elements paragraphs = document.select("p:not(:has(#coordinates))");

                    if (paragraphs.size() > 2)
                    {
                        Element first = paragraphs.get(1);

                        String meaning = first.text();

                        if (meaning != null && !meaning.equals("null") && !meaning.equals(""))
                        {
                            return setSuccessResultForName(meaningName,meaning,handlerInput);
                        }
                        else
                        {
                            return setFailureResultForName(meaningName,handlerInput);
                        }
                    }
                    else
                    {
                        SearchQuery query = new SearchQuery.Builder(meaningName).site("en.wikipedia.org").numResults(2).build();

                        SearchResult result = new GoogleWebSearch().search(query);

                        List<String> url = result.getUrls();

                        if (url != null && url.size() > 0)
                        {
                            Document finalDocument = Jsoup.connect(url.get(0)).get();

                            Elements finalParagraphs = finalDocument.select("p:not(:has(#coordinates))");

                            if (finalParagraphs.size() > 2)
                            {
                                Element firstParagraph = finalParagraphs.get(1);

                                String meaning = firstParagraph.text();

                                if (meaning != null && !meaning.equals("null") && !meaning.equals(""))
                                {
                                    return setSuccessResultForName(meaningName,meaning,handlerInput);
                                }
                                else
                                {
                                    return setFailureResultForName(meaningName,handlerInput);
                                }
                            }
                            else
                            {
                                return setFailureResultForName(meaningName,handlerInput);
                            }
                        }
                        else
                        {
                            return setFailureResultForName(meaningName,handlerInput);
                        }
                    }
                }
                catch (IOException e)
                {
                    return setFailureResultForName(meaningName,handlerInput);
                }
            }
            else
            {
                return sendFailureResult(handlerInput);
            }
        }
        else
        {
            return sendFailureResult(handlerInput);
        }
    }

    private Optional<Response> setSuccessResultForName(String meaningName, String meaning,HandlerInput handlerInput)
    {
        String cardTitle = "Definition for " + meaningName;

        return handlerInput.getResponseBuilder().withSpeech(meaning).withSimpleCard(cardTitle,meaning).build();
    }

    private Optional<Response> setFailureResultForName(String meaningName,HandlerInput handlerInput)
    {
        SearchQuery wikiQuery = new SearchQuery.Builder(meaningName).site("en.wikipedia.org").numResults(1).build();

        SearchResult wikiResult = new GoogleWebSearch().search(wikiQuery);

        ArrayList<String> urls = new ArrayList<>(wikiResult.getUrls());

        SearchQuery quoraQuery = new SearchQuery.Builder(meaningName).site("quora.com").numResults(1).build();

        SearchResult quoraResult = new GoogleWebSearch().search(quoraQuery);

        urls.addAll(quoraResult.getUrls());

        SearchQuery stackQuery = new SearchQuery.Builder(meaningName).site("stackoverflow.com").numResults(1).build();

        SearchResult stackResult = new GoogleWebSearch().search(stackQuery);

        urls.addAll(stackResult.getUrls());

        String listString = Joiner.on(", \n").join(urls);

        String cardTitle = "Definition";

        String speechText = "I could not find the definition for " + meaningName + ",Please go through the below best websites.\n" + listString;

        return handlerInput.getResponseBuilder().withSpeech(speechText).withSimpleCard(cardTitle,speechText).build();
    }

    private Optional<Response> sendFailureResult(HandlerInput handlerInput)
    {
        String speechText = "Oops.. there was some internal problem, don't worry. Please, say name again.";

        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
