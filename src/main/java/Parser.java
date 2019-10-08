import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Parser {
    public static void main(String[] args)
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("procesos.json"))
        {
            //Leer archivo JSON
            Object obj = jsonParser.parse(reader);

            JSONArray listaProcesos = (JSONArray) obj;
            //System.out.println(listaProcesos);

            for (Object x : listaProcesos)
            {
                JSONObject infoProceso = (JSONObject) x;
                System.out.println(infoProceso.get("tiempoLlegada"));
                System.out.println(infoProceso.get("io"));
                System.out.println(infoProceso.get("cpu"));
                System.out.println(infoProceso.get("prioridad"));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
