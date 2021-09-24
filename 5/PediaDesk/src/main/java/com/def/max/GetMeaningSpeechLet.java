package main.java.com.def.max;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.SimpleCard;
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

public class GetMeaningSpeechLet implements Speechlet
{

    @Override
    public void onSessionStarted(SessionStartedRequest request, Session session) throws SpeechletException
    {

    }

    @Override
    public SpeechletResponse onLaunch(LaunchRequest request, Session session) throws SpeechletException
    {
        return getWelcomeResponse();
    }

    @Override
    public SpeechletResponse onIntent(IntentRequest request, Session session) throws SpeechletException
    {
        Intent intent = request.getIntent();

        String intentName = (intent != null) ? intent.getName() : "";

        if (intentName != null && intentName.equals("GetMeaningNameIntent"))
        {
            String meaningName = intent.getSlot("name").getValue();

            if (meaningName != null && !meaningName.equals(""))
            {
                return getMeaningForName(session,meaningName);
            }
            else
            {
                return getFallbackResponse();
            }
        }
        else if (intentName != null && intentName.equals("AMAZON.FallbackIntent"))
        {
            return getFallbackResponse();
        }
        else if (intentName != null && intentName.equals("AMAZON.CancelIntent"))
        {
            return getStopOrCancelResponse();
        }
        else if (intentName != null && intentName.equals("AMAZON.StopIntent"))
        {
            return getStopOrCancelResponse();
        }
        else if (intentName != null && intentName.equals("AMAZON.YesIntent"))
        {
            return getYesResponse();
        }
        else if (intentName != null && intentName.equals("AMAZON.NoIntent"))
        {
            return getNoResponse();
        }
        else if (intentName != null && intentName.equals("AMAZON.RepeatIntent"))
        {
            String name = getStoredSessionName(session);

            if (name.equals(""))
            {
                return getRepeatFallbackResponse();
            }
            else
            {
                return getMeaningForName(session,name);
            }
        }
        else if (intentName != null && intentName.equals("AMAZON.HelpIntent"))
        {
            return getHelpResponse();
        }
        else
        {
            return getFallbackResponse();
        }
    }

    @Override
    public void onSessionEnded(SessionEndedRequest request, Session session) throws SpeechletException
    {

    }

    private SpeechletResponse getMeaningForName(Session session, String meaningName)
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
                    return setSuccessResultForName(session,meaningName,meaning);
                }
                else
                {
                    return setFailureResultForName(session,meaningName);
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
                            return setSuccessResultForName(session,meaningName,meaning);
                        }
                        else
                        {
                            return setFailureResultForName(session,meaningName);
                        }
                    }
                    else
                    {
                        return setFailureResultForName(session,meaningName);
                    }
                }
                else
                {
                    return setFailureResultForName(session,meaningName);
                }
            }
        }
        catch (IOException e)
        {
            return setFailureResultForName(session,meaningName);
        }
    }

    private String getStoredSessionName(Session session)
    {
        String name = (String) session.getAttribute("name");

        if (name != null)
        {
            return name;
        }
        else
        {
            return "";
        }
    }

    private SpeechletResponse setSuccessResultForName(Session session,String meaningName, String meaning)
    {
        String cardTitle = "Definition for " + meaningName;

        if (meaningName != null)
        {
            session.setAttribute("name",meaningName);

            String speechText = "Definition of " + meaningName + " : " + meaning + " Would you like to get the definition for another name. Ask to alexa, definition for \"some word\".";

            String rePromptText = "Would you like to get the definition for another name. Ask to alexa, definition for \"some word\".";

            return getSpeechLetResponse(cardTitle,speechText,rePromptText,true);
        }
        else
        {
            return getRepeatFallbackResponse();
        }
    }

    private SpeechletResponse setFailureResultForName(Session session,String meaningName)
    {
        if (meaningName != null)
        {
            session.setAttribute("name",meaningName);

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

            String speechText = "I could not find the definition for " + meaningName + ", Please go through the below best websites.\n" + listString + " Please, try another name to get the definition.";

            String rePrompt = "Please, try another name to get the definition.";

            return getSpeechLetResponse(cardTitle,speechText,rePrompt,true);
        }
        else
        {
            return getRepeatFallbackResponse();
        }
    }

    private SpeechletResponse getRepeatFallbackResponse()
    {
        String speechText = "I could not find any name to repeat the definition. Please, say a new name to get the definition.";

        String rePrompt = "Please, say a new name to get the definition.";

        String cardTitle = "Repeat";

        return getSpeechLetResponse(cardTitle,speechText,rePrompt,true);
    }

    private SpeechletResponse getHelpResponse()
    {
        String speechText = "Hey friend. I am glad to help you. If you have any doubts about pedia desk, don't worry. I am here to help to you. Now simply you can say or ask \"Alexa, open pedia desk and say definition for some word\".";

        String rePrompt = "Now simply you can say or ask \"Alexa, open pedia desk and say definition for some word\".";

        String cardTitle = "Help!";

        return getSpeechLetResponse(cardTitle,speechText,rePrompt,true);
    }

    private SpeechletResponse getFallbackResponse()
    {
        String speechText = "Oops..! there was some server or internal problem, don't worry. Please, try again or say another name.";

        String cardTitle = "Fallback";

        String rePrompt = "Please, try again or say another name.";

        return getSpeechLetResponse(cardTitle,speechText,rePrompt,true);
    }

    private SpeechletResponse getStopOrCancelResponse()
    {
        String speechText = "Would you like to cancel or stop all the conversations?";

        String cardTitle = "Stop";

        return getSpeechLetResponse(cardTitle,speechText,speechText,true);
    }

    private SpeechletResponse getYesResponse()
    {
        String speechText = "Ok, i stopped the all conversations. If you like to speak to me again. Simply you can ask or say \"Alexa, open pedia desk and say definition for any word\".";

        String cardTitle = "GoodBye!";

        return getSpeechLetResponse(cardTitle,speechText,"",false);
    }

    private SpeechletResponse getNoResponse()
    {
        String speechText = "Ok, don't worry. We can continue the conversation. Please, tell me some name to get the definition.";

        String rePrompt = "Please, tell me some name to get the definition.";

        String cardTitle = "No";

        return getSpeechLetResponse(cardTitle,speechText,rePrompt,true);
    }

    private SpeechletResponse getWelcomeResponse()
    {
        String speechText = "Welcome to the Alexa PediaDesk. It's a pleasure to talk to you. You can say or ask, a definition for any word!. I can give the definition of that word. Ok, now you can say or ask, definition of some word.";

        String rePromptText = "Ok, now you can say or ask, definition of some word.";

        String cardTitle = "Welcome";

        return getSpeechLetResponse(cardTitle,speechText,rePromptText,true);
    }

    private SpeechletResponse getSpeechLetResponse(String cardTitle,String speechText, String repromptText, boolean isAskResponse)
    {
        SimpleCard card = new SimpleCard();
        card.setTitle(cardTitle);
        card.setContent(speechText);

        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        if (isAskResponse)
        {
            PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
            repromptSpeech.setText(repromptText);
            Reprompt reprompt = new Reprompt();
            reprompt.setOutputSpeech(repromptSpeech);

            return SpeechletResponse.newAskResponse(speech, reprompt, card);

        }
        else
        {
            return SpeechletResponse.newTellResponse(speech, card);
        }
    }
}