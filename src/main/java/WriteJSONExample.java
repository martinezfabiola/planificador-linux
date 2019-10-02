import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;
import java.lang.IllegalArgumentException;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJSONExample {

    public static void main( String[] args ) throws InterruptedException
    {
        // Argumentos
        int cantidadProcesos = Integer.parseInt(args[0]); // Cantidad de procesos
        int velocidad = Integer.parseInt(args[1]); // Velocidad en segundos (ej. 10 min = 60 segundos)
        int cantidadCPU = Integer.parseInt(args[2]); // Cantidad de CPU
        int cantidadIO = Integer.parseInt(args[3]); // Cantidad de IO


        JSONArray cpu = new JSONArray(); // Arreglo de cpu
        JSONArray io = new JSONArray(); // Arreglo de io
        JSONArray listaProcesos = new JSONArray(); // Arreglo de procesos

        Integer id = 0; // Contador para id's de procesos
        Random random = new Random();

        // Verificar que IO tenga uno menos que CPU
        if (cantidadIO != cantidadCPU - 1){
            throw new IllegalArgumentException("IO debe ser igual a CPU - 1");
        }

        for (int i = 0; i < cantidadProcesos; i++) {

            // Cronometro
            Calendar cronometro = Calendar.getInstance();
            Integer hora = cronometro.get(Calendar.HOUR_OF_DAY); // hora
            Integer minutos = cronometro.get(Calendar.MINUTE); // minutos
            Integer segungos = cronometro.get(Calendar.SECOND); // segundos

            // Añadir información del proceso a objeto JSON
            JSONObject infoProceso = new JSONObject();
            infoProceso.put("id", id);
            infoProceso.put("tiempoLlegada", hora + ":" + minutos + ":" + segungos);
            infoProceso.put("prioridad", random.nextInt(10));

            for (int j = 0; j < cantidadCPU; j++) {
                cpu.add(random.nextInt(40));
            }

            for (int k = 0; k < cantidadIO; k++) {
                io.add(random.nextInt(40));
            }

            infoProceso.put("cpu", cpu.toString());
            infoProceso.put("io", io.toString());

            // Añadir proceso a lista de procesos
            listaProcesos.add(infoProceso);

            id++;
            Thread.sleep(1000);
            cronometro.add(Calendar.SECOND, velocidad);
        }

        try (FileWriter file = new FileWriter("procesos.json")) {
            // Escribir archivo
            file.write(listaProcesos.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}