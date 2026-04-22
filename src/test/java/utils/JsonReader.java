package utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    public static List<String> getSearchTerms(String filePath) {
        List<String> terms = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));

            JSONArray jsonArray = (JSONArray) jsonObject.get("searchTerms");

            for (Object obj : jsonArray) {
                terms.add(obj.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return terms;
    }
}
