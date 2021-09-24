package com.deffe.max.Handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.*;
import com.deffe.max.Models.MovieCastAndCrew;
import com.deffe.max.Models.MovieCrew;
import com.deffe.max.Models.MovieResponse;
import com.deffe.max.Models.MovieResults;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

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
            String apiKey = "0581b7383bf18be60b37e4c87d8511f1";

            String movieUrl = movieSlot.getValue();

            String movieName = movieUrl.replaceAll(" ", "+");

            try
            {
                URL url = new URL("https://api.themoviedb.org/3/search/movie?api_key=" + apiKey +"&query=" + movieUrl);

                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                con.setDoOutput(true);

                con.setRequestMethod("GET");

                con.setRequestProperty("Content-Type", "application/json");

                BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

                StringBuilder stringBuilder = new StringBuilder();

                String output;

                while ((output = br.readLine()) != null)
                {
                    stringBuilder.append(output);
                }

                String names = stringBuilder.toString();

                MovieResponse movieResponse = new Gson().fromJson(names,MovieResponse.class);

                if (movieResponse != null)
                {
                    List<MovieResults> movieResultsList = movieResponse.getResults();

                    if (movieResultsList != null && movieResultsList.size() > 0)
                    {
                        Map<Integer, String> getNames = new HashMap<>();

                        for (MovieResults movieResults : movieResultsList)
                        {
                            String language = movieResults.getOriginal_language();

                            if (language.equals("en"))
                            {
                                getNames.put(movieResults.getId(),movieResults.getOriginal_title());
                            }
                        }

                        if (getNames.size() > 1)
                        {
                            StringBuilder builder = new StringBuilder();

                            ArrayList<String> movieTitles = new ArrayList<>(getNames.values());

                            for (int i=0; i<movieTitles.size(); i++)
                            {
                                if (i == movieTitles.size() - 1)
                                {
                                    builder.append(movieTitles.get(i)).append(" \n");
                                }
                                else
                                {
                                    builder.append(movieTitles.get(i)).append(", \n");
                                }
                            }

                            String movieNames = builder.toString();

                            String speechText = "I have found " + String.valueOf(getNames.size()) + " movie results. Please choose exact movie title from below ones. \n" + movieNames;

                            String cardTitle = "Results using " + "\'" + movieName + "\'" + " keyword : ";

                            return handlerInput.getResponseBuilder()
                                    .withSpeech(speechText)
                                    .withSimpleCard(cardTitle,speechText)
                                    .build();
                        }
                        else
                        {
                            Map.Entry<Integer, String> entry = getNames.entrySet().iterator().next();

                            Integer id = entry.getKey();

                            try
                            {
                                URL urlDetail = new URL("https://api.themoviedb.org/3/movie/" + id + "/credits?api_key=" + apiKey);

                                HttpsURLConnection connection = (HttpsURLConnection) urlDetail.openConnection();

                                connection.setDoOutput(true);

                                connection.setRequestMethod("GET");

                                connection.setRequestProperty("Content-Type", "application/json");

                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                                StringBuilder resultBuilder = new StringBuilder();

                                String jsonOutput;

                                while ((jsonOutput = bufferedReader.readLine()) != null)
                                {
                                    resultBuilder.append(jsonOutput);
                                }

                                String details = resultBuilder.toString();

                                MovieCastAndCrew movieCastAndCrew = new Gson().fromJson(details,MovieCastAndCrew.class);

                                if (movieCastAndCrew != null)
                                {
                                    List<MovieCrew> movieCrewList = movieCastAndCrew.getCrew();

                                    if (movieCrewList != null && movieCrewList.size() > 0)
                                    {
                                        StringBuilder castAndCrewBuilder = new StringBuilder();

                                        String movieOriginalName = entry.getValue();

                                        castAndCrewBuilder.append("Movie Title : ").append(movieOriginalName).append(", \n");
                                        castAndCrewBuilder.append("Crew Count : ").append(String.valueOf(movieCrewList.size())).append(", \n");

                                        castAndCrewBuilder.append("Crew Details : ").append("\n");

                                        for (int i=0; i<movieCrewList.size(); i++)
                                        {
                                            if (i == movieCrewList.size() - 1)
                                            {
                                                String gender = "" ;

                                                if (movieCrewList.get(i).getGender() == 1)
                                                {
                                                    gender = "Female";
                                                }
                                                else if (movieCrewList.get(i).getGender() == 2)
                                                {
                                                    gender = "Male";
                                                }

                                                castAndCrewBuilder.append(String.valueOf(movieCrewList.size())).append(" . ")
                                                        .append("Name : ").append(movieCrewList.get(i).getName()).append(", \n")
                                                        .append("Gender : ").append(gender).append(", \n")
                                                        .append("Department : ").append(movieCrewList.get(i).getDepartment()).append(", \n")
                                                        .append("Job : ").append(movieCrewList.get(i).getJob()).append(". \n");
                                            }
                                            else
                                            {
                                                String gender = "" ;

                                                if (movieCrewList.get(i).getGender() == 1)
                                                {
                                                    gender = "Female";
                                                }
                                                else if (movieCrewList.get(i).getGender() == 2)
                                                {
                                                    gender = "Male";
                                                }

                                                castAndCrewBuilder.append(String.valueOf(i + 1)).append(" . ")
                                                        .append("Name : ").append(movieCrewList.get(i).getName()).append(", \n")
                                                        .append("Gender : ").append(gender).append(", \n")
                                                        .append("Department : ").append(movieCrewList.get(i).getDepartment()).append(", \n")
                                                        .append("Job : ").append(movieCrewList.get(i).getJob()).append(". \n");
                                            }
                                        }

                                        String movieCastsAndCrews = castAndCrewBuilder.toString();

                                        String cardTitle = entry.getValue() + " Crew Details : ";

                                        return handlerInput.getResponseBuilder().withSpeech(movieCastsAndCrews).withSimpleCard(cardTitle,movieCastsAndCrews).build();
                                    }
                                    else
                                    {
                                        return sendMovieFailedResponse(movieName,handlerInput);
                                    }
                                }
                                else
                                {
                                    return sendMovieFailedResponse(movieName,handlerInput);
                                }
                            }
                            catch (IOException e)
                            {
                                return sendMovieFailedResponse(movieName,handlerInput);
                            }
                        }
                    }
                    else
                    {
                        return sendMovieFailedResponse(movieName,handlerInput);
                    }
                }
                else
                {
                    return sendMovieFailedResponse(movieName,handlerInput);
                }
            }
            catch (IOException e)
            {
                return sendMovieFailedResponse(movieName,handlerInput);
            }
        }
        else
        {
            return sendFallbackResponse(handlerInput);
        }
    }

    private Optional<Response> sendMovieFailedResponse(String movieName, HandlerInput handlerInput)
    {
        String speechText = "I can't find any movie title results using the keyword \"" + movieName + "\". Please, say the correct movie title or try another title.";

        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }

    private Optional<Response> sendFallbackResponse(HandlerInput handlerInput)
    {
        String speechText = "There was some internal connection or server problem, don't worry. You say that the name again.";

        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
