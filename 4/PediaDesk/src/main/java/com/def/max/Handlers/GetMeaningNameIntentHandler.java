package main.java.com.def.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.google.common.base.Joiner;
import main.java.com.def.max.Utils.GoogleWebSearch;
import main.java.com.def.max.Utils.SearchQuery;
import main.java.com.def.max.Utils.SearchResult;
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

public class GetMeaningNameIntentHandler implements RequestHandler
{

    @Override
    public boolean canHandle(HandlerInput handlerInput)
    {
        return handlerInput.matches(intentName("GetMeaningNameIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput)
    {
        Request request = handlerInput.getRequestEnvelope().getRequest();

        IntentRequest intentRequest = (IntentRequest) request;

        Session session = handlerInput.getRequestEnvelope().getSession();

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
                            return setSuccessResultForName(handlerInput,session,meaningName,meaning);
                        }
                        else
                        {
                            return setFailureResultForName(session,meaningName,handlerInput);
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
                                    return setSuccessResultForName(handlerInput,session,meaningName,meaning);
                                }
                                else
                                {
                                    return setFailureResultForName(session,meaningName,handlerInput);
                                }
                            }
                            else
                            {
                                return setFailureResultForName(session,meaningName,handlerInput);
                            }
                        }
                        else
                        {
                            return setFailureResultForName(session,meaningName,handlerInput);
                        }
                    }
                }
                catch (IOException e)
                {
                    return setFailureResultForName(session,meaningName,handlerInput);
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

    private Optional<Response> setSuccessResultForName(HandlerInput handlerInput,Session session,String meaningName, String meaning)
    {
        if (meaningName != null)
        {
            session.getAttributes().put("name",meaningName);

            String cardTitle = "Definition for " + meaningName;

            String rePromptText = "Would you like to get the definition for another name. Ask to alexa, definition for \"some word\".";

            return handlerInput.getResponseBuilder().withSpeech(meaning).withSimpleCard(cardTitle,meaning).withReprompt(rePromptText).build();
        }
        else
        {
            String speechText = "I could not find any name to repeat the definition. Please, say a new name to get the definition.";

            String rePrompt = "Please, say the valid name to get the definition for that name.";

            String cardTitle = "Repeat";

            return handlerInput.getResponseBuilder().withSpeech(speechText).withSimpleCard(cardTitle,speechText).withReprompt(rePrompt).build();
        }
    }

    private Optional<Response> setFailureResultForName(Session session,String meaningName,HandlerInput handlerInput)
    {
        if (meaningName != null)
        {
            session.getAttributes().put("name",meaningName);

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

            String cardTitle = "Meaning";

            String speechText = "I could not find the definition for " + meaningName + ", Please go through the below best websites.\n" + listString;

            String rePrompt = " Please, say the valid name to get the definition.";

            return handlerInput.getResponseBuilder().withSpeech(speechText).withSimpleCard(cardTitle,speechText).withReprompt(rePrompt).build();
        }
        else
        {
            String speechText = "I could not find any name to repeat the definition. Please, say a new name to get the definition.";

            String rePrompt = "Please, say the valid name to get the definition for that name.";

            String cardTitle = "Repeat";

            return handlerInput.getResponseBuilder().withSpeech(speechText).withSimpleCard(cardTitle,speechText).withReprompt(rePrompt).build();
        }
    }

    private Optional<Response> sendFailureResult(HandlerInput handlerInput)
    {
        String speechText = "Oops.. there was some internal problem, don't worry. Please, say name again.";

        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
