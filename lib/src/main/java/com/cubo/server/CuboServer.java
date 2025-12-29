package com.cubo.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cubo.response.CuboResponse;
import com.cubo.utils.CuboHttpMethods;
import com.cubo.utils.CuboJson;
import com.cubo.utils.CuboPathMethods;

public class CuboServer {

    private HashMap<CuboHttpMethods, List<CuboPathMethods>> routes;

    public CuboServer(){
        this.routes = new HashMap<>();
        for (CuboHttpMethods m : CuboHttpMethods.values()){
            this.routes.put(m, new ArrayList<>());
        }
    }

    public void start(int port) {
        try (ServerSocket sc = new ServerSocket(port)){
            System.out.println("Servidor iniciado en puerto: " + port);
            while (true) {
                Socket client = sc.accept();
                handleClient(client);  
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private void handleClient(Socket client){
        try (
            BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream())
            );
            PrintWriter out = new PrintWriter(client.getOutputStream())
        ) {
    
            // 1️⃣ Leer request line
            String requestLine = in.readLine();
            if (requestLine == null) return;
    
            String[] parts = requestLine.split(" ");
            String method = parts[0];
            String path   = parts[1];
    
            // 2️⃣ Consumir headers
            String header;
            while ((header = in.readLine()) != null && !header.isEmpty()) {}
    
            // 3️⃣ Buscar ruta
            List<CuboPathMethods> list = routes.get(CuboHttpMethods.valueOf(method));
    
            for (CuboPathMethods pm : list) {
                if (pm.path().equals(path)) {
    
                    CuboResponse result = pm.func().func(new CuboResponse(), " ");
                    String body = CuboJson.toJson(result.getBody());
    
                    out.println("HTTP/1.1 200 OK");
                    out.println("Content-Type: application/json; charset=UTF-8");
                    out.println("Content-Length: " + body.length());
                    out.println();
                    out.println(body);
                    out.flush();
                    return;
                }
            }
    
            // 4️⃣ 404 obligatorio
            out.println("HTTP/1.1 404 Not Found");
            out.println("Content-Length: 0");
            out.println();
            out.flush();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setRoutes(HashMap<CuboHttpMethods, List<CuboPathMethods>> routes){
        for (CuboHttpMethods m : CuboHttpMethods.values()){
            this.routes.get(m).addAll(routes.get(m));
        }
    }

}
