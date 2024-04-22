package com.banco.scripts;

import com.banco.models.CuentaAhorro;
import com.banco.models.CuentaCorriente;
import com.banco.models.base.CuentaBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWriteJson {
    public static ObjectMapper mapper = new ObjectMapper();

    public static List<CuentaBase> readJson(){
        String filePath = "cuentas.json";
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                ObjectNode rootNode = mapper.createObjectNode();
                Files.createFile(Paths.get(filePath));
                System.out.println("Archivo creado: " + filePath);

                // Inicializar los arrays vacíos dentro del JSON.
                rootNode.putArray("CuentaCorriente");
                rootNode.putArray("CuentaAhorro");

                // Escribir el objeto JSON inicial con los arrays vacíos.
                try (FileWriter writer = new FileWriter(filePath)) {
                    mapper.writeValue(writer, rootNode);
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            List<CuentaBase> cuentas = new ArrayList<>();
            JsonNode rootNode = mapper.readTree(new File(filePath));
            JsonNode cuentasCorrienteNode = rootNode.path("CuentaCorriente");
            JsonNode cuentasAhorrosNode = rootNode.path("CuentaAhorro");

            if (cuentasCorrienteNode.isArray()) {
                for (JsonNode node : cuentasCorrienteNode) {
                    cuentas.add(mapper.treeToValue(node, CuentaCorriente.class));
                }
            }

            if (cuentasAhorrosNode.isArray()) {
                for (JsonNode node : cuentasAhorrosNode) {
                    cuentas.add(mapper.treeToValue(node, CuentaAhorro.class));
                }
            }

            return cuentas;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeJson(CuentaBase cuentaBase) {
        try {
            String filePath = "cuentas.json";
            File file = new File(filePath);
            ObjectNode rootNode = mapper.createObjectNode();
            if (!file.exists()) {
                try {
                    Files.createFile(Paths.get(filePath));
                    System.out.println("Archivo creado: " + filePath);

                    // Inicializar los arrays vacíos dentro del JSON.
                    rootNode.putArray("CuentaCorriente");
                    rootNode.putArray("CuentaAhorro");

                    // Escribir el objeto JSON inicial con los arrays vacíos.
                    try (FileWriter writer = new FileWriter(filePath)) {
                        mapper.writeValue(writer, rootNode);
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            JsonNode cuentasNode = mapper.readTree(file);

            if (cuentaBase instanceof CuentaCorriente) {
                ((ArrayNode) cuentasNode.path("CuentaCorriente")).addPOJO(cuentaBase);
            } else if (cuentaBase instanceof CuentaAhorro) {
                ((ArrayNode) cuentasNode.path("CuentaAhorro")).addPOJO(cuentaBase);
            }

            rootNode = (ObjectNode) cuentasNode;
            try (FileWriter writer = new FileWriter(filePath)) {
                mapper.writeValue(writer, rootNode);
                writer.close();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void editJson(Integer id, String column, Object dato) {
        try {
            String filePath = "cuentas.json";
            File file = new File(filePath);
            JsonNode rootNode = mapper.readTree(file);


            String idStr = id.toString();

            ArrayNode cuentasCorrienteNode = (ArrayNode) rootNode.path("CuentaCorriente");
            ArrayNode cuentasAhorrosNode = (ArrayNode) rootNode.path("CuentaAhorro");

            for (JsonNode node : cuentasCorrienteNode) {
                if (idStr.equals(node.path("id").asText())) {
                    if (dato instanceof Double) {
                        ((ObjectNode) node).put(column, (Double) dato);
                    } else if (dato instanceof String) {
                        ((ObjectNode) node).put(column, (String) dato);
                    }else if (dato instanceof Boolean){
                        ((ObjectNode) node).put(column, (Boolean) dato);
                    }
                }
            }

            for (JsonNode node : cuentasAhorrosNode) {
                if (idStr.equals(node.path("id").asText())) {
                    if (dato instanceof Double) {
                        ((ObjectNode) node).put(column, (Double) dato);
                    } else if (dato instanceof String) {
                        ((ObjectNode) node).put(column, (String) dato);
                    } else if (dato instanceof Boolean) {
                        ((ObjectNode) node).put(column, (Boolean) dato);
                    }
                }
            }


            try (FileWriter writer = new FileWriter(filePath)) {
                mapper.writeValue(writer, rootNode);
                writer.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}






