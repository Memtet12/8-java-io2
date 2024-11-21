package com.example.task01;

import java.io.File;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
public class Task01Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        //здесь вы можете вручную протестировать ваше решение, вызывая реализуемый метод и смотря результат
        // например вот так:

        /*
        System.out.println(extractSoundName(new File("task01/src/main/resources/3727.mp3")));
        */
    }

    public static String extractSoundName(File file) throws IOException, InterruptedException {
        try {
            String command = String.format("ffprobe -v error -of flat -show_format %s", file.getAbsolutePath());
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("format.tags.title")) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        return parts[1].replace("\"", "");
                    }
                }
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException("ffprobe command failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}