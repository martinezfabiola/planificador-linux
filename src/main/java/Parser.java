import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            JSONArray listaProcesos = (JSONArray) jsonParser.parse(reader);
            Integer pid = 1;
            for (Object x : listaProcesos)
            {
                JSONObject infoProceso = (JSONObject) x;

                // Transformamos los Numbers en Integer en cada una de las listas
                List<Number> cpuLongs = (List<Number>) infoProceso.get("cpu");
                List<Integer> cpuIntegers = new ArrayList<>();
                for (Number num : cpuLongs) {
                    cpuIntegers.add(num.intValue());
                }

                List<Number> ioNumbers = (List<Number>) infoProceso.get("io");
                List<Integer> ioIntegers = new ArrayList<>();
                for (Number num : ioNumbers) {
                    ioIntegers.add(num.intValue());
                }

                // Instaciamos el nuevo proceso
                Process newProcess = new Process(
                        pid,
                        ((Number) infoProceso.get("prioridad")).intValue(),
                        ((Number) infoProceso.get("tiempoLlegada")).intValue(),
                        cpuIntegers,
                        ioIntegers
                );

                // Imprimimos el orden en que seran ejecutados los procesos
                System.out.println("Proceso con pid: " + pid);
                System.out.print(newProcess.getNextCpuTime());
                while (newProcess.hasAnotherIo()) {
                    System.out.print(", ");
                    System.out.print(newProcess.getNextIoTime());
                    System.out.print(", ");
                    System.out.print(newProcess.getNextCpuTime());
                }
                System.out.println();
                pid = pid + 1;
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
