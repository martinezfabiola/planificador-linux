import java.io.FileWriter;
import java.io.IOException;
import java.lang.Integer;
import java.util.Random;
import java.lang.InterruptedException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WriteJSONExample {

    public static void main( String[] args ) throws InterruptedException {
        // Argumentos
        int cantidadProcesos = Integer.parseInt(args[0]); // Cantidad de procesos

        JSONArray listaProcesos = new JSONArray(); // Arreglo de procesos

        Integer id = 0; // Contador para id's de procesos
        Random random = new Random();

        int tiempo_llegada = 0;

        for (int i = 0; i < cantidadProcesos; i++) {
            JSONArray cpu = new JSONArray(); // Arreglo de cpu
            JSONArray io = new JSONArray(); // Arreglo de io
            int cantidadCPU = random.nextInt(20) + 1; // Cantidad de CPU
            int cantidadIO = cantidadCPU - 1; // Cantidad de IO

            // Añadir información del proceso a objeto JSON
            JSONObject infoProceso = new JSONObject();
            infoProceso.put("id", id);
            infoProceso.put("tiempoLlegada", tiempo_llegada);
            infoProceso.put("prioridad", random.nextInt(10));

            for (int j = 0; j < cantidadCPU; j++) {
                cpu.add(random.nextInt(100));
            }

            for (int k = 0; k < cantidadIO; k++) {
                io.add(random.nextInt(100));
            }

            infoProceso.put("cpu", cpu);
            infoProceso.put("io", io);

            // Añadir proceso a lista de procesos
            listaProcesos.add(infoProceso);

            id++;
            tiempo_llegada = tiempo_llegada + random.nextInt(101) + 1;
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